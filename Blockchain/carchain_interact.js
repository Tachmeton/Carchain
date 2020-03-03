let fs = require('fs')
let Web3 = require('web3')

let web3 = new Web3();
web3.setProvider(
    new web3.providers.HttpProvider('http://localhost:7545')
    );

let contractAdress = '0xdb268b971C46d61bd512071D57706dEADe11369B';
let fromAddress = '0x28f814Ff05aF5DbFF0401A6c98AB0942DbA95a63';

let abiStr = fs.readFileSync('abi.json', 'utf-8');
let abi = JSON.parse(abiStr);

let carchain = new web3.eth.Contract(abi, contractAdress);

sendTransactions()
    .then(function() {
        console.log("Done");
    })
    .catch(function(error) {
        console.log(error)
    })

async function sendTransactions() {
    console.log("add new car");
    //addCar(uint256 identifierCar, uint256 identifierOwner)
    await carchain.methods.addCar(1, 0x3d48704143135059A1990dcDF9eEC5C73f750179, 1).send({from: 0x3d48704143135059A1990dcDF9eEC5C73f750179, gas: 6000000});

    console.log("rent car number 1");
    //rentCar(uint256 identifierCar, uint256 identifierLeaser)
    await carchain.methods.rentCar(1).send({from: 0x73f6fCfE3fa7046eEBd19660795beF3a0bDE6827, gas: 6000000, value: 3600});

    console.log("get owner");
    //getOwner(uint256 identifierCar)
    let owner = await carchain.methods.getOwner(1).call({from: fromAddress});
    console.log(`Owner: ${owner}`);

    console.log("get Leaser");
    //getOwner(uint256 identifierCar)
    let leaser = await carchain.methods.getLeaser(1).call({from: fromAddress});
    console.log(`Leaser: ${leaser}`);

    console.log("Return Car number 1")
    //returnCarToCarpool(uint256 identifierCar)
    await carchain.methods.returnCarToCarpool(1).send({from: fromAddress, gas: 60000});
}