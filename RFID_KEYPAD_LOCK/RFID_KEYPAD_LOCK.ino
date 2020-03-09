#include <SPI.h>
#include <MFRC522.h>
#include <Keypad.h>

#define password_length 5 // 4 cijfers en een NULL voor keypad
#define SS_PIN 10
#define RST_PIN 2
#define ACCESS_DELAY 2000
#define DENIED_DELAY 1000
MFRC522 mfrc522(SS_PIN, RST_PIN);   // Create MFRC522 instance.

bool pasHRO = false;
bool pinHRO = false;
bool pasOV = false;
bool pinOV = false;

const byte ROWS = 4; //four rows
const byte COLS = 4; //four columns
//define the cymbols on the buttons of the keypads
char keys[ROWS][COLS] = {
  {'1', '2', '3', 'A'},
  {'4', '5', '6', 'B'},
  {'7', '8', '9', 'C'},
  {'*', '0', '#', 'D'}
};

byte rowPins[ROWS] = {4, 3, A0, A1}; //connect to the row pinouts of the keypad
byte colPins[COLS] = {A2, A3, A4, A5}; //connect to the column pinouts of the keypad

char passwordHRO[password_length] = "4321"; // hard-coded pincode
char passdataHRO[password_length] ; // de array waar de invoer in weggezet wordt
char passwordOV[password_length] = "1234"; // hard-coded pincode
char passdataOV[password_length] ; // de array waar de invoer in weggezet wordt
byte data_index = 0, pass_index = 0; // bijhouders voor de invoer
// instantie maken van Keypad object
Keypad keypad = Keypad( makeKeymap(keys), rowPins, colPins, ROWS, COLS );


void setup()
{

  Serial.begin(9600);   // Initiate a serial communication
  SPI.begin();          // Initiate  SPI bus
  mfrc522.PCD_Init();   // Initiate MFRC522
  Serial.println("Put your card to the reader...");
  Serial.println();

}

//--------------------------MAIN LOOP -----------------------
void loop()
{


  //----------------------------------RFID PART---------------------------
  // Look for new cards
  if ( ! mfrc522.PICC_IsNewCardPresent())
  {
    return;
  }
  // Select one of the cards
  if ( ! mfrc522.PICC_ReadCardSerial())
  {
    return;
  }
  //Show UID on serial monitor
  Serial.print("UID tag :");
  String content = "";
  byte letter;
  for (byte i = 0; i < mfrc522.uid.size; i++)
  {
    Serial.print(mfrc522.uid.uidByte[i] < 0x10 ? " 0" : " ");
    Serial.print(mfrc522.uid.uidByte[i], HEX);
    content.concat(String(mfrc522.uid.uidByte[i] < 0x10 ? " 0" : " "));
    content.concat(String(mfrc522.uid.uidByte[i], HEX));
  }
  Serial.println();
  Serial.print("Message : ");
  content.toUpperCase();
  if (content.substring(1) == "F6 D0 C2 32") //change here the UID of the card/cards that you want to give access
  {
    Serial.println("Meneer HRO presenteert zijn pas");
    Serial.println();
    pasHRO = true;
  }

   if (content.substring(1) == "A1 47 70 00") //change here the UID of the card/cards that you want to give access
  {
    Serial.println("Meneer OV presenteert zijn pas");
    Serial.println();
    pasOV = true;
    Serial.println(pasOV);
  }

  else   {
    Serial.println("Onbekende pas.");
  }
 
  //--------------keypad-----------------

void clearData();
{
  while (data_index != 0)
  {
    passdataHRO[data_index--] = 0;
    passdataOV[data_index--] = 0;
  }
  return;
}

 while (pasHRO == true)
 {
   Serial.println("Toets uw pincode in: ");
 
  char key = keypad.getKey();

  if (key) {


    Serial.println(key);

    if (key != '#' )
    {
      Serial.println("");
      if (data_index <= password_length)
      {
        passdataHRO[data_index] = key; // bewaar ingevoerde karakter
        data_index++;
      }
      else
      {

        Serial.println("Data entry exceeds password length");// error: "Data entry exceeds password length"
        clearData(); // ingevoerde code verwijderen uit array
      }
    }
    else if (key == '#')
    {

      if (!strcmp(passdataHRO, passwordHRO) ) // er is geen plek waar ze verschillend zijn
      { 
        Serial.println("-------------------");
        Serial.println("Welkom meneer HRO bij de get bank");
        Serial.println("-------------------");



      }
      else  // password niet goed ingetypt
      {
        Serial.println("Wrong Password"); // error: “Wrong Password”
        Serial.println("-------------------------");


        clearData(); // ingevoerde code verwijderen uit array
      }

    }
    else
    {
      Serial.println("Something went wrong!");// error: "Something went wrong!"444
    }
    if (key == 'C')
    {
      Serial.println("closed");
      Serial.println("-------------------------");

      clearData(); // ingevoerde code verwijderen uit array
    }
    if (key == 'A')
    {

      clearData(); // ingevoerde code verwijderen uit array
      pasHRO == false;
      Serial.println("Pin process afgebroken");
    }
  }
 }
 while (pasOV == true)
 {
     Serial.println("Toets uw pincode in: ");
 
  char key = keypad.getKey();

  if (key) {


    Serial.println(key);

    if (key != '#' )
    {
      Serial.println("");
      if (data_index <= password_length)
      {
        passdataOV[data_index] = key; // bewaar ingevoerde karakter
        data_index++;
      }
      else
      {

        Serial.println("Data entry exceeds password length");// error: "Data entry exceeds password length"
        clearData(); // ingevoerde code verwijderen uit array
      }
    }
    else if (key == '#')
    {

      if (!strcmp(passdataOV, passwordOV) ) // er is geen plek waar ze verschillend zijn
      { 
        Serial.println("-------------------");
        Serial.println("Welkom meneer HRO bij de get bank");
        Serial.println("-------------------");



      }
      else  // password niet goed ingetypt
      {
        Serial.println("Wrong Password"); // error: “Wrong Password”
        Serial.println("-------------------------");


        clearData(); // ingevoerde code verwijderen uit array
      }

    }
    else
    {
      Serial.println("Something went wrong!");// error: "Something went wrong!"444
    }
    if (key == 'C')
    {
      Serial.println("closed");
      Serial.println("-------------------------");

      clearData(); // ingevoerde code verwijderen uit array
    }
    if (key == 'A')
    {

      clearData(); // ingevoerde code verwijderen uit array
      pasOV == false;
      Serial.println("Pin proces afgebroken");
    }
  }
 }


// clearData functie haalt alle characters uit de passdata array en zet data_index weer op 0

}
