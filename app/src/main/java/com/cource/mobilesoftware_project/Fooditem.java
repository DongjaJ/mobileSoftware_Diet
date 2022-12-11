package com.cource.mobilesoftware_project;

public class Fooditem {
    // 음식의 item을 담기 위한 class

    String food_dates;
    String food_name;
    int food_kcal;
    byte[] food_image_name;

    public Fooditem(String date, String name, int kcal, byte[] ima) {
        this.food_dates = date;
        this.food_name = name;
        this.food_kcal = kcal;
        this.food_image_name = ima;
    }

    public String getDates() {
        return food_dates;
    }
    public void setDates(String date) {
        this.food_dates = date;
    }

    public String getName() {
        return food_name;
    }
    public void setName(String name) {
        this.food_name = name;
    }

    public int getKcal() {
        return food_kcal;
    }
    public void setKcal(int Kcal) {
        this.food_kcal = Kcal;
    }

    public byte[] getImageID() {return food_image_name;}
    public void setImageID(byte[] ima) {
        this.food_image_name = ima;
    }
}
