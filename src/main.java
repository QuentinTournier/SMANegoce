import agent.Negotiator;
import agent.Supplier;
import agent.strategies.BasicPolicySupplier;
import model.Ticket;
import model.TicketManager;

import java.util.List;

public class main {


    public static void main(String [ ] args) {

        TicketManager ticketManager = new TicketManager();

        List<Ticket> supplierTickets = ticketManager.createTickets(5, Ticket.PLANE);
        Supplier supplier = new Supplier(new BasicPolicySupplier(),supplierTickets);


        Negotiator negotiator = new Negotiator(ticketManager.getOneTicketFromList(supplierTickets));

        Thread thread = new Thread(supplier);
        thread.start();
        Thread thread2 = new Thread(negotiator);
        thread2.start();

        /*
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        com.lireMessage(fournId).afficher();*/
    }
}
