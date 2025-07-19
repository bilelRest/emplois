package org.iset.emplois.security.entities;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class AppRole {
@Id
 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roleId;
    private String roleName;
    private String description;

    // Relation ManyToMany avec Menu
    @ManyToMany
    @JoinTable(
        name = "role_menu", // Nom de la table de jointure
        joinColumns = @JoinColumn(name = "role_id"), // Colonne pour AppRole
        inverseJoinColumns = @JoinColumn(name = "menu_id") // Colonne pour Menu
    )
    private List<Menu> menus; // Liste des menus associés

}
