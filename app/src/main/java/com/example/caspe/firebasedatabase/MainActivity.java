package com.example.caspe.firebasedatabase;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.caspe.firebasedatabase.Model.ShoppingList;
import com.example.caspe.firebasedatabase.Model.User;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ui.email.SignInActivity;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//Sofie
public class MainActivity extends AppCompatActivity {
    private ArrayList<Map<String, Boolean>> data;
    ShoppingList shop;
    List<User> users;
    private FirebaseAuth mAuth;
    private int SIGN_IN_REQUEST_CODE = 1;
    private String TAG;
    User OverViewUser;
    private String m_Text = "";
    private CardArrayAdapter cardArrayAdapter;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        listView = (ListView) findViewById(R.id.card_listView);

        listView.addHeaderView(new View(this));
        listView.addFooterView(new View(this));

        cardArrayAdapter = new CardArrayAdapter(getApplicationContext(), R.layout.list_item_card);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createList();
            }
        });

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

        /*final Button button = (Button) findViewById(R.id.button3);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, CreateShoopingListActivity.class);
                startActivity(intent);
               // createList();
            }
        });


     final Button button2 = (Button) findViewById(R.id.button4);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


         addPerson();
           }
        });*/


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SIGN_IN_REQUEST_CODE) {
            //IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                // Successfully signed in
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


                    getUser();


                // ...
            } else {
                // Sign in failed, check response for error code
                // ...
            }
        }
    }
public void getGroups(String groupName) {
    mAuth = FirebaseAuth.getInstance();

    FirebaseDatabase database = FirebaseDatabase.getInstance();

    DatabaseReference userDB = database.getReference().child("shoppingLists").child(groupName);






    userDB.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {


            shop = dataSnapshot.getValue(ShoppingList.class);



            Toast.makeText(MainActivity.this, shop.getName(),
                    Toast.LENGTH_LONG).show();

        for (Map.Entry<String, Boolean> entry : shop.getUsers().entrySet()) {

                Toast.makeText(MainActivity.this, entry.getValue().toString() + entry.getKey().toString(),
                        Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            System.out.println("The read failed: " + databaseError.getCode());


        }
    });
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
            if (OverViewUser != null) {

                    if (OverViewUser.getShoppingList() != null) {


                        cardArrayAdapter.clear();
                        cardArrayAdapter.notifyDataSetChanged();


                        for (Map.Entry<String, Boolean> entry : OverViewUser.getShoppingList().entrySet()) {

                            Card card = new Card(entry.getKey(), entry.getValue().toString());
                            cardArrayAdapter.add(card);
                        }

                        listView.setAdapter(cardArrayAdapter);

                        Toast.makeText(MainActivity.this, OverViewUser.getShoppingList().size() + "",
                                Toast.LENGTH_LONG).show();

                        Toast.makeText(MainActivity.this, cardArrayAdapter.getCount() + "",
                                Toast.LENGTH_LONG).show();
/*
                        for (Map.Entry<String, Boolean> entry : OverViewUser.getShoppingList().entrySet()) {

                            getGroups(entry.getKey());

                        } */
                    }
                }
                if(OverViewUser == null) {
                 createUser();
                }

                textView.setText(OverViewUser.getName() +"");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }

    public void createUser() {
        if (OverViewUser == null && FirebaseAuth.getInstance().getCurrentUser() != null) {


            FireBaseHandler fire = new FireBaseHandler();
            fire.writeNewUser(FirebaseAuth.getInstance().getCurrentUser().getUid(), FirebaseAuth.getInstance().getCurrentUser().getDisplayName(), FirebaseAuth.getInstance().getCurrentUser().getEmail());
            getUser();
        }

    }

    public void addPerson(){
                FireBaseHandler fire = new FireBaseHandler();
                fire.addUserToShoppingList("Test 3", "52e5BgUocRNDJGrNLsqBGgkxOe73");
            }


    public void createList(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Create new list");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_SHORT_MESSAGE);
        builder.setView(input);

        builder.setPositiveButton("Create", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                m_Text = input.getText().toString();

                FireBaseHandler fire = new FireBaseHandler();
                fire.AddShoppingList(m_Text, OverViewUser);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }



    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.signOut:
                mAuth.signOut();
                startActivity(new Intent(this, MainActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
