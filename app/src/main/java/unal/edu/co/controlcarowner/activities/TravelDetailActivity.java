package unal.edu.co.controlcarowner.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import unal.edu.co.controlcarowner.R;

public class TravelDetailActivity extends AppCompatActivity {

    private String car_plate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel_detail);
        Bundle bundle = getIntent().getExtras();
        car_plate = bundle.getString("car_plate");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Viaje de " + car_plate);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }
}
