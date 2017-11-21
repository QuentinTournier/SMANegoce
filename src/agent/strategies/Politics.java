package agent.strategies;

import model.Deal;
import model.Offer;

/**
 * Created by Quentin on 06/11/2017.
 */
public interface Politics {

    Offer process(Deal d);
}
