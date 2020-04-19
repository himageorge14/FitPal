package com.fitpal.fitpal.model;

public class UserMeal {

    public String Calcium;
    public String Calories;
    public  String Carb;
    public String Date;
    public  String Fat;
    public  String Fibre;
    public String Iron;
    public  String MealName;
    public  String Phosphorous;
    public  String Protein;

    public UserMeal() {
    }

    public UserMeal(String calcium, String calories, String carb, String date, String fat, String fibre, String iron, String mealName, String phosphorous, String protein) {
        Calcium = calcium;
        Calories = calories;
        Carb = carb;
        Date = date;
        Fat = fat;
        Fibre = fibre;
        Iron = iron;
        MealName = mealName;
        Phosphorous = phosphorous;
        Protein = protein;
    }
//
//    public String getDate1() {
//        return Date;
//    }
//
//
//    public String getMealName() {
//        return MealName;
//    }
}
