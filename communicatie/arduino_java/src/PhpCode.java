import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.*;
import org.json.JSONArray;
import org.json.JSONObject;

public class PhpCode {

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


    public static String account(String rekeningnummer) {
        JSONObject nummer = null;

        try{

            //  JSONObject root = new JSONObject(fetch("https://getbank.ml/api/account.php"));

            //  //Naam = root.getString("success");
            //  //System.out.println(Naam);
            //  JSONArray klanten = root.getJSONArray("klantgegevens");
            //  for (int i = 0; i < klanten.length(); i++) {
            //     JSONObject klant = klanten.getJSONObject(i);
            //      System.out.println("Klant naam: " + klant.getString("VOORNAAM"));

            //String rekeningnummer = "SU-GERM-00000001";
            //String pincode = "1234";
            JSONObject root = new JSONObject(fetch("https://getbank.ml/api/rekeningnummer.php?reknr="+ rekeningnummer));
            JSONArray buffer = root.getJSONArray("rekeningnummer");
            for (int i = 0; i < buffer.length(); i++) {
                nummer = buffer.getJSONObject(i);
                System.out.println("Rekeningnummer is: " + nummer.getString("REKENINGNUMMER"));
                //System.out.println("PINCODE is: " + saldo.getString("PINCODE"));
            }

            if(nummer.getString("INLOGPOGINGEN").equals("3")) {
                return "";
            }

        }

        catch (Exception e) {
            e.printStackTrace();
        }

        return nummer.getString("REKENINGNUMMER");

    }

    public static String pincode(String rekeningnummer, String pincode ) {
        JSONObject nummer = null;

        try{

            JSONObject root = new JSONObject(fetch("https://getbank.ml/api/pincode.php?reknr="+ rekeningnummer + "&pincode=" + pincode));
            JSONArray buffer = root.getJSONArray("rekeningnummer");

            if(buffer.isEmpty()) {
                return "";
            }

            for (int i = 0; i < buffer.length(); i++) {
                nummer = buffer.getJSONObject(i);
                System.out.println("pincode is: " + nummer.getString("PINCODE"));
                System.out.println("inlogpoging is: " + nummer.getString("INLOGPOGINGEN"));
            }

            if(nummer.getString("INLOGPOGINGEN").equals("3")) {
                return "";
            }

        }

        catch (Exception e) {
            e.printStackTrace();
        }

        return nummer.getString("PINCODE");
    }
}
