package agent;

/**
 * Created by Quentin on 06/11/2017.
 */
public abstract class  Agent {
    protected String name;
    protected int idComm;

    public String getName() {
        return name;
    }

    public int getIdComm() {
        return idComm;
    }

    public void setIdComm(int idComm) {
        this.idComm = idComm;
    }
}
