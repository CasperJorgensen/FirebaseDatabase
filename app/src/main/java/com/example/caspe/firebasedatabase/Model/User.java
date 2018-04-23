package com.example.caspe.firebasedatabase.Model;

import java.util.List;
import java.util.Map;

/**
 * Created by sofie on 16-04-2018.
 */

public class User {
    String id;
    String name;
    String email;
    Map<String, Boolean> shoppingList;

    public User() {
    }


    public Map<String, Boolean> getShoppingList() {
        return shoppingList;
    }

    public void setShoppingList(Map<String, Boolean> shoppingList) {
        this.shoppingList = shoppingList;
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
