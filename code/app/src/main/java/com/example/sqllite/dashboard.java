package com.example.sqllite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class dashboard extends AppCompatActivity {
    String EmailHolder;
    TextView tv1;
    Button logout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        tv1 = (TextView) findViewById(R.id.tv1);
        logout = (Button) findViewById(R.id.logout);
        Intent intent = getIntent();
        EmailHolder = intent.getStringExtra(login.UserEmail);
        tv1.setText(tv1.getText().toString() + EmailHolder);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Finishing current DashBoard activity on button click.
                finish();
                Toast.makeText(dashboard.this,"Log Out Successful", Toast.LENGTH_LONG).show();
            }
        });
    }
}