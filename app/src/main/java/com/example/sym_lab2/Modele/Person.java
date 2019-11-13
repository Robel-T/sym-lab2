package com.example.sym_lab2.Modele;

public class Person {
    private String lastName;
    private String firstName;
    private String gender;
    private int phone;

    public Person(String n, String p, String g, int ph){
        lastName    = n;
        firstName = p;
        gender  = g;
        phone  = ph;
    }

    public String getLastName(){
        return lastName;
    }

    public String getFirstName(){
        return firstName;
    }


    public String toString(){
        return lastName + " " + firstName + ", " + gender + "\nMobile: " + phone;
    }

    public String getGender() {
        return gender;
    }

    public int getPhone() {
        return phone;
    }
}
