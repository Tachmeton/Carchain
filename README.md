# Verteilte Systeme - Projekt “Carchain”

[TOC]

## Aufgabenstellung, Beschreibung

Mit “ChainCar” soll eine dezentrale Plattform zum Mieten & Vermieten von Automobilen entstehen. Ziel ist die dezentrale Abwicklung der Mietvorgänge sowie der Zugriffskontrolle auf die Fahrzeuge.

Ein Fahrzeugbesitzer kann sich über das Web-Frontend der Plattform registrieren und sein Fahrzeug zur Miete registrieren. Dazu gibt er die erforderlichen Daten an. Über einen SmartContract wird das Fahrzeug in der Blockchain persistiert und zur Miete verfügbar gemacht. Kunden können sich nun ebenfalls über die Website registrieren (mit ihrer Wallet-Adresse) und verfügbare Fahrzeuge (in einer Liste, ggf. sortiert nach Nähe) einsehen.

Möchte der Kunde ein verfügbares Fahrzeug mieten, wird eine Transaktion ausgelöst. Ein SmartContract schreibt den Beginn der Transaktion sowie die Mietkonditionen (Preis, Leistungen, …) in die Blockchain, erhebt eine erstes Transaktionsentgelt und registriert den Kunden als legitimen Nutzer des Fahrzeuges. Der Kunde kann das Fahrzeug nun entsperren (siehe IoT-Team) und fahren. Soll der Mietvorgang beendet werden, wird wieder der SmartContract kontaktiert, welcher die Kosten der Fahrt berechnet, einzieht, dem Vermieter überweist und abschließend die Nutzungsrechte am Fahrzeug wieder entzieht.

Zu klären ist, ob der Eigentümer Zugriff auf sein Fahrzeug haben soll/kann, während ein Kunde dieses gemietet hat oder nicht.

# Architektur

## Komponenten

![Komponentendiagramm_01](Doku/assets/komponenten.png)

# Setup
Im Folgenden wird das Setup für die Carchain beschrieben. Um ein funktioniertendes Setup zu erstellen müssen alle Komponenten installiert werden.<br><br>
Empfohlene Reihenfolge beim Installieren:
1. Blockchain
2. Smart Contract
3. Datenbank
4. Raspberry Pie
5. App installieren

## Blockchain
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

## Smart Contract
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
 
## Datenbank

## App

## Raspberry Pie

# Beschreibung der Funktionalität

## Blockchain
Die Blockchain an sich dient als Hub für die Smart Contracts. Auf geschieht alles, was mit der Verwaltung der Mietdaten. Des Weiteren läuft über die Blockchain die Bezahlung um Autos zu mieten. Deswegen ist es im späteren Sinnvoll die Contracts auf eine Public Blockchain zu deployen, damit die Bezahlung auch wirklich einen Impakt hat. Aktuell ist der Smart Contract der weiter unten beschrieben wird nur auf der Development Blockchain Ganache getestet.

## Smart Contract
Der Smart Contract ist im Ordner Blockchain/ethereum in der Datei carchain.sol implementiert. Als Smart Contract Programmiersprahce wurde Solidity benutzt.

### Speichern der Auto Daten
Die wichtigste Aufgabe, die der Smart Contract übernimmt, ist das speichern, der Daten über die zu vermietenden Autos. Diese sind in einer Map gespeichert, die bei Erstellung des Contracts aufgebaut wird. Die Map bildet die Wallet Adressen der Autos auf eine Car-Struktur ab.<br>
In selbiger Car Struktur sind gespeichert:
* Daten über den Owner
* Daten über den aktuellen Mietstatus
* die Zeit wie lange das Auto aktuell gemietet ist
* der insgesammt bekommende Ether
* der normale Standort des Autos
* Daten über das Auto an sich
* Konditionen zum Mieten des Autos <br>

### Modifier
Solidity bietet die Möglichkeit Modifier, mithilfe von requires zu defineiren. Modifier dienen in diesem Projekt dazu, vor dem richtigen Start einer Funktion zu checken ob der Aufrufer dazu berechtigt ist selbige aufzurufen und ob alle Vorraussetzungen für einen kompletten Durchlauf der Funktion gegeben ist. Bisher implementierte Modifier:<br>
* knownCar - Existiert das Auto welches die Funktion betrifft
* onlyOwner - Ist die übergebene Wallet Adresse Owner des übergebenen Autos
* isLeased - Befindet sich das Auto schon im gemieteten Zustand
* isLeasedBy - Ist als Leaser die übergebene Wallet Adresse eingetragen
* carFree - Ist das Auto aktuell frei <br><br>
Bei allen Methoden, wo ein Auto mit beeinflusst ist, sollte die Funktion knownCar vorher aufgerufen werden um während der Ausführung der Fnktion nicht auf Fehler zu stoßen. Auch die Restriktion auf Adressen die nur bestimmt Funktionen ausführen dürfen sit wichtig. Hierfür sollte der Modifier onlyOwner verwendet werden. Man köannte noch andenken, dass ein Auto mehrere Owner hat. Dies ist aktuell aebr nciht implementiert. Die Checks ob ein Auto schon geleased ist oder von wem geleased wurde oder es frei ist, werden natürlich dann eingesetzt wenn es um den akuten Mietvorgang geht.
### Funktionen des Smart Contract
Über das Speichern der Autodaten hinaus bietet der Smart Contract die Möglichkeit die Daten zu verändern. Dazu dienen verschiedene Arten von Funktionen. Im Folgenden werden die Funktionen nach ihrer möglcihen Nutzung in den anderen Teilen des Projektes aufgegliedert.<br><br>
Für die App gibt es Funktionen, die hauptsächlich dazu dienen die aktuell freien Autos auszugeben und darauf folgend weitere Daten über sie zu entnehmen.<br>
Dafür implentierte Funktionen sind:<br>
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
      <td>Identifier des Autos als Adresse</td>
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
## Datenbank

## App

## Raspberry Pie

# Erweiterungen

# Probleme

# ToDos
* Smart Contract auf Public Blockchain einsetzen
* Web3J in der App implementierung

