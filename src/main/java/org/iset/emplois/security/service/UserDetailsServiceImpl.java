package org.iset.emplois.security.service;
 
import org.iset.emplois.security.entities.AppUser; 
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
 

import java.util.ArrayList;
import java.util.Collection; 

@Service  
public class UserDetailsServiceImpl implements UserDetailsService {

   @Autowired
     private SecurityService securityService;
 
 /* Solution Initiale*/

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser= securityService.loadByUserName(username);
Collection<GrantedAuthority> authorities= new ArrayList<>();
appUser.getAppRoles().forEach(role->{
SimpleGrantedAuthority authority= new SimpleGrantedAuthority("ROLE_" + role.getRoleName());
authorities.add(authority);

});
System.out.println(authorities.toString());


return org.springframework.security.core.userdetails.User.builder()
                .username(appUser.getUsername())
                .password(appUser.getPassword()) // Mot de passe encodé
                .authorities(authorities) // Ajouter les rôles/autorités
                .build();
    }

 

/* D2 Solution
@Autowired
private DroitsSubMenuUserService droitsSubMenuUserService;//Service securityService;

@Autowired
private  MenuService menuService;

@Override
public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    AppUser appUser = securityService.loadByUserName(username);

    if (appUser == null) {
        throw new UsernameNotFoundException("Utilisateur non trouvé : " + username);
    }

    Collection<GrantedAuthority> authorities = new ArrayList<>();
    List<Menu> accessibleMenus = new ArrayList<>();

    appUser.getAppRoles().forEach(role -> {
        authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRoleName()));

        List<Menu> roleMenus = menuService.getMenusByRole(role.getRoleName());

        for (Menu menu : roleMenus) {
            List<SubMenu> accessibleSubMenus = menu.getSubMenus().stream()
                .filter(subMenu -> droitsSubMenuUserService.hasAccess(appUser, subMenu))
                .toList();

            if (!accessibleSubMenus.isEmpty()) {
                menu.setSubMenus(accessibleSubMenus);
                accessibleMenus.add(menu);
            }
        }
    });

    appUser.setAccessibleMenus(accessibleMenus);

    return org.springframework.security.core.userdetails.User.builder()
            .username(appUser.getUsername())
            .password(appUser.getPassword())
            .authorities(authorities)
            .build();
}

 */   
}


