import dialogue.*;

public class main {


    public static void main(String [ ] args) {
        Communication com = Communication.getInstance();

        int fournId = com.nouveauFournisseur();
        int negoId = com.nouveauNegociateur();

        com.envoyerMessage(new Message("Bonjour", negoId), fournId);
        com.envoyerMessage(new Message("Bye", negoId), fournId);

        com.lireMessage(fournId).afficher();
        com.lireMessage(fournId).afficher();


        com.envoyerMessageTousFournisseurs(new Message("Broadcast", negoId));
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        com.lireMessage(fournId).afficher();
    }
}
