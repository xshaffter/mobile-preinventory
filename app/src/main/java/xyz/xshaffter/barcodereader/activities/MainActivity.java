package xyz.xshaffter.barcodereader.activities;

import android.Manifest;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import xyz.xshaffter.barcodereader.Global;
import xyz.xshaffter.barcodereader.R;
import xyz.xshaffter.barcodereader.utilities.Utilis;

public class MainActivity extends AppCompatActivity {
    private static final int ZBAR_CAMERA_PERMISSION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Global.setDriver(this);
    }

    public void requester(View view) {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, ZBAR_CAMERA_PERMISSION);
        Utilis.goToActivity(this, CameraActivity.class);
    }
}
