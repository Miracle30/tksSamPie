package d1711062183.thi;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.os.LocaleList;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private SQLiteDatabase db;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setList();
    }

    private void setList(){
        ListView lv1=findViewById(R.id.lv1);
        SQLiteOpenHelper locationHelper = new LocationHelper(this);

        try{
            db = locationHelper.getReadableDatabase();

            cursor = db.query("loca",new String[]{"_id","name","address","zip","country"},null,null,null,null,null);

            SimpleCursorAdapter listAdapter = new SimpleCursorAdapter(this,R.layout.row,
                    cursor,new String[]{"name","address","zip","country"},new int[]{R.id.Name,R.id.Add,R.id.Zip, R.id.Country},0);

            listAdapter.setViewBinder(new SimpleCursorAdapter.ViewBinder(){
                @Override
                public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
                    if(columnIndex==1){
                        TextView tv = (TextView)view;
                        String name = cursor.getString(cursor.getColumnIndex("name"));
                        tv.setText(name);
                        return true;
                    }if(columnIndex==2){
                        TextView tv = (TextView)view;
                        String address = cursor.getString(cursor.getColumnIndex("address"));
                        tv.setText(address);
                        return true;
                    }if(columnIndex==3){
                        TextView tv = (TextView)view;
                        String zip = cursor.getString(cursor.getColumnIndex("zip"));
                        tv.setText(zip);
                        return true;
                    }
                    if(columnIndex==4){
                        ImageView tv = (ImageView) view;
                        String country = cursor.getString(cursor.getColumnIndex("country"));
//                        Log.d("Tag", country);
                        switch (country){
                            case "Vietnam":
                                tv.setImageResource(R.drawable.vietnam);
                                break;
                            case "Japan":
                                tv.setImageResource(R.drawable.japan);
                                break;
                            case "Laos":
                                tv.setImageResource(R.drawable.laos);
                                break;
                            case "Denmark":
                                tv.setImageResource(R.drawable.denmark);
                                break;
                            case "Russia":
                                tv.setImageResource(R.drawable.russia);
                                break;
                            default:
                                break;
                        }
//                        if(country.equals("Vietnam")){
//                            tv.setImageResource(R.drawable.vietnam);
////                            Log.d("Tag", String.valueOf(tv));
//                        }
                        return true;
                    }
                    return false;
                }
            });
            lv1.setAdapter(listAdapter);
        }catch(SQLException e){
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }
        registerForContextMenu(lv1);

        AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, EditActivity.class);
                // truyền kiểu key - value
                intent.putExtra("ID",(int)id);
                Log.d("a", String.valueOf(id));
                startActivity(intent);
            }
        };
        lv1.setOnItemClickListener(itemClickListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true ;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()){
            case R.id.insert:
                Intent intent = new Intent(MainActivity.this, InsertActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }

        return true;
    }

    @Override
    public void onResume(){
        super.onResume();
        setList();
    }
}