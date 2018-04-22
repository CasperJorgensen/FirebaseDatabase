package com.example.caspe.firebasedatabase;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.caspe.firebasedatabase.Model.User;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
//Sofie
public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private int SIGN_IN_REQUEST_CODE = 1;
    private String TAG;
    User OverViewUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       mAuth = FirebaseAuth.getInstance();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final TextView textView = (TextView)findViewById(R.id.text);


        if (FirebaseAuth.getInstance().getCurrentUser() == null){
            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .build(),
                    SIGN_IN_REQUEST_CODE
            );

        }
        else {
            Toast.makeText(this, "Welcome " + FirebaseAuth.getInstance()
                            .getCurrentUser()
                            .getDisplayName()
                    , Toast.LENGTH_LONG).show();
            getUser();

        }

        if (OverViewUser == null) {


            FireBaseHandler fire = new FireBaseHandler();
            fire.writeNewUser(FirebaseAuth.getInstance().getCurrentUser().getUid(), FirebaseAuth.getInstance().getCurrentUser().getDisplayName(), FirebaseAuth.getInstance().getCurrentUser().getEmail());
            getUser();
        }

    }

    public void getUser() {
        mAuth = FirebaseAuth.getInstance();
        String uid = mAuth.getCurrentUser().getUid();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final TextView textView = (TextView)findViewById(R.id.text);
        DatabaseReference userDB = database.getReference().child("users").child(uid);


        userDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                OverViewUser = dataSnapshot.getValue(User.class);


                Toast.makeText(MainActivity.this, OverViewUser.getName(),
                        Toast.LENGTH_LONG).show();

                textView.setText(OverViewUser.getName());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }




}
