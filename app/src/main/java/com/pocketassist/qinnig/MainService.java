package com.pocketassist.qinnig;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.os.Vibrator;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

public class MainService extends Service implements SensorEventListener {
    private SensorManager mManager;
//    private Sensor sensorProximity;
    private Vibrator vibrator;
    private long[] pattern = {1000, 2000, 1000, 3000};

    public MainService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();

        mManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
//        sensorProximity = mManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
//        mManager.registerListener(this, sensorProximity, SensorManager.SENSOR_DELAY_NORMAL);

        TelephonyManager mTelephonyMgr = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        mTelephonyMgr.listen(new TeleListener(this,mManager,vibrator), PhoneStateListener.LISTEN_CALL_STATE);
    }

    @Override
    public void onDestroy(){
        mManager.unregisterListener(this);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float[] vals = event.values;
//        if (vals != null && event.sensor.getType() == Sensor.TYPE_PROXIMITY) {
//            exchangeDat.proximityV = vals[0];
//            if(exchangeDat.isRinging==true&&vals[0]==1.0){
//                vibrator.cancel();
//                mManager.unregisterListener(this);
//            }
//        }
        if(vals[0]==0.0) {
            vibrator.vibrate(pattern, 1);
        }else {
            vibrator.cancel();
            mManager.unregisterListener(this);
        }
    }
}

class TeleListener extends PhoneStateListener {
    private MainService ser;
    private SensorManager manager;
    private Vibrator vibrator;
    private Sensor sensorProximity;
//    private long[] pattern = {1000, 2000, 1000, 3000};

    public TeleListener(MainService mSer,SensorManager mgr,Vibrator vib){
        ser = mSer;
        manager = mgr;
        vibrator = vib;
        sensorProximity = manager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
    }

    @Override
    public void onCallStateChanged(int state,String incomingNumber) {
        super.onCallStateChanged(state, incomingNumber);
        switch (state) {
            case TelephonyManager.CALL_STATE_IDLE:
                vibrator.cancel();
                manager.unregisterListener(ser);
                break;
            case TelephonyManager.CALL_STATE_OFFHOOK:
                vibrator.cancel();
                manager.unregisterListener(ser);
                break;
            case TelephonyManager.CALL_STATE_RINGING:
                manager.registerListener(ser, sensorProximity, SensorManager.SENSOR_DELAY_FASTEST);
//                try{Thread.sleep(500);}catch(Exception e){};
//                exchangeDat.isRinging = true;
//                if(exchangeDat.proximityV==0.0) {
//                    vibrator.vibrate(pattern, 1);
//                }
                break;
            default:
                break;
        }
    }
}

//class exchangeDat {
//    static public float proximityV = 1;
//    static public boolean isRinging = false;
//}