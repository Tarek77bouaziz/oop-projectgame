package PROJECT;

public class Mage extends Personnage {

   
    public Mage(String nom) {
        super(nom, 100, 25, 10); 
    }

   
    public Mage(String nom, int pointsDeVie, int attaque, int defense) {
        super(nom, pointsDeVie, attaque, defense);
    }

    @Override
    public void utiliserCompetence(Personnage cible) {
        System.out.println( nom + " lance une Boule de Feu dévastatrice !" );

        
        int degats = (this.attaque * 2) - cible.defense;
        degats = Math.max(degats, 0); 
        cible.pointsDeVie = Math.max(0, cible.pointsDeVie - degats);
        System.out.printf(RED + "%s inflige %d dégâts à %s !\n" + RESET, nom, degats, cible.getNom());

        if (!cible.estVivant()) {
            System.out.println(YELLOW + cible.getNom() + " a été réduit en cendres !" + RESET);
        }
    }
}
