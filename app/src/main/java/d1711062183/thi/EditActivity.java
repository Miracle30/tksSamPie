package d1711062183.thi;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteCursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class EditActivity extends AppCompatActivity {
    private int id = 0;
    private EditText edtName, edtAddress, edtZip;
    private Button btnEdit, btnDelete;
    private Spinner spn;
    private RadioGroup rgSelect;
    private RadioButton rbSelect;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        addControl();
        addEvent();
        setDetail();
    }

    private void addControl() {
        edtName = findViewById(R.id.edtName);
        edtAddress = findViewById(R.id.edtAddress);
        edtZip = findViewById(R.id.edtZip);
        // get selected radio button from radioGroup
        rgSelect = findViewById(R.id.rgSelect);
        int selectedId = rgSelect.getCheckedRadioButtonId();
        rbSelect = findViewById(selectedId);
        //spinner
        spn = findViewById(R.id.spn);
        btnEdit = findViewById(R.id.btnEdit);
        btnDelete = findViewById(R.id.btnDelete);

    }
    private void addEvent(){
        //nhận value mà có key là "ID"
        id = (Integer)getIntent().getExtras().get("ID");
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveFood();
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteFood();
            }
        });
    }
    private void setDetail(){
        SQLiteOpenHelper locationDatabaseHelper = new LocationHelper(this);
        try {
            db = locationDatabaseHelper.getReadableDatabase();
            Cursor cursor = db.query("loca",new String[]{"_id","name","address","zip","country"},"_ID = ?", new String[]{Integer.toString(id)},null,null,null);
            if (cursor.moveToFirst()){
                String name = cursor.getString(1);
                String address = cursor.getString(2);
                String zip = cursor.getString(3);
                String country = cursor.getString(4);

                edtName.setText(name);
                edtAddress.setText(address);
                edtZip.setText(zip);
                spn.setSelection(getIndex(spn, country));

            }
            cursor.close();
            db.close();
        }
        catch(SQLException e) {
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    private void deleteFood(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete");
        builder.setMessage("Are your sure to delete this item?");
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                SQLiteOpenHelper locationDatabaseHelper = new LocationHelper(EditActivity.this);
                SQLiteDatabase db = locationDatabaseHelper.getWritableDatabase();
                db.execSQL("DELETE FROM loca WHERE _id ="+id+";");
                Toast.makeText(getApplicationContext(), "Deleted", Toast.LENGTH_SHORT).show();
                db.close();
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "Please check carefully", Toast.LENGTH_SHORT).show();
            }
        });

        builder.show();
    }

//    Save
    private void saveFood(){

        String new_name = edtName.getText().toString();
        String new_address = edtAddress.getText().toString();
        String new_zip = edtZip.getText().toString();
        String new_country = spn.getSelectedItem().toString();

        SQLiteOpenHelper locationDatabaseHelper = new LocationHelper(EditActivity.this);
        SQLiteDatabase db = locationDatabaseHelper.getWritableDatabase();
        db.execSQL("UPDATE loca SET name = '"+new_name+"', address = '"+new_address+"', zip = '"+new_zip+"', country = '"+new_country+"' WHERE _id = "+ id+";");
        Toast toast = Toast.makeText(this, "Saved", Toast.LENGTH_SHORT);
        toast.show();
        db.close();
        finish();

    }

    private int getIndex(Spinner spinner, String myString){
        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
                return i;
            }
        }
        return 0;
    }
}