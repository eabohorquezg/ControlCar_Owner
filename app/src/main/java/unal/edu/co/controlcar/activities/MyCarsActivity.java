package unal.edu.co.controlcar.activities;

/**
 * Created by Edwin on 11/10/2017.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import unal.edu.co.controlcar.R;
import unal.edu.co.controlcar.models.Car;

public class MyCarsActivity extends AppCompatActivity{

    private GoogleApiClient mGoogleApiClient;

    private DatabaseReference database;
    private FirebaseAuth firebaseAuth;

    private ListView listViewCars;
    List<Car> cars;
    private FloatingActionButton fabCreate;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mycars);
        setTitle("Control Car Owner");

        firebaseAuth = FirebaseAuth.getInstance();
        fabCreate = (FloatingActionButton) findViewById(R.id.fab_create);
        fabCreate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), AddCarActivity.class);
                startActivity(intent);
            }
        });

        listViewCars = (ListView) findViewById(R.id.list_cars);
        database = FirebaseDatabase.getInstance().getReference();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                    .build();
        }

        mGoogleApiClient.connect();

        loadCars();

        listViewCars.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String plate = cars.get(i).getPlate();
                Intent intent = new Intent(getApplicationContext(),CarDetailActivity.class);
                intent.putExtra("car_plate", plate);
                startActivity(intent);
            }
        });

    }

    private void loadCars(){
        Query queryCars = database.child("Cars").orderByChild("ownerEmail").equalTo(firebaseAuth.getCurrentUser().getEmail());
        queryCars.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    cars = new ArrayList<Car>();
                    for(DataSnapshot carsSnapshot: dataSnapshot.getChildren()){
                        Car car = carsSnapshot.getValue(Car.class);
                        cars.add(car);
                    }

                    Log.d("NumeroCarros", Integer.toString(cars.size()));
                    ArrayAdapter<Car> adapter = new ArrayAdapter<Car>(MyCarsActivity.this, R.layout.custom_list, R.id.list_name, cars);
                    listViewCars.setAdapter(adapter);
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                logout();
                return true;
        }
        return false;

    }

    public void logout(){
        FirebaseAuth.getInstance().signOut();
        Auth.GoogleSignInApi.signOut(mGoogleApiClient);
        finish();
        startActivity(new Intent(MyCarsActivity.this, LoginActivity.class));
    }
}