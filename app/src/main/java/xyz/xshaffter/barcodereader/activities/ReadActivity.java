package xyz.xshaffter.barcodereader.activities;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import xyz.xshaffter.barcodereader.Global;
import xyz.xshaffter.barcodereader.R;
import xyz.xshaffter.barcodereader.utilities.Utilis;

public class ReadActivity extends AppCompatActivity {
    private int i = 0;
    private TextView codigos, cantidades, nombre;
    private ImageView imgCodigo, imgCantidad;

    @TargetApi(Build.VERSION_CODES.M)
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);

        codigos = findViewById(R.id.CodigoNumero);
        cantidades = findViewById(R.id.Cantidad);
        imgCodigo = findViewById(R.id.imgCodigo);
        imgCantidad = findViewById(R.id.imgCantidad);
        nombre = findViewById(R.id.txtNombre);

        setData(i);
    }

    public void next(View view) {
        if (i + 1 < Global.codigos.size()) {
            setData(++i);
        } else {
            i = 0;
            Global.codigos.clear();
            Global.codigoBR = "";
            Utilis.goToActivity(this, MainActivity.class);
        }
    }

    public void back(View view) {
        if (i - 1 >= 0) {
            setData(--i);
        }
    }

    public void setData(final int index) {
        final String txtCodigo = Global.codigos.get(index);
        final Cursor cursor = Global.driver.getFirstFromTable(txtCodigo);
        cursor.moveToFirst();
        final int cantIndex = cursor.getColumnIndex("CANTIDAD");
        final String txtCantidad = cursor.getString(cantIndex);
        final int nombreIndex = cursor.getColumnIndex("NOMBRE");
        final String txtNombre = cursor.getString(nombreIndex);
        codigos.setText(txtCodigo);
        imgCodigo.setImageBitmap(Utilis.getBarCode(txtCodigo));
        imgCantidad.setImageBitmap(Utilis.getBarCode(txtCantidad));
        cantidades.setText(txtCantidad);
        nombre.setText(txtNombre);
        if (!cursor.isClosed()) {
            cursor.close();
        }
    }
}
