import agent.Negotiator;
import agent.Supplier;
import agent.strategies.BasicPolicySupplier;
import agent.strategies.Politics;
import agent.strategies.SimplePolicyNegotiator;
import dialogue.Communication;
import model.Offer;
import model.Ticket;
import model.TicketManager;

import java.util.ArrayList;
import java.util.List;

public class Evaluation {
    private TicketManager ticketManager;
    private int priceWanted;
    private int priceGiven;


    public Evaluation(int priceWanted, int priceGiven) {
        ticketManager = new TicketManager();
        this.priceWanted = priceWanted;
        this.priceGiven = priceGiven;
    }

    public void evaluateAllNegotiators(){
        List<Politics> politicsNegociator = new ArrayList<Politics>();

        politicsNegociator.add(new SimplePolicyNegotiator(1.0,6));
        politicsNegociator.add(new SimplePolicyNegotiator(1.1,6));
        politicsNegociator.add(new SimplePolicyNegotiator(1.25,6));
        politicsNegociator.add(new SimplePolicyNegotiator(1.40,6));
        politicsNegociator.add(new SimplePolicyNegotiator(1.50,6));
        politicsNegociator.add(new SimplePolicyNegotiator(1.75,6));
        politicsNegociator.add(new SimplePolicyNegotiator(2.0,6));

        for(Politics politic: politicsNegociator){
            evaluateNegociator(politic);
        }
    }

    private void evaluateNegociator(Politics policyNegotiator) {
        System.out.println(policyNegotiator.toString() +" Deals");
        System.out.println("---------------");
        List<Politics> politicsSupplier = new ArrayList<Politics>();
        politicsSupplier.add(new BasicPolicySupplier(1.0,6));
        politicsSupplier.add(new BasicPolicySupplier(1.1,6));
        politicsSupplier.add(new BasicPolicySupplier(1.25,6));
        politicsSupplier.add(new BasicPolicySupplier(1.40,6));
        politicsSupplier.add(new BasicPolicySupplier(1.50,6));
        politicsSupplier.add(new BasicPolicySupplier(1.75,6));
        politicsSupplier.add(new BasicPolicySupplier(2.0,6));

        for(Politics politic: politicsSupplier){

            List<Ticket> supplierTickets = ticketManager.createTickets(40, Ticket.PLANE,2, priceGiven);// 2== LYON
            Supplier supplier = new Supplier(politic,supplierTickets);

            Politics policyNegotiatorCopy = policyNegotiator.copy();
            Ticket ticket = ticketManager.getOneTicketFromList(supplierTickets);
            Negotiator negotiator = new Negotiator(policyNegotiatorCopy, ticketManager.changeTicketPrice(ticket, priceWanted));

            Thread thread = new Thread(supplier);
            thread.start();
            Thread thread2 = new Thread(negotiator);
            thread2.start();


            try {
                thread2.join();
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Communication communication = Communication.getInstance();
            Offer lastOffer = communication.getLastOffer();
            Boolean dealConcluded = lastOffer.getText() == "ACCEPT" ;
            int nbMessages = communication.getNbMessagesTotal();

            System.out.println("Against " + supplier.getPolicy().toString());
            if (dealConcluded){
                System.out.println("He negociated " + lastOffer.getTicket().getPrice() + "€ in " + nbMessages +" messages ");
            }
            else{
                System.out.println("Deal wasn\'t successful in " + nbMessages +" messages ");
            }
            System.out.println();

            communication.reset();
        }
    }
}
