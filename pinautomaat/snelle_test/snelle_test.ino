#define motorPin10 2 
#define motorPin20 5
#define motorPin50 8


void setup() {
  Serial.begin(9600);
  pinMode(motorPin10, OUTPUT);
  pinMode(motorPin20, OUTPUT);
  pinMode(motorPin50, OUTPUT);
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
      digitalWrite(motorPin10,HIGH);
      delay(1000);
      digitalWrite(motorPin10, LOW);
    }
    if(msg == "20")
    {
      Serial.println("Er wordt nu 1 "+msg+" biljet geprint");      
      digitalWrite(motorPin20,HIGH);
      delay(1000);
      digitalWrite(motorPin20, LOW);
    }
    if(msg == "50")
    {
      Serial.println("Er wordt nu 1 "+msg+" biljet geprint");
      digitalWrite(motorPin50,HIGH);
      delay(1000);
      digitalWrite(motorPin50, LOW);
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
