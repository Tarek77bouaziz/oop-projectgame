package PROJECT;

import java.util.*;

public class Play02 {

    
    private static final String RESET = "\u001B[0m";
    private static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";
    private static final String BLUE = "\u001B[34m";
 

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
    
        
        List<Map> iles = Arrays.asList(
            new Map("Valdaeria", "Sable mouvant réduit la vitesse"),
            new Map("Kaelthorn", "Vents augmentent les attaques à distance"),
            new Map("Myronis", "Lave inflige des dégâts constants"),
            new Map("Lunacrest Isles", "Froid ralentit les ennemis")
        );
    
       
        afficherBienvenue();
    
        
        Personnage joueur = chargerOuCreerPersonnage(scanner);
    
      
        for (Map ile : iles) {
            afficherArriveeIle(ile);
    
            
            
           List<Personnage> adversaires = CombatManager.genererAdversaires(joueur,ile);
            
            while (joueur.estVivant() && adversaires.stream().anyMatch(Personnage::estVivant)) {
                CombatManager.lancerCombat(joueur, adversaires, scanner);
            }
    
            
            if (!joueur.estVivant()) {
                System.out.println(RED + "\n" + joueur.getNom() + " a été vaincu sur cette Map " + ile.getNom() + ". Fin du jeu !" + RESET);
                scanner.close();
                return;
            }
    
            
            System.out.println(GREEN + "\nBravo ! Vous avez triomphé des adversaires de " + ile.getNom() + "." + RESET);
    
            
            joueur.gagnerExperience();
    
            
            Sauvegarde.sauvegarderPersonnage(joueur, "player.txt");
        }
    
        
        afficherFin();
        scanner.close();
    }
    
    private static void afficherBienvenue() {
        System.out.println("""
                    
                    ***************************************************
                    *                                                 *
                    *           BIENVENUE DANS LE MONDE DE Eryndor   *
                    *                                                 *
                    ***************************************************
                    """);
    
        System.out.println(GREEN + """
                Préparez-vous à découvrir quatre îles énigmatiques, chacune abritant 
                des secrets anciens et des épreuves mortelles :
                
                  1. Valdaeria - Les sables vivants dévorent les imprudents.
                  2. Kaelthorn - Les vents hurlants mettent vos nerfs à l'épreuve.
                  3. Myronis - Des flammes éternelles consument tout sur leur passage.
                  4. LunacrestIsles - Un froid glacial fige même les âmes les plus braves.
    
                """);

System.out.println("""
                Chaque Map recèle des trésors inestimables et des dangers sans nom. 
                Oserez-vous vous aventurer dans l'inconnu ?
                
                Forgez votre destin, affrontez les créatures légendaires
                et dominez les forces de la nature. 
                
                Le courage est votre seule arme, la gloire votre seule récompense.
                Êtes-vous prêt à entrer dans la légende ?
                """);

        System.out.println(GREEN + """
                    ============================================
                       QUE LA QUÊTE COMMENCE MAINTENANT !
                    ============================================\n
                    """ + RESET);
    }
    
    private static Personnage chargerOuCreerPersonnage(Scanner scanner) {
        
        if (Sauvegarde.fichierExiste("player.txt")) {
            System.out.println( """
                    
                    
                    Que souhaitez-vous faire ?
                    1. Nouvelle Partie
                    2. Charger Partie
                    
                    """ );
    
            int choix = lireChoix(scanner, 1, 2);
    
            
            if (choix == 2) {
                System.out.println( "\nChargement de votre partie en cours..." );
                Personnage joueur = Sauvegarde.chargerPersonnage("player.txt");
    
                
                if (joueur != null) {
                    System.out.println(GREEN + "Partie chargée avec succès ! Bienvenue de retour, " + joueur.getNom() + " !" + RESET);
                    return joueur;
                } else {
                    System.out.println(RED + "Erreur : Impossible de charger la partie. Une nouvelle partie sera créée !" + RESET);
                }
            }
        }
    
        
        System.out.println( "Création d'une nouvelle partie..." );
        return initPersonnage(scanner);
    }
    

    private static Personnage initPersonnage(Scanner scanner) {
        System.out.println(BLUE + """
                    
                    **********************************************
                    *                                            *
                    *          CRÉATION DE VOTRE HÉROS          *
                    *                                            *
                    **********************************************
                    """ + RESET);
    
        System.out.print("Entrez le nom de votre personnage : ");
        String nom = scanner.nextLine().trim();
    
       
        while (nom.isEmpty()) {
            System.out.print(RED + "Le nom ne peut pas être vide. Veuillez réessayer : " + RESET);
            nom = scanner.nextLine().trim();
        }
    
        System.out.println(GREEN + """
         1️. Guerrier : Un combattant puissant, capable de résister aux coups les plus dévastateurs grâce à sa force brute et son endurance.
         2️. Mage : Un maître des arcanes, utilisant des sorts destructeurs pour contrôler les champs de bataille et écraser ses ennemis à distance.
         3️. Voleur : Un expert des ombres, spécialisé dans les attaques rapides, furtives et les tactiques sournoises pour surprendre ses adversaires.
                    """ + RESET);
    
        int choix = lireChoix(scanner, 1, 3);
    
        
        return switch (choix) {
            case 1 -> {
                System.out.println(GREEN + "\n Vous avez choisi le Guerrier, un héros courageux doté d'une force et d'une robustesse inégalées !" + RESET);
                yield new Guerrier(nom);
            }
            case 2 -> {
                System.out.println(GREEN + "\nVous avez choisi un Mage, maître des arcanes et des éléments !" + RESET);
                yield new Mage(nom);
            }
            case 3 -> {
                System.out.println(GREEN + "\nVous avez choisi un Voleur, agile et furtif !" + RESET);
                yield new Voleur(nom);
            }
            default -> {
                System.out.println(RED + "\nChoix invalide. Par défaut, vous serez un Guerrier." + RESET);
                yield new Guerrier(nom);
            }
        };
    }
    

    private static int lireChoix(Scanner scanner, int min, int max) {
        int choix = -1;
        boolean choixValide = false;
    
        while (!choixValide) {
            System.out.print( "Entrez un nombre entre " + min + " et " + max + " : " );
    
            if (scanner.hasNextInt()) {
                choix = scanner.nextInt();
                if (choix >= min && choix <= max) {
                    choixValide = true; 
                } else {
                    System.out.println(RED + "Le choix doit être compris entre " + min + " et " + max + "." + RESET);
                }
            } else {
                System.out.println(RED + "Veuillez entrer un nombre valide." + RESET);
                scanner.next(); 
            }
        }
    
        scanner.nextLine(); 
        return choix;
    }
    
    private static void afficherArriveeIle(Map ile) {
        System.out.println(BLUE + """
                    
                    ***********************************************
                    *                                             *
                    *             ARRIVÉE SUR UNE Carte           *
                    *                                             *
                    ***********************************************
                    """ + RESET);
    
        System.out.println( "                 " + ile.getNom().toUpperCase() );
    
        System.out.println("""
                    
                    Effet de la Carte :
                    """ +  "    " + ile.getEffet());
    
        System.out.println(BLUE + """
                    
                    ***********************************************
                    
                    """ + RESET);
    }
    
    private static void afficherFin() {
        System.out.println(GREEN + """
                    
                    ***********************************************
                    *                                             *
                    *               VOUS ÊTES UN HÉROS !          *
                    *                                             *
                    ***********************************************
                    """ + RESET);
    
        System.out.println( """
                    Félicitations ! Vous avez conquis toutes les Cartes
                    du monde de Eryndor.
    
                    Votre courage, votre force et votre stratégie
                    vous ont permis de triompher contre vents et marées.
                    """ );
    
        System.out.println(GREEN + """
                    Votre nom sera gravé dans l'histoire pour les
                    générations futures.
    
                    Merci d'avoir joué et d'avoir marqué Eryndor
                    de votre empreinte !
                    """ + RESET);
    
        System.out.println(GREEN + """
                    ***********************************************
                    *                                             *
                    *              FIN DE L'AVENTURE             *
                    *                                             *
                    ***********************************************
                    """ + RESET);
    }
    
}
