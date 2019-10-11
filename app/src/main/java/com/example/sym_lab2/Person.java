package com.example.sym_lab2;

public class Person {
    private String nom;
    private String prenom;
    private int age;

    public Person(String n, String p, int a){
        nom    = n;
        prenom = p;
        age    = a;
    }

    public String getNom(){
        return nom;
    }

    public String getPrenom(){
        return prenom;
    }

    public int getAge(){
        return age;
    }

    public String toString(){
        return nom + " " + prenom + ", " + age;
    }
}
