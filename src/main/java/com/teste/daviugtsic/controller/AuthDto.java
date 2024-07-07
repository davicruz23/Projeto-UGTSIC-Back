package com.teste.daviugtsic.controller;

public record AuthDto(String username,String password) {

    public String getUsername() {
        return username();
    }
    public String getPassword(){
        return password();
    }
}
