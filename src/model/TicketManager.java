package model;

import java.util.*;

public class TicketManager {

    ArrayList<String> places = new ArrayList<>();

    public TicketManager() {
        String[] elements = {"Paris", "Marseille", "Lyon", "Toulouse", "Nice", "Nantes", "Strasbourg", "Montpellier", "Bordeaux", "Rennes"};

        for (String s: elements) {
            places.add(s);
        }
    }

    public List<Ticket> createTickets(int nbTickets, int type){
        List<Ticket> list = new ArrayList<>();
        Random random =new Random();
        int price = 50;
        int departure = random.nextInt(places.size());
        int arrival = 0;

        Date date = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, 1);
        Date date2 = c.getTime();

        for (int i=0; i< nbTickets; i++){
            do {
                arrival = random.nextInt(places.size());
            }while(departure == arrival);

            Ticket t = new Ticket(places.get(arrival),places.get(departure) ,date, date2, price,type);
            list.add(t);
        }
        return list;
    }

    public Ticket getOneTicketFromList(List<Ticket> supplierTickets) {
        Random random =new Random();
        int ticketNb = random.nextInt(supplierTickets.size());
        return supplierTickets.get(ticketNb);
    }
}
