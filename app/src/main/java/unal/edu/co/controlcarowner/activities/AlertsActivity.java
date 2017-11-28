package unal.edu.co.controlcarowner.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

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
    private TextView driver;
    private TextView start;
    private TextView end;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alerts);

        //setTitle("Alertas");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Alertas");
        }

        database = FirebaseDatabase.getInstance();
        listViewAlerts = (ListView) findViewById(R.id.list_alerts);
        driver = (TextView) findViewById(R.id.txtDriver);
        start = (TextView) findViewById(R.id.txtInitTravel);
        end = (TextView) findViewById(R.id.txtFinishTravel);

        driver.setText(getIntent().getExtras().getString("driver"));
        start.setText(getIntent().getExtras().getString("inicio"));
        end.setText(getIntent().getExtras().getString("fin"));

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
                    //Toast.makeText(getApplicationContext(), alert.getDescription(), Toast.LENGTH_SHORT).show();
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
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.location_menu, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.location:
                //Send location on request
                //database.getReference().child("Travels").child(getIntent().getExtras().getString("key")).child("requestLocation").setValue(1 + (int)(Math.random() * 1000000) );
                Intent intent = new Intent(getApplicationContext(), CurrentLocationActivity.class);
                intent.putExtra("key", getIntent().getExtras().getString("key"));
                startActivity(intent);
                return true;
        }
        return false;

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}