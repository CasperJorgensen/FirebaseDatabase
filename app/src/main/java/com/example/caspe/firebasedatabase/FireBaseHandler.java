package com.example.caspe.firebasedatabase;


import android.provider.ContactsContract;
import android.renderscript.Sampler;
import android.widget.TextView;
import android.widget.Toast;

import com.example.caspe.firebasedatabase.Model.ShoppingList;
import com.example.caspe.firebasedatabase.Model.User;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

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

    //Todo: refactor addShoppinglist og måske hopperRef
    public void AddShoppingList(String name, User user) {
        ShoppingList shoppingList = new ShoppingList();
        shoppingList.setName(name);
        String userId = user.getId();

        Map<String, Boolean> map = new HashMap<>();
        map.put(userId, true);

        shoppingList.setUsers(map);

        DatabaseReference hopperRef = mDatabase.child("users").child(userId).child("shoppingList");
        Map<String, Object> hopperUpdates = new HashMap<>();
        hopperUpdates.put(name, true);

        hopperRef.updateChildren(hopperUpdates);

        mDatabase.child("shoppingLists").child(name).setValue(shoppingList);
    }

    //Todo: Måske refactor hopperRef også her
    public void addUserToShoppingList(String ShoppingListName, String UserId) {
            Map<String, Boolean> shoppingListlist = new HashMap<>();
            shoppingListlist.put(ShoppingListName, true);

            DatabaseReference hopperRef = mDatabase.child("users").child(UserId).child("shoppingList");
            Map<String, Object> hopperUpdates = new HashMap<>();
            hopperUpdates.put(ShoppingListName, true);

            hopperRef.updateChildren(hopperUpdates);

            DatabaseReference ShopDB = mDatabase.child("shoppingLists").child(ShoppingListName).child("users");
            Map<String, Object> shopUpdate = new HashMap<>();

            shopUpdate.put(UserId, true);
            ShopDB.updateChildren(shopUpdate);
        }

    public void addAndRemoveItem(String shoppingListName, String name, Boolean check) {
        DatabaseReference shopDb = mDatabase.child("shoppingLists").child(shoppingListName).child("items");
        Map<String, Object> shopUpdate = new HashMap<>();

        shopUpdate.put(name, check);
        shopDb.updateChildren(shopUpdate);
    }
}
