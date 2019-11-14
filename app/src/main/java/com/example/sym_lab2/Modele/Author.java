package com.example.sym_lab2.Modele;

import com.google.gson.annotations.SerializedName;

public class Author {

    @SerializedName("id")
    int id;

    @SerializedName("first_name")
    private String first_name;

    @SerializedName("last_name")
    String last_name;

    @SerializedName("email")
    String email;




    public void setId(int i){
        id = i;
    }

    public void setFirst_name(String fn){
        first_name = fn;
    }

    public void setLast_name(String ln){
        last_name = ln;
    }

    public void setEmail(String e){
        email = e;
    }

    public int getId(){
        return id;
    }



    public String toString(){
        return id + ", " + first_name + " " + last_name + "\nEmail: " + email;
    }
}
