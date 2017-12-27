import agent.Negotiator;
import agent.Supplier;
import agent.strategies.BasicPolicySupplier;
import agent.strategies.SimplePolicyNegotiator;
import model.Ticket;
import model.TicketManager;

import java.util.List;

public class main {


    public static void main(String [] args) {
        //* Comment these line to evaluate
        TicketManager ticketManager = new TicketManager();

        List<Ticket> supplierTickets = ticketManager.createTickets(20, Ticket.PLANE);
        Supplier supplier = new Supplier(new BasicPolicySupplier(0.8,6),supplierTickets);


        Negotiator negotiator = new Negotiator(new SimplePolicyNegotiator(1.0, 6), ticketManager.createClientTicket());
        Negotiator negotiator2 = new Negotiator(new SimplePolicyNegotiator(1.5, 6), ticketManager.createClientTicket());
        Negotiator negotiator3 = new Negotiator(new SimplePolicyNegotiator(1.25, 6), ticketManager.createClientTicket());

        Thread thread = new Thread(supplier);
        thread.start();
        Thread thread2 = new Thread(negotiator);
        thread2.start();
        Thread thread3 = new Thread(negotiator2);
        thread3.start();
        Thread thread4 = new Thread(negotiator3);
        thread4.start();
        // */

        /* Uncomment these lines to test the evaluation
        Evaluation eval = new Evaluation(40, 50);
        eval.evaluateAllNegotiators(); //*/

    }
}
