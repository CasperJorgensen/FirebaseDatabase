package com.example.caspe.firebasedatabase.Model;

import java.util.List;

/**
 * Created by sofie on 16-04-2018.
 */

public class ShoppingList {
    int id;
    String name;
    List<User> users;
    List<Item> items;


    public ShoppingList(int id, String name, List<User> users, List<Item> items) {
        this.id = id;
        this.name = name;
        this.users = users;
        this.items = items;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
