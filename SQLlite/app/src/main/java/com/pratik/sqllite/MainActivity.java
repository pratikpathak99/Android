package com.pratik.sqllite;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper myDb;
    TextView name,Surname,Marks,id;
    Button add,get,update,delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main);

        myDb = new DatabaseHelper (this);

        name = findViewById (R.id.name);
        Surname = findViewById (R.id.Surname);
        Marks = findViewById (R.id.Marks);
        id = findViewById (R.id.id);
        add = findViewById (R.id.add);
        get = findViewById (R.id.get);
        update = findViewById (R.id.update);
        delete = findViewById (R.id.delete);

        add.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
               boolean isInserted = myDb.insertData (name.getText ().toString (),Surname.getText ().toString (),Marks.getText ().toString ());
                if(isInserted = true)
                    Toast.makeText (MainActivity.this, "Data Inserted", Toast.LENGTH_SHORT).show ();
                else
                    Toast.makeText (MainActivity.this, "Data Not Inseted", Toast.LENGTH_SHORT).show ();
            }
        });

        get.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Cursor res = myDb.getAllDate ();
                if (res.getCount () == 0){
                    // show message
                    showMassage("Error","Nothing found");
                    return;
                }
                StringBuffer buffer = new StringBuffer ();
                while (res.moveToNext ()){
                    buffer.append ("ID :- "+ res.getString (0)+"\n");
                    buffer.append ("Name :- "+ res.getString (1)+"\n");
                    buffer.append ("Surname :- "+ res.getString (2)+"\n");
                    buffer.append ("Marks :- "+ res.getString (3)+"\n");
                }

                //show all data
                showMassage("Data",buffer.toString ());
            }
        });

        update.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                boolean isUpdate = myDb.updateData(id.getText ().toString (),name.getText ().toString (),Surname.getText ().toString (),Marks.getText ().toString ());
                if(isUpdate == true)
                    Toast.makeText (MainActivity.this, "Data Updated", Toast.LENGTH_SHORT).show ();
                else
                    Toast.makeText (MainActivity.this, "Data Not Updated", Toast.LENGTH_SHORT).show ();

            }
        });

        delete.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Integer deletedRows = myDb.deleteData (id.getText ().toString ());
                if(deletedRows>0)
                    Toast.makeText (MainActivity.this, "Data Deleted", Toast.LENGTH_SHORT).show ();
                else
                    Toast.makeText (MainActivity.this, "Data Not Deleted", Toast.LENGTH_SHORT).show ();
            }
        });
    }
    public void showMassage(String title,String Massage){
        AlertDialog.Builder builder = new AlertDialog.Builder (this);
        builder.setCancelable (true);
        builder.setTitle (title);
        builder.setMessage (Massage);
        builder.show ();
    }
}