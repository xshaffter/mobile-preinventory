package xyz.xshaffter.barcodereader.activities;

import android.app.Activity;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.widget.FrameLayout;
import android.widget.Toast;

import me.dm7.barcodescanner.zbar.Result;
import me.dm7.barcodescanner.zbar.ZBarScannerView;
import xyz.xshaffter.barcodereader.Global;
import xyz.xshaffter.barcodereader.R;
import xyz.xshaffter.barcodereader.utilities.Utilis;

public class CameraActivity extends Activity implements ZBarScannerView.ResultHandler {
    private ZBarScannerView mScannerView;

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        mScannerView = new ZBarScannerView(this);
        setContentView(R.layout.activity_camera);
        mScannerView.setFlash(true);
        ((FrameLayout) findViewById(R.id.mainFrame)).addView(mScannerView);
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }

    @Override
    public void handleResult(Result rawResult) {
        Toast.makeText(this, "Codigo Leido", Toast.LENGTH_SHORT).show();

        Global.codigoBR = rawResult.getContents();
        try {
            Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone r = RingtoneManager.getRingtone(this, uri);
            r.play();
        } catch (Exception ex) {
            Toast.makeText(this, "error ocurrido", Toast.LENGTH_SHORT).show();
        }
        Utilis.goToActivity(getApplicationContext(), RegistrerActivity.class);
    }
}