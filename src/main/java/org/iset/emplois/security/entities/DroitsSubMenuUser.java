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

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DroitsSubMenuUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private AppUser user; // L'utilisateur concerné

    @ManyToOne
    @JoinColumn(name = "submenu_id", nullable = false)
    private SubMenu subMenu;// Le sous-menu concerné

    private boolean canAdd;
    private boolean canUpdate;
    private boolean canSearch;
    private boolean canDelete;
    private boolean canPrint;
}

