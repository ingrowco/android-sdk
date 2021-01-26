package co.ingrow.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import java.util.HashMap;

import co.ingrow.android.InGrowLogging;
import co.ingrow.android.action.InGrowClient;
import co.ingrow.android.action.InGrowProject;
import co.ingrow.android.action.InGrowSession;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        InGrowClient.initialize(new InGrowClient.Builder(new InGrowProject("3d9e0e466f469bb05c35a80c179a5264e074706a", "29", "AndroidTest", true, "4692836429", null), getApplication()).build());

        findViewById(R.id.sendEventButton).setOnClickListener(v -> {
            HashMap events = new HashMap<>();
            try {
                events.put("Name", "Nader");
                events.put("Position", "Developer");
                events.put("isActive", true);
                events.put("numberOfCommits", 12);
            } catch (Exception e){
                e.printStackTrace();
            }

            InGrowClient.client().logEvents(events);
            InGrowClient.client().setDebugMode(true);
            InGrowClient.enrichmentBySession(new InGrowSession(""));
        });

    }
}