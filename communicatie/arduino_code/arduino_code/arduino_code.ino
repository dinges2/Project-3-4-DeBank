#include <MFRC522.h>
#include <Keypad.h>
#include <SPI.h>

#define redLed 6
#define greenLed 7
#define RST_PIN 9
#define SS_PIN 10

//rfid
String mode = "rfid";
byte len = 18;
byte block = 1;
byte rfid_buffer[18];

// Create instances rfid
MFRC522 mfrc522(10, 9); // MFRC522 mfrc522(SS_PIN, RST_PIN)
MFRC522::MIFARE_Key key;
MFRC522::StatusCode status;

//keypad
char key_pressed = 0;
const byte rows = 4;
const byte columns = 4;

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

//variables
String line;

void setup() {
  Serial.begin(9600);
  SPI.begin();      
  mfrc522.PCD_Init();

  pinMode(redLed, OUTPUT);
  pinMode(greenLed, OUTPUT);

  for (byte i = 0; i < 6; i++) {
    key.keyByte[i] = 0xFF;  //keyByte is defined in the "MIFARE_Key" 'struct' definition in the .h file of the library
  }

}

void loop() {
  
  if(Serial.available() > 0) {
    line = Serial.readString();
    
  }
  
  
  
  
  if(mode == "rfid") {

    if(line == "ok") {
      digitalWrite(greenLed, HIGH);
      delay(2000);
      digitalWrite(greenLed, LOW);
      mode = "pinCheck";
    }
    else if(line == "wrong") {
      digitalWrite(redLed, HIGH);
      delay(2000);
      digitalWrite(redLed, LOW);
      line = "";
    }
    
    // Look for new cards
    if ( ! mfrc522.PICC_IsNewCardPresent()) {
      return;
    }
    
    // Select one of the cards
    if ( ! mfrc522.PICC_ReadCardSerial()) {
      return;
    }
        
    //reading the rfid at block 1
    status = mfrc522.PCD_Authenticate(MFRC522::PICC_CMD_MF_AUTH_KEY_A, 3, &key, &(mfrc522.uid)); //line 834 of MFRC522.cpp file
    if (status != MFRC522::STATUS_OK) {
      Serial.print(F("Authentication failed: "));
      Serial.println(mfrc522.GetStatusCodeName(status));
      return;
    }

    status = mfrc522.MIFARE_Read(block, rfid_buffer, &len);
    if (status != MFRC522::STATUS_OK) {
      Serial.print(F("Reading failed: "));
      Serial.println(mfrc522.GetStatusCodeName(status));
      return;
    }

    for (uint8_t i = 0; i < 16; i++) {
      Serial.write(rfid_buffer[i]);
      
    }
    Serial.print('\0');

    mfrc522.PICC_HaltA();
    mfrc522.PCD_StopCrypto1();

  }

  

  
  
  else if(mode == "pinCheck") {

    key_pressed = keypad_key.getKey();

    if(key_pressed) {
      Serial.print(key_pressed);
      Serial.print('!');
    }

    if(line == "pinOk") {
      digitalWrite(greenLed, HIGH);
      delay(2000);
      digitalWrite(greenLed, LOW);
      mode = "mainMenu";
    }
    else if(line == "pinWrong") {
      digitalWrite(redLed, HIGH);
      delay(2000);
      digitalWrite(redLed, LOW);
      mode = "pinCheck";
      line = "";
    }
    else if(line == "block") {
      digitalWrite(redLed, HIGH);
      delay(2000);
      digitalWrite(redLed, LOW);
      mode = "rfid";
    }

  }

  
  
  
  else if(mode == "mainMenu") {
    key_pressed = keypad_key.getKey();

    if(key_pressed) {
      Serial.print(key_pressed);
      Serial.print('&');
    }

    if(line == "abort") {
      mode = "rfid";
    }
    else if(line == "opnemen") {
      mode = "withdraw";
    }
    else if(line == "saldo") {
      mode = "balance";
    }
    else if(line == "snel70") {
      mode = "rfid";
    }
  }




  else if(mode == "withdraw") {
    key_pressed = keypad_key.getKey();

    if(key_pressed) {
      Serial.print(key_pressed);
      Serial.print('$');
    }

    if(line == "abort") {
      mode = "rfid";
    }
    else if(line == "backToMain") {
      mode = "mainMenu";
    }
    else if(line == "ten") {
      motor10(1000);
      mode = "optionTen";
    }
    else if(line == "twenty") {
      mode = "optionTwenty";
    }
    else if(line == "fifty") {
      mode = "optionFifty";
    }
    else if(line == "hundred") {
      mode = "optionHundred";
    }
    else if(line == "enterAmount") {
      mode = "enterAmount";
    }
    
  }




  else if(mode == "balance") {
    key_pressed = keypad_key.getKey();

    if(key_pressed) {
      Serial.print(key_pressed);
      Serial.print('%');
    }

    if(line == "abort") {
      mode = "rfid";
    }
    else if(line == "backToMain") {
      mode = "mainMenu";
    }
  }




  else if(mode == "optionTen") {
    key_pressed = keypad_key.getKey();

    if(key_pressed) {
      Serial.print(key_pressed);
      Serial.print('@');
    }

    if(line == "abort") {
      mode = "rfid";
    }
    else if(line == "backToMain") {
      mode = "mainMenu";
    }
    else if(line == "yes") {
      mode = "rfid";
    }
    else if(line == "no") {
      mode = "rfid";
    }
    else if(line == "withdraw") {
      mode = "withdraw";
    }
  }




  else if(mode == "optionTwenty") {
    key_pressed = keypad_key.getKey();

    if(key_pressed) {
      Serial.print(key_pressed);
      Serial.print('+');
    }

    if(line == "abort") {
      mode = "rfid";
    }
    else if(line == "backToMain") {
      mode = "mainMenu";
    }
    else if(line == "option1") {
      motor10(2000);
      mode = "receipt";
    }
    else if(line == "option2") {
      motor20(1000);
      mode = "receipt";
    }
    else if(line == "withdraw") {
      mode = "withdraw";
    }
  }




  else if(mode == "optionFifty") {
    key_pressed = keypad_key.getKey();

    if(key_pressed) {
      Serial.print(key_pressed);
      Serial.print('(');
    }

    if(line == "abort") {
      mode = "rfid";
    }
    else if(line == "backToMain") {
      mode = "mainMenu";
    }
    else if(line == "option1") {
      motor10(5000);
      mode = "receipt";
    }
    else if(line == "option2") {
      motor20(2000);
      motor10(1000);
      mode = "receipt";
    }
    else if(line == "withdraw") {
      mode = "withdraw";
    }
  }




  else if(mode == "optionHundred") {
    key_pressed = keypad_key.getKey();

    if(key_pressed) {
      Serial.print(key_pressed);
      Serial.print('_');
    }

    if(line == "abort") {
      mode = "rfid";
    }
    else if(line == "backToMain") {
      mode = "mainMenu";
    }
    else if(line == "option1") {
      motor20(2000);
      motor10(4000);
      mode = "receipt";
    }
    else if(line == "option2") {
      motor20(5000);
      mode = "receipt";
    }
    else if(line == "withdraw") {
      mode = "withdraw";
    }
  }



  else if(mode == "receipt") {
    key_pressed = keypad_key.getKey();

    if(key_pressed) {
      Serial.print(key_pressed);
      Serial.print(',');
    }

    if(line == "abort") {
      mode = "rfid";
    }
    else if(line == "backToMain") {
      mode = "mainMenu";
    }
    else if(line == "yes") {
      mode = "rfid";
    }
    else if(line == "no") {
      mode = "rfid";
    }
  }




  else if(mode == "enterAmount") {
    key_pressed = keypad_key.getKey();

    if(key_pressed) {
      Serial.print(key_pressed);
      Serial.print('?');
    }

    if(line == "abort") {
      mode = "rfid";
    }
    else if(line == "backToMain") {
      mode = "mainMenu";
    }
    else if(line == "enter") {
      mode = "billChoice";
    }
    
  }




  else if(mode == "billChoice") {
    key_pressed = keypad_key.getKey();

    if(key_pressed) {
      Serial.print(key_pressed);
      Serial.print('`');
    }

    if(line == "abort") {
      mode = "rfid";
    }
    else if(line == "backToMain") {
      mode = "mainMenu";
    }
    else if(line == "option1") {
      mode = "check";
    }
    else if(line == "option2") {
      mode = "check";
    }
    else if(line == "withdraw") {
      mode = "withdraw";
    }
    
    
  }



  else if(mode == "check") {
    if(line.length() == 3) {
      
      motorProcess((line.charAt(2) - '0')*1000, (line.charAt(1) - '0')*1000, (line.charAt(0) - '0')*1000);
      mode = "receipt";
    }

  }
}

void motor10(int tijd) {
  digitalWrite(greenLed, HIGH);
  delay(tijd);
  digitalWrite(greenLed, LOW);
}
void motor20(int tijd) {
  digitalWrite(redLed, HIGH);
  delay(tijd);
  digitalWrite(redLed, LOW);
}
void motor50(int tijd) {
  delay(tijd);
}
void motorProcess(int tijd1, int tijd2, int tijd3) {
  motor10(tijd1);
  motor20(tijd2);
  motor50(tijd3);
}
