package PROJECT;

import java.io.Serializable;

public abstract class Personnage implements Serializable {
    
	protected static final String RESET = "\u001B[0m";
    protected static final String RED = "\u001B[31m";
    protected static final String GREEN = "\u001B[32m";
    protected static final String YELLOW = "\u001B[33m";

  
    protected String nom;
    protected int pointsDeVie;
    protected int attaque;
    protected int defense;

    
    public Personnage(String nom, int pointsDeVie, int attaque, int defense) {
        this.nom = nom;
        this.pointsDeVie = pointsDeVie;
        this.attaque = attaque;
        this.defense = defense;
    }

    
    public boolean estVivant() {
        return pointsDeVie > 0;
    }

    
    public void recevoirDegats(int degats) {
        this.pointsDeVie = Math.max(0, this.pointsDeVie - degats); 
        System.out.printf(RED + "%s subit %d dégâts. PV restants : %d\n" + RESET, nom, degats, pointsDeVie);
    }

    
    public void attaquer(Personnage cible) {
        if (!this.estVivant()) {
            System.out.println(YELLOW + nom + " ne peut pas attaquer car il est vaincu !" + RESET);
            return;
        }

        int degats = Math.max(0, this.attaque - cible.defense);
        System.out.println(GREEN + this.nom + " attaque " + cible.nom + " pour " + degats + " dégâts." + RESET);
        cible.recevoirDegats(degats);

        
        if (!cible.estVivant()) {
            System.out.println(YELLOW + cible.nom + " a été vaincu !" + RESET);
        }
    }

    
    public abstract void utiliserCompetence(Personnage cible);

    
    public void afficherEtat() {
        System.out.printf( "%s : PV = %d, Attaque = %d, Défense = %d\n" + nom, pointsDeVie, attaque, defense);
    }

    
    public void gagnerExperience() {
        System.out.println( nom + " gagne de l'expérience et devient plus fort !" );
        this.pointsDeVie += 10;  
        this.attaque += 5;       
        this.defense += 2;       

        System.out.printf("Nouveaux stats : PV = %d, Attaque = %d, Défense = %d\n", pointsDeVie, attaque, defense);
    }

    // Getters
    public String getNom() {
        return nom;
    }

    public int getPointsDeVie() {
        return pointsDeVie;
    }

    public int getAttaque() {
        return attaque;
    }

    public int getDefense() {
        return defense;
    }

  
    public void setPointsDeVie(int pointsDeVie) {
        this.pointsDeVie = Math.max(0, pointsDeVie);
    }

    public void setAttaque(int attaque) {
        this.attaque = Math.max(0, attaque); 
    }

    public void setDefense(int defense) {
        this.defense = Math.max(0, defense);
    }
}
