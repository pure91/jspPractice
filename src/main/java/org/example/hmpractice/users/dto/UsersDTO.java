package org.example.hmpractice.users.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class UsersDTO {
    private int id;
    private String username;
    private String email;
    private String password;
    private Timestamp createdAt;
}
