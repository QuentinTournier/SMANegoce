package agent;


import dialogue.Communication;
import dialogue.Message;
import model.Offer;
import model.Ticket;



/**
 * Created by Quentin on 06/11/2017.
 */
public class Negotiator extends Agent implements Runnable{

    private Ticket ticket;

    public Negotiator(Ticket ticket) {
        this.ticket = ticket;
        Communication communication = Communication.getInstance();
        idComm = communication.nouveauNegociateur();

    }

    @Override
    public void run() {
        System.out.println("Negotiator running");
        Communication communication = Communication.getInstance();
        Offer offer = new Offer ("SEARCH", ticket);
        Message messageInit = new Message(offer, this.getIdComm(), null);
        communication.envoyerMessageTousFournisseurs(messageInit);

        while (!done){
            Message mess = communication.lireMessage(this.getIdComm());
            if(mess == null) {
                continue;
            }
            System.out.println("I got an offer");

            Message messAnswer = new Message(this.answerMessage(mess), this.getIdComm(), mess.getIdMessage());
            communication.envoyerMessage(messAnswer, mess.getExpediteur());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private int getExpeditorIdFromString(String stringId){
        return Integer.valueOf(stringId.split("_")[1]);
    }

    @Override
    public Offer answerMessage(Message message) {
        Offer offer = null;
        if(message.getOffer().getText().startsWith("OFFER")){
            return new Offer("Cool bro", null);
        }
        else if(message.getOffer().getText().startsWith("ACCEPT")){
            return new Offer("Cool bro", null);
        }
        else if(message.getOffer().getText().startsWith("REFUSE")){
            return null;
        }
        else {
            return new Offer("Not Cool bro", null);
        }

    }
}
