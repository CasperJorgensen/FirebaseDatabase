package com.example.caspe.firebasedatabase;


import com.example.caspe.firebasedatabase.Model.ShoppingList;
import com.example.caspe.firebasedatabase.Model.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sofie on 16-04-2018.
 */

public class FireBaseHandler {
    private DatabaseReference mDatabase;

    public FireBaseHandler() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }



    public void writeNewUser(String userId, String name, String email) {
        User user = new User();
        user.setId(userId);
        user.setName(name);
        user.setEmail(email);

        mDatabase.child("users").child(userId).setValue(user);
    }

    public void AddShoppingList(String name, User user) {
        ShoppingList shoppingList = new ShoppingList();
        shoppingList.setName(name);
        String userId = user.getId();

        Map<String, Boolean> map = new HashMap<>();
        map.put(userId, true);

        shoppingList.setUsers(map);


      /*  Map<String, Boolean> shoppingListlist = new HashMap<>();


        shoppingListlist.put(name, true);
        user.setShoppingList(shoppingListlist); */


        DatabaseReference hopperRef = mDatabase.child("users").child(userId).child("shoppingLists");
        Map<String, Object> hopperUpdates = new HashMap<>();
        hopperUpdates.put(name, true);

        hopperRef.updateChildren(hopperUpdates);

        mDatabase.child("shoppingLists").child(name).setValue(shoppingList);

       // mDatabase.child("users").child(userId).setValue(user);

    }

    public void addUserToShoppingList(String ShoppingListName, String UserId, Map<String, Boolean> Users) {

            Users.put(UserId, true);

        Map<String, Boolean> shoppingListlist = new HashMap<>();

        shoppingListlist.put(ShoppingListName, true);



        DatabaseReference hopperRef = mDatabase.child("users").child(UserId).child("shoppingLists");
        Map<String, Object> hopperUpdates = new HashMap<>();
        hopperUpdates.put(ShoppingListName, true);

        hopperRef.updateChildren(hopperUpdates);

        //mDatabase.child("users").child(UserId).child("shoppingLists").setValue(shoppingListlist);

        mDatabase.child("shoppingLists").child(ShoppingListName).setValue(Users);
    }


}
