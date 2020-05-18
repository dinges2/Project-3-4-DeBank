package ml.getbank.atm;

import javax.swing.*;
import java.awt.*;

import java.net.*;
import java.io.*;

import org.json.JSONArray;
import org.json.JSONObject;

public class Main{

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

private static String Naam;
    public static void main(String[] args){

        try {

             JSONObject root = new JSONObject(fetch("https://getbank.ml/api/account.php"));

             //Naam = root.getString("success");
             //System.out.println(Naam);

             JSONArray klanten = root.getJSONArray("klantgegevens");
             for (int i = 0; i < klanten.length(); i++) {
                JSONObject klant = klanten.getJSONObject(i);
                 System.out.println("Klant naam: " + klant.getString("VOORNAAM"));
             }

             String name = "696969";
             fetch("https://getbank.ml/api/saldo.php?name="+ name + "&saldo=999");


        } catch (Exception e) {
            e.printStackTrace();
        }

        PinAutomaat pinAutomaat = new PinAutomaat();
    }
}
