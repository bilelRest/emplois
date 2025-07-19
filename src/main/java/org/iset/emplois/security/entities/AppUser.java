package org.iset.emplois.security.entities;

  
import java.util.ArrayList; 
import java.util.List;
import java.util.stream.Collectors;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column; 
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType; 
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;

import  org.iset.emplois.model.Departement;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

 
@Entity
 @Data
 @AllArgsConstructor
 @NoArgsConstructor
/*  implements UserDetails  */
public class AppUser  {
    @Id 
    private String userId;
    @Column(unique = true)
    private String username; // Nom d'utilisateur
    private String password; // Mot de passe (hashé)
    @Column(unique = true)
    private String email; 
   
 private boolean active;

 private int semestre;

 private int annee;
  
 @ManyToMany(fetch = FetchType.EAGER)
 private List<AppRole> appRoles= new ArrayList<>();


 @ManyToMany(fetch = FetchType.EAGER)
 private List<Departement> departements= new ArrayList<>();

 @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
private List<DroitsSubMenuUser> droits = new ArrayList<>(); // Droits associés aux sous-menus

 
// Dans AppUser.java
@Transient
private List<Menu> accessibleMenus = new ArrayList<>();
 
/* 

// Implémentation des méthodes de UserDetails

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Convertit les AppRole en SimpleGrantedAuthority
        return appRoles.stream()
            .map(role -> new SimpleGrantedAuthority(role.getRoleName()))
            .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    // Tu peux adapter ces méthodes selon ta logique métier

    @Override
    public boolean isAccountNonExpired() {
        return true; // ou ta propre logique
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // ou ta propre logique
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // ou ta propre logique
    }

    @Override
    public boolean isEnabled() {
        return active;
    }
*/
}


