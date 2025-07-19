package org.iset.emplois.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class UE {

           @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Id 
private Long id;
@Column (unique = true,  columnDefinition = "NVARCHAR(300)" )
private String nomUE;
@Column(columnDefinition = "NVARCHAR(MAX)") 
private String descriptionUE;
@Enumerated(EnumType.STRING) // Pour mapper un enum comme une chaîne de caractères
private TypeUE typeUE;
private double creditUE;
private double coefUE;
private boolean actif;
private boolean visible;
 @ManyToMany (fetch = FetchType.EAGER)
 private List<Parcours> parcoursList= new ArrayList<>();
/*@ManyToOne
    @JoinColumn(name = "parcours_id", nullable = true)
    private Parcours parcours;*/
}
