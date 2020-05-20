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


    public static void main(String[] args){

        try{

            //  JSONObject root = new JSONObject(fetch("https://getbank.ml/api/account.php"));

            //  //Naam = root.getString("success");
            //  //System.out.println(Naam);
            //  JSONArray klanten = root.getJSONArray("klantgegevens");
            //  for (int i = 0; i < klanten.length(); i++) {
            //     JSONObject klant = klanten.getJSONObject(i);
            //      System.out.println("Klant naam: " + klant.getString("VOORNAAM"));

            String rekeningnummer = "SU-GERM-00000001";
            String pincode = "1234";
            JSONObject root = new JSONObject(fetch("https://getbank.ml/api/pincode.php?reknr="+ rekeningnummer +"&pincode="+ pincode));
            JSONArray saldos = root.getJSONArray("rekeningnummer");
            for (int i = 0; i < saldos.length(); i++) {
               JSONObject saldo = saldos.getJSONObject(i);
                System.out.println("Rekeningnummer is: " + saldo.getString("REKENINGNUMMER"));
                System.out.println("PINCODE is: " + saldo.getString("PINCODE"));
            }
        }




         catch (Exception e) {
            e.printStackTrace();
        }

        //PinAutomaat pinAutomaat = new PinAutomaat();
    }
}
