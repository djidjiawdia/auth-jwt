package com.example.authjwt.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Utilisateur extends AbstractModel {

    private String username;

    private String password;

    @ManyToOne()
    private Role role;

    private Boolean statut;

}
