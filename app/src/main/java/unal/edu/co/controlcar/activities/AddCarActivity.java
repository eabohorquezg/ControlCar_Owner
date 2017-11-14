package unal.edu.co.controlcar.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import unal.edu.co.controlcar.R;
import unal.edu.co.controlcar.models.Car;

/**
 * Created by kcastrop on 14/11/17.
 */

public class AddCarActivity  extends AppCompatActivity {

    Car newCar;

    private EditText plate;
    private EditText model;
    private EditText brand;
    private Button create;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addcar);

        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference();
        newCar = new Car();

        plate = (EditText)findViewById(R.id.plate);
        model = (EditText)findViewById(R.id.model);
        brand = (EditText)findViewById(R.id.brand);
        create = (Button)findViewById(R.id.btn_create);

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                newCar.setPlate(plate.getText().toString().toUpperCase());
                newCar.setModel(model.getText().toString());
                newCar.setBrand(brand.getText().toString());
                newCar.setOwnerEmail(firebaseAuth.getCurrentUser().getEmail());

                //TODO DO
                //Validar que el carro no exista

                database.child("Cars").child(newCar.getPlate()).setValue(newCar);
                finish();

                Intent i = new Intent(AddCarActivity.this,MyCarsActivity.class);
                startActivity(i);
            }
        });

    }


}
