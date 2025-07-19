package org.iset.emplois.security.service;

import java.util.List;

import org.iset.emplois.security.entities.AppRole;
import org.iset.emplois.security.entities.AppUser;
import org.iset.emplois.security.entities.DroitsSubMenuUser;

public interface SecurityService {
  AppUser saveNewUser(String username, String passqord,String rePassowrd);
AppRole saveNewRole(String roleName, String description);
void addRoleToUser(String username,String roleName);
void removeRoleFromUser(String username,String roleName);
AppUser loadByUserName(String username);


public List<DroitsSubMenuUser> getUserDroits(String userId);

}
