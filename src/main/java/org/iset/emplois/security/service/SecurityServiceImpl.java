package org.iset.emplois.security.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.iset.emplois.security.entities.AppRole;
import org.iset.emplois.security.entities.AppUser;
import org.iset.emplois.security.entities.DroitsSubMenuUser;
import org.iset.emplois.security.repositories.AppRoleRepository;
import org.iset.emplois.security.repositories.AppUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class SecurityServiceImpl implements SecurityService {
    private AppUserRepository appUserRepository;
    private AppRoleRepository appRoleRepository;
    private PasswordEncoder passwordEncoder;
    @Override
    public void addRoleToUser(String username, String roleName) {
        AppUser appUser=appUserRepository.findByUsername(username).get();
       if(appUser==null) throw new RuntimeException("User not found");
        AppRole appRole=appRoleRepository.findByRoleName(roleName).get();
        if(appRole==null) throw new RuntimeException("Role not found");
appUser.getAppRoles().add(appRole);
    }

    @Override
    public AppUser loadByUserName(String username) {
          return appUserRepository.findByUsername(username).get();
    }

    @Override
    public void removeRoleFromUser(String username, String roleName) {
        AppUser appUser=appUserRepository.findByUsername(username).get();
       if(appUser==null) throw new RuntimeException("User not found");
        AppRole appRole=appRoleRepository.findByRoleName(roleName).get();
        if(appRole==null) throw new RuntimeException("Role not found");
appUser.getAppRoles().remove(appRole);
      
    }

    @Override
    public AppRole saveNewRole(String roleName, String description) {
       
        Optional<AppRole> role = appRoleRepository.findByRoleName(roleName);
        if (role.isPresent()) {
            
            throw new RuntimeException("Role "+ roleName+ " Already exist ");
        } else {
            // Gérez le cas où aucun rôle n'est trouvé
        }
    
        AppRole appRole= new AppRole() ;
       appRole.setRoleName(roleName);
       appRole.setDescription(description);
       appRoleRepository.save(appRole);
       
       return appRole;
    }

    @Override
    public AppUser saveNewUser(String username, String password, String rePassowrd) {
       if (! password.equals(rePassowrd)) throw new RuntimeException( "Password not mutch !!");
       String hachedPWD =  passwordEncoder.encode(password);
       AppUser appUser= new AppUser();
       appUser.setUserId(UUID.randomUUID().toString());
       appUser.setUsername(username);
       appUser.setPassword(hachedPWD);
       appUser.setActive(true);
AppUser savedAppUser=appUserRepository.save(appUser);

        return savedAppUser;
    }

public List<DroitsSubMenuUser> getUserDroits(String userId) {
        AppUser user = appUserRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        return user.getDroits();
    }

}
