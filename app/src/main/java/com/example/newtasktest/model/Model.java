package com.example.newtasktest.model;

public class Model {
    private String name;
    private final String designation;
    private String hobby;
    private String gender;

     private String age;

    public Model(String name,String designation,String hobby,String gender,String age) {
        this.name = name;
        this.designation = designation;
        this.hobby = hobby;
        this.gender = gender;
        this.age= age;
    }

    public String getDesignation() {
        return designation;
    }

    public String getGender() {
        return gender;
    }

    public String getHobby() {
        return hobby;
    }

    public String getName() {
        return name;
    }

    public String getAge() {
        return age;
    }
}
