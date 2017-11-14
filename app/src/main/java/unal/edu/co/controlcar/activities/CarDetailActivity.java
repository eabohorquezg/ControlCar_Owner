package unal.edu.co.controlcar.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import unal.edu.co.controlcar.R;
import unal.edu.co.controlcar.models.Car;
import unal.edu.co.controlcar.models.Travel;

/**
 * Created by kcastrop on 14/11/17.
 */

public class CarDetailActivity extends AppCompatActivity {

    private FirebaseDatabase database;
    List<Travel> travels;
    private String car_plate;
    private TextView plate;
    private Car car;
    private ListView listViewTravels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailcar);
        Bundle bundle = getIntent().getExtras();
        car_plate = bundle.getString("car_plate");
        database = FirebaseDatabase.getInstance();

        plate = (TextView)findViewById(R.id.plate);
        listViewTravels = (ListView) findViewById(R.id.list_travels);

        loadCarDetail();

    }

    public void loadCarDetail(){
        car = new Car();

        DatabaseReference mycar = database.getReference("Cars/" + car_plate);
        mycar.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                car = dataSnapshot.getValue(Car.class);
                plate.setText(car.getPlate());
                Log.d("CarDetail", car.getPlate());
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w("Error Firebase DB", "Failed to read value.", error.toException());
            }
        });


        //Travel travel;
        DatabaseReference mytravel = database.getReference("Travels/");
        mytravel.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                travels = new ArrayList<Travel>();
                for (DataSnapshot travelSnapshot : dataSnapshot.getChildren()){
                    Travel travel = travelSnapshot.getValue(Travel.class);
                    if( travel.getPlate().equalsIgnoreCase(car.getPlate()) ){
                        Toast.makeText(getApplicationContext(), travel.getPlate(), Toast.LENGTH_LONG).show();
                        travels.add(travel);
                    }
                }
                ArrayAdapter<Travel> adapter = new ArrayAdapter<Travel>(CarDetailActivity.this, android.R.layout.simple_list_item_1,travels);
                listViewTravels.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w("Error Firebase DB", "Failed to read value.", error.toException());
            }
        });


    }

}
