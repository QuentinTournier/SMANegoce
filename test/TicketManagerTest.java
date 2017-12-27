import static org.junit.Assert.assertEquals;

import model.Ticket;
import model.TicketManager;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

public class TicketManagerTest {

    private static TicketManager ticketManager;

    @BeforeClass
    public static void testSetup() {
        ticketManager = new TicketManager();
    }


    @Test
    public void testClass() {
        int nb = 10;
        int placeDeparture = 1;
        List<Ticket> tickets = ticketManager.createTickets(nb, Ticket.PLANE,placeDeparture,50);
        assertEquals(nb, tickets.size());
        for(int i=0; i<tickets.size(); i++){
            Ticket t = tickets.get(i);
            assertEquals(t.getDeparture(), ticketManager.numberToPlace(placeDeparture));
        }
        int price1= 50;
        int price2 = 60;
        Ticket ticket = ticketManager.createClientTicket(
                ticketManager.numberToPlace(placeDeparture+1), ticketManager.numberToPlace(placeDeparture), price1, Ticket.PLANE);
        assertEquals(ticket.getPrice(),price1);
        Ticket ticket2 = ticketManager.changeTicketPrice(ticket, price2);
        assertEquals(ticket2.getPrice(),price2);


    }
} 