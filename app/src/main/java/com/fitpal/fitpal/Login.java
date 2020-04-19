package com.fitpal.fitpal;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fitpal.fitpal.model.Users;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Login extends AppCompatActivity implements View.OnClickListener{


    private SignInButton signInButton;
    private GoogleSignInClient mGoogleSignInClient;
    public static String Name;
    public static String Email;
    public static Uri profile_url;
    private TextView error_msg;
    FirebaseAuth mAuth;
    DatabaseReference databaseReference;
    Users f1;

    private static final int REQUEST_CODE=9001;
    public int reqCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        setContentView(R.layout.activity_login);
        error_msg=(TextView)findViewById(R.id.errorID);
        signInButton=(SignInButton)findViewById(R.id.signinID);

        f1=new Users();

        String date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

        //Log.d("dateeeStamppp",date);

        GoogleSignInOptions googleSignInOptions=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        //googleApiClient=new GoogleApiClient.Builder(this).enableAutoManage(this, (GoogleApiClient.OnConnectionFailedListener) this).addApi(Auth.GOOGLE_SIGN_IN_API,googleSignInOptions).build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);
        mAuth = FirebaseAuth.getInstance();

        signInButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        ConnectivityManager cm =
                (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        if(isConnected){
            signIn();
        }

        else{


            AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
            builder.setTitle("You need to have Internet Connection to login");

            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            final AlertDialog dialog = builder.create();
            dialog.show();

            final Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            LinearLayout.LayoutParams positiveButtonLL = (LinearLayout.LayoutParams) positiveButton.getLayoutParams();
            positiveButtonLL.gravity = Gravity.END;
            positiveButton.setLayoutParams(positiveButtonLL);
        }

    }
    private void signIn(){

        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, REQUEST_CODE);


//        Intent intent=Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
//        startActivityForResult(intent,REQUEST_CODE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        reqCode=requestCode;

        if (requestCode == REQUEST_CODE) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }

    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            Log.d("success","sss");
            updateUI(account);
            // Signed in successfully, show authenticated UI.

        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("failedddd", "signInResult:failed code=" + e.getStatusCode());
            updateUI(null);

        }
    }



    private void updateUI(GoogleSignInAccount account) {

        if(account!=null){

            Name=account.getDisplayName();
            Email=account.getEmail();
            profile_url=account.getPhotoUrl();
            error_msg.setVisibility(View.INVISIBLE);
            Log.d("sucessss","ssss");


            databaseReference = FirebaseDatabase.getInstance().getReference("Users");

            databaseReference.addValueEventListener(new ValueEventListener() {

                Users u=null;

                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){

                        f1 = dataSnapshot1.getValue(Users.class);
                        Log.d("userrrr",String.valueOf(f1));


                        if(f1.Email.equalsIgnoreCase(Email)){
                            u=f1;
                            break;
                        }
                    }

                    if(u!=null){
                        Intent in=new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(in);
                        finish();
                    }
                    else{
                        Intent in=new Intent(getApplicationContext(),BmiInput.class);
                        startActivity(in);
                        finish();
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }

    }

//    private void handleResults(GoogleSignInResult results){
//        if(results.isSuccess()){
//            GoogleSignInAccount account=results.getSignInAccount();
//            Name=account.getDisplayName();
//            Email=account.getEmail();
//            profile_url=account.getPhotoUrl().toString();
//            error_msg.setVisibility(View.INVISIBLE);
//            Log.d("sucessss","ssss");
//            Intent intent=new Intent(this,MainActivity.class);
//            startActivity(intent);
//            }
//            else{
//                error_msg.setVisibility(View.VISIBLE);
//                Log.d("failedddd","faileddd");
//              }
//
//    }

//
//
//    @Override
//    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
//
//    }


    @Override
    protected void onStart() {
        super.onStart();
//        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
//        updateUI(account);
    }
}
