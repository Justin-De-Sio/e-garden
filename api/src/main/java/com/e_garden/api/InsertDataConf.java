package com.e_garden.api;

import com.e_garden.api.report.Report;
import com.e_garden.api.report.ReportService;
import com.e_garden.api.user.Roles;
import com.e_garden.api.user.User;
import com.e_garden.api.user.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;


@Configuration
public class InsertDataConf {
    /**
     * Offre la possibilité de charger des objects dans la base de données si elles n'est pas déjà chargé de ces object.
     * La base de données est remplis de ces "données de tests systématiquement".
     * TODO : A retirer pour la mise en production.
     *
     * @param userService injection de l'objet UserService
     * @return commande
     */
    @Bean
    CommandLineRunner commandLineRunner(UserService userService, ReportService reportService) {
        return args -> {
            List<User> users = List.of(
                    new User("thomas.robert@ece.fr", "thomas", "Thomas", "ROBERT", "ING5"),
                    new User("gabin.cornaire@ece.fr", "gabin", "Gabin", "CORNAIRE", "ING5"),
                    new User("justin.desio@ece.fr", "justin", "Justin", "DE SIO", "ING5"),
                    new User("enzo.romero@ece.fr", "enzo", "Enzo", "ROMERO", "ING5"),
                    new User("augustin.meaudre@ece.fr", "augustin", "Augustin", "MEAUDRE DESGOUTTES", "ING5"),
                    new User("mathieu.aubert@ece.fr", "mathieu", "Mathieu", "AUBERT", "ING4"),
                    new User("yolaine.drouart@ece.fr", "yolaine", "Yolaine", "DROUART", "ING4"),
                    new User("maxence.fromentin@ece.fr", "maxence", "Maxence", "FROMENTIN", "ING4"),
                    new User("khadidiatou.ascofare@ece.fr", "khadidiatou", "Khadidiatou", "ASCOFARE", "ING4"),
                    new User("annabelle.joyen@ece.fr", "annabelle", "Annabelle", "JOYEN", "ING4"),
                    new User("cleo.lilywunsch@ece.fr", "cleo", "Cleo", "LILYWUNSCH", "ING4"),
                    new User("daria.cousy@ece.fr", "daria", "Daria", "COUSY", "ING4"),
                    new User("alexis.boulic@ece.fr", "alexis", "Alexis", "BOULIC", "ING4")
            );
            // Insérer les utilisateurs s'ils n'existent pas déjà
            for (User user : users) {
                user.setRole(String.valueOf(Roles.ADMINISTRATEUR));
                user.setGroupNumber(3);
                if (userService.createUser(user) == null) {
                    continue;
                }
                //user = userService.saveUser(user);
                // Ajouter un compte-rendu rempli
                Report filledReport = new Report(user, "Compte-rendu rempli pour " + user.getName() + " " + user.getSurname());
                filledReport.setValidated(true);
                reportService.saveReport(filledReport);
                // Ajouter un compte-rendu vide
                Report emptyReport = new Report(user, "");
                reportService.saveReport(emptyReport);
            }
            System.out.println("Utilisateurs et comptes-rendus insérés avec succès.");
        };
    }
}

