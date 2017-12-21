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
    private String idMessage;

    public Message(Offer texte, int expediteur,String idMessage) {
        this.texte = texte;
        this.expediteur = expediteur;
        this.dateEnvoi = null;
        this.dateLecture = null;
        this.idMessage = idMessage;
    }

    public void afficher(){
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        System.out.println("De : "+expediteur);
        if(texte == null ){
            System.out.println("no text");
        }else{
            System.out.println("Corps : \n"+ texte.toString());
        }
        System.out.println("---------");
    }

    public String getIdMessage() {
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
