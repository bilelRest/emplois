package org.iset.emplois.security.entities;
 

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString(exclude = "subMenus") // Exclut les subMenus de toString()
public class Menu {
      
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Identifiant du menu
    private int position;
    private String title; // Titre du menu
    private String description; // Titre du menu
    private String icon; // Icône du menu
@OneToMany(mappedBy = "menu", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
private List<SubMenu> subMenus;

    private boolean expanded; // Indique si le menu est développé par défaut

    public Menu(String title, String icon, List<SubMenu> subMenus) {
        this.title = title;
        this.icon = icon;
        this.subMenus = subMenus;
    }
}
