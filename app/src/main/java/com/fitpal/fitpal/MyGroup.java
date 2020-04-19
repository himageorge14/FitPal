package com.fitpal.fitpal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.fitpal.fitpal.model.FriendsAdapter;
import com.fitpal.fitpal.model.Restaurant;
import com.fitpal.fitpal.model.RestaurantAdapter;
import com.fitpal.fitpal.model.UserFriends;
import com.fitpal.fitpal.model.UserKeys;
import com.fitpal.fitpal.model.Users;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static com.fitpal.fitpal.MainActivity.KeyUserMealsFromDB;

public class MyGroup extends AppCompatActivity {

    DatabaseReference db,databaseUsers,databaseReference;
    ArrayList<String> people;
    ArrayList<Users> friends;

    private RecyclerView recyclerView;
    private FriendsAdapter rAdapter;
    Collection<Object> values;
    int flag=0,flag1=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_group);

        recyclerView = (RecyclerView) findViewById(R.id.groupRecyclerID);

        people=new ArrayList<>();
        friends=new ArrayList<>();



        db=FirebaseDatabase.getInstance().getReference("UserFriends").child(String.valueOf(KeyUserMealsFromDB));
        databaseUsers=FirebaseDatabase.getInstance().getReference("Users");


        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){

                    Map<String, Object> td = (HashMap<String,Object>) dataSnapshot.getValue();

                    values = td.values();
                    Log.d("keyyy",String.valueOf(values));

                    String temp1=String.valueOf(values).replaceAll("\\{", "");
                    String temp2=temp1.replaceAll("\\[", "").replaceAll("]","");

                    String[] arrOfStr =temp2.split(",");
                    for (int i=0; i < arrOfStr.length; i++)
                    {
                        String s=String.valueOf(arrOfStr[i]);
                        s=s.replaceAll("\\s", "");
                        s=String.valueOf(s.substring(0,s.indexOf('=')));
                        people.add(s);
//                        Log.d("keyyy",s);
//                        Log.d("peoo1",String.valueOf(people));
                    }

                    people= removeDuplicates(people);
                    people.remove(String.valueOf(KeyUserMealsFromDB));

                    flag=1;
                    display();

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }


    public static <T> ArrayList<T> removeDuplicates(ArrayList<T> list)
    {

        // Create a new ArrayList
        ArrayList<T> newList = new ArrayList<T>();

        // Traverse through the first list
        for (T element : list) {

            // If this element is not present in newList
            // then add it
            if (!newList.contains(element)) {

                newList.add(element);
            }
        }

        // return the new list
        return newList;
    }

    private void display(){
        if(flag==1){

            Log.d("peooo",String.valueOf(people));

            databaseUsers.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){

                        Users utemp = dataSnapshot1.getValue(Users.class);
                        for(int i=0;i<people.size();i++){
                            if(String.valueOf(utemp.KeyUserMeals).equals(people.get(i))){
                                friends.add(utemp);
                                Log.d("dbb",String.valueOf(utemp.KeyUserMeals));
                                Log.d("appp",String.valueOf(utemp)); // results seen
                                Log.d("arr3",String.valueOf(friends));
                            }
                            flag1=1;
                            Log.d("arr2",String.valueOf(friends));
                            seeFriends();
                        }

                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }else{
            display();
        }

    }



    private void seeFriends(){

        if(flag1==1){
            Log.d("arrr",String.valueOf(friends)); //blank

            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(rAdapter);

            rAdapter = new FriendsAdapter(friends,getApplicationContext());

            rAdapter.notifyDataSetChanged();
        }
        else{
            seeFriends();
        }

    }
}
