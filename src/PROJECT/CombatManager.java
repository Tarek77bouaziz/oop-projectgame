package PROJECT;

import java.util.*;

public class CombatManager {
    private static final String RESET = "\u001B[0m";
    private static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";
    
   
  
    public static void lancerCombat(Personnage joueur, List<Personnage> adversaires, Scanner scanner) {
        System.out.println( """
                    
                    =======================================
                           DEBUT D'UN NOUVEAU COMBAT
                    =======================================
                    """ );

        while (joueur.estVivant() && adversaires.stream().anyMatch(Personnage::estVivant)) {
            afficherEtatCombat(joueur, adversaires);

            System.out.println( """
                    Que souhaitez-vous faire ?
                    1. Attaquer un adversaire
                    2. Utiliser une compétence spéciale
                    3. Fuir le combat
                    """ );

            int choix = lireChoix(scanner, 1,3);

            switch (choix) {
                case 1 -> attaquerAdversaire(joueur, adversaires, scanner);
                case 2 -> utiliserCompetence(joueur, adversaires, scanner);
                case 3 -> {
                    System.out.println(RED + joueur.getNom() + " décide de fuir. Combat terminé !" + RESET);
                    return;
                }
                default -> System.out.println(RED + "Action invalide. Veuillez réessayer." + RESET);
            }

            
            adversaires.stream()
                    .filter(Personnage::estVivant)
                    .forEach(adversaire -> {
                        if (new Random().nextInt(100) < 35) { 
                            System.out.println( adversaire.getNom() + " hésite et ne fait rien." );
                        } else {
                            adversaire.attaquer(joueur);
                        }
                    });

            if (!joueur.estVivant()) {
                System.out.println(RED + "\n" + joueur.getNom() + " a été vaincu au combat..." +RESET);
                return;
            }
        }

        System.out.println(GREEN + "\nFélicitations ! Vous avez triomphé de vos adversaires !" + RESET);
    }

    private static void attaquerAdversaire(Personnage joueur, List<Personnage> adversaires, Scanner scanner) {
        System.out.println( "\nChoisissez un adversaire à attaquer :");

        for (int i = 0; i < adversaires.size(); i++) {
            if (adversaires.get(i).estVivant()) {
                System.out.println( (i + 1) + ". " + adversaires.get(i).getNom() + " (PV : " + adversaires.get(i).getPointsDeVie() + ")" );
            }
        }

        int cibleIndex = lireChoix(scanner, 1, adversaires.size()) - 1;
        Personnage cible = adversaires.get(cibleIndex);

        if (cible.estVivant()) {
            joueur.attaquer(cible);
            if (!cible.estVivant()) {
                System.out.println(RED + cible.getNom() + " a été vaincu !" + RESET);
            }
        } else {
            System.out.println(cible.getNom() + " est déjà vaincu. Choisissez un autre adversaire." );
        }
    }

    private static void utiliserCompetence(Personnage joueur, List<Personnage> adversaires, Scanner scanner) {
        System.out.println( "\nChoisissez un adversaire pour utiliser votre compétence spéciale :" );

        for (int i = 0; i < adversaires.size(); i++) {
            if (adversaires.get(i).estVivant()) {
                System.out.println( (i + 1) +"."+ adversaires.get(i).getNom() + " (PV : " + adversaires.get(i).getPointsDeVie() + ")");
            }
        }

        int cibleIndex = lireChoix(scanner, 1, adversaires.size()) - 1;
        Personnage cible = adversaires.get(cibleIndex);

        if (cible.estVivant()) {
            joueur.utiliserCompetence(cible);
            if (!cible.estVivant()) {
                System.out.println(RED + cible.getNom() + " a été vaincu par votre compétence spéciale !" + RESET);
            }
        } else {
            System.out.println(cible.getNom() + " est déjà vaincu. Choisissez un autre adversaire.");
        }
    }

    public static List<Personnage> genererAdversaires(Personnage joueur, Map ile) {
        Random random = new Random();
        List<Personnage> adversaires = new ArrayList<>();

        System.out.println("\nDes adversaires vous attendent sur Map de " + ile.getNom() + " !" );

        adversaires.add(new Guerrier("Guerrier de " + ile.getNom(), 50, 15, 5)); 
        adversaires.add(new Mage("Mage de " + ile.getNom(), 40, 20, 3));         

        if (random.nextBoolean()) {
            adversaires.add(new Voleur("Voleur de " + ile.getNom(), 30, 10, 4));
        }

        System.out.println("Adversaires générés pour cette île :" );
        for (Personnage adversaire : adversaires) {
            System.out.println( "- " + adversaire.getNom() + " (PV : " + adversaire.getPointsDeVie() + ")");
        }

        return adversaires;
    }

    private static void afficherEtatCombat(Personnage joueur, List<Personnage> adversaires) {
        System.out.println( """
                    
                    =========================================
                            ÉTAT ACTUEL DU COMBAT
                    =========================================
                    """ );

        System.out.println(GREEN + "Votre personnage : " + joueur.getNom() + " (PV : " + joueur.getPointsDeVie() + ")" + RESET);

        for (Personnage adversaire : adversaires) {
            if (adversaire.estVivant()) {
                System.out.println(RED + "Adversaire : " + adversaire.getNom() + " (PV : " + adversaire.getPointsDeVie() + ")" + RESET);
            } else {
                System.out.println("Adversaire : " + adversaire.getNom() + " (Vaincu)" );
            }
        }

        System.out.println( "========================================");
    }

    private static int lireChoix(Scanner scanner, int min, int max) {
        int choix = -1;

        while (choix < min || choix > max) {
            System.out.print( "Entrez un nombre entre " + min + " et " + max + " : " );
            while (!scanner.hasNextInt()) {
                System.out.println(RED + "Veuillez entrer un nombre valide." + RESET);
                scanner.next();
            }
            choix = scanner.nextInt();
            scanner.nextLine(); 
        }

        return choix;
    }
}


