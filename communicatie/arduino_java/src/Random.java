public class Random {
    
    public int getal(int min, int max) {
        int getal;
        getal = (int) (Math.random() * ((max - min) + 1)) + min;
        return getal;
    }
    
    public void tijd(int min, int max) {
        int getal = (int) this.getal(min, max);
        //System.out.println("bezig... ");
        try {
            Thread.sleep(getal);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}
