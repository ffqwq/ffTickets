package com.yrh.ff_ticket.jwt;

import lombok.Data;


@Data
public class Token {
    private String token;

    public Token(){
        this.token=null;
    }

    public Token(String token){
        this.token=token;
    }
}
