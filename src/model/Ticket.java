package model;

import java.util.Date;

/**
 * Created by Quentin on 06/11/2017.
 */
public class Ticket {

    public final static int PLANE = 0;
    public final static int TRAIN = 1;

    private String destination;
    private String departure;
    private Date takeOffDate;
    private Date landingDate;
    private int price;
    private int type;

    public Ticket(String destination, String departure) {
        this.destination = destination;
        this.departure = departure;
    }

    public Ticket(String destination, String departure, Date takeOffDate, Date landingDate, int price, int type) {
        this.destination = destination;
        this.departure = departure;
        this.takeOffDate = takeOffDate;
        this.landingDate = landingDate;
        this.price = price;
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public String getDestination() {
        return destination;
    }

    public String getDeparture() {
        return departure;
    }

    public Date getTakeOffDate() {
        return takeOffDate;
    }

    public Date getLandingDate() {
        return landingDate;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public boolean isEqual(Ticket ticket){
        if(!this.departure.equals(ticket.getDeparture())){
            return false;
        }
        if(!this.destination.equals(ticket.getDestination())){
            return false;
        }
        /*
        if(!this.takeOffDate.equals(ticket.takeOffDate)){
            return false;
        }
        if(!this.landingDate.equals(ticket.landingDate)){
            return false;
        }*/
        return true;
    }
}
