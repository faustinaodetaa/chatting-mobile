package com.example.fozmusicstore.data;


import androidx.annotation.NonNull;

import com.example.fozmusicstore.data.models.User;
import com.example.fozmusicstore.interfaces.FinishListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import com.example.fozmusicstore.utils.Crypt;

public class UserRepository {

    private static FirebaseDatabase db = FirebaseDatabase.getInstance();
    private static DatabaseReference userRef = db.getReference("users");

    public static User LoggedInUser = null;
    public static String userNow;

    public static void insertUser(String fullName, String username, String password){
        String key = userRef.push().getKey();
        User temp = new User(key, fullName, username, Crypt.generateHash(password));
        userRef.child(key).setValue(temp.toMap());
    }

    public static void auth(String username, String password, FinishListener<User> listener){
        Query query = userRef.orderByChild("username").equalTo(username).limitToFirst(1);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                User temp = transformSnapshot(snapshot);

                if(temp == null){
                    if(listener != null){
                        listener.onFinish(null, "Username not found!");
                    }
                }else {
                    if (!Crypt.verifyHash(password, temp.getPassword())) {
                        //pass ga sesuai sama db
                        if (listener != null) {
                            listener.onFinish(null, "Invalid Password!");
                        }
                    } else {
                        //berhasil
                        if (listener != null) {
                            userNow = username.toString();
                            listener.onFinish(temp, "Success Login");
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

    }

    private static User transformSnapshot(DataSnapshot snapshot){
        if(!snapshot.exists()) return null;


        String username, fullName, password;
        snapshot = snapshot.getChildren().iterator().next();
        username = snapshot.child("username").getValue().toString();
        fullName = snapshot.child("fullName").getValue().toString();
        password = snapshot.child("password").getValue().toString();
        return new User(snapshot.getKey(), username, fullName, password);

    }

    public static void find(String userKey, FinishListener<User> listener){
        Query query = userRef.orderByKey().equalTo(userKey).limitToFirst(1);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                User temp = transformSnapshot(snapshot);
                if(listener != null){
                    listener.onFinish(temp, null);
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }




}
