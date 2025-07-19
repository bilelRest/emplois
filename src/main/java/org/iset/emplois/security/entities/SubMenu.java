package org.iset.emplois.security.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity 
@ToString(exclude = "menu") // Exclut la relation parent dans toString()
public class SubMenu {
      
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Identifiant du sous-menu
    private int position;
    private String title; // Titre du sous-menu
    private String icon; // Icône du sous-menu
    private String url; // URL vers laquelle le sous-menu pointe
    private String description; // Titre du sous-menu

     // Constructor that accepts only the id
     public SubMenu(Long id) {
        this.id = id;
    }
     @ManyToOne
    @JoinColumn(name = "menu_id")

   

    private Menu menu;
    public SubMenu(String title, String url, String icon) {
        this.title = title;
        this.url = url;
        this.icon = icon;
    }
}
