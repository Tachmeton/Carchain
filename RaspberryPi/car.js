const PiCamera = require('pi-camera');
const QRReader = require('qrcode-reader');
const Web3 = require('web3');
const fs = require('fs');
const jimp = require('jimp');

// Einstellungen für die Kamera
const myCamera = new PiCamera({
  mode: 'photo',
  output: `${ __dirname }/qr_picture.jpg`,
  width: 1920,
  height: 1080,
  nopreview: true,
});

var Gpio = require('onoff').Gpio;
// Button-Pins
var registerButton = new Gpio(17, 'in', 'rising', {debounceTimeout: 10});
var qrButton = new Gpio(18, 'in', 'rising', {debounceTimeout: 10});
// LED-Pins
var redLED = new Gpio(19, 'out');
var yellowLED = new Gpio(20, 'out');
var greenLED = new Gpio(21, 'out');

// Per Default rote LED an, andere aus
redLED.writeSync(1);
yellowLED.writeSync(0);
greenLED.writeSync(0);

let web3 = new Web3();
web3.setProvider(
  new web3.providers.HttpProvider('http://193.196.54.51:8545')
);

let contractAdress = '0xdb268b971C46d61bd512071D57706dEADe11369B';
let fromAddress = '0x28f814Ff05aF5DbFF0401A6c98AB0942DbA95a63';
let renterAddress = undefined;
let carWallet = '0xfCB0306AadaFF0CD11A9576180bbd6a7787ca509';

var schtring = __dirname.replace('RaspberryPi', 'Blockchain/ethereum/build/contracts/Carchain.json');
let carchainStr = fs.readFileSync(schtring, 'utf-8');
let carchainAbi = JSON.parse(carchainStr);

let carchain = new web3.eth.Contract(carchainAbi.abi, contractAdress);

qrButton.watch(function (err, value) {
  if (err) {
    console.error('There was an error', err);
  return;
  }
  qrLookup();
});

registerButton.watch(function (err, value) {
  if (err) {
    console.error('There was an error', err);
  return;
  }
  registerCar();
});

function qrLookup() {
  console.log("qrLookup");
  //redLED.writeSync(1);
  yellowLED.writeSync(1);
  //greenLED.writeSync(0);
  myCamera.snap()
    .then((result) => {
      // Sobald ein Bild aufgenommen wurde
      run().catch(error => console.error(error.stack));
      
      // QR-Code auf dem aufgenommenen Bild erkennen
      async function run() {
        const img = await jimp.read(fs.readFileSync('./qr_picture.jpg'));
        const qr = new QRReader();

        const value = await new Promise((resolve, reject) => {
          qr.callback = (err, v) => err != null ? reject(err) : resolve(v);
          qr.decode(img.bitmap);
        });

        qrResult = value.result;
        console.log(value);

	console.log(qrResult);
	// Wenn ein QR-Code gefunden wurde
        if (qrResult !== undefined) {
          console.log("qr code found");

          // Abfragen der vorhandenen Wallet-Adressen
          chainAddresses = await web3.eth.getAccounts();
          console.log(chainAddresses);
          
	  // Wenn übergebene Adresse in der Blockchain vorhanden ist
          if (chainAddresses.includes(qrResult)){
            console.log("qr code is valid");
            renterAddress = qrResult
            checkLeaser()
              .then(function() {
                  console.log("qr code check done");
              })
              .catch(function(error) {
                  console.log(error);
              })
          }
          else{
            console.log("qr code is not valid");  
          }
        }
        else{
          yellowLED.writeSync(0);
        }
        //redLED.writeSync(1);
	yellowLED.writeSync(0);
        //greenLED.writeSync(0);
      }
   }).then(function() {
      yellowLED.writeSync(0);
      })
  .catch((error) => {
    redLED.writeSync(1);
    yellowLED.writeSync(0);
    greenLED.writeSync(0);
  });
}

function registerCar() {
  console.log("registerCar");
  // Regestrieren des Autos an der Blockchain und Bilderupload wird eigentlich innerhalb der Automatisierungs-Pipeline durchgeführt, sollte daher nur zu Entwicklungszwecken durchgeführt werden
  require('./ansible_rapi/data/register_car.js');
}

setInterval(function() {
  checkLeaser()
    .then(function() {
        console.log("interval check done");
    })
    .catch(function(error) {
        console.log(error);
    })
}, 15000);

async function checkLeaser() {
  console.log("check if renter is Legal Leaser");
  let allowed;
  try {
   allowed = await carchain.methods.isLegalLeaser(carWallet, renterAddress).call({from: carWallet});
  }
  catch (e) {
    allowed = false;
  }
  if (allowed){
    redLED.writeSync(0);
    greenLED.writeSync(1);
  }
  else
  {
    renterAddress = undefined;
    redLED.writeSync(1);
    greenLED.writeSync(0);
  }
  console.log(`allowed: ${allowed}`);
  return allowed
}

//Funktion wenn das Programm beendet wird: LEDs ausschalten und Unexport
function unexportOnClose() { 
  redLED.writeSync(0);
  yellowLED.writeSync(0);
  greenLED.writeSync(0);
  redLED.unexport(); 
  yellowLED.unexport();
  greenLED.unexport();
  registerButton.unexport(); 
  qrButton.unexport();
};
process.on('SIGINT', unexportOnClose);
