package com.dh.ClinicaDentalV2.login.entity;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Entity
public class Usuario implements UserDetails {

    @Id
    @SequenceGenerator(name = "usuario_sequence", sequenceName = "usuario_sequence" , allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usuario_sequence")
    @Column(name = "id" , nullable = false)
    private Long id;
    private String nombre;
    private String email;
    @Column(nullable = false, unique = true)
    private String username;
    private String password;
    @Enumerated
    private Rol rol;

    public Usuario(String nombre, String email, String username, String password, Rol rol) {

        this.nombre = nombre;
        this.email = email;
        this.username = username;
        this.password = password;
        this.rol = rol;
    }

    public Usuario(){}

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
