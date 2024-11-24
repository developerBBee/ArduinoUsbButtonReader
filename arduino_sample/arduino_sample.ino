const int buttonPin = 7; // デジタルピン7を使⽤
int buttonState = 0; 

void setup() {
  Serial.begin(115200);
  pinMode(buttonPin, INPUT);
}

void loop() {
  int buttonState = digitalRead(buttonPin);
  if (buttonState == HIGH) {
    Serial.println("CopyTactSwitchOn");
  } else {
    Serial.println("CopyTactSwitchOff");
  }

  delay(100); // 100ミリ秒間隔で読み取る
}
