package agent;

import agent.strategies.Politics;
import dialogue.Communication;
import dialogue.Message;
import model.Deal;
import model.Ticket;
import model.Offer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Quentin on 06/11/2017.
 */
public class Supplier extends Agent{

    private List<Ticket> store;
    private Politics policy;
    private List<Deal> currentDeals;

    public Supplier(Politics policy, List<Ticket> store){
        this.store = store;
        this.policy = policy;
        currentDeals = new ArrayList<>();
        Communication comm = Communication.getInstance();
        this.idComm = comm.nouveauFournisseur();
        this.name = "SUPP"+ this.idComm;

    }

    public Offer answerMessage(Offer message){
        if(message.getText().startsWith("SEARCH")){
            List<Ticket> listTicket = new ArrayList<>();
            Message mess = answerInitialMessage(message.getOffer());
            listTicket.add(mess.getOffer());
            Deal newDeal = new Deal(mess.getIdMessage(), listTicket);
            currentDeals.add(mess.getIdDeal(),newDeal);
            ////
        }
        else if(message.getText().startsWith("ACCEPT")){
            this.removeTicket(message.getOffer());
            currentDeals.remove(message.getIdDeal());
            return new Offer("Cool bro", null);
        }
        else if(message.getText().startsWith("REFUSE")){
            return null;
        }
        else {
            Deal d = currentDeals.get(message.getIdDeal());
            d.addReceivedOffer(message.getOffer());
            Offer m = policy.process(d);
            if(m.getText().startsWith("ACCEPT")){
                this.removeTicket(message.getOffer());
                currentDeals.remove(message.getIdDeal());
            }else if(m.getText().startsWith("REFUSE")){
                currentDeals.remove(message.getIdDeal());
            }
        }


    }

    private void removeTicket(Ticket offer) {
        for (Ticket t: store) {
            if(t.isEqual(offer)){
                store.remove(offer);
            }
        }
    }

    private Offer answerInitialMessage(Message message) {
        for (Ticket t: store) {
            if(t.isEqual(message.getOffer().getTicket())){
                return new Offer("OFFER", t);
            }
        }
        return new Offer("Null", null);
    }
}
