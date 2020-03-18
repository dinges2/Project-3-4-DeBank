// Include required libraries
#include <MFRC522.h>
#include <Keypad.h>
#include <SPI.h>
// Create instances

MFRC522 mfrc522(10, 9); // MFRC522 mfrc522(SS_PIN, RST_PIN)

// Initialize Pins for led's
// Blue LED is connected to 5V
constexpr uint8_t greenLed = 7;
constexpr uint8_t redLed = 6;
char initial_password[4] = {'1', '2', '3', '4'};  // Variable to store initial password
String tagUID = "91 C3 65 05";  // String to store UID of tag. Change it with your tag's UID
char hr[4] = {'4', '3', '2', '1'};
String tagHR = "F6 79 D5 32";
char password[4];   // Variable to store users password
char cancel;
boolean RFIDMode = true; // boolean to change modes
char key_pressed = 0; // Variable to store incoming keys
uint8_t i = 0;  // Variable used for counter
// defining how many rows and columns our keypad have
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
byte row_pins[rows] = {4, 3, A0, A1};
byte column_pins[columns] = {A2, A3, A4, A5};
// Create instance for keypad
Keypad keypad_key = Keypad( makeKeymap(hexaKeys), row_pins, column_pins, rows, columns);

void setup() {
  // Arduino Pin configuration
  pinMode(redLed, OUTPUT);
  pinMode(greenLed, OUTPUT);
  Serial.begin(9600);
  SPI.begin();      // Init SPI bus
  mfrc522.PCD_Init();   // Init MFRC522
  
}


void loop() {
  // System will first look for mode
  if (RFIDMode == true) {

    Serial.println("Zoeken naar pas");
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
    if (tag.substring(1) == tagUID)
    {
      // If UID of tag is matched.
      Serial.println("Bestaande pas");
      digitalWrite(greenLed, HIGH);
      delay(3000);
      digitalWrite(greenLed, LOW);
      Serial.println("Voer uw wachtwoord:");
      RFIDMode = false; // Make RFID mode false
    }
    else if (tag.substring(1) == tagHR)
    {
      // If UID of tag is matched.
      Serial.println("Bestaande pas");
      digitalWrite(greenLed, HIGH);
      delay(3000);
      digitalWrite(greenLed, LOW);
      Serial.println("Voer uw wachtwoord:");
      RFIDMode = false; // Make RFID mode false
    }
    else
    {
      // If UID of tag is not matched.
      Serial.println("Niet bestaande pas");
      digitalWrite(redLed, HIGH);
      delay(3000);
      digitalWrite(redLed, LOW);
      
    }
  }
  // If RFID mode is false, it will look for keys from keypad
  if (RFIDMode == false) {
    key_pressed = keypad_key.getKey(); // Storing keys
    if (key_pressed)
    {
      password[i++] = key_pressed; // Storing in password variable
      cancel = key_pressed;
    }
    if (i == 4) // If 4 keys are completed
    {
      delay(200);
      if (!(strncmp(password, initial_password, 4))) // If password is matched
      {
        Serial.println("Juiste wachtwoord");
        digitalWrite(greenLed, HIGH);
        delay(3000);
        digitalWrite(greenLed, LOW);
        Serial.println("Welkom");
        
        i = 0;
        //RFIDMode = true; // Make RFID mode true
      }
      else if (!(strncmp(password, hr, 4))) // If password is matched
      {
        Serial.println("Juiste wachtwoord");
        digitalWrite(greenLed, HIGH);
        delay(3000);
        digitalWrite(greenLed, LOW);
        Serial.println("Welkom");
        
        i = 0;
        //RFIDMode = true; // Make RFID mode true
      }
      else    // If password is not matched
      {
        Serial.println("Foute wachtwoord");
        digitalWrite(redLed, HIGH);
        delay(3000);
        digitalWrite(redLed, LOW);
        
        i = 0;
        RFIDMode = true;  // Make RFID mode true
      }
    }
    else if(cancel == 'C')
    {
      RFIDMode = true;  // Make RFID mode true
      i = 0;
      Serial.println("Beëindigd");
      delay(3000);
      
    }
    cancel = NULL;
  }
}
