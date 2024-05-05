package Entities;

import java.util.Date;

public class Demande {

	private int id;
	private int user_id;
	private String nom_victime;
	private String num_constat;
	private Date date_realisation;
	private String localisation;
	private String etat_demande;
	private Float latitude;
	private Float longitude;

	public Demande() {

	}

	public Float getLatitude() {
		return latitude;
	}

	public void setLatitude(Float latitude) {
		this.latitude = latitude;
	}

	public Float getLongitude() {
		return longitude;
	}

	public void setLongitude(Float longitude) {
		this.longitude = longitude;
	}

	public Demande(int userId, String nomVictime, String numConstat, Date dateRealisation, String localisationDemande, String etatDemande) {
	}

	public Demande(int id, int user_id, String nom_victime, String num_constat, Date date_realisation, String localisation, String etat_demande) {
		this.id = id;
		this.user_id = user_id;
		this.nom_victime = nom_victime;
		this.num_constat = num_constat;
		this.date_realisation = date_realisation;
		this.localisation = localisation;
		this.etat_demande = etat_demande;
	}

    public Demande(int id, int userId, String numConstat, java.sql.Date dateRealisation, String localisationDemande, String etatDemande) {
    }

	public Demande(int id, int userId, String nomVictime, int numConstat, String localisation, String etatDemande, java.sql.Date dateRealisation) {
	}

	public int getId() {
		return id;
	}

	@Override
	public String toString() {
		return this.nom_victime;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public String getNom_victime() {
		return nom_victime;
	}

	public void setNom_victime(String nom_victime) {
		this.nom_victime = nom_victime;
	}

	public String getNum_constat() {
		return num_constat;
	}

	public void setNum_constat(String num_constat) {
		this.num_constat = num_constat;
	}

	public Date getDate_realisation() {
		return date_realisation;
	}

	public void setDate_realisation(Date date_realisation) {
		this.date_realisation = date_realisation;
	}

	public String getLocalisation() {
		return localisation;
	}

	public void setLocalisation(String localisation) {
		this.localisation = localisation;
	}

	public String getEtat_demande() {
		return etat_demande;
	}

	public void setEtat_demande(String etat_demande) {
		this.etat_demande = etat_demande;
	}
	public Demande(int id, int userId, String nomVictime, String numConstat, String localisation, String etatDemande, java.sql.Date dateRealisation)
	{

	}
}