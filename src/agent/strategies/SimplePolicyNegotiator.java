package agent.strategies;

import model.Deal;
import model.Offer;
import model.TicketManager;

public class SimplePolicyNegotiator implements Politics {

    private double maxValueFactor;
    private int nbNegoce;
    private int currentNbNegoce;

    public SimplePolicyNegotiator(double maxValueFactor, int nbNegoce) {
        this.maxValueFactor = maxValueFactor;
        this.nbNegoce = nbNegoce;
        currentNbNegoce = 0;
    }

    public Offer process(Deal d) {
        int initPrice = d.getValueInit();
        int lastPrice = d.getLastPrice();
        if(initPrice * maxValueFactor > lastPrice){
            return new Offer("ACCEPT", d.getLastTicket() );
        }
        else{
            if (currentNbNegoce >= nbNegoce){
                return new Offer("REFUSE", d.getLastTicket() );
            }
            currentNbNegoce ++;

            double negociationFactor = 1 + (maxValueFactor - 1) * ((double)currentNbNegoce / nbNegoce );
            int priceOffered = (int) Math.round(initPrice * negociationFactor);
            TicketManager ticketManager = new TicketManager();
            return new Offer("PROPOSE", ticketManager.changeTicketPrice(d.getLastTicket(), priceOffered));
        }
    }

    public Politics copy() {
        return new SimplePolicyNegotiator(this.maxValueFactor, this.nbNegoce);
    }

    public String toString(){
        return "SimplePolicyNegociator : (" + this.maxValueFactor + "," + this.nbNegoce + ")";
    }
}
