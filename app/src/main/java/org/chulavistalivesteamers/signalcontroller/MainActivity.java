package org.chulavistalivesteamers.signalcontroller;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;


public class MainActivity extends ActionBarActivity {
    private static final String TAG = "SignalController";
    public BluetoothAdapter mBTAdapter = null;
    public BluetoothSocket mBTSocket = null;
    public BluetoothDevice mBTDevice = null;
    public InputStream mBTInputStream  = null;
    public OutputStream mBTOutputStream = null;

    static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    String address;
    boolean clockwise;
    public static boolean enableDataSend;
    public SharedPreferences preferences;

    Button Sensor1;
    Button Sensor2;
    Button Sensor3;
    Button Sensor4;
    Button Sensor5;
    Button Sensor6;
    TextView Signal1;
    TextView Signal2;
    TextView Signal3;
    TextView Signal4;
    TextView Signal5;
    TextView Signal6;
    TextView connectionStatus;
    byte signalred = 0;
    byte signalyellow = 1;
    byte signalgreen = 2;
    byte signalwhite = 3;
    byte signaloff = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        clockwise = preferences.getBoolean("direction", false);
        mBTAdapter = BluetoothAdapter.getDefaultAdapter();
        int delay = 0;
        int period = 500;
        Timer t = new Timer();
        Sensor1 = (Button) findViewById(R.id.Sensor1);
        Sensor2 = (Button) findViewById(R.id.Sensor2);
        Sensor3 = (Button) findViewById(R.id.Sensor3);
        Sensor4 = (Button) findViewById(R.id.Sensor4);
        Sensor5 = (Button) findViewById(R.id.Sensor5);
        Sensor6 = (Button) findViewById(R.id.Sensor6);
        Signal1 = (TextView) findViewById(R.id.Signal1);
        Signal2 = (TextView) findViewById(R.id.Signal2);
        Signal3 = (TextView) findViewById(R.id.Signal3);
        Signal4 = (TextView) findViewById(R.id.Signal4);
        Signal5 = (TextView) findViewById(R.id.Signal5);
        Signal6 = (TextView) findViewById(R.id.Signal6);
        connectionStatus = (TextView) findViewById(R.id.connectionStatus);
        if(clockwise) {
            Sensor1.setEnabled(false);
            Sensor2.setEnabled(false);
            Sensor3.setEnabled(false);
            Sensor4.setEnabled(true);
            Sensor5.setEnabled(true);
            Sensor6.setEnabled(true);
            Sensor1.setVisibility(View.INVISIBLE);
            Sensor2.setVisibility(View.INVISIBLE);
            Sensor3.setVisibility(View.INVISIBLE);
            Sensor4.setVisibility(View.VISIBLE);
            Sensor5.setVisibility(View.VISIBLE);
            Sensor6.setVisibility(View.VISIBLE);
            Signal1.setVisibility(View.INVISIBLE);
            Signal2.setVisibility(View.INVISIBLE);
            Signal3.setVisibility(View.INVISIBLE);
            Signal4.setVisibility(View.VISIBLE);
            Signal5.setVisibility(View.VISIBLE);
            Signal6.setVisibility(View.VISIBLE);
        }
        else {
            Sensor1.setEnabled(true);
            Sensor2.setEnabled(true);
            Sensor3.setEnabled(true);
            Sensor4.setEnabled(false);
            Sensor5.setEnabled(false);
            Sensor6.setEnabled(false);
            Sensor1.setVisibility(View.VISIBLE);
            Sensor2.setVisibility(View.VISIBLE);
            Sensor3.setVisibility(View.VISIBLE);
            Sensor4.setVisibility(View.INVISIBLE);
            Sensor5.setVisibility(View.INVISIBLE);
            Sensor6.setVisibility(View.INVISIBLE);
            Signal1.setVisibility(View.VISIBLE);
            Signal2.setVisibility(View.VISIBLE);
            Signal3.setVisibility(View.VISIBLE);
            Signal4.setVisibility(View.INVISIBLE);
            Signal5.setVisibility(View.INVISIBLE);
            Signal6.setVisibility(View.INVISIBLE);
        }

