#include <MFRC522.h>
#include <Keypad.h>
#include <SPI.h>

#define motor1 2
#define motor2 5
#define motor3 8
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
  pinMode(motor1, OUTPUT);
  pinMode(motor2, OUTPUT);
  pinMode(motor3, OUTPUT);

  for (byte i = 0; i < 6; i++) {
    key.keyByte[i] = 0xFF;  
  }

}

void loop() {

  //check if data is available
  if(Serial.available() > 0) {
    line = Serial.readString();
    
  }
  
  
  //if and if-else statement with different "mode" depends what it gets from java

  
  //mode when checking the rfid
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
    status = mfrc522.PCD_Authenticate(MFRC522::PICC_CMD_MF_AUTH_KEY_A, 3, &key, &(mfrc522.uid)); 
    if (status != MFRC522::STATUS_OK) {
      //Serial.print(F("Authentication failed: "));
      //Serial.println(mfrc522.GetStatusCodeName(status));
      digitalWrite(redLed, HIGH);
      delay(2000);
      digitalWrite(redLed, LOW);
      return;
    }

    //gives reading failed if something is wrong
    status = mfrc522.MIFARE_Read(block, rfid_buffer, &len);
    if (status != MFRC522::STATUS_OK) {
      Serial.print(F("Reading failed: "));
      Serial.println(mfrc522.GetStatusCodeName(status));
      return;
    }

    //save the reading data in a buffer
    for (uint8_t i = 0; i < 16; i++) {
      Serial.write(rfid_buffer[i]);
      
    }
    Serial.print('\0');

    mfrc522.PICC_HaltA();
    mfrc522.PCD_StopCrypto1();

  }

  

  
  //mode that check the pin
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
    else if(line == "abort") {
      mode = "rfid";
    }

  }

  
  
  //mode for the mainMenu
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
      motor50(1000);
      motor20(1000);
    }
    else if(line == "withdraw") {
      mode = "mainMenu";
    }
  }



  //mode for the withdraw process
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



  //mode for the balance process
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



  //mode when you choose the option 10
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
      mode = "mainMenu";
    }
  }



  //mode when you choose option 20
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
      mode = "mainMenu";
    }
  }



  //mode when you choose the option 50
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
      motor50(1000);
      mode = "receipt";
    }
    else if(line == "option2") {
      motor20(2000);
      motor10(1000);
      mode = "receipt";
    }
    else if(line == "withdraw") {
      mode = "mainMenu";
    }
  }



  //mode when you choose the option 100
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
      motor20(3000);
      motor10(4000);
      mode = "receipt";
    }
    else if(line == "option2") {
      motor50(2000);
      mode = "receipt";
    }
    else if(line == "withdraw") {
      mode = "mainMenu";
    }
  }


  //mode for the receipt process
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
      mode = "mainMenu";
    }
    
    
  }


  //mode for the billing process
  else if(mode == "check") {
    if(line.length() == 3) {
      
      motorProcess((line.charAt(2) - '0')*1000, (line.charAt(1) - '0')*1000, (line.charAt(0) - '0')*1000);
      mode = "receipt";
    }

  }
}

//for turning on the dc depends on time
void motor10(int tijd) {
  digitalWrite(motor1, HIGH);
  delay(tijd);
  digitalWrite(motor1, LOW);
}
void motor20(int tijd) {
  digitalWrite(motor2, HIGH);
  delay(tijd);
  digitalWrite(motor2, LOW);
}
void motor50(int tijd) {
  digitalWrite(motor3, HIGH);
  delay(tijd);
  digitalWrite(motor3, LOW);
}

//process which dc has to be on
void motorProcess(int tijd1, int tijd2, int tijd3) {
  motor10(tijd1);
  motor20(tijd2);
  motor50(tijd3);
}
