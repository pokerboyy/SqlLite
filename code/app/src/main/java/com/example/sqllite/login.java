package com.example.sqllite;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class login extends AppCompatActivity implements View.OnClickListener {
    EditText etEmail,etPassword;
    Button login_btn;
    TextView register;
    String EmailHolder,PasswordHolder;
    Boolean EditTextEmptyHolder;
    SQLiteDatabase sqLiteDatabaseObj;
    DatabaseHelper databaseHelper;
    Cursor cursor;
    String TempPassword = "NOT_FOUND" ;
    public static final String UserEmail = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initialize();
        databaseHelper = new DatabaseHelper(this);
        register.setOnClickListener(this);
        login_btn.setOnClickListener(this);

    }
    private void initialize() {
        etPassword=findViewById(R.id.etPassword);
        etEmail=findViewById(R.id.etEmail);
        register=findViewById(R.id.register);
        login_btn=findViewById(R.id.login_btn);

    }

    @Override
    public void onClick(View view) {
        if(view==register){
            Intent intent=new Intent(login.this,register.class);
            startActivity(intent);

        }
        if(view==login_btn){
            CheckEditTextStatus();
            LoginFunction();

        }

    }

    private void CheckEditTextStatus() {
        EmailHolder=etEmail.getText().toString();
        PasswordHolder=etPassword.getText().toString();
        if(TextUtils.isEmpty(EmailHolder) || TextUtils.isEmpty(PasswordHolder)){
            EditTextEmptyHolder=false;
        }
        else{
            EditTextEmptyHolder=true;
        }
    }
    @SuppressLint("Range")
    public void LoginFunction(){
        if(EditTextEmptyHolder){
            sqLiteDatabaseObj=databaseHelper.getWritableDatabase();
            cursor=sqLiteDatabaseObj.query(databaseHelper.TABLE_NAME,null,""+databaseHelper.Table_Column_2_Email +
                    "=?",new String[]{EmailHolder},null,null,null);
            while (cursor.moveToNext()){
                if(cursor.isFirst()){
                    cursor.moveToFirst();
                    TempPassword = cursor.getString(cursor.getColumnIndex(DatabaseHelper.Table_Column_3_Password));

                    cursor.close();
                }
            }
            CheckFinalResult();
        }

    }

    private void CheckFinalResult() {

        if(TempPassword.equalsIgnoreCase(PasswordHolder))
        {
            Toast.makeText(login.this,"Login Successful",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(login.this, dashboard.class);
            intent.putExtra(UserEmail, EmailHolder);
            startActivity(intent);
        }
        else {
            Toast.makeText(login.this,"UserName or Password is Wrong, Please Try Again.",Toast.LENGTH_LONG).show();
        }
        TempPassword = "NOT_FOUND" ;
    }

}