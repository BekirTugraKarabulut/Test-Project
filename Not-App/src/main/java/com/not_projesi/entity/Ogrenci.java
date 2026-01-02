package com.not_projesi.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

import java.time.LocalDateTime;

@Entity
@Table(name = "ogrenci")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ogrenci implements UserDetails {

    @Id
    @NotEmpty
    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "ogrenci_adi")
    private String ogrenciAdi;

    @Column(name = "ogrenci_soyadi")
    private String ogrenciSoyadi;

    @Column(name = "ogrenci_email")
    private String ogrenciEmail;

    @Column(name = "ogrenci_sifre")
    private String ogrenciSifre;

    @Column(name = "reset_token")
    private String resetToken;

    @Column(name = "token_expiration")
    private LocalDateTime tokenExpiration;


    @ManyToOne
    @JoinColumn(name = "bolum_id", referencedColumnName = "bolum_id")
    private Bolum bolumList;

    @OneToMany(mappedBy = "ogrenci", cascade = CascadeType.ALL)
    private List<DersNotu> dersNotu;

    @OneToMany(mappedBy = "ogrenci", cascade = CascadeType.ALL)
    private List<Ders> dersList;

    @OneToMany(mappedBy = "ogrenci", cascade = CascadeType.ALL)
    private List<Begen> begen;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return ogrenciSifre;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
