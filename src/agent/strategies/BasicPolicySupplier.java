package agent.strategies;

import model.Deal;
import model.Offer;

/**
 * Created by Quentin on 06/11/2017.
 */
public class BasicPolicySupplier implements Politics {

    public Offer process(Deal d) {
        if(d.getValueInit()<= d.getLastPrice()){
            return new Offer("ACCEPT", d.getLastTicket());
        }
        return null;
    }
}
