package com.fitpal.fitpal;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ImageHome extends AppCompatActivity {

    private ImageView picture;
    private Button clickButton,uploadbutton;
    private final int reqCode=1;
    private StorageReference mStorageRef;
    Uri imgUri;
    String time;
    private RequestQueue mQueue;
    String answer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_home);
        mStorageRef = FirebaseStorage.getInstance().getReference().child("images1/temp.jpg");

        mQueue = Volley.newRequestQueue(this);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder(); StrictMode.setVmPolicy(builder.build());

        clickButton=(Button)findViewById(R.id.clickID);
        uploadbutton=(Button)findViewById(R.id.uploadiD);
        picture=(ImageView)findViewById(R.id.pictureID);

        clickButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent picIntent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                File externalStoragePublicDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
                String path=getPathName();
                File myDir = new File(externalStoragePublicDirectory+ "/saved_images");
                myDir.mkdirs();
                File img=new File(myDir,path);
                imgUri=Uri.fromFile(img);

                Bundle bundle = new Bundle();
                bundle.putString("data",String.valueOf(imgUri));


                //picIntent.putExtra(MediaStore.EXTRA_OUTPUT,imgUri);


                Log.d("uriii",String.valueOf(imgUri));
                startActivityForResult(picIntent,reqCode,bundle);
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
           if(requestCode == 1) {


               final Bitmap bp = (Bitmap) data.getExtras().get("data");
               picture.setImageBitmap(bp);

               uploadbutton.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {

                       ByteArrayOutputStream bos = new ByteArrayOutputStream();
                       bp.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos);
                       byte[] bitmapdata = bos.toByteArray();
                       ByteArrayInputStream bs = new ByteArrayInputStream(bitmapdata);


                       Uri tempUri = getImageUri(getApplicationContext(), bp);

                       // CALL THIS METHOD TO GET THE ACTUAL PATH
                       File finalFile = new File(getRealPathFromURI(tempUri));


                       Log.d("checc", "inresult");

                       Log.d("checc", String.valueOf(finalFile));

                       mStorageRef.putStream(bs)
                               .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                   @Override
                                   public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                       // Get a URL to the uploaded content
                                       //Uri downloadUrl = taskSnapshot.getDownloadUrl();

                                       Task<Uri> uri = taskSnapshot.getStorage().getDownloadUrl();
                                       while(!uri.isComplete());
                                       Uri url = uri.getResult();

                                       //Toast.makeText(getApplicationContext(), "Upload Success, download URL " +url.toString(), Toast.LENGTH_LONG).show();


                                        jsonParse(url.toString());


                                       Intent i=new Intent(getApplicationContext(),ImageResults.class);
                                       i.putExtra("dishImageName",answer);
                                       startActivity(i);

                                       Log.d("putf",url.toString());
                                   }
                               })
                               .addOnFailureListener(new OnFailureListener() {
                                   @Override
                                   public void onFailure(@NonNull Exception exception) {
                                       // Handle unsuccessful uploads
                                       // ...
                                       Log.d("putf","ffff");
                                   }
                               });
//

                   }
               });


           }

    }

    private String getPathName() {
        SimpleDateFormat date1=new SimpleDateFormat("ddmmYYYY");
        time=date1.format(new Date());
        Log.d("checc","inpath");
        return "Image"+time+".jpg";
    }


    public Uri getImageUri(Context inContext, Bitmap inImage) {
        Bitmap OutImage = Bitmap.createScaledBitmap(inImage, 1000, 1000,true);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), OutImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        String path = "";
        if (getContentResolver() != null) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                path = cursor.getString(idx);
                cursor.close();
            }
        }
        return path;
    }


    private void jsonParse(final String uf){
        String link = "http://34.66.97.185:7989/";
        StringRequest MyStringRequest = new StringRequest(Request.Method.POST, link, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //This code is executed if the server responds, whether or not the response contains data.
                //The String 'response' contains the server's response.
                Log.d("eesponse11", response);
                answer=response;
                Toast.makeText(getApplication(),response,Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
            @Override
            public void onErrorResponse(VolleyError error) {
                //This code is executed if there is an error.
                Log.d("ierror", String.valueOf(error));
                answer="";
                //Toast.makeText(getApplicationContext(), error+"",Toast.LENGTH_LONG).show();
            }
        }) {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> MyData = new HashMap<String, String>();
                MyData.put("url", uf); //Add the data you'd like to send to the server.
                return MyData;
            }
        };
        mQueue.add(MyStringRequest);
    }


}
