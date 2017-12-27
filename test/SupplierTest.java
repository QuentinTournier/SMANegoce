import static org.junit.Assert.assertEquals;

import agent.Negotiator;
import agent.Supplier;
import agent.strategies.BasicPolicySupplier;
import agent.strategies.SimplePolicyNegotiator;
import dialogue.Communication;
import dialogue.Message;
import model.Offer;
import model.Ticket;
import model.TicketManager;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

public class SupplierTest {

    private static Supplier supplier;
    private static TicketManager ticketManager;
    private static Communication com;
    private static int idNegociationFournisseur;
    private static int idNegociationNegociateur;
    private static List<Ticket> tickets;
    private static Thread thread;

    @BeforeClass
    public static void testSetup() {
        ticketManager = new TicketManager();
        tickets = ticketManager.createTickets(100, Ticket.PLANE);
        supplier = new Supplier(new BasicPolicySupplier(1.5,6), tickets);
        com = Communication.getInstance();
        idNegociationFournisseur = 0;
        idNegociationNegociateur = com.nouveauNegociateur();
        thread = new Thread(supplier);
        thread.start();
        
    }

    @Test
    public void testClass() {
        
        Offer offer = new Offer("SEARCH", ticketManager.createClientTicket());
        Message m = new Message(offer, idNegociationNegociateur, "");
        com.envoyerMessage(m, idNegociationFournisseur);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertEquals(1, com.nbNouveauxMessages(idNegociationNegociateur));
        Message m2 = com.getNouveauxMessages(idNegociationNegociateur).get(0);
        Offer offer2 = new Offer("ACCEPT", m2.getOffer().getTicket());
        Message m3 = new Message(offer2,idNegociationNegociateur,m2.getIdMessage());
        com.envoyerMessage(m3,idNegociationFournisseur);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertEquals(false, thread.isAlive());

    }

    @AfterClass
    public static void clean(){
        com.reset();
    }
}