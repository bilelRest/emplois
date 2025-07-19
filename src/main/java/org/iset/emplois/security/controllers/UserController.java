package org.iset.emplois.security.controllers;
 
import java.util.ArrayList;
import java.util.List;
import java.util.Map; 
import java.util.UUID;

import org.iset.emplois.dto.DroitsSubMenuUserDTO;
import org.iset.emplois.dto.MenuSubMenuDTO;
import org.iset.emplois.model.Departement;
import org.iset.emplois.security.entities.AppRole;
import org.iset.emplois.security.entities.AppUser;
import org.iset.emplois.security.entities.DroitsSubMenuUser;
import org.iset.emplois.security.entities.Menu;
import org.iset.emplois.security.entities.SubMenu;
import org.iset.emplois.security.repositories.AppUserRepository;
import org.iset.emplois.security.service.AppRoleService;
import org.iset.emplois.security.service.MenuService;
import org.iset.emplois.security.service.SubMenuService;
import org.iset.emplois.service.DepartementService;
import org.iset.emplois.service.UserService;
import org.iset.emplois.wrapper.DroitsWrapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.AllArgsConstructor;
@Controller
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {
   //  @Autowired
    private MenuService menuService;
 //   @Autowired 3
    private AppUserRepository appUserRepository;
private UserService service;
private SubMenuService subMenuService;
private AppRoleService appRoleService;
private DepartementService departementService;
    private PasswordEncoder passwordEncoder;
 

    @GetMapping("/addUser")
    public String getAddUserForm(Model model) {
        // Charger les menus avec leurs sous-menus
        List<Menu> menus = menuService.getAllMenusWithSubMenus();
        
        // Ajouter un utilisateur vide pour le formulaire
        model.addAttribute("user", new AppUser());
        model.addAttribute("menus", menus);
        return "addUserForm"; // Nom de la page HTML
    }
 
@PostMapping("/saveUser")
public String saveUser(
        @ModelAttribute AppUser user,
        @ModelAttribute DroitsWrapper droitsWrapper) {

            String hachedPWD = "";
    List<DroitsSubMenuUserDTO> droitsDTOs = droitsWrapper.getDroits();
    droitsDTOs.forEach(droit -> {
        System.out.println(droit);
    });
                System.out.println("-- MAJ DES DONNESS --------- ");


  
hachedPWD =  passwordEncoder.encode(user.getPassword());

System.out.println("Password : "+user.getPassword());
user.setPassword(hachedPWD);
user.setActive(true);

 



     

        if (droitsDTOs == null || droitsDTOs.isEmpty()) {
            // Gérer le cas où aucun droit n'est envoyé
            user.getDroits().clear();

            System.out.println("droitsDTOs is empty !!!!");
        } else {
            // Réinitialiser les droits existants
            user.getDroits().clear();
           
          
            // Transformer les DTOs en entités DroitsSubMenuUser
            for (DroitsSubMenuUserDTO dto : droitsDTOs) {
                DroitsSubMenuUser droit = new DroitsSubMenuUser();
                SubMenu subMenu=subMenuService.findById(dto.getSubMenuId()).get();
                droit.setUser(user);
                droit.setSubMenu(subMenu); // Remplacez ceci par une recherche dans votre service si nécessaire
                droit.setCanAdd(dto.isCanAdd());
                droit.setCanUpdate(dto.isCanUpdate());
                droit.setCanSearch(dto.isCanSearch());
                droit.setCanDelete(dto.isCanDelete());
                droit.setCanPrint(dto.isCanPrint());
    
                user.getDroits().add(droit);
            }
        }
    
        // Sauvegarder l'utilisateur
      
 
   // Sauvegarder l'utilisateur
   appUserRepository.save(user);
        return "redirect:/users";
    }
    
    

/**
 * Set attributes for a DroitsSubMenuUser based on form data.
 */
private void setDroitAttributes(DroitsSubMenuUser droit, Map<String, String> attributes) {
    droit.setCanAdd("on".equals(attributes.get("canAdd")));
    droit.setCanUpdate("on".equals(attributes.get("canUpdate")));
    droit.setCanSearch("on".equals(attributes.get("canSearch")));
    droit.setCanDelete("on".equals(attributes.get("canDelete")));
    droit.setCanPrint("on".equals(attributes.get("canPrint")));
}

 @GetMapping
    public String list(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "5") int size,
        Model model) { 

          
            model.addAttribute("currentPage", page);
            model.addAttribute("pageSize", size);
            Page<AppUser> users = service.findusers(page, size);
        model.addAttribute("users", users);
        model.addAttribute("currentPageName", "Liste des users");
        return "administration/users/list";
    }

    @SuppressWarnings("null")
    @GetMapping("/create")
    public String createForm(Model model) {
        try {
            AppUser user = new AppUser();
            user.setUserId(UUID.randomUUID().toString());
            model.addAttribute("currentPageName", "Nouveau Utilisateur");
            model.addAttribute("user", user);


            System.out.println( "user " + user.toString());
    
          List<Departement> depExistants = departementService.findAll();
model.addAttribute("depExistants", depExistants != null ? depExistants : new ArrayList<>());

List<AppRole> rolesExistants = appRoleService.getAllRoles();
model.addAttribute("rolesExistants", rolesExistants != null ? rolesExistants : new ArrayList<>());

List<MenuSubMenuDTO> menuSubMenuDTOs = menuService.findSubMenusWithTitles();
model.addAttribute("menuSubMenuDTOs", menuSubMenuDTOs != null ? menuSubMenuDTOs : new ArrayList<>());

      
menuSubMenuDTOs.forEach(dto -> {
                System.out.println("SubMenuId: " + dto.getSubMenuId() +
                                   ", MenuTitle: " + dto.getMenuTitle() +
                                   ", SubMenuTitle: " + dto.getSubMenuTitle());
            });
            model.addAttribute("menuSubMenuDTOs", menuSubMenuDTOs);
    
            return "administration/users/form";
        } catch (Exception e) {
            e.printStackTrace(); // Affiche l'exception dans les logs
            throw e; // Renvoyer l'exception pour la gestion des erreurs
        }
    }
    
    @PostMapping("/save")
    public String save(@ModelAttribute AppUser user,  RedirectAttributes redirectAttributes) {
        service.save(user);
         // Ajout d'un message flash pour la redirection
    redirectAttributes.addFlashAttribute("message", "user ajouté avec succès.");
    redirectAttributes.addFlashAttribute("alertType", "info"); // Ajout du type d'alerte
      
        return "redirect:/users";
    }
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") String id, Model model)   {
        // Exemple de récupération du créneau
        AppUser   user= service.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user ID:" + id));
        model.addAttribute("currentPageName", "Mise à jour Utilisateur");
        model.addAttribute("user", user);
        List<Departement> depExistants=departementService.findAll();
        model.addAttribute("depExistants", depExistants);
    
              // Vérifiez que les données sont toujours initialisées
        List<AppRole> rolesExistants = appRoleService.getAllRoles();
        if (rolesExistants == null || rolesExistants.isEmpty()) {
            throw new IllegalStateException("No roles found!");
        }
        model.addAttribute("rolesExistants", rolesExistants);
    
         
     

        List<MenuSubMenuDTO> menuSubMenuDTOs = menuService.findSubMenusWithTitlesAndUserRights(user.getUserId());
 
    if (menuSubMenuDTOs==null)       menuSubMenuDTOs= menuService.findSubMenusWithTitles();
        

        model.addAttribute("menuSubMenuDTOs", menuSubMenuDTOs );
    
 
         return "administration/users/form"; // Le nom de la vue Thymeleaf
    }

    @PostMapping("/delete/{id}")
    public String deleteGrade(@PathVariable String id, RedirectAttributes redirectAttributes) {
        try {
            service.deleteById(id);
            redirectAttributes.addFlashAttribute("message", "Utilisateur supprimé avec succès.");
            redirectAttributes.addFlashAttribute("alertType", "danger"); // Message visuel
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Erreur lors de la suppression de l'utilisateur.");
            redirectAttributes.addFlashAttribute("alertType", "warning"); // Message visuel
        }
        return "redirect:/users";
    }



     @GetMapping("/search")
    public String searchEtablissements(
            @RequestParam(required = false) String nomdepfr,
            @RequestParam(required = false) String nomdepar,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            Model model) {

        // Définir les paramètres de pagination
        Pageable pageable = PageRequest.of(page, size);

        // Rechercher les départements avec pagination
        Page<AppUser> usersPage = service.searchusers(nomdepfr, nomdepar, pageable);

        // Ajouter les résultats et informations de pagination au modèle
        model.addAttribute("users", usersPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", usersPage.getTotalPages());
        model.addAttribute("nometabfr", nomdepfr);
        model.addAttribute("nometabar", nomdepar);
        model.addAttribute("currentPageName", "Liste des utilisateurs");
        return "administration/users/list";// Nom de la vue Thymeleaf
    }
}
