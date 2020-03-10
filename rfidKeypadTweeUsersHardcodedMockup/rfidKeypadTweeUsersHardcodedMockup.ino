#include <SPI.h>
#include <MFRC522.h>
#include <Keypad.h>

#define SS_PIN 10
#define RST_PIN 2
MFRC522 mfrc522(SS_PIN, RST_PIN);


char passwordHRO[4] = {'1', '2', '3', '4'};
String tagHRO = "F6 D0 C2 32";

char passwordOV[4] = {'4', '3', '2', '1'};
String tagOV = "A1 47 70 00";

char password[4];

int ledGroen = 6;
int ledRood = 7;

bool rfidMode = true;
bool pasHRO = false;
bool pasOV = false;
bool loginHRO = false;
bool loginOV = false;

int attemptCounter = 0;

char key_pressed = 0; 
uint8_t i = 0;


const byte rows = 4;
const byte columns = 4;
// Keypad pin map
char hexaKeys[rows][columns] = {
  {'1', '2', '3', 'A'},
  {'4', '5', '6', 'B'},
  {'7', '8', '9', 'C'},
  {'*', '0', '#', 'D'}
};
// Initializing pins for keypad
byte row_pins[rows] = {A0, A1, A2, A3};
byte column_pins[columns] = {2, 1, 0};
// Create instance for keypad
Keypad keypad_key = Keypad( makeKeymap(hexaKeys), row_pins, column_pins, rows, columns);

void setup() {
  // put your setup code here, to run once:
  mfrc522.PCD_Init();   // Init MFRC522
  Serial.begin(9600);
  SPI.begin();
  pinMode(ledRood, OUTPUT);
  pinMode(ledGroen, OUTPUT);
}

void loop() {
  // put your main code here, to run repeatedly:

  if (rfidMode == true)
  {
    Serial.println("Zoeken naar pas");
    digitalWrite(ledRood, LOW);
    digitalWrite(ledGroen, LOW);
  }

 // Look for new cards
    if ( ! mfrc522.PICC_IsNewCardPresent()) {
      return;
    }
    // Select one of the cards
    if ( ! mfrc522.PICC_ReadCardSerial()) {
      return;
    }
    //Reading from the card
    String tag = "";
    for (byte j = 0; j < mfrc522.uid.size; j++)
    {
      tag.concat(String(mfrc522.uid.uidByte[j] < 0x10 ? " 0" : " "));
      tag.concat(String(mfrc522.uid.uidByte[j], HEX));
    }
    tag.toUpperCase();
    //Checking the card
    if (tag.substring(1) == tagHRO)
    {
      Serial.write("Toets uw pincode in:");
      rfidMode = false;
      pasHRO = true;
      
    }
    else if (tag.substring(1) == tagOV)
    {
      Serial.write("Toets uw pincode in:");
      rfidMode = false;
      pasOV = true;
    }
    else
    {
      Serial.write("Verkeerde pas");
      rfidMode = true;
      pasOV = false;
      pasHRO = false;
    }
  
  // If RFID mode is false, it will look for keys from keypad
  if (rfidMode == false) {
    key_pressed = keypad_key.getKey(); // Storing keys
    if (key_pressed)
    {
      password[i++] = key_pressed; // Storing in password variable
    }
    if (i == 4) // If 4 keys are completed
    {
      delay(200);
      if (!(strncmp(password, passwordHRO, 4))) // If password is matched
      {
        if ( pasHRO == true)
      {
        Serial.print("Pass Accepted");
        digitalWrite(ledRood, HIGH);
      }     
       if (!(strncmp(password, passwordOV, 4))) // If password is matched
      {
        if ( pasOV == true)
        {
          Serial.print("Pass Accepted");
          digitalWrite(ledGroen, HIGH);
        }
      }
     }
      else    // If password is not matched
      {
        Serial.print("Wrong Password");
        attemptCounter++;
      }
      if (attemptCounter = 2)
      {
        rfidMode = true;
      }
    }
  }
}
