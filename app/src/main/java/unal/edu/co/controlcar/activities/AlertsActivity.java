package unal.edu.co.controlcar.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import unal.edu.co.controlcar.R;
import unal.edu.co.controlcar.models.Alert;
import unal.edu.co.controlcar.models.Travel;

/**
 * Created by Edwin on 14/11/17.
 */

public class AlertsActivity extends AppCompatActivity {

    private FirebaseDatabase database;
    private ListView listViewAlerts;
    List<Alert> alerts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alerts);
        setTitle("Alertas");

        database = FirebaseDatabase.getInstance();
        listViewAlerts = (ListView) findViewById(R.id.list_alerts);

        loadAlerts();

    }

    public void loadAlerts(){
        DatabaseReference myAlert = database.getReference().child("Travels").child(getIntent().getExtras().getString("key")).child("Alerts");
        myAlert.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                alerts = new ArrayList<Alert>();
                for (DataSnapshot alertSnapshot : dataSnapshot.getChildren()){
                    Alert alert = alertSnapshot.getValue(Alert.class);
                    alerts.add(alert);
                }
                ArrayAdapter<Alert> adapter = new ArrayAdapter<Alert>(AlertsActivity.this, R.layout.custom_list_alerts, R.id.list_name, alerts);
                listViewAlerts.setAdapter(adapter);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w("Error Firebase DB", "Failed to read value.", error.toException());
            }
        });

    }

}
