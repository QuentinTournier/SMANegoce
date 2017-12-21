package model;

/**
 * Created by Quentin on 06/11/2017.
 */
public class Offer {
    private String text;
    private Ticket ticket;

    public Offer(String text, Ticket offer) {
        this.text = text;
        this.ticket = offer;
    }

    public String getText() {
        return text;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public String toString(){
        if(ticket == null)
            return "pas de ticket";

        String message = text + " : " + ticket.getDeparture() + " - " + ticket.getDestination() + " : " + ticket.getPrice() + "€";
        return message;
    }

}
