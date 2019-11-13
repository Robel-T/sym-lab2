package com.example.sym_lab2.Modele;

public class Author {
    int id;
    String first_name;
    String last_name;
    String email;
    Post post;

    Author(int i, String fn, String ln, String e, Post p){
        first_name = fn;
        last_name = ln;
        email = e;
        post = p;
        id = i;
    }

    public String toString(){
        return first_name + " " + last_name;
    }
}
