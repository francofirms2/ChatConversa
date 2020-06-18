package com.example.chatconversa;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class UserRespuestaErrorReg {

    private List<String> username;
    private List<String> email;
    private List<String> token_enterprice;

    public UserRespuestaErrorReg() {
    }

    public UserRespuestaErrorReg(List<String> username, List<String> email, List<String> token_enterprice) {
        this.username = username;
        this.email = email;
        this.token_enterprice = token_enterprice;
    }

    public List<String> getUsername() {
        return username;
    }

    public void setUsername(List<String> username) {
        this.username = username;
    }

    public List<String> getEmail() {
        return email;
    }

    public void setEmail(List<String> email) {
        this.email = email;
    }

    public List<String> getToken_enterprice() {
        return token_enterprice;
    }

    public void setToken_enterprice(List<String> token_enterprice) {
        this.token_enterprice = token_enterprice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserRespuestaErrorReg that = (UserRespuestaErrorReg) o;
        return Objects.equals(username, that.username) &&
                Objects.equals(email, that.email) &&
                Objects.equals(token_enterprice, that.token_enterprice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, email, token_enterprice);
    }

    @Override
    public String toString() {
        return "UserRespuestaErrorReg{" +
                "username=" + username +
                ", email=" + email +
                ", token_enterprice=" + token_enterprice +
                '}';
    }
}
