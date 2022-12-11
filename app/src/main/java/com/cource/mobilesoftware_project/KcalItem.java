package com.cource.mobilesoftware_project;

public class KcalItem {

    int index;
    int f_kcal;
    String f_name;

    public KcalItem(int i, int k, String n){
        this.index = i;
        this.f_kcal = k;
        this.f_name = n;
    }

    public KcalItem() {

    }

    public int getIndex() {
        return index;
    }
    public void setIndex(int index) {
        this.index = index;
    }
    public int getF_kcal() {
        return f_kcal;
    }
    public void setF_kcal(int f_kcal) {
        this.f_kcal = f_kcal;
    }
    public String getF_name() {
        return f_name;
    }
    public void setF_name(String f_name) {
        this.f_name = f_name;
    }
}
