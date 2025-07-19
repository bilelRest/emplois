package org.iset.emplois.model;
 
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
 
import org.springframework.format.annotation.DateTimeFormat;

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
public class Seance {
     @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
 private Long id;

 private Long numero;
    private String nomMatiere;
    private String nomGroupe;
    private String nomEnseignant;
    private String nomSalle;
    private String creneauHoraire;
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime heureDebut;
    
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime heureFin;
    private boolean par15;
    private boolean typeCours;
    private String jourSemaine;
    private String styleBootstrap;

    public LocalDateTime getStartDateTime() {
        // Calculer la date à partir du jour de la semaine
        LocalDate today = LocalDate.now();
        int daysToAdd = getDaysUntil(jourSemaine);
        LocalDate startDate = today.plusDays(daysToAdd);

        // Combiner la date et l'heure de début pour obtenir un LocalDateTime
        LocalTime startTime = LocalTime.parse(heureDebut.toString(), DateTimeFormatter.ofPattern("HH:mm"));
        return LocalDateTime.of(startDate, startTime);
    }

    public LocalDateTime getEndDateTime() {
        // Calculer la date de fin à partir de l'heure de fin
        LocalTime endTime = LocalTime.parse(heureFin.toString(), DateTimeFormatter.ofPattern("HH:mm"));
        return getStartDateTime().withHour(endTime.getHour()).withMinute(endTime.getMinute());
    }

    private int getDaysUntil(String jourSemaine) {
        // Logique pour calculer le nombre de jours jusqu'à un jour particulier
        switch (jourSemaine) {
            case "Lundi":
                return 1 - LocalDate.now().getDayOfWeek().getValue();
            case "Mardi":
                return 2 - LocalDate.now().getDayOfWeek().getValue();
            case "Mercredi":
                return 3 - LocalDate.now().getDayOfWeek().getValue();
            case "Jeudi":
                return 4 - LocalDate.now().getDayOfWeek().getValue();
            case "Vendredi":
                return 5 - LocalDate.now().getDayOfWeek().getValue();
            case "Samedi":
                return 6 - LocalDate.now().getDayOfWeek().getValue();
            default:
                return 0; // Si le jour est inconnu
        }
    }
}
