let fs = require('fs')
let Web3 = require('web3')

let web3 = new Web3();
web3.setProvider(
    new web3.providers.HttpProvider('http://193.196.54.51:8545')
    );

let contractAdress = '0xdb268b971C46d61bd512071D57706dEADe11369B';
let fromAddress = '0x28f814Ff05aF5DbFF0401A6c98AB0942DbA95a63';
let renterAddress = '0x3d48704143135059A1990dcDF9eEC5C73f750179';
let carWallet = '0xfCB0306AadaFF0CD11A9576180bbd6a7787ca509';

var schtring = __dirname.replace('RaspberryPi', 'Blockchain/ethereum/build/contracts/Carchain.json');
let carchainStr = fs.readFileSync(schtring, 'utf-8');
let carchainAbi = JSON.parse(carchainStr);


let carchain = new web3.eth.Contract(carchainAbi.abi, contractAdress);

sendTransactions()
    .then(function() {
        console.log("Done");
    })
    .catch(function(error) {
        console.log(error);
    })

async function sendTransactions() {
    console.log("Return Car rented")
    await carchain.methods.returnCarToCarpool(carWallet).send({from: renterAddress, gas: 60000});

    console.log("Reset");
    await carchain.methods.resetCar(carWallet).send({from: renterAddress, gas: 600000});

    console.log("Remove Car");
    await carchain.methods.removeCar(carWallet).send({from: renterAddress, gas: 600000});
}
