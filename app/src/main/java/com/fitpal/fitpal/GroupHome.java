package com.fitpal.fitpal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class GroupHome extends AppCompatActivity {

    Button mygroup,addmember,changekey;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_home);

        mygroup=(Button)findViewById(R.id.myGroupId);
        addmember=(Button)findViewById(R.id.addMemId);
        changekey=(Button)findViewById(R.id.changeKeyId);

        mygroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),MyGroup.class);
                startActivity(i);
            }
        });

        addmember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),GroupAddMember.class);
                startActivity(i);
            }
        });

        changekey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),ChangeGroupPassword.class);
                startActivity(i);
            }
        });


    }
}
