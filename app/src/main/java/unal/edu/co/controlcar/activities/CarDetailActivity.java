package unal.edu.co.controlcar.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import unal.edu.co.controlcar.R;
import unal.edu.co.controlcar.models.Car;

/**
 * Created by kcastrop on 14/11/17.
 */

public class CarDetailActivity extends AppCompatActivity {

    private FirebaseDatabase database;

    private String car_plate;
    private TextView plate;
    private Car car;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailcar);
        Bundle bundle = getIntent().getExtras();
        car_plate = bundle.getString("car_plate");
        database = FirebaseDatabase.getInstance();

        plate = (TextView)findViewById(R.id.plate);

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

    }

}
