package org.iset.emplois.model;
 
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
@Data 
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class CreneauHoraire {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String libelleFr;
    @Column( columnDefinition = "NVARCHAR(255)")
    private String libelleAr;
       @DateTimeFormat(pattern = "HH:mm")
    private LocalTime heureDebut;
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime heureFin;
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime heureDebutRamadan;
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime heureFinRamadan;
    private int position;
    private int taille;
    private double dureeSeance;
    private boolean compose;

    @ManyToMany
    @JoinTable(
        name = "creneau_composition",
        joinColumns = @JoinColumn(name = "creneau_id"),
        inverseJoinColumns = @JoinColumn(name = "composed_id")
    )
    @ToString.Exclude // Exclure pour éviter les problèmes
    private List<CreneauHoraire> composedCreneaux;

    
    @Override
public String toString() {
    return "CreneauHoraire{" +
            "id=" + id +
            ", libelleFr='" + libelleFr + '\'' +
            ", heureDebut=" + heureDebut +
            ", heureFin=" + heureFin +
            ", position=" + position +
            ", taille=" + taille +
            ", dureeSeance=" + dureeSeance +
            ", compose=" + compose +
            // Exclusion de composedCreneaux pour éviter des cycles
            '}';
}

}

