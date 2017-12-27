import agent.Negotiator;
import agent.Supplier;
import agent.strategies.BasicPolicySupplier;
import agent.strategies.SimplePolicyNegotiator;
import model.Ticket;
import model.TicketManager;

import java.util.List;

public class main {


    public static void main(String [ ] args) {

        /*TicketManager ticketManager = new TicketManager();

        List<Ticket> supplierTickets = ticketManager.createTickets(20, Ticket.PLANE);
        Supplier supplier = new Supplier(new BasicPolicySupplier(1.5,6),supplierTickets);


        Negotiator negotiator = new Negotiator(new SimplePolicyNegotiator(1.5, 6), ticketManager.createClientTicket());

        Thread thread = new Thread(supplier);
        thread.start();
        Thread thread2 = new Thread(negotiator);
        thread2.start();*/

        Evaluation eval = new Evaluation(20, 50);
        eval.evaluateAllNegotiators();

    }
}
