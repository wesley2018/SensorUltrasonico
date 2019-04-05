//INCLUSÃO DAS BIBLIOTECAS NECESSÁRIAS PARA A EXECUÇÃO DO CÓDIGO
#include <SPI.h>
#include <Client.h>
#include <Ethernet.h>
#include <Server.h>
#include <Udp.h>
#include "Ultrasonic.h"

#define echoPin 7 // PINO 7 RECEBE O PULSO DO ECHO
#define trigPin 6 // PINO 6 ENVIA O PULSO PARA GERAR O ECHO

Ultrasonic ultrasonic(6,7); //INICIALIZANDO OS PINOS DO ARDUINO

byte mac[] = { 0xDE, 0xAD, 0xBE, 0xEF, 0xFE, 0xED }; // NÃO PRECISA MEXER
byte ip[] = { 192, 168, 0, 177 }; // COLOQUE UMA FAIXA DE IP DISPONÍVEL DO SEU ROTEADOR. EX: 192.168.1.110  **** ISSO VARIA, NO MEU CASO É: 192.168.0.177
byte gateway[] = { 192, 168, 0, 1 }; // MUDE PARA O GATEWAY PADRÃO DO SEU ROTEADOR **** NO MEU CASO É O 192.168.0.1
byte subnet[] = { 255, 255, 255, 0 }; //NÃO PRECISA MEXER
EthernetServer server(80); //CASO OCORRA PROBLEMAS COM A PORTA 80, UTILIZE OUTRA (EX:8082,8089)
byte sampledata=50;

String readString = String(30); //CRIA UMA STRING CHAMADA "readString"
int distancia;
String result; //CRIA UMA STRING CHAMADA "result"

void setup(){

  Ethernet.begin(mac, ip, gateway, subnet); // INICIALIZA A CONEXÃO ETHERNET
  
   pinMode(echoPin, INPUT); // DEFINE O PINO 7 COMO ENTRADA (RECEBE)
   pinMode(trigPin, OUTPUT); // DEFINE O PINO 6 COMO SAIDA (ENVIA)
}
void loop(){
  
hcsr04(); // FAZ A CHAMADA DO MÉTODO "hcsr04()"
    
EthernetClient client = server.available(); // CRIA UMA VARIÁVEL CHAMADA client
  if (client) { // SE EXISTE CLIENTE
    while (client.connected()) { //ENQUANTO  EXISTIR CLIENTE CONECTADO
   if (client.available()) { // SE EXISTIR CLIENTE HABILITADO
    char c = client.read(); //CRIA A VARIÁVEL c

    if (readString.length() < 100) // SE O ARRAY FOR MENOR QUE 100
      {
        readString += c; // "readstring" VAI RECEBER OS CARACTERES LIDO
      }
        if (c == '\n') { // SE ENCONTRAR "\n" É O FINAL DO CABEÇALHO DA REQUISIÇÃO HTTP
          if (readString.indexOf("?") <0) //SE ENCONTRAR O CARACTER "?"
          {
          }
          else // SENÃO
        if(readString.indexOf("L=1") >0){ //SE ENCONTRAR O PARÂMETRO "L=1"
          result = String(distancia); //  STRING "result" RECEBE O VALOR MEDIDO
        }
          client.println("HTTP/1.1 200 OK"); // ESCREVE PARA O CLIENTE A VERSÃO DO HTTP
          client.println("Content-Type: text/html"); // ESCREVE PARA O CLIENTE O TIPO DE CONTEÚDO(texto/html)
          client.println();
          
          client.println(result); // RETORNA PARA O CLIENTE O VALOR DA DISTÂNCIA
          readString="";
          client.stop(); //FINALIZA A REQUISIÇÃO HTTP
            }
          }
        }
      }
 }
 void hcsr04(){
    digitalWrite(trigPin, LOW); //SETA O PINO 6 COM UM PULSO BAIXO "LOW" OU DESLIGADO OU AINDA 0
    delayMicroseconds(2); // DELAY DE 2 MICROSSEGUNDOS
    digitalWrite(trigPin, HIGH); //SETA O PINO 6 COM PULSO ALTO "HIGH" OU LIGADO OU AINDA 1
    delayMicroseconds(10); // DELAY DE 10 MICROSSEGUNDOS
    digitalWrite(trigPin, LOW); //SETA O PINO 6 COM PULSO BAIXO NOVAMENTE
    // FUNÇÃO RANGING, FAZ A CONVERSÃO DO TEMPO DE
    //RESPOSTA DO ECHO EM CENTIMETROS, E ARMAZENA
    //NA VARIAVEL "distancia"
    distancia = (ultrasonic.Ranging(CM));
    delay(500); // DELAY DE 1 SEGUNDO
 }
