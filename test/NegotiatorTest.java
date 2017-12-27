import static org.junit.Assert.assertEquals;

import agent.Negotiator;
import agent.strategies.SimplePolicyNegotiator;
import dialogue.Communication;
import dialogue.Message;
import model.Offer;
import model.Ticket;
import model.TicketManager;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class NegotiatorTest {

    private static Negotiator negotiator;
    private static TicketManager ticketManager;
    private static Communication com;
    private static int idNegociationFournisseur;
    private static int idNegociationNegociateur;
    private static Ticket ticket;
    private static Thread thread;

    @BeforeClass
    public static void testSetup() {
        ticketManager = new TicketManager();
        ticket = ticketManager.createClientTicket();
        negotiator = new Negotiator(new SimplePolicyNegotiator(1.5,6), ticket);
        com = Communication.getInstance();
        idNegociationFournisseur = com.nouveauFournisseur();
        idNegociationNegociateur = 10000;
        thread = new Thread(negotiator);
        thread.start();


    }

    @Test
    public void testClass() {
        //Wait the beginning of negociations
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertEquals(1, com.nbNouveauxMessages(idNegociationFournisseur));
        Message m = com.getNouveauxMessages(idNegociationFournisseur).get(0);
        assertEquals(true, m.getOffer().getText().startsWith("SEARCH"));

        Ticket ticketOffered = new Ticket(ticket.getDestination(), ticket.getDeparture(), ticket.getTakeOffDate(), ticket.getLandingDate(), 50, Ticket.PLANE);
        Offer offer = new Offer("PROPOSE", ticketOffered);
        com.envoyerMessage(new Message(offer, idNegociationFournisseur,""+ idNegociationFournisseur +"_"+ idNegociationNegociateur), idNegociationNegociateur);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertEquals(2, com.nbNouveauxMessages(idNegociationFournisseur));

    }
    @AfterClass
    public static void clean(){

        com.reset();
    }
}