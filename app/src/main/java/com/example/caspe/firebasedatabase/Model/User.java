package com.example.caspe.firebasedatabase.Model;

import java.util.List;

/**
 * Created by sofie on 16-04-2018.
 */

public class User {
    String id;
    String name;
    String email;
    List<ShoppingList> shoppingListList;


    public User(String id, String name, String email, List<ShoppingList> shoppingListList) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.shoppingListList = shoppingListList;
    }

    public List<ShoppingList> getShoppingListList() {
        return shoppingListList;
    }

    public void setShoppingListList(List<ShoppingList> shoppingListList) {
        this.shoppingListList = shoppingListList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
