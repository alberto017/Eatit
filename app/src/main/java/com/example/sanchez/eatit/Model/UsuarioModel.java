package com.example.sanchez.eatit.Model;

public class UsuarioModel {

    private String Name;
    private String Phone;
    private String Password;

    public UsuarioModel() {
    }//UsuarioModel

    public UsuarioModel(String name, String phone, String password) {
        Name = name;
        Phone = phone;
        Password = password;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getName() {
        return Name;
    }//getName

    public void setName(String name) {
        Name = name;
    }//setName

    public String getPassword() {
        return Password;
    }//getPassword

    public void setPassword(String password) {
        Password = password;
    }//setPassword
}//UsuarioModel
