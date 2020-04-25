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
Um das gleiche Setup zu bekommen wie bisher genutzt wurde und damit richtige Testeinstellungen zu bkeommen muss Ganache installiert werden.<br>
Zur Installation von Ganache Desktop: https://www.trufflesuite.com/ganache <br>
Zur Installation von Ganache-cli: https://github.com/trufflesuite/ganache-cli <br>
<br>
Wie man die Einstellungen ändert, unterscheidet sich zwischen der Cli und der Desktop Variante. Im Folgenden wird sich auf die CLI Variante beschränkt, da diese am besten einzusetzten sit wenn man nicht nur an der Blockchain entwicklen will sondern auch die Verbindung zu der App oder dem RaspberryPie testen möchte. <br>
Einstellungen zum Start der Blockchain:<br>
ganache-cli -m "dragon canoe knife need marine business arctic honey make layer company solar" -h "<IP-Adresse>" -p <Port> -e 10000 &<br>
<br>
Die Message die hier Ganache mitgegeben wird bestimmt welche Adressen die Wallets, die schon vornherien genereiert und zur Verfügung gestellt werden, haben. Voreingestllt ist für die Entwicklung 10.000 Ether, die jede Wallet von vornherein bekommt. Sobald der Command ausgeführt wurde gibt es nun eine Blockchian, die für die weitere Entwicklung genutzt werden kann.

## Smart Contract

## Datenbank

## App

## Raspberry Pie

# Beschreibung der Funktionalität

## Blockchain

## Datenbank

## App

## Raspberry Pie

# Erweiterungen

# Probleme

# ToDos
* Smart Contract auf Public Blockchain einsetzen
* Web3J in der App implementierung

