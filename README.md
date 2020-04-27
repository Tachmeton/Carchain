# Verteilte Systeme - Projekt “Carchain”

# Table of Contents

1. [Aufgabenstellung und Beschreibung](#chapter-001)
2. [Architektur](#chapter-002)
3. [Setup](#chapter-003)<br>
  3.1 [Blockchain](#chapter-0031)<br>
  3.2 [Smart Contract](#chapter-0032)<br>
  3.3 [Datenbank](#chapter-0033)<br>
  3.4 [App](#chapter-0034)<br>
  3.5 [Raspberry Pi](#chapter-0035)
4. [Beschreibung der Funktionalität](#chapter-004)<br>
  4.1 [Blockchain](#chapter-0041)<br>
  4.2 [Smart Contract](#chapter-0042)<br>
    4.2.1 [Speicherung der Daten](#chapter-00421)<br>
    4.2.2 [Modifier](#chapter-00422)<br>
    4.2.3 [Funktionen des Smart Contract](#chapter-00423)<br>
  4.3 [Datenbank](#chapter-0043)<br>
  4.4 [App](#chapter-0044)<br>
  4.5 [Raspberry Pi](#chapter-0045)<br>
    4.5.1 [Allgemein](#chapter-00451)<br>
    4.5.2 [Regestrieren](#chapter-00452)<br>
    4.5.3 [QR-Lookup](#chapter-00453)
5. [Offene Punkte](#chapter-005)
6. [Bisherige Verantwortliche](#chapter-006)

# Aufgabenstellung, Beschreibung<a id="chapter-001"></a>

Mit “ChainCar” soll eine dezentrale Plattform zum Mieten & Vermieten von Automobilen entstehen. Ziel ist die dezentrale Abwicklung der Mietvorgänge sowie der Zugriffskontrolle auf die Fahrzeuge.

Ein Fahrzeugbesitzer kann sich über das Web-Frontend der Plattform registrieren und sein Fahrzeug zur Miete registrieren. Dazu gibt er die erforderlichen Daten an. Über einen SmartContract wird das Fahrzeug in der Blockchain persistiert und zur Miete verfügbar gemacht. Kunden können sich nun ebenfalls über die Website registrieren (mit ihrer Wallet-Adresse) und verfügbare Fahrzeuge (in einer Liste, ggf. sortiert nach Nähe) einsehen.

Möchte der Kunde ein verfügbares Fahrzeug mieten, wird eine Transaktion ausgelöst. Ein SmartContract schreibt den Beginn der Transaktion sowie die Mietkonditionen (Preis, Leistungen, …) in die Blockchain, erhebt eine erstes Transaktionsentgelt und registriert den Kunden als legitimen Nutzer des Fahrzeuges. Der Kunde kann das Fahrzeug nun entsperren (siehe IoT-Team) und fahren. Soll der Mietvorgang beendet werden, wird wieder der SmartContract kontaktiert, welcher die Kosten der Fahrt berechnet, einzieht, dem Vermieter überweist und abschließend die Nutzungsrechte am Fahrzeug wieder entzieht.

Zu klären ist, ob der Eigentümer Zugriff auf sein Fahrzeug haben soll/kann, während ein Kunde dieses gemietet hat oder nicht.

# Architektur<a id="chapter-002"></a>

![Komponentendiagramm_01](Doku/assets/komponenten.png)

# Setup<a id="chapter-003"></a>
Im Folgenden wird das Setup für die Carchain beschrieben. Um ein funktioniertendes Setup zu erstellen müssen alle Komponenten provisioniert werden.<br><br>
Empfohlene Reihenfolge beim bereitstellen:
1. Blockchain
2. Smart Contract
3. Datenbank
4. Raspberry Pi
5. App installieren

## Blockchain<a id="chapter-0031"></a>
Um am Smart Contract zu entwicklen wird eine Blockchain gebraucht um den Smart Contrac auch deployen zu können.
Biser wird dazu Ganache genutzt. Es ist egal ob dabei die Desktop Variante oder Kommandozeielnvariante (Ganache-cli) genutzt wird. <br>
Um das gleiche Setup zu bekommen wie bisher genutzt wurde und damit richtige Testeinstellungen zu bkeommen muss Ganache installiert werden.<br><br>
Zur Installation von Ganache Desktop: https://www.trufflesuite.com/ganache <br>
Zur Installation von Ganache-cli: https://github.com/trufflesuite/ganache-cli <br>
<br>
Wie man die Einstellungen ändert, unterscheidet sich zwischen der Cli und der Desktop Variante. Im Folgenden wird sich auf die CLI Variante beschränkt, da diese am besten einzusetzten sit wenn man nicht nur an der Blockchain entwicklen will sondern auch die Verbindung zu der App oder dem RaspberryPie testen möchte. <br>
Einstellungen zum Start der Blockchain:<br>
ganache-cli -m "dragon canoe knife need marine business arctic honey make layer company solar" -h "<IP-Adresse>" -p <Port> -e 10000 &<br>
<br>
Die Message die hier Ganache mitgegeben wird bestimmt welche Adressen die Wallets, die schon vornherien genereiert und zur Verfügung gestellt werden, haben. Voreingestllt ist für die Entwicklung 10.000 Ether, die jede Wallet von vornherein bekommt. Sobald der Command ausgeführt wurde gibt es nun eine Blockchian, die für die weitere Entwicklung genutzt werden kann.

## Smart Contract<a id="chapter-0032"></a>
Um mit den Smart Contracts arbeiten zu können sind einige Installationen nötig.
Diese werden mit npm (dem Node Package Manager) installiert. Zur Installation:<br><br>
https://www.npmjs.com/get-npm <br><br>
Dazu einfach nach Downlaod des Git Repositpries im Blockchain Ordner folgenden Befehel ausführen:<br>
npm install<br>
<br>
Das Kompilieren, Testen und das Deployen des Smart Contracts geschieht nun mit Truffle. Zu allererst muss dazu im Ordner /Blockchain/ethereum/ die Datei truffle-config.js angepasst werden. Dazu kann ein Server erstellt werden. Ein Beispiel hierfür ist hier zu sehen:<br>
![Einstellungen zum Deployen](Doku/assets/Einstellungen_Blockchian_Deploy.PNG)<br>
Neben Host und Port gibt es gas. Gas dient dazu zu bestimmen wieviel maximal an Gas ausgegeben werden darf um den Contract zu deployen. Network_id ist auch speziell für die Blockchain, falls es unterschiedlcihe Blockchians auf der gleichen IP gibt. Nachdem die Einstellungen getätigt sind kann nun der Contract compiled und deployed werden. Dazu dient das Kommando:<br>
truffle migrate --network <Network-Name> <br>
Der Network-Name ist dabei der Name, der in der truffle-config.js in den networks eingetragen wurde. Weitere Dokumentation zu dem Kommando truffle migrate ist unter<br><br>
https://www.trufflesuite.com/docs/truffle/getting-started/running-migrations<br><br>
zu finden.

## Datenbank<a id="chapter-0033"></a>

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
  * Installieren von NodeJS, NPM und dem Node-Exporter über APT + enable Node-Repository 
  * Kopieren des privaten SSH-Schlüssels für Zugriff auf Git
  * Konfiguration von OpenSSH (durch ssh_config.j2) & ssh-keyscan git.smagcloud.de
  * Klonen des Repos (git@git.smagcloud.de:DHBW17B/carchain.git)
  * Installieren der benötigten NPM-Pakete (aus npm-requirements.txt)
  * Kopieren des angelegten Unit-Files (durch car_js.service) für automatisches Starten
  * Konfiguration von Systemd (car_js.service & node-exporter starten + enablen)
  * Ausführen von register_car.js (Registrieren des PIs an der BC + Bild-Upload an DB)
Die von den Tasks verwendeten Hilfs-Dateien sind dabei in einem extra angelegten "Data"-Ordner im Repository vorhanden:<br>
https://github.com/Tachmeton/Carchain/tree/master/RaspberryPi/ansible_rapi/data<br>

Das Playbook kann durch den Befehl "ansible-playbook rapi_playbook.yaml" ausgeführt werden. Ansible provisioniert anschließend den Raspberry Pi automatisiert. Das Ergebnis ist, dass das Entwicklungs-Repository auf den Raspberry Pi gekloned wurde, alle benötigten Abhängigkeiten installiert und das Node-JS Skript, dass die Funktionen "Regestrieren" und "QR-Lookup" bereitstellt, als Systemd-Dienst gestartet wurde. Ebenfalls wird der für das Monitoring benötigte sogenannte "Prometheus-Node-Exporter" installiert und gestartet.

**Monitoring**<p>

Das optional umgesetzte Monitoring des RaPis mit Prometheus und Grafana wurde erstellt, da es sinnvoll ist ein Monitoring der Autos zu haben, wenn die Anwendung eines Tages produktiv zum Einatz kommen sollte, um Festzzustellen das alles korrekt funktioniert. Hierfür wurde um nicht immer mit einer abstrakten IP zu arbeiten, eine kostenlose ".tk" Domain für unseren Entwicklungs-/Blockchainserver eingerichtet. Angelegt wurde ein DNS-Eintrag der Domain "carchain-server.tk", die auf den Server mit der IP "193.196.54.51" verweist. Die entsprechenden Stellen im Quellcode sind entsprechend bei einer geänderten Umsetzung zu ersetzen.

Prometheus und Grafana selbst betreiben wir über Docker, das wir entsprechend auf dem Server installiert haben. Als Grundlage haben wir die offiziellen Standard-Images von Prometheus und Grafana verwendet. Da die Container an sich zustandslos sind und nach dem Stoppen unverändert vorliegen, haben wir für einen persistenten Speicher 2 Volumes angelegt, einen für Prometheus und einen für Grafana, die entsprechend in die Container gemountet werden. Dies hat denn Sinn dass nach einem Neustart des Servers unsere vorherigen gespeicherten Monitoring-Daten weiterhin verfügbar sind. Ein eingerichteter Cronjob sorgt dafür nach einem Neustart des Servers die beiden Container automatisch wieder gestartet werden.

Prometheus bestitzt eine Time Series Database, in die per HTTP gesammelte Metriken geschrieben werden. Die Konfiguration geschieht über eine YAML-Datei, die mit in den Container gemountet wird: <br>https://github.com/Tachmeton/Carchain/blob/master/RaspberryPi/monitoring_rapi/prometheus_config_carchain.yaml<br> In der Datei haben wir ein Scrape-Intervall von 15 Sekunden festgelegt, d.h. alle 15 Sekunden führt Prometheus eine HTTP-Anfrage zu dem Raspberry Pi aus, in unserem Fall über die Domain carchain-pi.dnsuser.de.

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
      <td>RaspberryPie</td>
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

## Datenbank<a id="chapter-0043"></a>

## App<a id="chapter-0044"></a>

Die App besitzt die drei Hauptreiter “Entdecken/Durchsuchen”, “Digitale Schlüssel/akt. Mieten” und “Einstellungen”.

Im “Entdecken”-Reiter können die verfügbaren Fahrzeuge eingesehen und gefiltert werden. Standardmäßig ist die Liste nach Entfernung des Autos in aufsteigender Reihenfolge sortiert. Mit einem Klick auf das Auto können Detail-Informationen angezeigt werden und die gewünschte Mietdauer eingestellt werden. Mit einem Klick auf “Mieten” wird eine Miete initiiert - sofern genug Guthaben im Wallet verfügbar ist.

Das gemietete Fahrzeug wird dann im Reiter “Digitale Schlüssel / aktive Mieten” angezeigt. Hier kann eingesehen werden, wie weit das Auto entfernt ist und wie lange die Miete noch läuft. Ein Klick auf das Auto generiert den digitalen Autoschlüssel - einen QR-Code - der Informationen enthält, mit der das Auto (RaspberryPi) den Mieter identifizieren kann und überprüfen, ob der Nutzer zum Zutritt berechtigt ist.

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
