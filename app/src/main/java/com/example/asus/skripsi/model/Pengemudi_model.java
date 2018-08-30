package com.example.asus.skripsi.model;

public class Pengemudi_model {
    private String Namapengemudi;
    private String Token;
    private String Username;
    private String Password;
    private String State;
    private int Idpengemudi;
    private boolean Switch;

    public Pengemudi_model(){

    }

    public String getNamapengemudi() {
        return Namapengemudi;
    }

    public void setNamapengemudi(String namapengemudi) {
        Namapengemudi = namapengemudi;
    }

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public int getIdpengemudi() {
        return Idpengemudi;
    }

    public void setIdpengemudi(int idpengemudi) {
        Idpengemudi = idpengemudi;
    }

    public boolean isSwitch() {
        return Switch;
    }

    public void setSwitch(boolean aSwitch) {
        Switch = aSwitch;
    }

}
