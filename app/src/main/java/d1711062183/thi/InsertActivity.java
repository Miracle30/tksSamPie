package d1711062183.thi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

public class InsertActivity extends AppCompatActivity {

    private EditText edtName, edtAddress, edtZip;
    private Button btnAdd;
    private Spinner spn;
    private RadioGroup rgSelect;
    private RadioButton rbSelect;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);
        addControls();
        addEvents();
    }


    private void addControls() {
        edtName = findViewById(R.id.edtName);
        edtAddress = findViewById(R.id.edtAddress);
        edtZip = findViewById(R.id.edtZip);
        // get selected radio button from radioGroup
        rgSelect = findViewById(R.id.rgSelect);
        int selectedId = rgSelect.getCheckedRadioButtonId();
        rbSelect = findViewById(selectedId);
        //spinner
        spn = findViewById(R.id.spn);
        btnAdd = findViewById(R.id.btnAdd);
    }

    private void addEvents() {
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addLocation();
            }
        });
    }
    private void addLocation(){
        SQLiteOpenHelper locationHelper = new LocationHelper(this);
        db = locationHelper.getReadableDatabase();
            //168 add food
        db.execSQL("INSERT INTO loca (name, address,zip,country) VALUES('" + edtName.getText().toString() + "','" + edtAddress.getText().toString() + "','" + edtZip.getText().toString() + "','" + spn.getSelectedItem().toString() + "')");
        finish();
    }

}