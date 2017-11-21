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

    public Offer answerMessage(Message message){
        if(message.getOffer().getText().startsWith("SEARCH")){
            List<Ticket> listTicket = new ArrayList<>();
            Offer offer = answerInitialMessage(message.getOffer());
            listTicket.add(offer.getTicket());
            Deal newDeal = new Deal(generateIdDeal(message.getExpediteur()), listTicket);
            currentDeals.add(newDeal);
            ////
        }
        else if(message.getOffer().getText().startsWith("ACCEPT")){
            this.removeTicket(message.getOffer().getTicket());
            currentDeals.remove(message.getIdMessage());
            return new Offer("Cool bro", null);
        }
        else if(message.getOffer().getText().startsWith("REFUSE")){
            return null;
        }
        else {
            Deal d = currentDeals.get(message.getIdMessage());
            d.addReceivedOffer(message.getOffer());
            Offer m = policy.process(d);
            if(m.getText().startsWith("ACCEPT")){
                this.removeTicket(message.getOffer().getTicket());
                currentDeals.remove(message.getIdMessage());
            }else if(m.getText().startsWith("REFUSE")){
                currentDeals.remove(message.getIdMessage());
            }
        }

        return null;
    }

    private String generateIdDeal(int expediteur) {
        //TODO, correct this
        String idDeal = ""+ this.getIdComm() +"_"+ expediteur;
        for(Deal d : currentDeals){
            if(d.getIdDeal().equals(idDeal)){
                return "changeId";
            }
        }
        return idDeal;
    }

    private void removeTicket(Ticket offer) {
        for (Ticket t: store) {
            if(t.isEqual(offer)){
                store.remove(offer);
            }
        }
    }

    private Offer answerInitialMessage(Offer offer) {
        for (Ticket t: store) {
            if(t.isEqual(offer.getTicket())){
                return new Offer("OFFER", t);
            }
        }
        return new Offer("Null", null);
    }
}
