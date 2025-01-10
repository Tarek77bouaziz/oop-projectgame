package PROJECT;

import java.io.*;

public class Sauvegarde {
    public static boolean fichierExiste(String chemin) {
        File fichier = new File(chemin);
        return fichier.exists();
    }

    public static void sauvegarderPersonnage(Personnage personnage, String chemin) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(chemin))) {
            oos.writeObject(personnage);
        } catch (IOException e) {
            System.out.println("Erreur lors de la sauvegarde !");
        }
    }

    public static Personnage chargerPersonnage(String chemin) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(chemin))) {
            return (Personnage) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Erreur lors du chargement !");
            return null;
        }
    }
}
