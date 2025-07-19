package org.iset.emplois.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
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
public class ModuleCours {

           @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Id 
private Long id;
@Column (unique = true, length =300 )
private String nommodulefr;
@Column (unique = true , length =100, columnDefinition = "NVARCHAR(255)")
private String nommodulear;
 @ElementCollection 
private List<Integer> modules;
}
