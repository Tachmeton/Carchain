var register = require('./register_car.js')
let fs = require('fs')
let Web3 = require('web3')
let newman = require('newman');

let web3 = new Web3();
web3.setProvider(
    new web3.providers.HttpProvider('http://193.196.54.51:8545')
    );

let contractAdress = '0xdb268b971C46d61bd512071D57706dEADe11369B';
let fromAddress = '0x28f814Ff05aF5DbFF0401A6c98AB0942DbA95a63';
let renterAddress = '0x3d48704143135059A1990dcDF9eEC5C73f750179';
let carWallet = '0xfCB0306AadaFF0CD11A9576180bbd6a7787ca509';

var schtring = __dirname.replace('RaspberryPi/ansible_rapi/data', '/Blockchain/ethereum/build/contracts/Carchain.json');
console.log(schtring);
let carchainStr = fs.readFileSync(schtring, 'utf-8');
let carchainAbi = JSON.parse(carchainStr);

let carchain = new web3.eth.Contract(carchainAbi.abi, contractAdress);

exports.registerCar = function() {
    // register car@blockchain
    sendTransactions()
        .then(function() {
            console.log("car registered @ blockchain");
        })
        .catch(function(error) {
            console.log(error);
        })

    async function sendTransactions() {
        console.log("add new car");
        await carchain.methods.addCar(carWallet, "RT-VS-2020", "VW Golf 7 GTI", "Kombi", "VW", "schwarz", 245, 17, 480, 60).send({from: fromAddress, gas: 6000000});
    }

    // upload picture2backend
    newman.run({
        collection: require('./register-collection.json'),
        reporters: 'cli'
    }, function (err) {
        if (err) { 
    	    throw err; }
        else {
   	    console.log("picture uploaded to database") 
        }
        console.log('http put request completed!');
    });
}

register.registerCar();