        Sensor1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //send sensor1 pushed
                Signal1.setBackgroundColor(Color.GREEN);
                Signal2.setBackgroundColor(Color.RED);
                Signal3.setBackgroundColor(Color.RED);
                Signal4.setBackgroundColor(Color.TRANSPARENT);
                Signal5.setBackgroundColor(Color.TRANSPARENT);
                Signal6.setBackgroundColor(Color.TRANSPARENT);
                sendDataByte((byte) 1);
                sendDataByte((byte) 2);
                sendDataByte((byte) 2);
                sendDataByte((byte) 0);
                sendDataByte((byte) 3);
                sendDataByte((byte) 0);
                sendDataByte((byte) 4);
                sendDataByte((byte) 4);
                sendDataByte((byte) 5);
                sendDataByte((byte) 4);
                sendDataByte((byte) 6);
                sendDataByte((byte) 4);
            }
        });
        Sensor2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //send sensor1 pushed
                Signal1.setBackgroundColor(Color.RED);
                Signal2.setBackgroundColor(Color.GREEN);
                Signal3.setBackgroundColor(Color.GREEN);
                Signal4.setBackgroundColor(Color.TRANSPARENT);
                Signal5.setBackgroundColor(Color.TRANSPARENT);
                Signal6.setBackgroundColor(Color.TRANSPARENT);
                sendDataByte((byte) 1);
                sendDataByte((byte) 0);
                sendDataByte((byte) 2);
                sendDataByte((byte) 2);
                sendDataByte((byte) 3);
                sendDataByte((byte) 2);
                sendDataByte((byte) 4);
                sendDataByte((byte) 4);
                sendDataByte((byte) 5);
                sendDataByte((byte) 4);
                sendDataByte((byte) 6);
                sendDataByte((byte) 4);
            }
        });
        Sensor3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //send sensor1 pushed
                Signal1.setBackgroundColor(Color.RED); setSignal(1, signalred);
                Signal2.setBackgroundColor(Color.GREEN); setSignal(2, signalgreen);
                Signal3.setBackgroundColor(Color.GREEN); setSignal(3, signalgreen);
                Signal4.setBackgroundColor(Color.TRANSPARENT); setSignal(4, signaloff);
                Signal5.setBackgroundColor(Color.TRANSPARENT); setSignal(5, signaloff);
                Signal6.setBackgroundColor(Color.TRANSPARENT); setSignal(6, signaloff);
            }
        });
        Sensor4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //send sensor1 pushed
                Signal1.setBackgroundColor(Color.TRANSPARENT); setSignal(1, signaloff);
                Signal2.setBackgroundColor(Color.TRANSPARENT); setSignal(2, signaloff);
                Signal3.setBackgroundColor(Color.TRANSPARENT); setSignal(3, signaloff);
                Signal4.setBackgroundColor(Color.GREEN); setSignal(4, signalgreen);
                Signal5.setBackgroundColor(Color.RED); setSignal(5, signalred);
                Signal6.setBackgroundColor(Color.RED); setSignal(6, signalred);
            }
        });
        Sensor5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //send sensor1 pushed
                Signal1.setBackgroundColor(Color.TRANSPARENT); setSignal(1, signaloff);
                Signal2.setBackgroundColor(Color.TRANSPARENT); setSignal(2, signaloff);
                Signal3.setBackgroundColor(Color.TRANSPARENT); setSignal(3, signaloff);
                Signal4.setBackgroundColor(Color.RED); setSignal(4, signalred);
                Signal5.setBackgroundColor(Color.GREEN); setSignal(5, signalgreen);
                Signal6.setBackgroundColor(Color.GREEN); setSignal(6, signalgreen);
            }
        });
        Sensor6.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //send sensor1 pushed
                Signal1.setBackgroundColor(Color.TRANSPARENT); setSignal(1, signaloff);
                Signal2.setBackgroundColor(Color.TRANSPARENT); setSignal(2, signaloff);
                Signal3.setBackgroundColor(Color.TRANSPARENT); setSignal(3, signaloff);
                Signal4.setBackgroundColor(Color.RED); setSignal(4, signalred);
                Signal5.setBackgroundColor(Color.GREEN); setSignal(5, signalgreen);
                Signal6.setBackgroundColor(Color.GREEN); setSignal(6, signalgreen);
            }
        });
        t.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if(enableDataSend) {
                    try {
                        while (mBTInputStream.available() > 0) {
                            char thisInput = (char) mBTInputStream.read();
                            String thisInputStr = String.valueOf(thisInput);
                            Log.d(TAG, thisInputStr);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, delay, period);
    }

    @Override
    protected void onResume() {
        super.onResume();
        clockwise = preferences.getBoolean("direction", false);
        if(clockwise) {
            Sensor1.setEnabled(false);
            Sensor2.setEnabled(false);
            Sensor3.setEnabled(false);
            Sensor4.setEnabled(true);
            Sensor5.setEnabled(true);
            Sensor6.setEnabled(true);
            Sensor1.setVisibility(View.INVISIBLE);
            Sensor2.setVisibility(View.INVISIBLE);
            Sensor3.setVisibility(View.INVISIBLE);
            Sensor4.setVisibility(View.VISIBLE);
            Sensor5.setVisibility(View.VISIBLE);
            Sensor6.setVisibility(View.VISIBLE);
            Signal1.setVisibility(View.INVISIBLE);
            Signal2.setVisibility(View.INVISIBLE);
            Signal3.setVisibility(View.INVISIBLE);
            Signal4.setVisibility(View.VISIBLE);
            Signal5.setVisibility(View.VISIBLE);
            Signal6.setVisibility(View.VISIBLE);
        }
        else {
            Sensor1.setEnabled(true);
            Sensor2.setEnabled(true);
            Sensor3.setEnabled(true);
            Sensor4.setEnabled(false);
            Sensor5.setEnabled(false);
            Sensor6.setEnabled(false);
            Sensor1.setVisibility(View.VISIBLE);
            Sensor2.setVisibility(View.VISIBLE);
            Sensor3.setVisibility(View.VISIBLE);
            Sensor4.setVisibility(View.INVISIBLE);
            Sensor5.setVisibility(View.INVISIBLE);
            Sensor6.setVisibility(View.INVISIBLE);
            Signal1.setVisibility(View.VISIBLE);
            Signal2.setVisibility(View.VISIBLE);
            Signal3.setVisibility(View.VISIBLE);
            Signal4.setVisibility(View.INVISIBLE);
            Signal5.setVisibility(View.INVISIBLE);
            Signal6.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.connect:
                ThreadExecutor.runTask(new Runnable() {
                    public void run() {
                        if(connect()) {
                            enableDataSend = true;
                            runOnUiThread(new Thread(new Runnable() {
                                public void run() {
                                    connectionStatus.setText("Connected");
                                    connectionStatus.setTextColor(Color.GREEN);
                                }
                            }));
                        }
                    }
                });
                return true;
            case R.id.disconnect:
                enableDataSend = false;
                resetConnection();
                connectionStatus.setText("Disconnected");
                connectionStatus.setTextColor(Color.RED);
                return true;
            case R.id.settings:
                Intent i = new Intent(this, SettingsActivity.class);
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void sendDataByte(byte message) {
        if(enableDataSend) {
            Log.d(TAG, "Send data: " + message);
            try {
                mBTOutputStream.write(message);
            } catch (IOException e) {
            }
        }
    }
    private void resetConnection() {
        if (mBTInputStream != null) {
            try {mBTInputStream.close();} catch (Exception e) {}
            mBTInputStream = null;
        }
        if (mBTOutputStream != null) {
            try {mBTOutputStream.close();} catch (Exception e) {}
            mBTOutputStream = null;
        }
        if (mBTSocket != null) {
            try {mBTSocket.close();} catch (Exception e) {}
            mBTSocket = null;
        }
    }

    public boolean connect() {
        address = preferences.getString("btdevice", "00:00:00:00:00:00");
        Log.d(TAG,"connecting to " + address);
        resetConnection();
        if (mBTDevice == null) {
            mBTDevice = mBTAdapter.getRemoteDevice(address);
        }
        try {mBTSocket = mBTDevice.createRfcommSocketToServiceRecord(MY_UUID);
        } catch (Exception e1) {
            Log.d(TAG, "connect(): Failed to bind to RFCOMM by UUID. msg=" + e1.getMessage());
            return false;
        }
        try {
            mBTSocket.connect();
        } catch (Exception e) {
            return false;
        }
        Log.d(TAG, "connect(): CONNECTED!");
        try {
            mBTOutputStream = mBTSocket.getOutputStream();
            mBTInputStream  = mBTSocket.getInputStream();
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    void setSignal(int signal, byte color) {
        sendDataByte((byte) signal);
        sendDataByte(color);
    }
}
