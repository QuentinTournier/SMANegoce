package dialogue;

import model.Offer;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Message {
    private Offer texte;
    private int expediteur;
    private Date dateEnvoi;
    private Date dateLecture;
    private int idMessage;

    public Message(Offer texte, int expediteur,int idMessage) {
        this.texte = texte;
        this.expediteur = expediteur;
        this.dateEnvoi = null;
        this.dateLecture = null;
        this.idMessage = idMessage;
    }

    public void afficher(){
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        System.out.println("De : "+expediteur);
        System.out.println("Envoyé le : "+df.format(dateEnvoi));
        System.out.println("Lu le : "+df.format(dateLecture));
        System.out.println("Corps : "+texte);
        System.out.println();
    }

    public int getIdMessage() {
        return idMessage;
    }

    public Offer getOffer() {
        return texte;
    }

    public int getExpediteur() {
        return expediteur;
    }

    public void setExpediteur(int expediteur) {
        this.expediteur = expediteur;
    }

    public Date getDateEnvoi() {
        return dateEnvoi;
    }

    public void setDateEnvoi(Date dateEnvoi) {
        this.dateEnvoi = dateEnvoi;
    }

    public Date getDateLecture() {
        return dateLecture;
    }

    public void setDateLecture(Date dateLecture) {
        this.dateLecture = dateLecture;
    }
}
