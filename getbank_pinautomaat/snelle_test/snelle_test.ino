#define CW 8


void setup() {
  Serial.begin(9600);
  pinMode(CW, OUTPUT);
}

void loop() {
  String msg = "";

  if(Serial.available() > 0)
  {
    while(Serial.available() > 0)
    {
      msg += char(Serial.read());
    }
    if(msg == "10")
    {
      Serial.println("Er wordt nu 1 "+msg+" biljet geprint");
      digitalWrite(CW,HIGH);
      delay(1000);
      digitalWrite(CW, LOW);
    }
    if(msg == "20")
    {
      Serial.println("Er wordt nu 1 "+msg+" biljet geprint");      
      digitalWrite(CW,HIGH);
      delay(1000);
      digitalWrite(CW, LOW);
    }
    if(msg == "50")
    {
      Serial.println("Er wordt nu 1 "+msg+" biljet geprint");
      digitalWrite(CW,HIGH);
      delay(1000);
      digitalWrite(CW, LOW);
    }
    else
    {
      Serial.println("Dit biljet bestaat niet");
    }
  }
  else
  {
    Serial.println("There is no data available right now");
    delay(5000);  
  }
}
