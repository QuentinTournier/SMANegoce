package model;

import java.util.List;

/**
 * Created by Quentin on 06/11/2017.
 */
public class Deal {
    private int idDeal;
    private List<Ticket> dealOffered;
    private List<Ticket> dealReceived;

    public Deal(int idDeal, List<Ticket> dealOffered) {
        this.idDeal = idDeal;
        this.dealOffered = dealOffered;
    }
}
