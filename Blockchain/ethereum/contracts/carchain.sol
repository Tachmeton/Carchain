pragma solidity >=0.4.21 <0.7.0;


contract carchain {

uint256 constant private maxArrayLength = 2**256-1;

enum CarState {
    Free,
    Leased,
    Blocked
}

  //every Car has to have an owner!
  struct Car{
    address owner;
    CarState currentState;
    address leaser;
    uint256 timeRented;
    uint256 amountEarned;
    int256 longitude;
    int256 latitude;
    string nummernschild;
    string modell;
    string typ;
    string hersteller;
    string farbe;
    uint256 ps;
    uint256 mietpreis;  //pro Minute
    uint256 maxMietdauer;
    uint256 minMietdauer;
  }

  mapping (address => Car) carpool;
  address[1000] allCars;
  uint256 private realLengthAll;


  constructor() public {
    realLengthAll = 0;
  }

//--------------------------------------------------------------------------------------------------------------------

  /*
  Adds a new Car to the Carpool.
  */
  function addCar(address identifierCar,
    string memory nummernschild,string memory modell, string memory typ, string memory hersteller, string memory farbe,
    uint256 ps, uint256 mietpreis, uint256 maxMietdauer, uint256 minMietdauer)
    public {
      require(
        carpool[identifierCar].owner == address(0), "ID already used."
        );

      Car memory newCar = Car({
        owner: msg.sender, currentState: CarState.Free, leaser: address(0),timeRented: 0, amountEarned: 0,
        longitude: 0, latitude: 0,nummernschild: nummernschild, modell: modell, typ: typ, hersteller: hersteller, farbe: farbe,
        ps: ps, mietpreis: mietpreis, maxMietdauer: maxMietdauer, minMietdauer: minMietdauer
        });

      allCars[realLengthAll] = identifierCar;
      realLengthAll++;
      carpool[identifierCar] = newCar;
  }

  /*
  Removes a car which is not currently in Use from the total Carpool.
  */
  function removeCar(address identifierCar) public
            knownCar(identifierCar) onlyOwner(identifierCar, msg.sender) carFree(identifierCar) {
    uint256 index = 0;
    index = getPostionInAllCars(identifierCar);
    if(allCars[index] == identifierCar){
      deleteCarFromAllCars(index);
      realLengthAll--;
    }
    delete carpool[identifierCar];
  }

//--------------------------------------------------------------------------------------------------------------------

  /*
  Marks a car as rented and safes the point from where the car was rented.
  */
  function rentCar(address payable identifierCar) public knownCar(identifierCar) carFree(identifierCar) payable {
    //Mindestmietdauer?
    require(msg.value >= 2300, "You did not pay enough. (Minimum: 2300)");
    require(msg.value >= carpool[identifierCar].mietpreis * carpool[identifierCar].minMietdauer + 2300,
     "You did not pay more than min Duration of Rent * rental price + 2300");

    carpool[identifierCar].amountEarned = msg.value;
    address payable beneficiary = identifierCar;
    beneficiary.transfer(msg.value - 2300);
    carpool[identifierCar].currentState = CarState.Leased;
    carpool[identifierCar].leaser = msg.sender;
    //Problem: block.timestamp ist worst case 90 sekunden ungenau wegen manipulation von minern.
    carpool[identifierCar].timeRented = getTimeNow() + (msg.value / carpool[identifierCar].mietpreis);
  }

  /*
  Gives a boolean value if a car is now avaible for rent.
  */
  function mayRent(address identifierCar) public knownCar(identifierCar) view returns (bool) {
    if(carpool[identifierCar].leaser == address(0) && getCurrentState(identifierCar) == CarState.Free){
      return true;
    }else{
      return false;
    }
  }

  /*
  Gives a boolean value if a Leaser is still Lawfully/ Legally a Leaser and has enough credit.
  */
  function isLegalLeaser(address identifierCar, address identifierLeaser) public
            knownCar(identifierCar) isLeased(identifierCar) isLeasedBy(identifierCar, identifierLeaser) view returns (bool) {
    if(carpool[identifierCar].timeRented >= getTimeNow()){
      return true;
    }
    return false;
  }

  /*
  After a Car was rented the car is given back to the free carpool and given the state not rented.
  */
  function returnCarToCarpool(address payable identifierCar) public
            knownCar(identifierCar) isLeased(identifierCar) isLeasedBy(identifierCar, msg.sender) payable {
    carpool[identifierCar].timeRented = 0;
    carpool[identifierCar].currentState = CarState.Leased;
    delete carpool[identifierCar].leaser;
  }

  function getAvailableVehicles() public view returns (address[100] memory){
    address[100] memory avaibleCars;
    uint256 identifierAll = 0;
    uint256 identifierAvaible = 0;
    if(allCars.length != maxArrayLength){
      while(identifierAll < allCars.length && identifierAvaible < avaibleCars.length){
        if(carpool[allCars[identifierAll]].owner != address(0) && mayRent(allCars[identifierAll])) {
          avaibleCars[identifierAvaible] = allCars[identifierAll];
          identifierAvaible++;
        }
        identifierAll++;
      }
    }
    return avaibleCars;
  }



//--------------------------------------------------------------------------------------------------------------------

//public getter for Car

  function getOwner(address identifierCar) public knownCar(identifierCar) view returns (address) {
    return carpool[identifierCar].owner;
  }

  /*
  Checks if a Car is already rented/ in Use.
  */
  function getCurrentState(address identifierCar) public knownCar(identifierCar) view returns (CarState) {
    return carpool[identifierCar].currentState;
  }

  function getLeaser(address identifierCar) public knownCar(identifierCar) view returns (address) {
    return carpool[identifierCar].leaser;
  }

  /*
  Get the Time till when the Car is rented.
  The return value the passed time since the start of the Unix epoch (1st January 1970, 00:00)
  */
  function getTimeRented(address identifierCar) public knownCar(identifierCar) view returns (uint256) {
    return carpool[identifierCar].timeRented;
  }

  function getLatitude(address identifierCar) public knownCar(identifierCar) view returns (int256) {
    return carpool[identifierCar].latitude;
  }

  function getLongitude(address identifierCar) public knownCar(identifierCar) view returns (int256) {
    return carpool[identifierCar].longitude;
  }

  function getNummernschild(address identifierCar) public knownCar(identifierCar) view returns (string memory) {
    return carpool[identifierCar].nummernschild;
  }

  function getTyp(address identifierCar) public knownCar(identifierCar) view returns (string memory) {
    return carpool[identifierCar].typ;
  }

  function getModell(address identifierCar) public knownCar(identifierCar) view returns (string memory) {
    return carpool[identifierCar].modell;
  }

  function getHersteller(address identifierCar) public knownCar(identifierCar) view returns (string memory) {
    return carpool[identifierCar].hersteller;
  }

  function getFarbe(address identifierCar) public knownCar(identifierCar) view returns (string memory) {
    return carpool[identifierCar].farbe;
  }

  function getPs(address identifierCar) public knownCar(identifierCar) view returns (uint256) {
    return carpool[identifierCar].ps;
  }

  function getMietpreis(address identifierCar) public knownCar(identifierCar) view returns (uint256) {
    return carpool[identifierCar].mietpreis;
  }

  function getMaxMietdauer(address identifierCar) public knownCar(identifierCar) view returns (uint256) {
    return carpool[identifierCar].maxMietdauer;
  }

  function getMinMietdauer(address identifierCar) public knownCar(identifierCar) view returns (uint256) {
    return carpool[identifierCar].minMietdauer;
  }


//--------------------------------------------------------------------------------------------------------------------

  /*
  Resets a car from the total Carpool.
  Just for debugging Reasons. Should not make to the final version.
  */
  function resetCar(address identifierCar) public{
    Car memory defaultValueCar = Car({
      owner: msg.sender, currentState: CarState.Free, leaser: address(0),timeRented: 0, amountEarned: 0,
       longitude: 0, latitude: 0,nummernschild: "", modell: "", typ: "", hersteller: "", farbe: "",
        ps: 0, mietpreis: 0, maxMietdauer: 0, minMietdauer: 0
      });

    carpool[identifierCar] = defaultValueCar;
  }

  //--------------------------------------------------------------------------------------------------------------------

  //Private Methods

  function getTimeNow() private view returns (uint256){
    return now;
  }

  function makeCarFree (address identifierCar) private {
    delete carpool[identifierCar].leaser;
    carpool[identifierCar].currentState = CarState.Free;
  }

  function getPostionInAllCars(address identifierCar) private view returns (uint256){
    if(allCars.length != maxArrayLength){
      for(uint256 i = 0; i < allCars.length; ++i){
        if(allCars[i] == identifierCar) {
          return i;
        }
      }
    }
    return maxArrayLength;
  }

  function deleteCarFromAllCars(uint256 index) private {
    if (index >= realLengthAll) return;

    if(realLengthAll == 1){
      delete allCars[0];
      return;
    }
    allCars[index] = allCars[realLengthAll - 1];
    delete allCars[realLengthAll - 1];
    return;
  }

  //--------------------------------------------------------------------------------------------------------------------

  //Requires for the beginning of functions

  modifier knownCar (address identifierCar) {
    require(carpool[identifierCar].owner != address(0), "Car is not in carpool");
    _;
  }

  modifier onlyOwner (address identifierCar, address identifierOwner) {
    require(carpool[identifierCar].owner == identifierOwner, "Not the right owner of the Car.");
    _;
  }

  modifier isLeased (address identifierCar) {
    require(getCurrentState(identifierCar) == CarState.Leased, "Car is not Leased");
    _;
  }

  modifier isLeasedBy (address identifierCar, address identifierLeaser) {
    require(getLeaser(identifierCar) == identifierLeaser, "Not the right Leaser of the Car.");
    _;
  }

  modifier carFree (address identifierCar) {
    require(getCurrentState(identifierCar) == CarState.Free, "Car is not free.");
    _;
  }

}

