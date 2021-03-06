//Onderbrengen in de overkoepelende package
package ATM;

//De benodigde libraries importeren
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.*;
import org.json.JSONArray;
import org.json.JSONObject;

public class PhpCode {

    //De benodigde globale variabel aanmaken
    static String inlogPoging;

    //De 'getter' voor de variabel
    static String getInlogPoging() {
        return inlogPoging;
    }

    //Methode die m.b.v. een URL 
    public static String fetch(String url) throws Exception {

        URLConnection connection = new URL(url).openConnection();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder stringBuilder = new StringBuilder();
        String line;

        while ((line = bufferedReader.readLine()) != null) {
            stringBuilder.append(line);
            stringBuilder.append(System.lineSeparator());
        }

        bufferedReader.close();

        return stringBuilder.toString();
    }

    //Methode die de rekeningnummer naar de php toestuurd
    public static String account(String rekeningnummer) {

        JSONObject nummer = null;

        try{
            JSONObject root = new JSONObject(fetch("http://getbank.ml/api/rekeningnummer.php?reknr="+ rekeningnummer));
            JSONArray buffer = root.getJSONArray("rekeningnummer");
            for (int i = 0; i < buffer.length(); i++) {
                nummer = buffer.getJSONObject(i);

                System.out.println("");
                System.out.println("Rekeningnummer is: " + nummer.getString("REKENINGNUMMER"));
                System.out.println("inlogpoging is: " + nummer.getString("INLOGPOGINGEN"));
                inlogPoging = nummer.getString("INLOGPOGINGEN");
            }

            if(inlogPoging.equals("3")) {
                return "";
            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return nummer.getString("REKENINGNUMMER");

    }

    //Methode die de inloggegevens naar de php stuurt
    public static String pincode(String rekeningnummer, String pincode ) {

        JSONObject nummer = null;

        try{
            JSONObject root = new JSONObject(fetch("http://getbank.ml/api/pincode.php?reknr="+ rekeningnummer + "&pincode=" + pincode));
            JSONArray buffer = root.getJSONArray("rekeningnummer");

            if(buffer.isEmpty()) {
                wrongPin(rekeningnummer);
                inlogPoging = poging(rekeningnummer);
                return "";
            }

            for (int i = 0; i < buffer.length(); i++) {
                nummer = buffer.getJSONObject(i);
                System.out.println("pincode is: " + nummer.getString("PINCODE"));
            }

            if(nummer.getString("INLOGPOGINGEN").equals("3")) {
                return "";
            }

        }

        catch (Exception e) {
            e.printStackTrace();
        }

        rightPin(rekeningnummer);
        return nummer.getString("PINCODE");
    }

    //Methode die ervoor zorg dat de inlogpoging in de database met 1 wordt verhoogd
    public static void wrongPin(String rekeningNummer) {

        try{
            JSONObject root = new JSONObject(fetch("http://getbank.ml/api/wrongPin.php?reknr="+ rekeningNummer));

        }

        catch (Exception e) {
            e.printStackTrace();
        }

    }

    //Methode die ervoor zorg dat de inlogpoging gereset wordt
    public static void rightPin(String rekeningNummer) {

        try{
            JSONObject root = new JSONObject(fetch("http://getbank.ml/api/rightPin.php?reknr="+ rekeningNummer));

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Methode die de saldo opvraagd van de database
    public static String saldos(String rekeningnummer) {

        JSONObject nummer = null;

        try{
            JSONObject root = new JSONObject(fetch("http://getbank.ml/api/saldo.php?reknr="+ rekeningnummer));
            JSONArray buffer = root.getJSONArray("saldo");


            for (int i = 0; i < buffer.length(); i++) {
                nummer = buffer.getJSONObject(i);
            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return nummer.getString("SALDO");
    }

    //Methode die een bepaalde bedrag van een bepaalde rekening afschrijft
    public static void collectMoney(String rekeningNummer, int amount) {

        try{
            JSONObject root = new JSONObject(fetch("http://getbank.ml/api/collect.php?reknr="+ rekeningNummer + "&amount="+ amount));

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Methode die de inlogpoging opvraagd van de database
    public static String poging(String rekeningnummer) {
        
        JSONObject nummer = null;

        try{
            JSONObject root = new JSONObject(fetch("http://getbank.ml/api/inlogPoging.php?reknr="+ rekeningnummer));
            JSONArray buffer = root.getJSONArray("inlogpoging");


            for (int i = 0; i < buffer.length(); i++) {
                nummer = buffer.getJSONObject(i);
            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return nummer.getString("INLOGPOGINGEN");
    }
}

