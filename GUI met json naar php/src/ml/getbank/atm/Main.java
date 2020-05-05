package ml.getbank.atm;

import javax.swing.*;
import java.awt.*;

import java.net.*;
import java.io.*;

import org.json.JSONArray;
import org.json.JSONObject;

public class Main{

private static String Naam;
    public static void main(String[] args){

        try {
            URLConnection connection = new URL("https://getbank.ml/api/account.php").openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append(System.lineSeparator());
            }
            bufferedReader.close();


             String response = stringBuilder.toString();
             System.out.println(response);

             JSONObject root = new JSONObject(response);

             //Naam = root.getString("success");
             //System.out.println(Naam);

             JSONArray klanten = root.getJSONArray("klantgegevens");
             for (int i = 0; i < klanten.length(); i++) {
                JSONObject klant = klanten.getJSONObject(i);

                 System.out.println("Klant naam: " + klant.getString("VOORNAAM"));
             }

        } catch (Exception e) {
            e.printStackTrace();
        }

        PinAutomaat pinAutomaat = new PinAutomaat();
    }
}
