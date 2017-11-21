package model;

import java.util.List;

/**
 * Created by Quentin on 06/11/2017.
 */
public class Deal {
    private String idDeal;
    private List<Ticket> dealOffered;
    private List<Ticket> dealReceived;

    public Deal(String idDeal, List<Ticket> dealOffered) {
        this.idDeal = idDeal;
        this.dealOffered = dealOffered;
    }

    public String getIdDeal() {
        return idDeal;
    }

    public void setIdDeal(String idDeal) {
        this.idDeal = idDeal;
    }

    public void addReceivedOffer(Offer offer) {
    }
}
