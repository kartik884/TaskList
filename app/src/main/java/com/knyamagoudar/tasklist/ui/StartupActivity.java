package com.knyamagoudar.tasklist.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.knyamagoudar.tasklist.R;

import java.util.Timer;
import java.util.TimerTask;

public class StartupActivity extends Activity {

    private static final long DELAY = 3000;
    private boolean scheduled = false;
    private Timer startupTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup);


        startupTimer = new Timer();
        startupTimer.schedule(new TimerTask()
        {
            @Override
            public void run()
            {
                StartupActivity.this.finish();
                startActivity(new Intent(StartupActivity.this, MainActivity.class));
            }
        }, DELAY);
        scheduled = true;
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        if (scheduled)
            startupTimer.cancel();
        startupTimer.purge();
    }
}
