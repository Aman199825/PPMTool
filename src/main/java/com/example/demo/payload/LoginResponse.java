package com.example.demo.payload;

public class LoginResponse {
    private boolean sucess;
    private String token;

    public LoginResponse(boolean sucess, String token) {
        this.sucess = sucess;
        this.token = token;
    }

    public boolean isSucess() {
        return sucess;
    }

    public void setSucess(boolean sucess) {
        this.sucess = sucess;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "LoginResponse{" +
                "sucess=" + sucess +
                ", token='" + token + '\'' +
                '}';
    }
}
