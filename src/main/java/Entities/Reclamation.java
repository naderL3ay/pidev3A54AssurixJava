package Entities;

public class Reclamation {

    private int id;
    private String titre;
    private String reclamation;

    // Constructors
    public Reclamation() {
        // Default constructor
    }

    public Reclamation(int id, String titre, String reclamation) {
        this.id = id;
        this.titre = titre;
        this.reclamation = reclamation;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getReclamation() {
        return reclamation;
    }

    public void setReclamation(String reclamation) {
        this.reclamation = reclamation;
    }

    @Override
    public String toString() {
        return "Reclamation{" +
                "id=" + id +
                ", titre='" + titre + '\'' +
                ", reclamation='" + reclamation + '\'' +
                '}';
    }
}
