package dialogue;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;

public class Communication {
    private static Communication instance = null;
    private HashMap<Integer, LinkedList<Message>> nouveauxMessages;
    private HashMap<Integer, ArrayList<Message>> messagesArchives;
    private int nbFournisseurs;
    private static final int nbFournisseursMax = 10000;
    private int nbNegociateurs;

    private Communication()
    {
        nouveauxMessages = new HashMap<Integer, LinkedList<Message>>();
        messagesArchives = new HashMap<Integer, ArrayList<Message>> ();
        nbFournisseurs = 0;
        nbNegociateurs = 0;
    }

    public static synchronized Communication getInstance()
    {
        if (instance == null)
        {
            instance = new Communication();
        }
        return instance;
    }

    public synchronized int nouveauFournisseur (){
        if (nbFournisseurs < nbFournisseursMax) {
            nouveauxMessages.put(nbFournisseurs, new LinkedList<Message>());
            messagesArchives.put(nbFournisseurs, new ArrayList<Message>());
            nbFournisseurs++;
            return nbFournisseurs - 1;
        }
        return -1;
    }

    public synchronized int nouveauNegociateur (){
        nouveauxMessages.put(nbFournisseursMax + nbNegociateurs, new LinkedList<Message>());
        messagesArchives.put(nbFournisseursMax + nbNegociateurs, new ArrayList<Message>());
        nbNegociateurs++;
        return nbFournisseursMax + nbNegociateurs - 1;
    }

    public int nbNouveauxMessages(int id){
        return nouveauxMessages.get(id).size();
    }

    public synchronized Message lireMessage(int id){
        if (nbNouveauxMessages(id) > 0) {
            Message message = nouveauxMessages.get(id).removeFirst();
            message.setDateLecture(new Date());
            messagesArchives.get(id).add(message);
            return message;
        }
        return null;
    }

    public synchronized void envoyerMessage(Message message, int destinataire){
        message.setDateEnvoi(new Date());
        nouveauxMessages.get(destinataire).addLast(message);
    }

    public synchronized void envoyerMessageTousFournisseurs(Message message){
        for (int i = 0; i <nbFournisseurs; i++) {
            envoyerMessage(message, i);
        }
    }

    public static int getNbFournisseursMax() {
        return nbFournisseursMax;
    }
}
