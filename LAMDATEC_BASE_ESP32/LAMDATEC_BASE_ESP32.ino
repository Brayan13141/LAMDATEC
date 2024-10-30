#include <math.h>
#include <MQ135.h>
#include <WiFi.h>
#include <FirebaseESP32.h>
#include <ESP32Time.h>
//#include <MQ2.h>


// Credenciales WiFi
const char* _SSID = "LAMDATEC";
const char* _PASSWORD = "itsur2024";

// URL de la base de datos en Firebase
#define BD "https://lamdatec-7c03d-default-rtdb.firebaseio.com/"
#define C "AIzaSyA7Ow8J-wFhpRnhRYYT4keNPSP9Q2vkcYM"
#define U "s20120185@alumnos.itsur.edu.mx"
#define Con "12345678"

// Pines y constantes
#define RZERO 55000
#define LED_BUILTIN 2
#define PIN_MQ135 34
#define PIN_MQ2 35

ESP32Time rtc;

//CONFIG PARA FIREBASE
FirebaseData firebaseData;
FirebaseAuth auth;
FirebaseConfig config;

MQ135 gasSensorMQ135 = MQ135(PIN_MQ135);

// Variable para el estado en Firebase
bool estado = 0;  // Valor inicial


unsigned long tiempoInicio = 0;
int acumuladorSensorMQ2 = 0;
int contadorLecturas = 0;
const int intervaloLectura = 600000; // 10 minutos en milisegundos


const int Seg = 10000; // 10 minutos en milisegundos

void setup() {//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    Serial.begin(115200);

    //pinMode(PIN_MQ135, INPUT);

    pinMode(PIN_MQ2, INPUT);

    // Conexión WiFi
    conectarWiFi();

    // Obtener y configurar la hora actual
    obtenerHora();

    // Autenticación y configuración de Firebase
    autenticarFirebase();

    float rze = 0;
    for (int i = 0; i < 30; i++) {
        rze = rze + analogRead(PIN_MQ135);

    }
    rze = rze / 30.0;
    Serial.print("VALOR PROMEDIO: ");
    Serial.println(rze);


    tiempoInicio = millis(); // inicializar el tiempo de inicio
}

void loop() {//----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    // Verificar si WiFi está conectado
    if (WiFi.status() != WL_CONNECTED) {
        Serial.println("Conexión WiFi perdida, intentando reconectar...");
        conectarWiFi();
    }

    int SensorMQ2 = analogRead(PIN_MQ2);

    String date = rtc.getTime("%Y-%m-%d");
    String Time = rtc.getTime("%H:%M");
    Serial.println(date+" "+Time);
    Serial.print("SENSOR MQ2: ");
    Serial.println(SensorMQ2);




    if (Firebase.getBool(firebaseData, "LAMDATEC/Sensores/Estatus")) {
        estado = firebaseData.boolData();
    } else {
        Serial.print("Error al obtener el estado: ");
        Serial.println(firebaseData.errorReason());
        autenticarFirebase();
    }
    if (estado) {
        if (Firebase.setInt(firebaseData, "LAMDATEC/Sensores/SENSORES/MQ2/Valor", SensorMQ2)) {
            acumuladorSensorMQ2 += SensorMQ2;
            contadorLecturas++;
        }
        else {
            Serial.print("Error al enviar los datos MQ2: ");
            Serial.println(firebaseData.errorReason());
        }
    }

/*
  if (millis() - tiempoInicio >= Seg) {
    int promedioMQ2 = acumuladorSensorMQ2 / contadorLecturas;
    if(Firebase.setInt(firebaseData, "LAMDATEC/Sensores/SENSORES/MQ2/FECHAS/"+date+"/"+Time+"/VSENSOR/", SensorMQ2))
    {  Serial.print("Promedio guardado: ");
      Serial.println(promedioMQ2);
    } else {
      Serial.print("Error al enviar el promedio: ");
      Serial.println(firebaseData.errorReason());
    }


    // Reiniciar el contador y acumulador
    acumuladorSensorMQ2 = 0;
    contadorLecturas = 0;
    tiempoInicio = millis();
  }
*/


    delay(2000);
}

// Función para conectar al WiFi con reintentos
void conectarWiFi() {
    Serial.println("CONECTANDO...");
    WiFi.mode(WIFI_STA);
    WiFi.disconnect();
    delay(2000);

    WiFi.begin(_SSID, _PASSWORD);
    int attempts = 0;

    while (WiFi.status() != WL_CONNECTED && attempts < 30) {
        delay(500);
        Serial.print(".");
        attempts++;
    }

    if (WiFi.status() == WL_CONNECTED) {
        Serial.println("\nConexión WiFi establecida.");
    } else {
        Serial.println("\nError: No se pudo conectar a WiFi.");
        ESP.restart();
    }
}

// Función para autenticarse en Firebase
void autenticarFirebase() {
    config.api_key = C;
    config.database_url = BD;
    auth.user.email = U;
    auth.user.password = Con;
    firebaseData.setBSSLBufferSize(4096, 1024);

    Firebase.begin(&config, &auth);
    Firebase.setDoubleDigits(5);
    Firebase.reconnectNetwork(true);

    if (Firebase.setBool(firebaseData, "LAMDATEC/Sensores/Estatus", 0)) {
        Serial.println("Estado inicial establecido en Firebase.");
    } else {
        Serial.print("Error al establecer el estado: ");
        Serial.println(firebaseData.errorReason());
        ESP.restart();
    }
}

// Función para obtener y configurar la hora desde NTP
void obtenerHora() {
    configTime(0, 0, "pool.ntp.org", "time.nist.gov");

    struct tm timeinfo;
    if (getLocalTime(&timeinfo)) {
        rtc.setTime(timeinfo.tm_hour, timeinfo.tm_min, timeinfo.tm_sec, timeinfo.tm_mday, timeinfo.tm_mon + 1, timeinfo.tm_year + 1900);
        Serial.println("RTC configurado con la hora actual.");
    } else {
        Serial.println("Error obteniendo la hora.");
        ESP.restart();
    }

    Serial.println(rtc.getTime("%Y-%m-%d %H:%M:%S"));
}