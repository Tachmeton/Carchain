# Verteilte Systeme - Projekt “Carchain”

# Table of Contents

1. [Aufgabenstellung und Beschreibung](#chapter-001)
2. [Architektur](#chapter-002)
3. [Setup](#chapter-003)<br>
    3.1 [Blockchain](#chapter-0031)<br>
    3.2 [Smart Contract](#chapter-0032)<br>
    3.3 [App](#chapter-0034)<br>
    3.4 [Raspberry Pi](#chapter-0035)
4. [Beschreibung der Funktionalität](#chapter-004)<br>
    4.1 [Blockchain](#chapter-0041)<br>
    4.2 [Smart Contract](#chapter-0042)<br>
    4.2.1 [Speicherung der Daten](#chapter-00421)<br>
    4.2.2 [Modifier](#chapter-00422)<br>
    4.2.3 [Funktionen des Smart Contract](#chapter-00423)<br>
    4.3 [Image-Server](#chapter-0043)<br>
    4.4 [App](#chapter-0044)<br>
    4.5 [Raspberry Pi](#chapter-0045)<br>
    4.5.1 [Allgemein](#chapter-00451)<br>
    4.5.2 [Regestrieren](#chapter-00452)<br>
    4.5.3 [QR-Lookup](#chapter-00453)
5. [Offene Punkte](#chapter-005)
6. [Bisherige Verantwortliche](#chapter-006)

# Aufgabenstellung, Beschreibung<a id="chapter-001"></a>

Mit “ChainCar” soll eine dezentrale Plattform zum Mieten & Vermieten von Automobilen entstehen. Ziel ist die dezentrale Abwicklung der Mietvorgänge sowie der Zugriffskontrolle auf die Fahrzeuge.

Ein Fahrzeugbesitzer kann sich über die mobile App der Plattform registrieren und sein Fahrzeug zur Miete registrieren. Dazu gibt er die erforderlichen Daten im Fahrzeug an (Raspberry), und das Fahrzeug meldet sich dann selbstständig an der Blockchain an. Über einen SmartContract wird das Fahrzeug in der Blockchain persistiert und zur Miete verfügbar gemacht. Kunden können sich nun ebenfalls über die App registrieren (mit ihrer Wallet-Adresse) und verfügbare Fahrzeuge (in einer Liste, sortiert nach Nähe) einsehen.

Möchte der Kunde ein verfügbares Fahrzeug mieten, wird eine Transaktion ausgelöst. Ein SmartContract schreibt den Beginn der Transaktion sowie die Mietkonditionen (Preis, Leistungen, …) in die Blockchain, erhebt die Kosten für die Miete im Voraus und registriert den Kunden als legitimen Nutzer des Fahrzeuges. Der Kunde kann das Fahrzeug nun entsperren (siehe IoT-Team) und fahren. Soll der Mietvorgang beendet werden, wird wieder der SmartContract kontaktiert, welcher dem Mieter abschließend die Nutzungsrechte am Fahrzeug wieder entzieht.

Die Kosten können entweder dem Auto auf das entsprechende Wallet - oder dem Vermieter direkt überwiesen werden.

# Architektur<a id="chapter-002"></a>

![Komponentendiagramm_01](Doku/assets/komponenten.png)

Die Architektur besteht hauptsächlich aus den folgenden 4 Komponenten:

- Car:
  Das Auto speichert seine Daten und verwaltet die Zutrittskontrolle. Es überprüft, wer ein legitimer Nutzer ist und Zugang zum Fahrzeug enthält.
- Blockchain:
  Die Blockchain verwaltet alle Transaktionen, Mietvorgänge und Nutzer.
- App:
  Über die App können Nutzer sich registrieren und Autos mieten/vermieten. Sie dient gleichzeitig als digitaler Autoschlüssel.
- Backend:
  Das Backend dient rein als Image-Server, um die Bilder der Fahrzeuge zu speichern, um die teure Speicherung in der Blockchain zu umgehen.

Die einzelnen Komponenten werden nachfolgend in Funktion und Setup erläutert.

# Setup<a id="chapter-003"></a>

Im Folgenden wird das Setup für die Carchain beschrieben. Um ein funktionierendes Setup zu erstellen müssen alle Komponenten provisioniert werden.<br><br>
Empfohlene Reihenfolge beim bereitstellen:

1. Blockchain
2. Smart Contract
3. Datenbank
4. Raspberry Pi
5. App installieren

## Blockchain<a id="chapter-0031"></a>
Um am Smart Contract zu entwickeln wird eine Blockchain gebraucht um den Smart Contract auch deployen zu können.
Bisher wird dazu Ganache genutzt. Es ist egal ob dabei die Desktop Variante oder Kommandozeilenvariante (Ganache-cli) genutzt wird. <br>
Um das gleiche Setup zu bekommen wie bisher genutzt wurde und damit richtige Testeinstellungen zu bekommen muss Ganache installiert werden.<br><br>
Zur Installation von Ganache Desktop: https://www.trufflesuite.com/ganache <br>
Zur Installation von Ganache-cli: https://github.com/trufflesuite/ganache-cli <br>
<br>
Wie man die Einstellungen ändert, unterscheidet sich zwischen der CLI und der Desktop Variante. Im Folgenden wird sich auf die CLI Variante beschränkt, da diese am besten einzusetzten ist wenn man nicht nur an der Blockchain entwickeln will sondern auch die Verbindung zu der App oder dem Raspberry Pi testen möchte. <br>
Einstellungen zum Start der Blockchain:<br>
ganache-cli -m "dragon canoe knife need marine business arctic honey make layer company solar" -h "<IP-Adresse>" -p <Port> -e 10000 &<br>
<br>
Die Message die hier Ganache mitgegeben wird bestimmt welche Adressen die Wallets, die schon vornherein generiert und zur Verfügung gestellt werden, haben. Voreingestellt ist für die Entwicklung 10.000 Ether, die jede Wallet von vornherein bekommt. Sobald der Command ausgeführt wurde gibt es nun eine Blockchian, die für die weitere Entwicklung genutzt werden kann.

## Smart Contract<a id="chapter-0032"></a>
Um mit den Smart Contracts arbeiten zu können sind einige Installationen nötig.
Diese werden mit npm (dem Node Package Manager) installiert. Zur Installation:<br><br>
https://www.npmjs.com/get-npm <br><br>
Dazu einfach nach Download des Git Repositpries im Blockchain Ordner folgenden Befehl ausführen:<br>
npm install<br>
<br>
Das Kompilieren, Testen und das Deployen des Smart Contracts geschieht nun mit Truffle. Zu aller erst muss dazu im Ordner /Blockchain/ethereum/ die Datei truffle-config.js angepasst werden. Dazu kann ein Server erstellt werden. Ein Beispiel hierfür ist hier zu sehen:<br>
![Einstellungen zum Deployen](Doku/assets/Einstellungen_Blockchian_Deploy.PNG)<br>
Neben Host und Port gibt es gas. Gas dient dazu zu bestimmen wieviel maximal an Gas ausgegeben werden darf um den Contract zu deployen. Network_id ist auch speziell für die Blockchain, falls es unterschiedlcihe Blockchians auf der gleichen IP gibt. Nachdem die Einstellungen getätigt sind kann nun der Contract compiled und deployed werden. Dazu dient das Kommando:<br>
truffle migrate --network <Network-Name> <br>
Der Network-Name ist dabei der Name, der in der truffle-config.js in den networks eingetragen wurde. Weitere Dokumentation zu dem Kommando truffle migrate ist unter<br><br>
https://www.trufflesuite.com/docs/truffle/getting-started/running-migrations<br><br>
zu finden.

## App<a id="chapter-0034"></a>

Derzeit wird die App als APK-Paket angeboten und muss manuell installiert werden. Eine Distribution über die gängigen AppStores wie bspw. GooglePlay ist natürlich angedacht, sobald eine Veröffentlichung denkbar ist.

Um die App zu installieren, muss mittels einem Datei-Manager die APK-Datei ausgewählt werden. In aktuelleren Android Versionen muss zunächst in den Einstellungen die Sicherheitsrichtlinie “Apps aus Fremdquellen installieren” aktiviert werden. Dann kann die App mit einem Klick auf die Datei installiert und ausgeführt werden. 

Zum aktuellen Zeitpunkt wird die entsprechende Wallet-Adresse noch vorgegeben und vom Server/Blockchain abgerufen, sodass in diesem Stadium die weitere Funktionalität getestet werden kann. Später ist angedacht, beim ersten Start der App ein Wallet-File zu generieren und mit den Credentials des Users zu sichern. Dieses Wallet kann dann aufgeladen werden.

Beim starten der App wird automatisch der aktuelle Standort abgerufen (sofern Berechtigung erteilt) und die verfügbaren Autos im Umkreis geladen und angezeigt [aktuell Demodaten].

## Raspberry Pi<a id="chapter-0035"></a>
**Raspberry Pi**<p>
Um die Funktionalitäten des Raspberry Pi's im Gesamtkontext des Projekts nutzen zu können muss zunächst eine passende Entwicklungsumgebung aufgebaut werden. Hierfür ist es notwendig an das Camera Serial Interface des Pi's eine Kamera anzuschließen und diese in den Einstellungen zu aktivieren (sudo raspi-config --> Enable Camera).

Desweiteren sind für den produktiven Betrieb 3 LEDs und 2 Buttons notwendig. Diese werden über die GPIO-Pins des Raspberry Pi's angesteuert werden. Dabei ist folgende Zuordnung zwischen den Pins und der Hardware zu treffen:

* Pin 17: Regestrieren Button (Blau)
* Pin 18: QR-Lookup Button (Gelb)
* Pin 19: Rote LED
* Pin 20: Gelbe LED
* Pin 21: Grüne LED

Die rote LED gibt im Projektkontext an, dass das Auto gesperrt ist, die gelbe LED symbolisiert das gerade intern eine Verarbeitung geschieht und die grüne LED steht entsprechend dafür, dass das Auto geunlocked ist. Die beiden Buttons werden dafür verwendet die beiden Hauptfunktionalitäten auszulösen: Der blaue um das Auto an der Blockchain und dem Datenbankserver anzulegen und der gelbe Button um einen QR-Lookup durchzuführen und das Auto gegebenenfalls anschließend zu entsperren.

Innerhalb unserer Entwicklungsumgebung wurden die genannten Komponenten auf einer Steckplatine platziert und entsprechend verkabelt. Die Steckplatine wurde über einen T-Cobbler mit den Pins des Raspberry Pis verbunden. Die notwendigen Hintergrundinformationen um die Verkabelung durchzuführen wurden mit Hilfe der Seite <br> https://www.w3schools.com/nodejs/nodejs_raspberrypi_gpio_intro.asp <br> erarbeitet.

Der Zugriff auf den Raspberry Pi in diesem Projekt geschieht über die Domain "carchain-pi.dnsuser.de". Die entsprechenden Stellen im Quellcode sind entsprechend bei einer geänderten Umsetzung zu ersetzen. Der Raspberry Pi befand sich innerhalb der Entwicklungsumgebung in einem lokalen Netz (192.168.178.0/24) eines Routers mit der lokalen IP: 192.168.178.1. An dem Netzwerk-Interface des Raspberry Pis wurde die statischen lokalen IP 192.168.178.42 angelegt. Damit die Domain "carchain-pi.dnsuser.de" stetig auf die sich ändernde öffentliche IP des Routers verweiset, wurde ein DynDNS-Dienst in der Konfiguration des Routers eingerichtet. Damit Anfragen aus dem Internet durch Router auf den Raspberry Pi innerhalb des lokalen Netzes weitergeleitet werden, wurde ein Port-Forwarding eingerichtet (Port: 22, für SSH-Verbindungen, Port 9100: für den "Prometheus-Node-Exporter").

Über die eigentliche Zielsetung des Projekts hinaus wurde im Projektverlauf noch eine Automatisierte-Bereitstellungspipeline mit Ansible erstellt, um neue Raspberry Pis/Autos einfach automatisiert provisionieren zu können und ein Monitoring des Raspberry Pis/Autos mit Prometheus und Grafana eingerichtet. Hierfür ist es notwendig Ansible, Prometheus und Grafana auf einem Server zu installieren. Im Projektkontext wurde der gleiche Server auf dem auch die Blockchain läuft verwendet. Prometheus und Grafana wurden dabei innerhalb von zwei Docker Containern betrieben.

**Automatisierte Bereitstellungspipeline**<p>
Wie bereits erwähnt wird Ansible für die optional entwickelte automatisierte Bereitstellungspipeline verwendet. Ansible besteht aus drei elementaren Komponenten:

1. Eine allgemeinen Konfigurationsdatei, in der wir lediglich den Pfad zu unserem Inventory angegeben haben:
https://github.com/Tachmeton/Carchain/blob/master/RaspberryPi/ansible_rapi/ansible.cfg
2. Ein Inventory, dass alle IPs/Hostnames der Systeme/Autos die orchestriert werden sollen enthält (in dieses wurde der Hostname des Raspberry Pis zusammen mit den Zugangsdaten hinterlegt):
https://github.com/Tachmeton/Carchain/blob/master/RaspberryPi/ansible_rapi/host.file
3. Ein Playbook, dieses enthält eine Menge von Tasks, also Aufgaben die von Ansbile auf der liste von Systemen in dem Inventory durchgeführt werden. Hier kann man deklarativ im yaml format den gewünschten Systemzustand beschreiben.
<br> https://github.com/Tachmeton/Carchain/blob/master/RaspberryPi/ansible_rapi/rapi_playbook.yml 
Unser Playbook enthält dabei folgende Tasks: 
  * Installieren von Node.Js, NPM und dem Node-Exporter über APT + enable Node-Repository 
  * Kopieren des privaten SSH-Schlüssels für Zugriff auf Git
  * Konfiguration von OpenSSH (durch ssh_config.j2) & ssh-keyscan git.smagcloud.de
  * Klonen des Repos (git@github.com:Tachmeton/Carchain.git)
  * Installieren der benötigten NPM-Pakete (aus npm-requirements.txt)
  * Kopieren des angelegten Unit-Files (durch car_js.service) für automatisches Starten
  * Konfiguration von Systemd (car_js.service & node-exporter starten + enablen)
  * Ausführen von register_car.js (Registrieren des PIs an der BC + Bild-Upload an DB)
Die von den Tasks verwendeten Hilfs-Dateien sind dabei in einem extra angelegten "Data"-Ordner im Repository vorhanden:<br>
https://github.com/Tachmeton/Carchain/tree/master/RaspberryPi/ansible_rapi/data<br>

Das Playbook kann durch den Befehl "ansible-playbook rapi_playbook.yaml" ausgeführt werden. Ansible provisioniert anschließend den Raspberry Pi automatisiert. Das Ergebnis ist, dass das Entwicklungs-Repository auf den Raspberry Pi gekloned wurde, alle benötigten Abhängigkeiten installiert und das Node.Js Skript, dass die Funktionen "Regestrieren" und "QR-Lookup" bereitstellt, als Systemd-Dienst gestartet wurde. Ebenfalls wird der für das Monitoring benötigte sogenannte "Prometheus-Node-Exporter" installiert und gestartet.

**Monitoring**<p>

Das optional umgesetzte Monitoring des RaPis mit Prometheus und Grafana wurde erstellt, da es sinnvoll ist ein Monitoring der Autos zu haben, wenn die Anwendung eines Tages produktiv zum Einatz kommen sollte, um Festzzustellen das alles korrekt funktioniert. Hierfür wurde um nicht immer mit einer abstrakten IP zu arbeiten, eine kostenlose ".tk" Domain für unseren Entwicklungs-/Blockchainserver eingerichtet. Angelegt wurde ein DNS-Eintrag der Domain "carchain-server.tk", die auf den Server mit der IP "193.196.54.51" verweist. Die entsprechenden Stellen im Quellcode sind entsprechend bei einer geänderten Umsetzung zu ersetzen.

Prometheus und Grafana selbst betreiben wir über Docker, das wir entsprechend auf dem Server installiert haben. Als Grundlage haben wir die offiziellen Standard-Images von Prometheus und Grafana verwendet. Da die Container an sich zustandslos sind und nach dem Stoppen unverändert vorliegen, haben wir für einen persistenten Speicher 2 Volumes angelegt, einen für Prometheus und einen für Grafana, die entsprechend in die Container gemountet werden. Dies hat denn Sinn dass nach einem Neustart des Servers unsere vorherigen gespeicherten Monitoring-Daten weiterhin verfügbar sind. Ein eingerichteter Cronjob sorgt dafür nach einem Neustart des Servers die beiden Container automatisch wieder gestartet werden.

Prometheus bestitzt eine Time Series Database, in die per HTTP gesammelte Metriken geschrieben werden. Die Konfiguration geschieht über eine YAML-Datei, die mit in den Container gemountet wird: <br>https://github.com/Tachmeton/Carchain/blob/master/RaspberryPi/monitoring_rapi/prometheus_config_carchain.yaml<br> In der Datei haben wir ein Scrape-Intervall von 15 Sekunden festgelegt, d.h. alle 15 Sekunden führt Prometheus eine HTTP-Anfrage zu dem Raspberry Pi aus, in unserem Fall über die Domain "carchain-pi.dnsuser.de".

Damit auf dem Raspberry Pi überhaupt an einem HTTP-Endpunkt Metriken bereitgestellt werden, kommt unter Prometheus ein sogennanter Exporter zum Einsatz der hierfür verantwortlich ist. Wir haben uns für den Einsatz des offiziell von Prometheus bereitgestellten Node-Exporter entschieden. Der Node-Exporter wurde in der automatisierten Bereitstellungspipeline zuvor installiert und gestartet. Der Node-Exporter ist in der Lage allgemeine HW und OS Metriken auf dem TCP port 9100 bereitzustellen. 

Damit wir auf die von Prometheus gesammelten und gespeicherten Daten übersichtlich zugreifen können verwenden wir Grafana. Mit Grafana lassen sich einfach Dashboards anlegen. Die Dashboards werden dabei im JSON-Format beschrieben, für den Node-Exporter haben wir dann ein vorgefertigtes Dashboard genommen und nur geringfügig angepasst: <br>https://github.com/Tachmeton/Carchain/blob/master/RaspberryPi/monitoring_rapi/grafana_dashboard_carchain.json<br> Das Dashboard ist in unserem Fall unter der Domain unserers Entwicklungsserveers auf Port 3000 erreichbar. Auch lassen sich in Grafana Alerts festlegen, so das wir automatisch benachrichtigt werden sobald z.B. ein Raspi ausfällt oder die System-Werte so aussehen das er in Zukunft ausfallen könnte.

# Beschreibung der Funktionalität<a id="chapter-004"></a>

## Blockchain<a id="chapter-0041"></a>
Die Blockchain an sich dient als Hub für die Smart Contracts. Auf geschieht alles, was mit der Verwaltung der Mietdaten. Des Weiteren läuft über die Blockchain die Bezahlung um Autos zu mieten. Deswegen ist es im späteren Sinnvoll die Contracts auf eine Public Blockchain zu deployen, damit die Bezahlung auch wirklich einen Impakt hat. Aktuell ist der Smart Contract der weiter unten beschrieben wird nur auf der Development Blockchain Ganache getestet.

## Smart Contract<a id="chapter-0042"></a>
Der Smart Contract ist im Ordner Blockchain/ethereum in der Datei carchain.sol implementiert. Als Smart Contract Programmiersprahce wurde Solidity benutzt.

### Speicherung der Daten<a id="chapter-00421"></a>
Die wichtigste Aufgabe, die der Smart Contract übernimmt, ist das speichern, der Daten über die zu vermietenden Autos. Diese sind in einer Map gespeichert, die bei Erstellung des Contracts aufgebaut wird. Die Map bildet die Wallet Adressen der Autos auf eine Car-Struktur ab.<br>
In selbiger Car Struktur sind gespeichert:
* Daten über den Owner
* Daten über den aktuellen Mietstatus
* die Zeit wie lange das Auto aktuell gemietet ist
* der insgesammt bekommende Ether
* der normale Standort des Autos
* Daten über das Auto an sich
* Konditionen zum Mieten des Autos <br>

### Modifier<a id="chapter-00422"></a>
Solidity bietet die Möglichkeit Modifier, mithilfe von requires zu defineiren. Modifier dienen in diesem Projekt dazu, vor dem richtigen Start einer Funktion zu checken ob der Aufrufer dazu berechtigt ist selbige aufzurufen und ob alle Vorraussetzungen für einen kompletten Durchlauf der Funktion gegeben ist. Bisher implementierte Modifier:<br>
<table>
  <thead>
    <tr>
      <th>Name des Modifiers</th>
      <th>Parameter</th>
      <th>Funktion</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td>knownCar</td>
      <td>Identifier/ Walletadresse des Autos</td>
      <td>Festellen ob das Auto welches die Funktion betrifft überhaupt exisitiert</td>
    </tr>
    <tr>
      <td>onlyOwner</td>
      <td>Identifier/ Walletadresse des Autos<br>Identifier/ Walletadresse des vorgeblichen Owners</td>
      <td>Feststellen ob die übergebene Wallet Adresse Owner des übergebenen Autos ist</td>
    </tr>
    <tr>
      <td>isLeased</td>
      <td>Identifier/ Walletadresse des Autos</td>
      <td>Befindet sich das Auto schon im gemieteten Zustand</td>
    </tr>
    <tr>
      <td>isLeasedBy</td>
      <td>Identifier/ Walletadresse des Autos<br>Identifier/ Walletadresse des Leasers</td>
      <td>Ist als Leaser die Walletadresse des vorgeblichen Mieters eingetragen</td>
    </tr>
    <tr>
      <td>carFree</td>
      <td>Identifier/ Walletadresse des Autos</td>
      <td>Ist das Auto aktuell frei</td>
    </tr>
  </tbody>
</table>
<br><br>
Bei allen Methoden, wo ein Auto mit beeinflusst ist, sollte die Funktion knownCar vorher aufgerufen werden um während der Ausführung der Funktion nicht auf Fehler zu stoßen. Auch die Restriktion auf Adressen die nur bestimmt Funktionen ausführen dürfen sit wichtig. Hierfür sollte der Modifier onlyOwner verwendet werden. Man köannte noch andenken, dass ein Auto mehrere Owner hat. Dies ist aktuell aebr nciht implementiert. Die Checks ob ein Auto schon geleased ist oder von wem geleased wurde oder es frei ist, werden natürlich dann eingesetzt wenn es um den akuten Mietvorgang geht.

### Funktionen des Smart Contract<a id="chapter-00423"></a>
Über das Speichern der Autodaten hinaus bietet der Smart Contract die Möglichkeit die Daten zu verändern. Dazu dienen verschiedene Arten von Funktionen.<br>
Die implementierten public Funktionen sind im Folgenden aufgelistet mit ihrer möglichen Einsatzfunktion:<br>
<table>
  <thead>
    <tr>
      <th>Name der Funktion</th>
      <th>Übergabeparameter</th>
      <th>Modifier</th>
      <th>View</th>
      <th>Rückgabewerte</th>
      <th>Payable</th>
      <th>Einsatzgebiet</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td>getter zugehörig zum Attribut des Autos</td>
      <td>Identifier/ Walletadresse des Autos</td>
      <td>knownCar</td>
      <td>Ja</td>
      <td>das Attribut im getter</td>
      <td>Nein</td>
      <td>App</td>
    </tr>
    <tr>
      <td>addCar</td>
      <td>Alle Daten zugehörig zum Auto</td>
      <td>ID ist noch nicht genutzt</td>
      <td>Nein</td>
      <td> - </td>
      <td>Nein</td>
      <td>App</td>
    </tr>
    <tr>
      <td>removeCar</td>
      <td>Identifier/ Walletadresse des Autos</td>
      <td>knownCar, onlyOwner, carFree</td>
      <td>Nein</td>
      <td> - </td>
      <td>Nein</td>
      <td>App</td>
    </tr>
    <tr>
      <td>rentCar</td>
      <td>Identifier/ Walletadresse des Autos</td>
      <td>knownCar, carFree</td>
      <td>Nein</td>
      <td> - </td>
      <td>Ja</td>
      <td>App</td>
    </tr>
    <tr>
      <td>mayRent</td>
      <td>Identifier/ Walletadresse des Autos</td>
      <td>knownCar</td>
      <td>Ja</td>
      <td>Boolean Value</td>
      <td>Nein</td>
      <td>App</td>
    </tr>
    <tr>
      <td>isLegalLeaser</td>
      <td>Identifier/ Walletadresse des Autos, Identifier/ Walletadresse des Mieters</td>
      <td>knownCar, isLeased. isLeasedBy</td>
      <td>Ja</td>
      <td>Boolean Value</td>
      <td>Nein</td>
      <td>Raspberry Pi</td>
    </tr>
    <tr>
      <td>returnCarToCarpool</td>
      <td>Identifier/ Walletadresse des Autos</td>
      <td>knownCar, isLeased. isLeasedBy</td>
      <td>Ja</td>
      <td> - </td>
      <td>Nein</td>
      <td>App</td>
    </tr>
    <tr>
      <td>getAvailableVehicles</td>
      <td> - </td>
      <td> - </td>
      <td>Ja</td>
      <td>Array aus Wallet Adressen zugehörig zu den freien Autos</td>
      <td>Nein</td>
      <td>App</td>
    </tr>
  </tbody>
</table>
<br>
Will man die Funktionen einmal ausprobieren oder einen kompletten Durchlauf sehen und ob dieser funktioniert. Eignet sich neben Unit Tests, die noch zu implementieren sind die Dateien carchain_interact.js und carchain_interact2.js im Ordner /Blockchain/ethereum. Die erste carchain_interact Datei legt eine Auto an, was in Folge dessen gemietet wird. Darufhin werden einzelne Attribute des Autos abgefragt und das Auto zum Schluss wieder gelöscht. In der zweiten Datei wird ein Auto angelegt, woraufhin alle verfügbaren Auots angefragt weren und danach die Blockchain wieder resettet wird. Beide Dateien sind dazu gedacht mit der Blockchian rumspielen zu können und Funktionen mit der Hilfe von Java Script zu testen und ein Gfefühl für die Blockchian zu bekommen.
<br>

## Image-Server<a id="chapter-0043"></a>

Carchain soll in der Lage sein Bilder für Autos speichern und abrufen zu können. Da das Speichern und Abfragen von Bildern an der Blockchain sowohl das Aufkommen von Transaktionen so wie den Speicherplatz sehr stark belasten würde, wurde diese Funktion ausgelagert. Es wurde daher ein REST-Backend zur Speicherung und Abfrage von Bildern implementiert. Das Backend bietet fünf Schnittstellen beziehungsweise Endpunkte an, die für die Speicherung und Abfrage der Bilder genutzt werden können. Das Backend wurde in NodeJs implementiert und hat eine Anbindung an eine Postgres Datenbank, in der die Bilder verwaltet werden. Die Bilder werden beim Empfang in Base64 umkodiert und in der Datenbank als Text gespeichert. Wenn ein Bild hochgeladen wird, wird dies für ein spezielles Auto hochgeladen. Daher muss im Voraus das Auto in der Datenbank angelegt worden sein, da ansonsten das Bild mit keinem Auto verknüpft werden kann. Es werden in dem Backend zwei (HTTP-) PUT, zwei GET und eine DELETE Funktion bereitgestellt. 

Diese Funktionen sind hier nun kurz mit Übergabeparameter und Return-Values angegeben: 

### put /addCar

Erwartet application/json mit carid 

```json
{
	"carid": "string"
}
```

Returns:
```json
{
  "success": 1,
  "error": false,
  "msg": "Message received-insert succeed"
}
```

### put /addImage

Erwartet application/json mit carid (string) und einem Bild (File)
```json
{
"carid": "string"
"carimage": "File"
}
```
Returns:
```json
{
  "success": 0,
  "error": true,
  "msg": "Message received-insert succeed",
  "imageid": 2
}
```
### get /getImage/:imageID:

Erwartet application/json mit carid (string) und einem Bild (File)
```json
{
"carid": "string"
"carimage": "File"
}
```
Returns:
```json
{
  "success": 0,
  "error": true,
  "msg": "Message received-insert succeed",
  "imageid": 2
}
```
### get /getImages/:carid

Erwartet carid in URL à 193.196.54.51:3005/getImages/String (Car Id)
Returns:

```json
{
  "success": 1,
  "error": false,
  "msg": "Message received with id:TÜ-LF-308",
  "imagejson": [
	{id:2,image:base64EncodedImage},
	{id:3,image:base64EncodedImage},… 
  ]
}
```
### delete deleteCar/:carid

Erwartet carid in URL à 193.196.54.51:3005/getImages/String (Car Id)
Returns:

```json
{
    "success": 1,
    "error": false,
    "msg": "Car and all images deleted"
}
```
Das Backend wird wie bereits erwähnt genutzt, um die Bilder für die Autos zu speichern. Es wird hier nun der Vorgang schematisch dargestellt. 

[BILD]

![img](data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAl0AAAFVCAYAAADVOVrbAAAgAElEQVR4Xu2dB5gUxRaFzy675MyScw6KRBFJgiAIiCBKUMwJEANGMCKIAZ9ZeSKKgiiKGQGVHCUICCJIlpxzho3vO8WbZTbPzPb09Myc8ttPdqa76tZ/a3ZOV926FZGUlJQEFREQAREQAREQAREQAb8RiGCR6PIbX1UsAiIgAiIgAiIgAoaARJcGggiIgAiIgAiIgAjYQECiywbIakIEREAEREAEREAEJLo0BkRABERABERABETABgISXTZAVhMiIAIiIAIiIAIiINGlMSACIiACIiACIiACNhCQ6LIBspoQAREQAREQAREQAYkujQEREAEREAEREAERsIGARJcNkNWECIiACIiACIiACEh0aQyIgAiIgAiIgAiIgA0EJLpsgKwmREAEREAEREAERECiS2NABERABERABERABGwgINFlA2Q1IQIiIAIiIAIiIAISXRoDIiACIiACIiACImADAYkuGyCrCREQAREQAREQARGQ6NIYEAEREAEREAEREAEbCEh02QBZTYiACIiACIiACIiARJfGgAiIgAiIgAiIgAjYQECiywbIakIEREAEREAEREAEJLo0BkRABERABERABETABgISXTZAVhMiIAIiIAIiIAIiINGlMSACIiACIiACIiACNhCQ6LIBspoQAREQAREQAREQAYkujQEREAEREAEREAERsIGARJcNkNWECIiACIiACIiACEh0aQyIgAiIgAiIgAiIgA0EJLpsgKwmREAEREAEREAERECiy6Fj4NChQ+jZsyeOHDliqYWtW7fGO++8Y2mdqkwEREAEREAERCBrAhJdWTMKyBXDhg3DkCFD/NJ2UlKSX+pVpSIgAiIgAiIgAhkTkOhy6Oh48cUXMXTo0Eyte+211zBo0KAU13To0AHTp0/P9D6JLoc6XWaJgAiIgAiENAGJLoe61xPRtW3bNlSsWDFFD8aNG4c777xTosuhfpVZIiACIiAC4UtAosuhvvdEdPlquma6fCWn+0RABERABETAdwISXb6z8+udEl1+xavKRUAEREAERMB2AhJdtiP3rEF30bVy5UrUr1/fsxszuIr3//XXX+ZdzXRlC6VuFgEREAEREAGfCEh0+YTN/zdJdPmfsVoQAREQAREQATsJSHTZSduLtgIhunr3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nMW6++67UalSJSOuGjRokIJ0oUKF0LlzZ7P8+O677/okupxyTJFDh5DMyoTA4lXbUb9mCeTJk0ecREAERMA2AhJdtqH2riGni67jx4+jbNmyZpbrhx9+QLFixbBp0yaMGDECL7/8Mlq1amWE2J133okpU6Zg6dKlJriepWXLliaxKme7mFpizZo1qFChQoaAsorp8o6srhYBERABERCBwBCQ6AoM9yxbdbLo4gzTfffdh7Fjx+LGG2806S1q166N3377DU888QSWLFmC/PnzY9WqVRgwYICZCRs+fDieffZZI8zq16+P06dPGwafffYZJkyYgBkzZtgiunjI8fCPZuG9Z7oiKkdkln7QBSIgAiIgAiJgFQGJLqtIWlyPk0UXhdOVV15pZqhKlUq5SzA1BsZ9zZo1CzfccIPJ4dWpUydMmjTJpJZgiY+PR61atTBy5Eh06NAhXYpWznT9vXEvbnt6Ih6/oxVuu76hxV5TdU4m8O+uwyhdvCDy5NK5mk72k2wTgVAmINHlUO86WXQxDobnL15++eUe01u8eDE6duyIvHnzmlQS7mXBggXo3r07Dh486HfRxQaGjZqJH2asweev9sJlNUp73AddGLwEYuMScfVdo1C4YB78/MEdiIzULGfwelOWi0DwEpDocqjvnCq6mJOLgfJ///231+S40/H+++/HLbekzQDOGC/GgfXt2zdNvVbOdLkq7/TA59iz/wjmjnsAhZWl3mtfBtsNPR77Apu2H8KCz/ujQL4LsYUqIiACImA3AYkuu4l72J4TRReD50uUKIHz58972Iu0l3Gm69ixY8iZM+1xPDzT8fDhwyhYsGCKG/0huthAsz4jkTdPTsz85D6f+6MbnU2ASVBfHDkDU+evw4TXb0HtKiWcbbCsEwERCGkCEl0Oda8TRVfp0qUxevRodOnie24jxnPdeuutOHnyZBryzzzzjFl6ZHA9hZar+Et0sX4egJ0754XzI1VCj8BDr0zCopXb8Fzfq3FDu7qh10H1SAREIKgISHQ51F1OE11vvvmmOeaHy4quQ659RcfcXVymvP3229NUwTxf//zzj5lRs0N0+doH3RccBLbvOYrte4+hVSOduRkcHpOVIhDaBCS6HOpfJ4kuLvnxqB+Koax2K3qKk8H4u3fvRtGiRVPcwpxe/fr1M8cKuYuut99+O8V1jz76KKxOjrpt92GM/3klHrmtBQrmzzxLvqf91HUiIAIiIAIi4PZ9FhGRZPW3l/hmm4CTRBfza73yyivZ7pM3FbgPSS4vphZdrGvgwIHeVJnltVPnrcOQkTMQHZ0Dt1/fEPf3uAI5tMstS25OuWDa7xsx6psl+Or1W5BbR0E5xS2yQwREwI2AZrocOhycJLoCjSi9mC5/2XT85Fm8OXYefp67HtFROdCiQQW8Pbirv5pTvdkkcPJMPD7+dhFmL92CXfuPo1LZIvj4xRtRvGj+bNas20VABETAegISXdYztaRGia6LGO0UXa5WDx87jbE/rcDspZsx9cO7U/j09z+3oXnDSpb4WZX4TmDXvuO4/sGxSExKQoNaZfDs/VejSvliiIy8uAnD99p1pwiIgAhYT0Ciy3qmltQo0RVY0eVqncuc7jspd+07husGjE3j4xvaXYIh/a9Jfv3vTXtx2+CJaa4beHsL3Nm1cfLry9fsxL1Dvk9z3TuDu6D15VWTX5+xaCOefPOXNNf99N7tqFT2YlzcZz8uw7tf/J7mulXfp1yKfWnUTHw/Y02K6yhW/vz2kRSv9R36PZau3pnitTy5o7H4ywEpXuv8wKfYvf9EiteKFMyDOZ+lzLtW/8Z30thWvGg+zPj4YtqO+PgENO71fprrCuXPjXnj+iW/Tt+s3bwf1SvGIFfOKEs+d6pEBERABPxJQKLLn3SzUbe76GL297p1s7fdnYdM89geFk9C+Cg0jh49mo0eWHdrkSJFPLLZuhZVkwiIgAiIgAhYT0Ciy3qmltToLrosqdCtEk9EFw+ldlLh4dkqIiACIiACIhDMBCS6HOq9QIsuh2KRWSIgAiIgAiIQtAQkuhzqOokuhzpGZomACIiACIiAjwQkunwE5+/bJLr8TVj1i4AIiIAIiIC9BCS67OXtcWsvv/wynnvuOY+v9/RCBsgnJiZ6ermuEwEREAEREAERsIiARJdFIK2uJjY2FkuWLLFcIBUrVizbOyGt7qvqEwEREAEREIFwICDRFQ5eVh9FQAREQAREQAQCTkCiK+AukAEiIAIiIAIiIALhQECiKxy8rD6KgAiIgAiIgAgEnIBEV8BdIANEQAREQAREQATCgYBEVzh4WX0UAREQAREQAREIOAGJroC7QAaIgAiIgAiIgAiEAwGJrnDwsvooAiIgAiIgAiIQcAISXQF3gQwQAREQAREQAREIBwISXeHgZfVRBERABERABEQg4AQkugLuAhkgAiIgAiIgAiIQDgQkusLBy+qjCIiACIiACIhAwAlIdAXcBTJABERABERABEQgHAhIdIWDl9VHERABERABERCBgBOQ6Aq4C2SACIiACIiACIhAOBCQ6AoHL6uPIiACIiACIiACAScg0RVwF8gAERABERABERCBcCAg0RUOXlYfRUAEREAEREAEAk5AoivgLpABIiACIiACIiAC4UBAoiscvKw+ioAIiIAIiIAIBJyARFfAXSADREAEREAEREAEwoGARFc4eFl9FAEREAEREAERCDgBia6Au0AGiIAIiIAIiIAIhAMBia5w8LL6KAIiIAIiIAIiEHACEl0Bd4EMEAEREAEREAERCAcCEl3h4GX1UQREQAREQAREIOAEJLoC7gIZIAIiIAIiIAIiEA4EJLrCwcvqowiIgAiXovM9AAACLUlEQVSIgAiIQMAJSHQF3AUyQAREQAREQAREIBwISHSFg5fVRxEQAREQAREQgYATkOgKuAtkgAiIgAiIgAiIQDgQkOgKBy+rjyIgAiIgAiIgAgEnINEVcBfIABEQAREQAREQgXAgINEVDl5WH0VABERABERABAJOQKIr4C6QASIgAiIgAiIgAuFAQKIrHLysPoqACIiACIiACAScgERXwF0gA0RABERABERABMKBgERXOHhZfRQBERABERABEQg4AYmugLtABoiACIiACIiACIQDAYmucPCy+igCIiACIiACIhBwAhJdAXeBDBABERABERABEQgHAhJd4eBl9VEEREAEREAERCDgBCS6Au4CGSACIiACIiACIhAOBCS6wsHL6qMIiIAIiIAIiEDACUh0BdwFMkAEREAEREAERCAcCEh0hYOX1UcREAEREAEREIGAE5DoCrgLZIAIiIAIiIAIiEA4EJDoCgcvq48iIAIiIAIiIAIBJyDRFXAXyAAREAEREAEREIFwICDRFQ5eVh9FQAREQAREQAQCTkCiK+AukAEiIAIiIAIiIALhQECiKxy8rD6KgAiIgAiIgAgEnIBEV8BdIANEQAREQAREQATCgYBEVzh4WX0UAREQAREQAREIOAGJroC7QAaIgAiIgAiIgAiEAwGJrnDwsvooAiIgAiIgAiIQcAISXQF3gQwQAREQAREQAREIBwJGdIVDR9VHERABERABERABEQg0gf8BkpFvJjQQCNcAAAAASUVORK5CYII=)

Beim Hinzufügen eines Bildes wird dieses, mit der ID, welche ein Auto repräsentiert, an den Server geschickt. Der Server prüft mit einer Datenbank Abfrage, ob die ID in der Datenbank enthalten ist. Ist diese ID enthalten, wird das Bild, nachdem es in Base64 umkodiert wurde, in der Datenbank gespeichert und mit der ID verknüpft. Die ID des Bildes wird anschließend in der http Repsonse mitgeschickt. Man kann mehrere Bilder einem Auto hinzufügen. Diese Bilder können alle gesammelt abgefragt werden. Hierzu muss man nur den entsprechenden Befehl mit der Auto Id aufrufen. Natürlich muss man zu Beginn die Auto Id anlegen. Es ist auch möglich, die Bilder einzeln anhand ihrer ID abzufragen. Das Backend wurde mithilfe von nodejs entwickelt. Das nodejs Programm wird auf dem Server aufgesetzt und die genutzten Packages installiert. Dies wären: 

- express: Starten der Server App
- bodyParser: Bearbeitung des http Body 
- http: Server Funktion
- multer: Für File upload 
- fs: Für Filesystem
- crypto-js: Für die Hashfunktion des Tokens

Die Server Applikation wird auf dem Port 3005 gestartet. 

## App<a id="chapter-0044"></a>

Die App besitzt die drei Hauptreiter “Entdecken/Durchsuchen”, “Digitale Schlüssel/akt. Mieten” und “Einstellungen”.

Im “Entdecken”-Reiter können die verfügbaren Fahrzeuge eingesehen und gefiltert werden. Standardmäßig ist die Liste nach Entfernung des Autos in aufsteigender Reihenfolge sortiert. Mit einem Klick auf das Auto können Detail-Informationen angezeigt werden und die gewünschte Mietdauer eingestellt werden. Mit einem Klick auf “Mieten” wird eine Miete initiiert - sofern genug Guthaben im Wallet verfügbar ist.

Das gemietete Fahrzeug wird dann im Reiter “Digitale Schlüssel / aktive Mieten” angezeigt. Hier kann eingesehen werden, wie weit das Auto entfernt ist und wie lange die Miete noch läuft. Ein Klick auf das Auto generiert den digitalen Autoschlüssel - einen QR-Code - der Informationen enthält, mit der das Auto (Raspberry Pi) den Mieter identifizieren kann und überprüfen, ob der Nutzer zum Zutritt berechtigt ist.

Im Einstellungen-Reiter sollen künftig das Profil und Wallet verwaltet werden können.

## RaspberryPi<a id="chapter-0045"></a>

Im Folgenden werden die beiden wesentlichen im Pflichtenheft geforderten und in Node.Js umgesetzten Funktionen (Regestrieren + QR-Lookup) aus der Datei "car.js" stichpunktartig beschrieben:
<br> https://github.com/Tachmeton/Carchain/blob/master/RaspberryPi/car.js <br>

### Allgemein<a id="chapter-00451"></a>
Für den Betrieb des Skripts müssen im Voraus über den Node package manager folgende Pakete installiert werden (werden aber eigentlich bereits automatisiert in der Bereitstellungs-Pipeline installiert):
  * fs@0.0.1-security
  * jimp@0.5.4
  * newman@5.0.0
  * onoff@5.0.1
  * pi-camera@1.3.0
  * qrcode-reader@1.0.4
  * solc@0.4.25
  * web3@1.2.6

Das OnOff-Modul für das Steuern der GPIO-Pins verwendet. Es wird ein Event ausgelöst sobald einer der beiden Buttons gedrückt wird, wenn dies der Fall ist wird die entsprechende dahinterliegende Funktion ausgelöst. Mit Blockchain wird über die eingebundene Web3-Schnittstelle kommuniziert. Zudem läuft eine dauerhaft im Intervall von 15 Sekunden ausgeführte Schleife im Hintergrund, diese überprüft an der Blockchain über die eingebundene “isLegalLeaser” Smart-Contract Funktion, ob der zuletzt hinterlegte Mieter eines Autos weiterhin der Mieter ist. Dies hat den Sinn, dass ein ausgeliehenes Auto nicht unbegrenzt genutzt werden kann und sich spätestens 15 Sekunden nach dem ausgelaufenen Mietvertrag wieder sperrt.

### Regestrieren<a id="chapter-00452"></a>

Anmerkung der Inhalt der Funktion wurde ausgelagert, da diese primär innerhalb der automatisierten Bereitstellungspipeline ausgeführt werden sollte: <br> https://github.com/Tachmeton/Carchain/blob/master/RaspberryPi/ansible_rapi/data/register_car.js <br>
* Bei Knopfdruck: Registrieren (auch realisiert in Bereitstellungs-Pipeline, aber gleiche ausgeführte Funktion)
* Nutzen der Smart-Contract Funktion: “addCar”
* Picture Upload über HTTP-Put mit newman
* register-collection.json für Beschreibung der HTTP-Anfrage

### QR-Lookup<a id="chapter-00453"></a>
* Bei Knopfdruck: QR-Lookup → Gelbe “In Bearbeitung”-LED
* Aufnahme und speichern eines Fotos mit Pi-Camera-Modul
* Suchen nach QR-Code mit Qrcode-Reader-Modul
* Falls Wallet-Adresse gefunden: Abfragen ob existent an Blockchain (Web3)
* Nutzen der Smart-Contract Funktion: “isLegalLeaser” um zu überprüfen, ob der Nutzer der übergebenen Wallet-Adresse berechtigt ist auf das Auto zuzugreifen
  * True: Grüne LED = Offen (+Gelb aus)
  * False: Rote LED = Geschlossen (+Gelb aus)

# Offene Punkte<a id="chapter-005"></a>
* Web3 Implementierung in der App
* Unit Tests für Smart Contract schreiben
* Smart Contract auf Public Blockchain einsetzen

# Bisherige Verantwortliche<a id="chapter-006"></a>
Blockchain - Tachmeton (Bastian Frewert)<br>
App - Simon Gaugler, Jan Quintus<br>
Datenbank - lfs1991 (Lukas Faiß)<br>
Raspberry Pi - nilsriekers, (Nils Riekers)<br>
