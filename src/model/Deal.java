package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Quentin on 06/11/2017.
 */
public class Deal {
    private String idDeal;
    private List<Ticket> dealOffered;
    private List<Ticket> dealReceived;
    private int valueInit;

    public Deal(int valueInit) {
        this("_", new ArrayList<Ticket>(), valueInit);
    }

    public Deal(String idDeal, List<Ticket> dealReceived, int valueInit) {
        this.idDeal = idDeal;
        this.dealReceived = dealReceived;
        this.valueInit = valueInit;
        dealOffered = new ArrayList<Ticket>();
    }

    public int getValueInit() {
        return valueInit;
    }

    public String getIdDeal() {
        return idDeal;
    }

    public void addReceivedOffer(Offer offer) {
        dealReceived.add(offer.getTicket());
    }

    public void addSentOffer(Offer offer) {
        dealOffered.add(offer.getTicket());
    }

    public Ticket getLastTicket(){
        if(dealReceived.size()<1){
            return null;
        }
        return dealReceived.get(dealReceived.size()-1);
    }

    public int getLastPrice() {

        Ticket lastTicket = getLastTicket();
        if(lastTicket == null){
            return 0;
        }
        return lastTicket.getPrice();
    }
}
