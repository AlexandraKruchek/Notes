package com.example.alexadra.simplan;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.util.Log;
import android.view.MenuItem;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    DBHelper dbHelper;

    Button btnRead;
    Button btnList;

    ListView listView;
    TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btnList = (Button) findViewById(R.id.btnList);
        btnList.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
       /*         Intent intent = new Intent(MainActivity.this, WLAddActivity.class);
                startActivity(intent);*/
            }
        });

        dbHelper = new DBHelper(this);

        /* Функция вывод списка на экран */
        Output();

        /**       Обработка нажатия на пункт списка      **/
        ListView listView = (ListView) findViewById(R.id.listView);
        final TextView textView = (TextView) findViewById(R.id.textView);

        SQLiteDatabase database = dbHelper.getReadableDatabase();

        Cursor cursor = database.rawQuery("SELECT * FROM notes", null);
        cursor.moveToFirst();

        ItemAdapter itemAdapter=new ItemAdapter(this,cursor);

        listView.setAdapter(itemAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, EditActivity.class);
                intent.putExtra("id_note", id);
                startActivity(intent);
            }

        }/**  -----------------------------------------  **/
        );

        /**  -------------------Делаю чеклист----------------------  **/



     }


    private void Output() {

        SQLiteDatabase database = dbHelper.getReadableDatabase();

        ArrayList<HashMap<String, Object>> notes = new ArrayList<HashMap<String, Object>>();

        HashMap<String, Object> note;

        Cursor cursor = database.rawQuery("SELECT * FROM notes", null);
        cursor.moveToFirst();

        ItemAdapter itemAdapter=new ItemAdapter(this,cursor);
        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(itemAdapter);

        dbHelper.close();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent=new Intent(MainActivity.this,AddActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
