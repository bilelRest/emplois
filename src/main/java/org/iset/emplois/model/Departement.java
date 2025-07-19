package org.iset.emplois.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Departement {

           @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Id 
private Long id;
@Column (unique = true, length =300 )
private String nomdepfr;
@Column (unique = true, columnDefinition = "NVARCHAR(255)")
private String nomdepar;
}
