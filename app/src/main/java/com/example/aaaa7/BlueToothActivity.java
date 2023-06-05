//package com.example.aaaa7;
//
//import android.app.ActionBar;
//import android.app.Activity;
//import android.app.ListActivity;
//import android.bluetooth.BluetoothAdapter;
//import android.bluetooth.BluetoothDevice;
//import android.bluetooth.BluetoothGattCharacteristic;
//import android.bluetooth.BluetoothManager;
//import android.bluetooth.le.ScanResult;
//import android.content.Context;
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.graphics.Color;
//import android.graphics.drawable.ColorDrawable;
//import android.os.Bundle;
//import android.os.Handler;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.Window;
//import android.view.WindowManager;
//import android.widget.BaseAdapter;
//import android.widget.ListView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.app.ActivityCompat;
//import androidx.core.content.ContextCompat;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class BlueToothActivity extends AppCompatActivity implements BluetoothGuide.OnCheckModelListener, BluetoothGuide.OnNotifyValueListener<XiaomiSensor>{
//
//    private TextView textView = null;
//
//    private final BluetoothGuide bluetoothGuide = new BluetoothGuide();
//    private final PermissionListener permissionListener = new PermissionListener() {
//        @Override
//        public void onPermissionGranted() {
//            // Start Scan Device
//            bluetoothGuide.scanDevices();
//        }
//
//        @Override
//        public void onPermissionDenied(List<String> deniedPermissions) {
//
//        }
//    };
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if( bluetoothGuide.onActivityResult(requestCode, resultCode))
//        {
//            TedPermission.with(this)
//                    .setPermissionListener(permissionListener)
//                    .setDeniedMessage("Denied Permission.")
//                    .setPermissions(Manifest.permission.ACCESS_FINE_LOCATION)
//                    .check();
//        }
//    }
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        textView = findViewById(R.id.temperatureTextView);
//
//        // Add Listeners
//        bluetoothGuide
//                .setOnCheckModelListener(this)
//                .setOnNotifyValueListener(this);
//
//        // Bluetooth System On with permission
//        if( bluetoothGuide.isOn() ) {
//            TedPermission.with(this)
//                    .setPermissionListener(permissionListener)
//                    .setDeniedMessage("Denied Permission.")
//                    .setPermissions(Manifest.permission.ACCESS_FINE_LOCATION)
//                    .check();
//        }
//        else {
//            bluetoothGuide.on(this);
//        }
//
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        // Clear the resources
//        bluetoothGuide.disconnectGATTAll();
//        bluetoothGuide.onComplete();
//    }
//
//
//    @Override
//    public boolean isChecked(byte[] bytes) {
//        return XiaomiSensor.isType(bytes);
//    }
//
//    @Override
//    public void scannedDevice(ScanResult result) {
//        // Start Connecting device.
//        bluetoothGuide.connGATT(getApplicationContext(), result.getDevice());
//    }
//
//    @Override
//    public void onValue(BluetoothDevice deivce, XiaomiSensor value) {
//        // Show the data that is notified value
//        textView.setText(String.valueOf(value.temperature));
//    }
//
//    @Override
//    public XiaomiSensor formatter(BluetoothGattCharacteristic characteristic) {
//        // Format the data
//        Integer value = characteristic.getIntValue(BluetoothGattCharacteristic.FORMAT_UINT16, 0);
//        Integer value2 = characteristic.getIntValue(BluetoothGattCharacteristic.FORMAT_UINT16, 1);
//        float temperature = value * 0.01f;
//        int humidity = (value2 & 0xFF00) >> 8;
//        return new XiaomiSensor(
//                System.currentTimeMillis(),
//                temperature,
//                humidity
//        );
//    }
//}
