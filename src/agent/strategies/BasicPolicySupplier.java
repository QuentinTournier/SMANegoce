package agent.strategies;

import model.Deal;
import model.Offer;
import model.TicketManager;

/**
 * Created by Quentin on 06/11/2017.
 */
public class BasicPolicySupplier implements Politics {

    private double minValueFactor;
    private int nbNegoce;
    private int currentNbNegoce;

    public BasicPolicySupplier(double minValueFactor, int nbNegoce) {
        this.minValueFactor = minValueFactor;
        this.nbNegoce = nbNegoce;
    }

    public Offer process(Deal d) {
        int initPrice = d.getValueInit();
        int lastPrice = d.getLastPrice();
        if(initPrice * minValueFactor < lastPrice){
            return new Offer("ACCEPT", d.getLastTicket() );
        }
        else{
            if (currentNbNegoce >= nbNegoce){
                return new Offer("REFUSE", d.getLastTicket() );
            }
            currentNbNegoce ++;

            double negociationFactor = 1 - (1-minValueFactor) * ((double)currentNbNegoce / nbNegoce );
            int priceOffered = (int) Math.round(initPrice * negociationFactor);
            TicketManager ticketManager = new TicketManager();
            return new Offer("PROPOSE", ticketManager.changeTicketPrice(d.getLastTicket(), priceOffered));
        }
    }
}