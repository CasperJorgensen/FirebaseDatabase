package com.example.caspe.firebasedatabase.Model;

/**
 * Created by sofie on 16-04-2018.
 */

public class Item {
    int id;
    String text;
    int quantity;
    ShoppingList shoppingList;

    public Item(int id, String text, int quantity, ShoppingList shoppingList) {
        this.id = id;
        this.text = text;
        this.quantity = quantity;
        this.shoppingList = shoppingList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public ShoppingList getShoppingList() {
        return shoppingList;
    }

    public void setShoppingList(ShoppingList shoppingList) {
        this.shoppingList = shoppingList;
    }
}
