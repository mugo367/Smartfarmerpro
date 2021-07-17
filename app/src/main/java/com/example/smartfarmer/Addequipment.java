package com.example.smartfarmer;

public class Addequipment {
    String id, name; String quantity; String equipstatus;

    public Addequipment() {
    }

    public Addequipment( String id, String name, String quantity, String equipstatus) {
        this.id =id;
        this.name = name;
        this.quantity = quantity;
        this.equipstatus = equipstatus;
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

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getEquipstatus() {
        return equipstatus;
    }

    public void setEquipstatus(String equipstatus) {
        this.equipstatus = equipstatus;
    }
}
