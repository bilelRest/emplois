package org.iset.emplois.security.service;

 
 
import org.iset.emplois.security.entities.AppUser;
import org.iset.emplois.security.repositories.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AppUserService {
    @Autowired
    private AppUserRepository userRepository;

    public AppUser findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

 

    /**
     * Récupère l'utilisateur actuellement authentifié
     * @return l'utilisateur authentifié (AppUser)
     */
    public AppUser getCurrentUser() {
        // Récupérer l'utilisateur principal depuis le contexte de sécurité
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof org.springframework.security.core.userdetails.User) {
            String username = ((org.springframework.security.core.userdetails.User) principal).getUsername();
            // Charger l'utilisateur depuis la base de données
            return userRepository.findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé : " + username));
        }

        // Si aucun utilisateur n'est authentifié
        throw new IllegalStateException("Aucun utilisateur authentifié");
    }
}

