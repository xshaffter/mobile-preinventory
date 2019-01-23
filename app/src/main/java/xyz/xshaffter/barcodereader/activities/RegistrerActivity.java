package xyz.xshaffter.barcodereader.activities;

import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import xyz.xshaffter.barcodereader.Global;
import xyz.xshaffter.barcodereader.R;
import xyz.xshaffter.barcodereader.utilities.Utilis;

public class RegistrerActivity extends AppCompatActivity {
    private EditText cantidades;
    private EditText nombre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrer);
        cantidades = findViewById(R.id.cantidades);
        nombre = findViewById(R.id.nombre);
        ((TextView) findViewById(R.id.CodigoGuardado)).setText(Global.codigoBR);

        final Cursor cursor = Global.driver.getFirstFromTable(Global.codigoBR);
        cursor.moveToFirst();
        final int nombreIndex = cursor.getColumnIndex("NOMBRE");
        final int cantIndex = cursor.getColumnIndex("CANTIDAD");
        try {
            final String txtNombre = cursor.getString(nombreIndex);
            final String txtCantidad = cursor.getString(cantIndex);
            nombre.setText(txtNombre);
            cantidades.setText(txtCantidad);
        } catch (CursorIndexOutOfBoundsException ignored) {

        }

        if (!cursor.isClosed()) {
            cursor.close();
        }

    }

    private boolean upgrade() {
        final String txtCantidad = cantidades.getText().toString();
        final int intCantidad = Integer.parseInt(txtCantidad);

        final String txtNombre = nombre.getText().toString();

        Global.codigos.add(Global.codigoBR);

        final String result = Global.driver.AddOrUpgrade(Global.codigoBR, txtNombre, intCantidad);

        Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
        return true;
    }

    public void load(View view) {
        upgrade();
        Utilis.goToActivity(this, CameraActivity.class);
    }

    public void finish(View view) {
        upgrade();
        Utilis.goToActivity(this, ReadActivity.class);
    }
}
