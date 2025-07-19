package org.iset.emplois.repository;

import java.util.List;

import org.iset.emplois.model.JourSemaine;
import org.iset.emplois.model.SeanceCours;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;
@Repository
public interface SeanceCoursRepository extends JpaRepository<SeanceCours,Long> {
    // Méthode pour récupérer les cellules pour un jour spécifique
    List<SeanceCours> findByJourSemaine(JourSemaine jourSemaine);

 // Récupérer les séances pour un jour spécifique et un groupe donné
 List<SeanceCours> findByJourSemaineAndNomGroupe(JourSemaine jourSemaine, String nomGroupe);

 // Récupérer les séances pour un jour spécifique et une salle donnée
 List<SeanceCours> findByJourSemaineAndNomSalle(JourSemaine jourSemaine, String nomSalle);

 // Récupérer les séances pour un jour spécifique et un enseignant donné
 List<SeanceCours> findByJourSemaineAndNomEnseignant(JourSemaine jourSemaine, String nomEnseignant);
 

      @Modifying
    @Transactional
    @Query("UPDATE SeanceCours s SET s.offset = s.position WHERE s.ordreLigne = 1 AND s.position != 0")
    void mettreAJourOffsetSelonPositionEtOrdreLigne();

    // Liste des enseignants sans doublons
    @Query("SELECT DISTINCT s.nomEnseignant FROM SeanceCours s")
    List<String> findDistinctEnseignants();

    // Liste des groupes sans doublons
    @Query("SELECT DISTINCT s.nomGroupe FROM SeanceCours s")
    List<String> findDistinctGroupes();
    

    // Liste des Matieres by Goupe sans doublons
    @Query("SELECT DISTINCT s.nomMatiere FROM SeanceCours s WHERE (:groupe IS NULL OR s.nomGroupe = :groupe)")
    List<String> findDistinctMatieresByGroupe(@Param("groupe") String groupe);
    
    

    // Liste des départements sans doublons
    @Query("SELECT DISTINCT s.nomDepartement FROM SeanceCours s")
    List<String> findDistinctDepartements();

  // Liste des creneauHoraires sans doublons
  @Query("SELECT DISTINCT s.creneauHoraire FROM SeanceCours s")
  List<String> findDistinctCreneauHoraires();

  // Liste des jour dela semaine sans doublons
  @Query("SELECT DISTINCT s.jourSemaine FROM SeanceCours s")
  List<String> findDistinctjourSemaines();
// Liste des jour dela semaine sans doublons
@Query("SELECT DISTINCT s.nomMatiere FROM SeanceCours s")
List<String> findDistinctMatieres();


// Liste des modules sans doublons
@Query("SELECT DISTINCT s.module FROM SeanceCours s")
List<String> findDistinctModules();

    // Récupérer tous les niveaux distincts par département
 @Query("SELECT DISTINCT p.nomGroupe FROM SeanceCours p WHERE (:departement IS NULL OR p.nomDepartement = :departement)")
List<String> findDistinctNiveauxByDepartement( @Param("departement") String departement);


    // Liste des salles sans doublons
    @Query("SELECT DISTINCT s.nomSalle FROM SeanceCours s")
    List<String> findDistinctSalles();


}
