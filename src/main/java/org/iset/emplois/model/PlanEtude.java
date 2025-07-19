package org.iset.emplois.model;

import java.util.Date;
import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class PlanEtude {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(unique = true, length = 300)
    private String nomplanfr;

    @Column(unique = true, columnDefinition = "NVARCHAR(255)")
    private String nomplanar;

    private String description;

    private int annee;

    // Utilisation de @Temporal pour spécifier qu'il s'agit d'une date (sans heure)
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")  // Format de date
    private Date dateHabilitation;

    private boolean habilite;

    // Utilisation de @Temporal pour spécifier que c'est une date sans heure
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")  // Format de date
    private Date datedebut;

    // Utilisation de @Temporal pour spécifier que c'est une date sans heure
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")  // Format de date
    private Date datefin;

 private boolean actif;
private boolean visible;

    @ManyToOne
    @JoinColumn(name = "parcours_id", nullable = true)
    private Parcours parcours;
}
