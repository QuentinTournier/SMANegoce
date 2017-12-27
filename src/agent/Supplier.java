package agent;

import agent.strategies.Politics;
import dialogue.Communication;
import dialogue.Message;
import model.Deal;
import model.Ticket;
import model.Offer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Quentin on 06/11/2017.
 */
public class Supplier extends Agent implements Runnable{

    private List<Ticket> store;
    private Politics policy;
    private List<Deal> currentDeals;

    public Supplier(Politics policy, List<Ticket> store){
        this.store = store;
        this.policy = policy;
        currentDeals = new ArrayList<Deal>();
        Communication comm = Communication.getInstance();
        this.idComm = comm.nouveauFournisseur();
        this.name = "SUPP"+ this.idComm;
    }

    public Offer answerMessage(Message message){
        Offer offer = null;
        Deal d = null;
        if(! message.getOffer().getText().startsWith("SEARCH")) {
            d = currentDeals.get(getExpeditorIdFromString(message.getIdMessage()));
        }

        if(message.getOffer().getText().startsWith("SEARCH")){
            List<Ticket> listTicket = new ArrayList<Ticket>();
            offer = answerInitialMessage(message.getOffer());
            listTicket.add(offer.getTicket());
            d = new Deal(generateIdDeal(message.getExpediteur()), listTicket, offer.getTicket().getPrice());
            currentDeals.add(d);
        }
        else if(message.getOffer().getText().startsWith("ACCEPT")){

            this.removeTicket(message.getOffer().getTicket());
            currentDeals.remove(message.getIdMessage());
            done = true;
            return new Offer("Cool bro", null);
        }
        else if(message.getOffer().getText().startsWith("REFUSE")){
            done = true;
            return null;
        }
        else {
            //No use to add to received if we end the deal
            d.addReceivedOffer(message.getOffer());
            Offer m = policy.process(d);
            if(m.getText().startsWith("ACCEPT")){
                this.removeTicket(message.getOffer().getTicket());
                currentDeals.remove(message.getIdMessage());
                done = true;
            }else if(m.getText().startsWith("REFUSE")){
                currentDeals.remove(message.getIdMessage());
                done = true;
            }
            return m;
        }

        d.addSentOffer(offer);
        return offer;
    }

    private String generateIdDeal(int expediteur) {
            String idDeal = ""+ this.getIdComm() +"_"+ expediteur;

        return idDeal;
    }

    private void removeTicket(Ticket offer) {
        /*Iterator<Ticket> iter = store.iterator();

        while (iter.hasNext()) {
            Ticket ticket = iter.next();
            if (ticket.isEqual(offer))
                store.remove(offer);
        }*/
    }

    private Offer answerInitialMessage(Offer offer) {
        for (Ticket t: store) {
            if(t.isEqual(offer.getTicket())){
                return new Offer("PROPOSE", t);
            }
        }
        return new Offer("Null", null);
    }

    public void run() {
        Communication communication = Communication.getInstance();
        while (!done || 1==1){
            Message mess = communication.lireMessage(this.getIdComm());
            if(mess != null){
                Offer answer = this.answerMessage(mess);
                if(answer == null){
                    done = true;
                    continue;
                }
                Message messAnswer = new Message(answer , this.getIdComm(),generateIdDeal(mess.getExpediteur()));
                communication.envoyerMessage(messAnswer, mess.getExpediteur());
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public Politics getPolicy() {
        return policy;
    }

    private int getExpeditorIdFromString(String stringId){
        return Integer.valueOf(stringId.split("_")[0]);
    }
}
