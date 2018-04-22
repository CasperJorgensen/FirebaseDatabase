package com.example.caspe.firebasedatabase;


import com.example.caspe.firebasedatabase.Model.ShoppingList;
import com.example.caspe.firebasedatabase.Model.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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

    public void createShoppingList(String name, String uid) {
        ShoppingList shoppingList = new ShoppingList();
        shoppingList.setName(name);


        shoppingList.setUsers();
    }

}
