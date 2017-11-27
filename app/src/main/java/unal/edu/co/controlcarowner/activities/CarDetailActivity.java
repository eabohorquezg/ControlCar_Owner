package unal.edu.co.controlcarowner.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import unal.edu.co.controlcarowner.R;
import unal.edu.co.controlcarowner.models.Car;
import unal.edu.co.controlcarowner.models.Travel;

/**
 * Created by kcastrop on 14/11/17.
 */

public class CarDetailActivity extends AppCompatActivity {

    private FirebaseDatabase database;
    private List<Travel> travels;
    private String car_plate;
    private Car car;
    private ListView listViewTravels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_detail);
        Bundle bundle = getIntent().getExtras();
        car_plate = bundle.getString("car_plate");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Viajes de " + car_plate);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        setTitle("Viajes de: " + car_plate);

        database = FirebaseDatabase.getInstance();

        listViewTravels = (ListView) findViewById(R.id.list_travels);

        loadCarDetail();

        listViewTravels.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), AlertsActivity.class);
                intent.putExtra("driver", travels.get(i).getDriverName());
                intent.putExtra("inicio", travels.get(i).getInitHour());
                intent.putExtra("fin", travels.get(i).getEndTime());
                intent.putExtra("key", travels.get(i).getId());
                startActivity(intent);
            }
        });
    }

    public void loadCarDetail() {
        car = new Car();

        DatabaseReference mycar = database.getReference("Cars/" + car_plate);
        mycar.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                car = dataSnapshot.getValue(Car.class);
                Log.d("Deatil-------------->",car.getPlate());
                //DatabaseReference mytravel = database.getReference("Travels/");
                DatabaseReference mytravel = database.getReference();
                Query queryTravels = mytravel.child("Travels").orderByChild("plate").equalTo(car.getPlate());
                queryTravels.addValueEventListener(new ValueEventListener() {
                //mytravel.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        travels = new ArrayList<>();
                        for (DataSnapshot travelSnapshot : dataSnapshot.getChildren()) {
                            Travel travel = travelSnapshot.getValue(Travel.class);
                            if (travel.getPlate().equalsIgnoreCase(car.getPlate())) {
                                travels.add(travel);
                            }
                        }

                        ArrayAdapter<Travel> adapter = new ArrayAdapter<>(CarDetailActivity.this, R.layout.custom_list_travels, R.id.list_name, travels);
                        listViewTravels.setAdapter(adapter);
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        Log.w("Error Firebase DB", "Failed to read value.", error.toException());
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w("Error Firebase DB", "Failed to read value.", error.toException());
            }
        });


        //Travel travel;

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
