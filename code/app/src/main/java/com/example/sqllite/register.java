package com.example.sqllite;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class register extends AppCompatActivity implements View.OnClickListener {
    EditText etNewName, etNewEmail, etNewPass;
    RadioGroup gender;
    Button etNewRegister;
    TextView tvLogin;
    String NameHolder, EmailHolder, PasswordHolder;
    Boolean EditTextEmptyHolder;
    SQLiteDatabase sqLiteDatabaseObj;
    String SQLiteDataBaseQueryHolder;
    DatabaseHelper databaseHelper;
    Cursor cursor;
    String F_Result = "Not_Found";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initialize();
        databaseHelper = new DatabaseHelper(this);
        etNewRegister.setOnClickListener(this);
        tvLogin.setOnClickListener(this);
        databaseHelper=new DatabaseHelper(this);
    }

    private void initialize() {
        etNewName=findViewById(R.id.etNewName);
        etNewEmail=findViewById(R.id.etNewEmail);
        etNewPass=findViewById(R.id.etNewPass);
        etNewRegister=findViewById(R.id.etNewRegister);
        tvLogin=findViewById(R.id.tvLogin);
    }

    @Override
    public void onClick(View view) {
        if(view==etNewRegister){
            SQLiteDatabaseBuild();
            SQLiteTableBuild();
            CheckEditTextStatus();
           CheckingEmailAlreadyExistsOrNot();
           EmptyEditTextAfterDataInsert();
        }
        if(view==tvLogin){
            Intent intent=new Intent(register.this,login.class);
            startActivity(intent);
        }
    }

    private void EmptyEditTextAfterDataInsert() {
        etNewName.getText().clear();
        etNewEmail.getText().clear();
        etNewPass.getText().clear();
    }

    public void CheckingEmailAlreadyExistsOrNot(){
        // Opening SQLite database write permission.
        sqLiteDatabaseObj = databaseHelper.getWritableDatabase();
        // Adding search email query to cursor.
        cursor = sqLiteDatabaseObj.query(DatabaseHelper.TABLE_NAME, null, " " + DatabaseHelper.Table_Column_2_Email + "=?", new String[]{EmailHolder}, null, null, null);
        while (cursor.moveToNext()) {
            if (cursor.isFirst()) {
                cursor.moveToFirst();
                // If Email is already exists then Result variable value set as Email Found.
                F_Result = "Email Found";
                // Closing cursor.
                cursor.close();
            }
        }
        // Calling method to check final result and insert data into SQLite database.
        CheckFinalResult();
    }

    public void CheckFinalResult(){
        // Checking whether email is already exists or not.
        if(F_Result.equalsIgnoreCase("Email Found"))
        {
            // If email is exists then toast msg will display.
            Toast.makeText(register.this,"Email Already Exists",Toast.LENGTH_LONG).show();
        }
        else {
            // If email already dose n't exists then user registration details will entered to SQLite database.
            InsertDataIntoSQLiteDatabase();
        }
        F_Result = "Not_Found" ;
    }


    private void CheckEditTextStatus() {
        NameHolder = etNewName.getText().toString() ;
        EmailHolder = etNewEmail.getText().toString();
        PasswordHolder = etNewPass.getText().toString();
        if(TextUtils.isEmpty(NameHolder) || TextUtils.isEmpty(EmailHolder) || TextUtils.isEmpty(PasswordHolder)){
            EditTextEmptyHolder = false ;
        }
        else {
            EditTextEmptyHolder = true ;
        }
    }

    private void SQLiteTableBuild() {
        sqLiteDatabaseObj.execSQL("CREATE TABLE IF NOT EXISTS " + DatabaseHelper.TABLE_NAME + "(" + DatabaseHelper.Table_Column_ID + " PRIMARY KEY AUTOINCREMENT NOT NULL, " + DatabaseHelper.Table_Column_1_Name + " VARCHAR, " + DatabaseHelper.Table_Column_2_Email + " VARCHAR, " + DatabaseHelper.Table_Column_3_Password + " VARCHAR);");
    }

    private void SQLiteDatabaseBuild() {
        sqLiteDatabaseObj=openOrCreateDatabase(DatabaseHelper.DATABASE_NAME, Context.MODE_PRIVATE, null);
    }
    public void InsertDataIntoSQLiteDatabase(){
        if(EditTextEmptyHolder == true)
        {
            SQLiteDataBaseQueryHolder = "INSERT INTO "+DatabaseHelper.TABLE_NAME+" (name,email,password) VALUES('"+NameHolder+"', '"+EmailHolder+"', '"+PasswordHolder+"');";
            sqLiteDatabaseObj.execSQL(SQLiteDataBaseQueryHolder);
            sqLiteDatabaseObj.close();

            Toast.makeText(register.this,"User Registered Successfully", Toast.LENGTH_LONG).show();
//            Log.e(TAG, "InsertDataIntoSQLiteDatabase: "+Toast.makeText(register.this,"User Registered Successfully", Toast.LENGTH_LONG).show());
//            Log.e(TAG, "InsertDataIntoSQLiteDatabase: "+ DatabaseHelper.TABLE_NAME);
//            Log.e(TAG, "InsertDataIntoSQLiteDatabase: "+ NameHolder);
//            Log.e(TAG, "InsertDataIntoSQLiteDatabase: "+ EmailHolder);
//            Log.e(TAG, "InsertDataIntoSQLiteDatabase: "+ PasswordHolder);
        }

        else {
            Toast.makeText(register.this,"Please Fill All The Required Fields.", Toast.LENGTH_LONG).show();
        }


    }
}
