package unal.edu.co.controlcarowner.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import unal.edu.co.controlcarowner.R;

/**
 * Created by kevincastro on 11/26/17.
 */

public class CurrentLocationActivity extends AppCompatActivity{

    private FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currentlocation);

        database = FirebaseDatabase.getInstance();

        viewCurLocation();
    }

    public void viewCurLocation(){
        DatabaseReference ref = database.getReference("Travels/" + getIntent().getExtras().getString("key")  + "/curLocation" );
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String[] location = dataSnapshot.getValue(String.class).split(",");
                Log.d("latitud", location[0]);
                Log.d("longitud", location[1]);
                //Pintar el mapa aqui
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }

}
