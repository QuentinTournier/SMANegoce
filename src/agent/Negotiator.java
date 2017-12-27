package agent;


import agent.strategies.Politics;
import agent.strategies.SimplePolicyNegotiator;
import dialogue.Communication;
import dialogue.Message;
import model.Deal;
import model.Offer;
import model.Ticket;



/**
 * Created by Quentin on 06/11/2017.
 */
public class Negotiator extends Agent implements Runnable{

    private Ticket ticket;
    private Politics policy;
    private Deal d;

    public Negotiator(Politics policy, Ticket ticket) {
        this.ticket = ticket;
        Communication communication = Communication.getInstance();
        idComm = communication.nouveauNegociateur();
        this.policy = policy;
        d = new Deal(ticket.getPrice());
    }

    public void run() {
        Communication communication = Communication.getInstance();
        Offer offer = new Offer ("SEARCH", ticket);
        Message messageInit = new Message(offer, this.getIdComm(), null);
        communication.envoyerMessageTousFournisseurs(messageInit);

        while (!done){
            Message mess = communication.lireMessage(this.getIdComm());
            if (mess != null){
                d.addReceivedOffer(mess.getOffer());
            }
            if(mess == null) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                continue;
            }

            Offer answer = this.answerMessage(mess);
            if(answer == null){
                continue;
            }
            Message messAnswer = new Message(answer, this.getIdComm(), mess.getIdMessage());
            communication.envoyerMessage(messAnswer, mess.getExpediteur());

        }

    }


    @Override
    public Offer answerMessage(Message message) {

        if(message.getOffer().getText().startsWith("PROPOSE")){
            Offer offer = policy.process(d);
            if (!offer.getText().startsWith("PROPOSE")){
                System.out.println(offer.getText());
                done = true;
            }
            return offer;
        }
        done = true;
        return null;
    }

    public Politics getPolicy() {
        return policy;
    }
}
