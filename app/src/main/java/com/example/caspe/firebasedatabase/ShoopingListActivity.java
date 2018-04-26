package com.example.caspe.firebasedatabase;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.caspe.firebasedatabase.Model.ShoppingList;
import com.example.caspe.firebasedatabase.Model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Map;


public class ShoopingListActivity extends AppCompatActivity {

    ShoppingList shoppingList;
    private ArrayList<String> items;
    private ArrayList<String> deletedItems;
    private ArrayAdapter<String> itemsAdapter;
    private ListView deletedVItems;
    private ArrayAdapter<String> deletedItemsAdapter;
    private ListView lvItems;
    // ArrayList<User> users;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shooping_list);

        Intent intent = getIntent();
        String write = intent.getStringExtra("text");

        getShoppingListItems(write);




        lvItems = (ListView) findViewById(R.id.lvItems);

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected item text from ListView
                String selectedItem = (String) parent.getItemAtPosition(position);
                handleItem(selectedItem, false);

            }

            });

        deletedVItems = (ListView) findViewById(R.id.deletedItems);
        deletedVItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected item text from ListView
                String selectedItem = (String) parent.getItemAtPosition(position);
                handleItem(selectedItem, true);

            }

        });

        final Button button = (Button) findViewById(R.id.btnAddItem);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onAddItem();

            }
        });


        final Button button2 = (Button) findViewById(R.id.btnAddUser);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addUserToList("Mqgkqnz45hdZspkz0DYEVOZVt2F2");

            }
        });

    }


    public  void addUserToList(String uid) {
        FireBaseHandler fire = new FireBaseHandler();




        fire.addUserToShoppingList(shoppingList.getName(), uid);

    }

    public void getShoppingListItems(String ShoppingListName) {


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ShopDb = database.getReference().child("shoppingLists").child(ShoppingListName);
        ShopDb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                shoppingList = dataSnapshot.getValue(ShoppingList.class);
                if (shoppingList != null) {

                    Toast.makeText(ShoopingListActivity.this, shoppingList.getName(),
                            Toast.LENGTH_LONG).show();

                   /* if (shoppingList.getUsers().size() != 0) {
                        for (Map.Entry<String, Boolean> entry : shoppingList.getUsers().entrySet()) {
                            getUser(entry.getKey());
                        }
                    } */


                    items = new ArrayList<String>();
                    itemsAdapter = new ArrayAdapter<String>(ShoopingListActivity.this, android.R.layout.simple_list_item_1, items);
                    lvItems.setAdapter(itemsAdapter);


                    deletedItems = new ArrayList<String>();
                    deletedItemsAdapter = new ArrayAdapter<String>(ShoopingListActivity.this, android.R.layout.simple_list_item_1, deletedItems);
                    deletedVItems.setAdapter(deletedItemsAdapter);

            if (shoppingList.getItems() != null) {
                    for (Map.Entry<String, Boolean> entry : shoppingList.getItems().entrySet()) {


                        if (entry.getValue() == true) {
                            items.add(entry.getKey());
                        }
                        if(entry.getValue() == false){
                            deletedItems.add(entry.getKey());
                        }



                    }
                }  }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());

            }
    });}


  /*  public void getUser(String uid) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference userDB = database.getReference().child("users").child(uid);


        userDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);


                users.add(user);
                Toast.makeText(ShoopingListActivity.this, user.getName(),
                        Toast.LENGTH_LONG).show();


                }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
    });
    }
*/
    public void onAddItem() {
        EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
        String itemText = etNewItem.getText().toString();
        handleItem(itemText, true);
        etNewItem.setText("");
    }

    public void handleItem(String name, Boolean check) {
        FireBaseHandler fire = new FireBaseHandler();
        fire.addAndRemoveItem(shoppingList.getName(), name, check);

    }



}
