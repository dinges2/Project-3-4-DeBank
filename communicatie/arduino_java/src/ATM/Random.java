//Onderbrengen in de overkoepelende package
package ATM;

public class Random {
    
    //Methode die d.m.v. Math.random() en 2 meegegeven parameters een willekeurig getal teruggeeft
    //dat binnen de waarden ligt die meegegeven zijn als paramaters
    public int getal(int min, int max) {

        int getal;
        getal = (int) (Math.random() * ((max - min) + 1)) + min;
        return getal;
    }
    
    //Methode die gebruik maakt van de voorgaande methode om een Thread.sleep commando uit te voeren
    //met een tijd die ligt binnen de waarden van de meegegeven parameters
    public void tijd(int min, int max) {

        int getal = (int) this.getal(min, max);
        try {
            Thread.sleep(getal);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}
