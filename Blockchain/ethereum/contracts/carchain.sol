pragma solidity >=0.4.21 <0.7.0;


contract carchain {

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
  }

  mapping (address => Car) carpool;


  constructor() public {
  }

//--------------------------------------------------------------------------------------------------------------------

  /*
  Adds a new Car to the Carpool.
  */
  function addCar(address identifierCar) public {
    require(
      carpool[identifierCar].owner == address(0), "ID already used."
      );

    Car memory newCar = Car({
      owner: msg.sender, currentState: CarState.Free, leaser: address(0), timeRented: 0, amountEarned: 0,longitude: 0, latitude: 0
      });

    carpool[identifierCar] = newCar;
  }

  /*
  Removes a car which is not currently in Use from the total Carpool.
  */
  function removeCar(address payable identifierCar) public
            knownCar(identifierCar) onlyOwner(identifierCar, msg.sender) carFree(identifierCar) payable {
    delete carpool[identifierCar];
  }

//--------------------------------------------------------------------------------------------------------------------

  /*
  Marks a car as rented and safes the point from where the car was rented.
  */
  function rentCar(address payable identifierCar) public knownCar(identifierCar) carFree(identifierCar) payable {
    //Mindestmietdauer?
    require(msg.value > 2300, "You did not pay enough. (Minimum: 2300)");

    carpool[identifierCar].amountEarned = msg.value;
    address payable beneficiary = identifierCar;
    beneficiary.transfer(msg.value - 2300);
    carpool[identifierCar].currentState = CarState.Leased;
    carpool[identifierCar].leaser = msg.sender;
    //Problem: block.timestamp ist worst case 90 sekunden ungenau wegen manipulation von minern.
    carpool[identifierCar].timeRented = getTimeNow() + msg.value;
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
    if(carpool[identifierCar].timeRented < getTimeNow()){
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

  function getTimeRented(address identifierCar) public knownCar(identifierCar) view returns (uint256) {
    return carpool[identifierCar].timeRented;
  }

  function getLatitude(address identifierCar) public knownCar(identifierCar) view returns (int256) {
    return carpool[identifierCar].latitude;
  }

  function getLongitude(address identifierCar) public knownCar(identifierCar) view returns (int256) {
    return carpool[identifierCar].longitude;
  }


//--------------------------------------------------------------------------------------------------------------------

  /*
  Resets a car from the total Carpool.
  Just for debugging Reasons. Should not make to the final version.
  */
  function resetCar(address identifierCar) public{
    Car memory defaultValueCar = Car({
      owner: address(0), currentState: CarState.Free, leaser: address(0), timeRented: 0, amountEarned: 0, longitude: 0, latitude: 0
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

