package com.example.sym_lab2;

public class Person {
    private String nom;
    private String prenom;
    private String genre;
    private int phone;

    public Person(String n, String p, String g, int ph){
        nom    = n;
        prenom = p;
        genre  = g;
        phone  = ph;
    }

    public String getNom(){
        return nom;
    }

    public String getPrenom(){
        return prenom;
    }


    public String toString(){
        return nom + " " + prenom + ", " + genre + "\nMobile: " + phone;
    }

    public String getGenre() {
        return genre;
    }

    public int getPhone() {
        return phone;
    }
}
