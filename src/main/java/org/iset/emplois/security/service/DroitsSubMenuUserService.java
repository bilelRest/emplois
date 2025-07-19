package org.iset.emplois.security.service;

import org.iset.emplois.security.entities.AppUser;
import org.iset.emplois.security.entities.DroitsSubMenuUser;
import org.iset.emplois.security.entities.SubMenu;
import org.iset.emplois.security.repositories.DroitsSubMenuUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DroitsSubMenuUserService {

    @Autowired
    private DroitsSubMenuUserRepository droitsSubMenuUserRepository;

    public boolean hasAccess(AppUser user, SubMenu subMenu) {
        DroitsSubMenuUser droits = droitsSubMenuUserRepository.findByUserAndSubMenu(user, subMenu);
        return droits != null && (droits.isCanSearch() || droits.isCanAdd() || droits.isCanUpdate() || droits.isCanDelete());
    }
 

    
}

