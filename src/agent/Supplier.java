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
public class Supplier extends Agent implements Runnable{

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
        Offer offer = null;
        Deal d = null;
        if(! message.getOffer().getText().startsWith("SEARCH")) {
            d = currentDeals.get(getExpeditorIdFromString(message.getIdMessage()));
        }

        if(message.getOffer().getText().startsWith("SEARCH")){
            List<Ticket> listTicket = new ArrayList<>();
            offer = answerInitialMessage(message.getOffer());
            listTicket.add(offer.getTicket());
            d = new Deal(generateIdDeal(message.getExpediteur()), listTicket, offer.getTicket().getPrice());
            currentDeals.add(d);
        }
        else if(message.getOffer().getText().startsWith("ACCEPT")){

            this.removeTicket(message.getOffer().getTicket());
            currentDeals.remove(message.getIdMessage());
            return new Offer("Cool bro", null);
        }
        else if(message.getOffer().getText().startsWith("REFUSE")){
            System.out.println("end of negociation supplier");
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
            }else if(m.getText().startsWith("REFUSE")){
                currentDeals.remove(message.getIdMessage());
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
        for (Ticket t: store) {
            if(t.isEqual(offer)){
                store.remove(offer);
            }
        }
    }

    private Offer answerInitialMessage(Offer offer) {
        for (Ticket t: store) {
            if(t.isEqual(offer.getTicket())){
                return new Offer("PROPOSE", t);
            }
        }
        return new Offer("Null", null);
    }

    @Override
    public void run() {
        System.out.println("Supplier running");
        Communication communication = Communication.getInstance();
        while (!done){
            Message mess = communication.lireMessage(this.getIdComm());
            System.out.println("Nb nouveaux messages " + communication.nbNouveauxMessages(idComm));
            if(mess != null){
                System.out.println("Reading new Message");
                Offer answer = this.answerMessage(mess);

                if(answer == null){
                    System.out.println("Deal done");
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

    private int getExpeditorIdFromString(String stringId){
        return Integer.valueOf(stringId.split("_")[0]);
    }
}
