package com.cource.mobilesoftware_project;

public class Fooditem {
    // 음식의 item을 담기 위한 class

    String food_dates;
    String food_name;
    String food_kcal;
    int food_image_id;

    public Fooditem(String date, String name, String kcal, int id) {
        this.food_dates = date;
        this.food_name = name;
        this.food_kcal = kcal;
        this.food_image_id = id;
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

    public String getKcal() {
        return food_kcal;
    }
    public void setKcal(String Kcal) {
        this.food_kcal = Kcal;
    }

    public int getImageID() {
        return food_image_id;
    }
    public void setImageID(int id) {
        this.food_image_id = id;
    }
}
