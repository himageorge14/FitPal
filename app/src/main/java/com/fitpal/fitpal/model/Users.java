package com.fitpal.fitpal.model;

public class Users {

    public int Age;
    public float BMI;
    public String Email;
    public String Gender;
    public float Goal;
    public float Height;
    public long KeyUserMeals;
    public float Weight;

    public Users() {
    }

    public Users(int age, float BMI, String email, String gender, float goal, float height, long keyUserMeals, float weight) {
        Age = age;
        this.BMI = BMI;
        Email = email;
        Gender = gender;
        Goal = goal;
        Height = height;
        KeyUserMeals = keyUserMeals;
        Weight = weight;
    }

}
