package org.iset.emplois.service;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.iset.emplois.model.JourSemaine;
import org.iset.emplois.model.SeanceCours;
import org.iset.emplois.repository.SeanceCoursRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SeanceCoursService {
    @Autowired
    private SeanceCoursRepository seanceCoursRepository;

    public Map<JourSemaine, List<SeanceCours>> getCellulesParJour() {
        return seanceCoursRepository.findAll()
                .stream()
                .collect(Collectors.groupingBy(SeanceCours::getJourSemaine,
                        () -> new EnumMap<>(JourSemaine.class),
                        Collectors.toList()));
    }

    public List<SeanceCours> getCellulesByJour(JourSemaine jourSemaine) {
        return seanceCoursRepository.findByJourSemaine(jourSemaine);



    }

 
    public List<SeanceCours> getSeancesByJourSemaineAndNomGroupe(JourSemaine jourSemaine, String nomGroupe) {
        return seanceCoursRepository.findByJourSemaineAndNomGroupe(jourSemaine, nomGroupe);
    }
 
    public List<SeanceCours> getSeancesByJourSemaineAndNomSalle(JourSemaine jourSemaine, String nomSalle) {
        return seanceCoursRepository.findByJourSemaineAndNomSalle(jourSemaine, nomSalle);
    }
 
    public List<SeanceCours> getSeancesByJourSemaineAndNomEnseignant(JourSemaine jourSemaine, String nomEnseignant) {
        return seanceCoursRepository.findByJourSemaineAndNomEnseignant(jourSemaine, nomEnseignant);
    }

    public List<SeanceCours> getAllSeanceCoursses() {
        return seanceCoursRepository.findAll();
    }

    public void mettreAJourOffsetSelonPositionEtOrdreLigne() {
        seanceCoursRepository.mettreAJourOffsetSelonPositionEtOrdreLigne();
    }

    public List<String> getDepartementsDistincts() {

        return seanceCoursRepository.findDistinctDepartements();
    }
    public List<String> getCreneauHorairesDistincts() {

        return seanceCoursRepository.findDistinctCreneauHoraires();
    }
    public List<String> getMatieresDistincts() {

        return seanceCoursRepository.findDistinctMatieres();
    }


    public List<String> getModulesDistincts() {

        return seanceCoursRepository.findDistinctModules();
    }


    public List<String> getJourSemainesDistincts() {

        return seanceCoursRepository.findDistinctjourSemaines();
    }


    public List<String> getSallesDistincts() {

        return seanceCoursRepository.findDistinctSalles();
    }

    public List<String> getGroupesDistincts() {

        return seanceCoursRepository.findDistinctGroupes();
    }
    public List<String> getMatieresByGroupe(String groupe) {
        return seanceCoursRepository.findDistinctMatieresByGroupe(groupe);
    }

    //findDistinctMatieresByGroupe

    public List<String> getEnseignantsDistincts() {

        return seanceCoursRepository.findDistinctEnseignants();
    }

    // Récupérer les matières distinctes par département  en tenant compte des valeurs null
public List<String> getDistinctNiveauxByDepartement(String departement) {
    return seanceCoursRepository.findDistinctNiveauxByDepartement(departement);
}

}
