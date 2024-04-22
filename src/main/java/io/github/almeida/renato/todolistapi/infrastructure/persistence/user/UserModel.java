package io.github.almeida.renato.todolistapi.infrastructure.persistence.user;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "TBUSR")
public class UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name = "USERNAME", length = 20)
    private String username;
    @Column(name = "PASSWORD", length = 200)
    private String password;

    public UserModel() {
    }

    public UserModel(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public record UserModelDTO(String username, String password){}

}
