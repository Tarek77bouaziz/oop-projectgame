package PROJECT;

public class Voleur extends Personnage {

    
    public Voleur(String nom) {
        super(nom, 90, 15, 10); 
    }


    public Voleur(String nom, int pointsDeVie, int attaque, int defense) {
        super(nom, pointsDeVie, attaque, defense);
    }

    @Override
    public void utiliserCompetence(Personnage cible) {
        System.out.println( nom + " effectue une attaque furtive dévastatrice !");

       
        int degats = (this.attaque * 3) - cible.defense;
        degats = Math.max(degats, 0); 
        cible.pointsDeVie = Math.max(0, cible.pointsDeVie - degats); 

        
        System.out.printf(RED + "%s inflige %d dégâts critiques à %s !\n" + RESET, nom, degats, cible.getNom());

   
        if (!cible.estVivant()) {
            System.out.println(YELLOW + cible.getNom() + " a été abattu dans l'ombre !" + RESET);
        }
    }
}
