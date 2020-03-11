#include <SPI.h>
#include <MFRC522.h>
#include <Keypad.h>

#define SS_PIN 10
#define RST_PIN 2
MFRC522 mfrc522(SS_PIN, RST_PIN);


char passwordHRO[4] = {'1', '2', '3', '4'}; //Hardcoded password
String tagHRO = "F6 D0 C2 32";              //Hardcoded tagUID

char passwordOV[4] = {'4', '3', '2', '1'};
String tagOV = "A1 47 70 00";

char password[4];   //Lege array van 4 cijfers, hier komt het ingevoerde cijfer combinatie van de keypad. 

int ledGroen = 6;
int ledRood = 7;

bool rfidMode = true;
bool pasHRO = false;
bool pasOV = false;       //Alle booleans om de boel te beheren
bool loginHRO = false;
bool loginOV = false;

int attemptCounter = 0;     //Telt het aantal inlog pogingen

char key_pressed = 0;     //Telt het aantal ingetoetste knopjes op keypad
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
byte row_pins[rows] = {4, 3, A0, A1};     //De pins waarop de keypad aangesloten moet worden
byte column_pins[columns] = {A2, A3, A4, A5};
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


/* void clearData()
{
  while (password != 0)
  {
    password[i--] = 0;
  }
  return;
}
*/ 

void loop() {
  // put your main code here, to run repeatedly:

  if (rfidMode == true)  //Zoekt naar pas mode
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
    if (tag.substring(1) == tagHRO) //Wanneer de HRO pas gelezen wordt
    {
      Serial.print("Toets uw HRO pincode in:");
      rfidMode = false;
      pasHRO = true;
      
    }
    else if (tag.substring(1) == tagOV) //Wanneer de OV pas gelezen wordt
    {
      Serial.print("Toets uw OV pincode in:");
      rfidMode = false;
      pasOV = true;
    }
    else
    {
      Serial.write("Verkeerde pas");  //Wanneer een verkeerde pas gelezen wordt.
      rfidMode = true;
      pasOV = false;
      pasHRO = false;
    }
  
  // If RFID mode is false, it will look for keys from keypad
  while (rfidMode == false) {
    key_pressed = keypad_key.getKey(); // Storing keys
    if (key_pressed)
    {
      password[i++] = key_pressed; // Storing in password variable wat hier in komt is wat hij later vegerlijkt met jouw ingebakken password. 
      
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
        break;
      }     
       else if (!(strncmp(password, passwordOV, 4))) // If password is matched
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
        
        // clearData();
        attemptCounter++;  //Telt ééntje bij de attemptcounter
      }
      if (attemptCounter == 2)
      {
        rfidMode = true;
        Serial.print("Jij bent password get");
      }
    }
  }
  
}
