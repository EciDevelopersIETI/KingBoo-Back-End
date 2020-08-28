package edu.eci.ieti.config;

import edu.eci.ieti.entities.User;

public class Token {

    private String accessToken;

    private User user;

    public Token(String accessToken) {
        this.accessToken = accessToken;
    }

    public Token(String accessToken, User user) {
        this.accessToken = accessToken;
        this.user = user;
    }
    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String access_token) {
        this.accessToken = access_token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
