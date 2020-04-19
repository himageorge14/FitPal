package com.fitpal.fitpal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.fitpal.fitpal.model.UserKeys;
import com.fitpal.fitpal.model.Users;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.fitpal.fitpal.Login.Email;
import static com.fitpal.fitpal.MainActivity.KeyUserMealsFromDB;
import static com.fitpal.fitpal.VoiceInput.count;

public class ChangeGroupPassword extends AppCompatActivity {

    DatabaseReference databaseUsers;
    TextView key;
    //Button submitKey;

    //REMOVE BUTTON

    private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_group_password);

        key=(TextView) findViewById(R.id.userkeyId);
       // submitKey=(Button)findViewById(R.id.keySubmitId);


        databaseUsers=FirebaseDatabase.getInstance().getReference("UserKeys");

        String k=randomAlphaNumeric(6);

        UserKeys um=new UserKeys();
        um.setKey(k);
        um.setUserKey(KeyUserMealsFromDB);
        databaseUsers.child(String.valueOf(KeyUserMealsFromDB)).setValue(um);
        key.setText(k);
    }



    public static String randomAlphaNumeric(int count) {
        StringBuilder builder = new StringBuilder();
        while (count-- != 0) {
            int character = (int)(Math.random()*ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }
        return builder.toString();
    }

}
