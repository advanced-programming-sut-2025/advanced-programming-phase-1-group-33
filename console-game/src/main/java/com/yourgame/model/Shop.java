package com.yourgame.model;

import java.util.HashMap;
import java.util.Map;

public class Shop {
    private String name;
    private String ownerNpcName;
    private Map<Item, ShopItemDetails> stock;
    private int openHour;
    private int closeHour;

    public Shop(String name, String ownerNpcName, int openHour, int closeHour) {
        this.name = name;
        this.ownerNpcName = ownerNpcName;
        this.openHour = openHour;
        this.closeHour = closeHour;
        this.stock = new HashMap<>();
    }
    
    // Adds an item to the shop stock. If the item exists, update its quantity.
    public void addItem(Item item, int quantity, int priceOverride) {
        ShopItemDetails details = stock.get(item);
        if (details == null) {
            details = new ShopItemDetails(priceOverride, quantity);
        } else {
            details.addQuantity(quantity);
        }
        stock.put(item, details);
    }
    
    // Removes a given quantity of an item from stock.
    public boolean removeItem(Item item, int quantity) {
        if (stock.containsKey(item)) {
            ShopItemDetails details = stock.get(item);
            if (details.getQuantity() >= quantity) {
                details.removeQuantity(quantity);
                if (details.getQuantity() == 0) {
                    stock.remove(item);
                }
                return true;
            }
        }
        return false;
    }
    
    // Checks if a given quantity of an item can be purchased.
    public boolean canPurchase(Item item, int quantity) {
        ShopItemDetails details = stock.get(item);
        return details != null && details.getQuantity() >= quantity;
    }
    
    // Returns the shop's selling price for a specific item.
    public int getSellPriceForItem(Item item) {
        ShopItemDetails details = stock.get(item);
        if (details != null) {
            return details.getPrice();
        }
        return -1; // Indicates the item is not available.
    }
    
    // Checks if the shop is currently open based on the provided hour.
    public boolean isOpen(int currentHour) {
        return currentHour >= openHour && currentHour < closeHour;
    }
    
    public String getName() {
        return name;
    }
    
    public String getOwnerNpcName() {
        return ownerNpcName;
    }
    
    public int getOpenHour() {
        return openHour;
    }
    
    public int getCloseHour() {
        return closeHour;
    }
    
    public Map<Item, ShopItemDetails> getStock() {
        return stock;
    }
    
    @Override
    public String toString() {
        return "Shop{" +
                "name='" + name + '\'' +
                ", ownerNpcName='" + ownerNpcName + '\'' +
                ", stock=" + stock +
                ", openHour=" + openHour +
                ", closeHour=" + closeHour +
                '}';
    }
    
    // Inner class to encapsulate item details within the shop.
    public static class ShopItemDetails {
        private int price;
        private int quantity;
        
        public ShopItemDetails(int price, int quantity) {
            this.price = price;
            this.quantity = quantity;
        }
        
        public int getPrice() {
            return price;
        }
        
        public int getQuantity() {
            return quantity;
        }
        
        public void addQuantity(int quantity) {
            this.quantity += quantity;
        }
        
        public void removeQuantity(int quantity) {
            this.quantity -= quantity;
            if(this.quantity < 0) {
                this.quantity = 0;
            }
        }
        
        @Override
        public String toString() {
            return "ShopItemDetails{" +
                    "price=" + price +
                    ", quantity=" + quantity +
                    '}';
        }
    }
}