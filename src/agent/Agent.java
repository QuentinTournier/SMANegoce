package agent;

import dialogue.Message;
import model.Offer;

/**
 * Created by Quentin on 06/11/2017.
 */
public abstract class  Agent {
    protected String name;
    protected int idComm;
    protected boolean done=false;

    public String getName() {
        return name;
    }

    public int getIdComm() {
        return idComm;
    }

    public void setIdComm(int idComm) {
        this.idComm = idComm;
    }

    public abstract Offer answerMessage(Message mess);
}
