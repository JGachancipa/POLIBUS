#include <Arduino.h>
#include <TinyGPS++.h>
#include <WiFi.h>
#include <HTTPClient.h>

TinyGPSPlus gps;
#define RXD2 16
#define TXD2 17

const char* ssid = "Carola 5G"; 
const char* password = "1033680242*";

float latitude, longitude;
String lat_str, lng_str;

const char* loginUrl = "https://polibug-gps-dcef309899c5.herokuapp.com/api/auth/login";
const char* apiUrl = "https://polibug-gps-dcef309899c5.herokuapp.com/api/v1/gps/location";

String authToken = "";

WiFiServer server(80);

unsigned long lastGPSUpdate = 0;
unsigned long gpsTimeout = 10000;

float bogotaLat = 4.7110;
float bogotaLng = -74.0721;

void getAuthToken();
void sendGPSData();


void setup() {
  Serial.begin(115200);
  Serial2.begin(9600, SERIAL_8N1, RXD2, TXD2);
  Serial.println("Iniciando GPS...");

  Serial.print("Conectando a WiFi: ");
  Serial.println(ssid);
  WiFi.begin(ssid, password);
  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }
  Serial.println("\nWiFi conectado.");
  Serial.print("Dirección IP: ");
  Serial.println(WiFi.localIP());
}

void loop() {
  unsigned long currentMillis = millis();
  
  while (Serial2.available() > 0) {
    if (gps.encode(Serial2.read())) {
      if (gps.location.isValid()) {
        latitude = gps.location.lat();
        longitude = gps.location.lng();
        lat_str = String(latitude, 6);
        lng_str = String(longitude, 6);
        lastGPSUpdate = currentMillis;
      }
    }
  }

  if (currentMillis - lastGPSUpdate > gpsTimeout) {
    latitude = bogotaLat;
    longitude = bogotaLng;
    lat_str = String(latitude, 6);
    lng_str = String(longitude, 6);
  }

  if (authToken == "") {
    getAuthToken();
  } else {
    sendGPSData();
  }

  delay(10000);
}

void getAuthToken() {
  HTTPClient http;
  
  http.begin(loginUrl);
  http.addHeader("Content-Type", "application/json");

  String postData = "{\"username\": \"root\", \"password\": \"bG96M0FtN2p1czFDemZ1\"}";

  int httpCode = http.POST(postData);

  if (httpCode > 0) {
    String payload = http.getString();
    Serial.println("Token recibido: " + payload);
    
    authToken = payload;
  } else {
    Serial.println("Error al obtener token");
  }

  http.end();
}

void sendGPSData() {
  HTTPClient http;

  http.begin(apiUrl);
  http.addHeader("Content-Type", "application/json");
  http.addHeader("Authorization", "Bearer " + authToken);

  String postData = "[{\"id\": 1, \"latitude\": " + lat_str + ", \"longitude\": " + lng_str + "}]";

  int httpCode = http.POST(postData);

  if (httpCode > 0) {
    String payload = http.getString();
    Serial.println("Datos enviados: " + payload);
  } else {
    Serial.println("Error al enviar datos");
  }

  http.end();
}
