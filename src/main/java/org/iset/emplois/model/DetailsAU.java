package org.iset.emplois.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity; 
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(
    uniqueConstraints = {
       @UniqueConstraint(columnNames = {"parcours_id", "niveau","au_id"})
  }
)
public class DetailsAU {

   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Id 
private Long id;
@Column (unique = true, length =300 )
private String nomabr;
 private int niveau;


 private int nbregroupe;

 @ManyToOne
    @JoinColumn(name = "au_id", nullable = false)
    private AU au;


@ManyToOne
    @JoinColumn(name = "planetude_id", nullable = false)
    private PlanEtude planetude;

 @ManyToOne
    @JoinColumn(name = "parcours_id", nullable = false)
    private Parcours parcours;

}
