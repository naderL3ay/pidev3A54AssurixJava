package Entities;

import java.util.Date;

public class DossierReclamation {

    private int id;
    private String serieVehicule;
    private String imageRectoCin;
    private String imageVersoCin;
    private String imageCarteGrise;
    private int idUtilisateur;
    private Date date;
    private Reclamation reclamation;

    // Constructors
    public DossierReclamation() {
    }

    public DossierReclamation(int id, String serieVehicule, String imageRectoCin, String imageVersoCin, String imageCarteGrise, int idUtilisateur, Date date, Reclamation reclamation) {
        this.id = id;
        this.serieVehicule = serieVehicule;
        this.imageRectoCin = imageRectoCin;
        this.imageVersoCin = imageVersoCin;
        this.imageCarteGrise = imageCarteGrise;
        this.idUtilisateur = idUtilisateur;
        this.date = date;
        this.reclamation= reclamation;
    }

    public Reclamation getReclamation() {
        return reclamation;
    }

    public void setReclamation(Reclamation reclamation) {
        this.reclamation = reclamation;
    }

    public DossierReclamation(int id, String serieVehicule, String imageRectoCin, String imageVersoCin, String imageCarteGrise, int idUtilisateur, Date date) {
        this.id = id;
        this.serieVehicule = serieVehicule;
        this.imageRectoCin = imageRectoCin;
        this.imageVersoCin = imageVersoCin;
        this.imageCarteGrise = imageCarteGrise;
        this.idUtilisateur = idUtilisateur;
        this.date = date;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSerieVehicule() {
        return serieVehicule;
    }

    public void setSerieVehicule(String serieVehicule) {
        this.serieVehicule = serieVehicule;
    }

    public String getImageRectoCin() {
        return imageRectoCin;
    }

    public void setImageRectoCin(String imageRectoCin) {
        this.imageRectoCin = imageRectoCin;
    }

    public String getImageVersoCin() {
        return imageVersoCin;
    }

    public void setImageVersoCin(String imageVersoCin) {
        this.imageVersoCin = imageVersoCin;
    }

    public String getImageCarteGrise() {
        return imageCarteGrise;
    }

    public void setImageCarteGrise(String imageCarteGrise) {
        this.imageCarteGrise = imageCarteGrise;
    }

    public int getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(int idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Reclamation{" +
                "id=" + id +
                ", serieVehicule='" + serieVehicule + '\'' +
                ", imageRectoCin='" + imageRectoCin + '\'' +
                ", imageVersoCin='" + imageVersoCin + '\'' +
                ", imageCarteGrise='" + imageCarteGrise + '\'' +
                ", idUtilisateur=" + idUtilisateur +
                ", date=" + date +
                '}';
    }
}
