package unal.edu.co.controlcarowner.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import unal.edu.co.controlcarowner.R;
import unal.edu.co.controlcarowner.models.Alert;

/**
 * Created by Edwin on 14/11/17.
 */

public class AlertsActivity extends AppCompatActivity {

    private FirebaseDatabase database;
    private ListView listViewAlerts;
    private List<Alert> alerts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alerts);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Alertas");
        }

        database = FirebaseDatabase.getInstance();
        listViewAlerts = (ListView) findViewById(R.id.list_alerts);

        loadAlerts();
    }

    public void loadAlerts(){
        DatabaseReference myAlert = database.getReference().child("Travels").child(getIntent().getExtras().getString("key")).child("Alerts");
        myAlert.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                alerts = new ArrayList<>();
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

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}