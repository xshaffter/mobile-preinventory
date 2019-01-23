package xyz.xshaffter.barcodereader.utilities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import xyz.xshaffter.barcodereader.Global;

public class Utilis {
    public static Bitmap getBarCode(final String text) {
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(text, BarcodeFormat.CODABAR, 500, 200);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            return barcodeEncoder.createBitmap(bitMatrix);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void goToActivity(Context context, Class clase) {
        Intent intent = new Intent(context, clase);
        context.startActivity(intent);
        makeToastShort("Activity Loaded");
    }

    public static void goToActivity(Class clase) {
        Intent intent = new Intent(Global.applicationContext, clase);
        Global.applicationContext.startActivity(intent);
        makeToastShort("Activity Loaded");
    }

    public static void makeToastShort(final String text) {
        Toast.makeText(Global.applicationContext, text, Toast.LENGTH_SHORT).show();
    }

    public static void makeToastLong(final String text) {
        Toast.makeText(Global.applicationContext, text, Toast.LENGTH_LONG).show();
    }
}
