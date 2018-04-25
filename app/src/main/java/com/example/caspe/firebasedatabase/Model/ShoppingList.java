package com.example.caspe.firebasedatabase.Model;

import java.util.List;
import java.util.Map;

/**
 * Created by sofie on 16-04-2018.
 */

public class ShoppingList {

    String name;
    Map<String, Boolean> users;
    Map<String, Boolean> items;

    public ShoppingList() {
    }






    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, Boolean> getUsers() {
        return users;
    }

    public void setUsers(Map<String, Boolean> users) {
        this.users = users;
    }

    public Map<String, Boolean> getItems() {
        return items;
    }

    public void setItems(Map<String, Boolean> items) {
        this.items = items;
    }
}
