package Entities;

public class Reponse {

    private int id;
    private int demande_id;
    private String titre_rapport;
    private String description;


    public Reponse(int id, int demande_id, String titre_rapport, String description) {
        this.id = id;
        this.demande_id = demande_id;
        this.titre_rapport = titre_rapport;
        this.description = description;
    }

    public Reponse(int demande_id, String titre_rapport, String description) {
        this.demande_id = demande_id;
        this.titre_rapport = titre_rapport;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDemande_id() {
        return demande_id;
    }

    public void setDemande_id(int demande_id) {
        this.demande_id = demande_id;
    }

    public String getTitre_rapport() {
        return titre_rapport;
    }

    public void setTitre_rapport(String titre_rapport) {
        this.titre_rapport = titre_rapport;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}