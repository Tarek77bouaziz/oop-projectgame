package PROJECT;

public class Guerrier extends Personnage {

    
    private static final String RESET = "\u001B[0m";
    private static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";
    
    

   
    public Guerrier(String nom) {
        super(nom, 150, 20, 15); 
    }

    
    public Guerrier(String nom, int pointsDeVie, int attaque, int defense) {
        super(nom, pointsDeVie, attaque, defense);
    }

    @Override
    public void utiliserCompetence(Personnage cible) {
        System.out.println(GREEN + nom + " déclenche une Frappe Puissante !" + RESET);

        
        int degats = (this.attaque * 2) - cible.defense;
        degats = Math.max(degats, 0); 
        cible.pointsDeVie = Math.max(0, cible.pointsDeVie - degats); 

       
        System.out.printf(RED + "%s inflige %d points de dégâts à %s !\n" + RESET, nom, degats, cible.getNom());

        
        if (!cible.estVivant()) {
            System.out.println( cible.getNom() + " est vaincu !" );
        }
    }
}

