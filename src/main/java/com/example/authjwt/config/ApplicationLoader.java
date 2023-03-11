package com.example.authjwt.config;

import com.example.authjwt.model.Role;
import com.example.authjwt.model.Utilisateur;
import com.example.authjwt.repository.RoleRepository;
import com.example.authjwt.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ApplicationLoader implements CommandLineRunner {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
        if (roleRepository.count() <= 0) {
            System.out.println("Load fixtures");
            loadRoles();
            utilisateurRepository.save(new Utilisateur(
                    "admin",
                    new BCryptPasswordEncoder().encode("admin"),
                    roleRepository.findByLibelle("admin").orElseThrow(),
                    true
            ));
        }
    }

    private void loadRoles() {
        List<Role> roles = List.of(
                new Role("admin"),
                new Role("user")
        );
        roleRepository.saveAll(roles);
    }
}
