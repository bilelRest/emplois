package org.iset.emplois; 
import org.iset.emplois.repository.SeanceCoursRepository; 
import org.iset.emplois.security.repositories.MenuRepository; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication; 
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
 
 

@SpringBootApplication
//@AllArgsConstructor
public class EmploisApplication  extends SpringBootServletInitializer implements ApplicationRunner{
 @Autowired
	SeanceCoursRepository  seanceCoursRepository;

 @Autowired
    private MenuRepository menuRepository;
  

// private SecurityService securityService;
	public static void main(String[] args) {
		SpringApplication.run(EmploisApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {

		// Ajout des enregistrements

	//	 SeanceCours s1=new SeanceCours(1L,1, "Prof A", "Math", "Salle 101", "08:00 - 10:00", "col-6", true, false, true,JourSemaine.LUNDI);
	//	 SeanceCours s2= new SeanceCours(2L,2, "Prof B", "Physique", "Salle 102", "10:00 - 12:00", "col-4", false, true, false,JourSemaine.MARDI);
	//	seanceCoursRepository.save(s1);
	//	seanceCoursRepository.save(s2);


		 
	 // Supprimer les données existantes
     //   menuRepository.deleteAll();

        // Ajouter les menus et sous-menus
    /*    List<Menu> menus = List.of(
            new Menu("Parametrages", "bi-house-door", List.of(
                new SubMenu("Departements", "/creneaux/create", "bi-file-earmark-text"),
                new SubMenu("Salles", "/creneaux", "bi-file-earmark-code") 
            )),
            new Menu("Planification", "bi-person", List.of(
                new SubMenu("Plan Etude", "#", "bi-person-check"),
                new SubMenu("Matières", "#", "bi-person-x") 
            ))
        );

        // Sauvegarde des menus
        for (Menu menu : menus) {
            // Associer chaque sous-menu à son menu parent
            menu.getSubMenus().forEach(subMenu -> subMenu.setMenu(menu));
         //   menuRepository.save(menu);
        }
*/ 
        System.out.println("Menus initialisés en base de données.");
 /* 
        securityService.saveNewUser("admin2", "1234", "1234");
        securityService.saveNewUser("user", "1234", "1234");
        securityService.saveNewUser("nissen", "1234", "1234");

        securityService.saveNewRole("ADMIN", "Super Admin");
        
        securityService.saveNewRole("USER", "utilisateur");

        securityService.addRoleToUser("admin", "ADMIN");
        securityService.addRoleToUser("nissen", "ADMIN");
        securityService.addRoleToUser("user", "USER");  */
      //  securityService.saveNewUser("admin2", "1234", "1234");
     //   securityService.addRoleToUser("admin2", "ADMIN");
        System.out.println("Utilisateurs ajoutés avec succès.");
 
    }


}
