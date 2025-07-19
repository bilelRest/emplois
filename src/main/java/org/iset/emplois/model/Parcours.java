package org.iset.emplois.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Parcours {

           @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Id 
private Long id;
@Column (unique = true, length =300 )
private String nomparcoursfr;
@Column (unique = true, columnDefinition = "NVARCHAR(255)")
private String nomparcoursar;
@Enumerated(EnumType.STRING) // Pour mapper un enum comme une chaîne de caractères
private TypeParcours typeParcours;
@Column(columnDefinition = "NVARCHAR(MAX)") // Compatible avec SQL Server
// Pour gérer les longues descriptions
private String descriptionparcours;

private boolean actif;
private boolean visible;


    @ManyToOne
    @JoinColumn(name = "departement_id", nullable = true)
    private Departement departement ;

}
