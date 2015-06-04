package com.pocketassist.qinnig;

import java.util.ArrayList;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import android.app.ActivityManager;
import android.content.Context;
//import android.view.Menu;
//import android.view.MenuItem;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {
    private Intent mainSer;
    private View btStart;
    private View btStop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainSer = new Intent(this, MainService.class);

        btStart = this.findViewById(R.id.button);
        btStart.setOnClickListener(this);

        btStop = this.findViewById(R.id.button2);
        btStop.setOnClickListener(this);

        if(isSerWorked("com.pocketassist.qinnig.MainService")) {
            btStart.setEnabled(false);
            btStop.setEnabled(true);
        }else {
            btStart.setEnabled(true);
            btStop.setEnabled(false);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button:
                startService(mainSer);
                btStart.setEnabled(false);
                btStop.setEnabled(true);
                Toast.makeText(MainActivity.this,"服务已启动",Toast.LENGTH_SHORT).show();
                break;
            case R.id.button2:
                stopService(mainSer);
                btStart.setEnabled(true);
                btStop.setEnabled(false);
                Toast.makeText(MainActivity.this,"服务已停止",Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private boolean isSerWorked(String Ser) {
        ActivityManager myManager=(ActivityManager)this.getSystemService(Context.ACTIVITY_SERVICE);
        ArrayList<ActivityManager.RunningServiceInfo> runningService = (ArrayList<ActivityManager.RunningServiceInfo>) myManager.getRunningServices(30);
        for(int i = 0 ; i<runningService.size();i++) {
            if((runningService.get(i).service.getClassName().toString()).equals(Ser)) {
                return true;
            }
        }
        return false;
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
}
