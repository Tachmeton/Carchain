pragma solidity >=0.4.21 <0.7.0;


contract carchain {

  //every Car has to have an owner!
  struct Car{
    address owner;
    bool inUse;
    address leaser;
    uint256 timeRented;
    uint256 amountPaid;
    //lattitude longitute
    int256 xCoordinate;
    int256 yCoordinate;
    int256 zCoordinate;
  }

  mapping (uint256 => Car) carpool;


  constructor() public {
  
  }

//--------------------------------------------------------------------------------------------------------------------

  /*
  Adds a new Car to the Carpool.
  */
  function addCar(uint256 identifierCar) public {
    require(
      carpool[identifierCar].owner == address(0), "Car is already in Carpool and a new Car can not be added."
      );

    Car memory newCar = Car({
      owner: msg.sender, inUse: false, leaser: address(0), timeRented: 0, amountPaid: 0, xCoordinate: 0, yCoordinate: 0, zCoordinate: 0
      });

    carpool[identifierCar] = newCar;
  }

  /*
  Removes a car which is not currently in Use from the total Carpool.
  */
  function removeCar(uint256 identifierCar, address identifierOwner) public{
    require(
      carpool[identifierCar].inUse == false && carpool[identifierCar].leaser == address(0) && carpool[identifierCar].timeRented == 0,"Car still in use and can not be removed."
      );
    require(
      carpool[identifierCar].owner == identifierOwner, "Wrong Owner of Car."
    );

    Car memory defaultValueCar = Car({
      owner: address(0), inUse: false, leaser: address(0), timeRented: 0, amountPaid: 0, xCoordinate: 0, yCoordinate: 0, zCoordinate: 0
      });

    carpool[identifierCar] = defaultValueCar;
  }

//--------------------------------------------------------------------------------------------------------------------

  /*
  Marks a car as rented and safes the point from where the car was rented.
  */
  function rentCar(uint256 identifierCar) public payable {
    require(carpool[identifierCar].owner != address(0), "Car is not in carpool");
    require(getInUse(identifierCar) == false, "Car has no one who leased it.");
    //Mindestmietdauer?
    require(msg.value < 1800, "You did not pay enough. (1800)");

    carpool[identifierCar].inUse = true;
    carpool[identifierCar].leaser = msg.sender;
    //Problem: block.timestamp ist ungefähr 90 sekunden ungenau wegen manipulation von minern.
    carpool[identifierCar].timeRented = block.timestamp + msg.value;
  }

  /*
  Gives a boolean value if a car is now avaible for rent.
  */
  function mayRent(uint256 identifierCar) public view returns (bool) {
    require(carpool[identifierCar].owner != address(0), "Car is not in carpool");

    if(carpool[identifierCar].leaser == address(0) && getInUse(identifierCar) == false){
      return true;
    }else{
      return false;
    }
  }

  /*
  Gives a boolean value if a Leaser is still Lawfully/ Legally a Leaser and has enough credit.
  */
  function isLegalLeaser(uint256 identifierCar, address identifierLeaser) public view returns (bool) {
    require(carpool[identifierCar].owner > address(0), "Car is not in carpool");
    require(msg.sender == identifierLeaser, "Sender is not the same as the Parameter Leaser");
    require(getInUse(identifierCar) == true, "Car is not in use. --> There can not be a leaser.");

    if(carpool[identifierCar].leaser == identifierLeaser && carpool[identifierCar].timeRented < block.timestamp){
      return true;
    }
    return false;
  }

  /*
  After a Car was rented the car is given back to the free carpool and given the state not rented.
  */
  function returnCarToCarpool(uint256 identifierCar) public payable {
    require(carpool[identifierCar].leaser > address(0) && getInUse(identifierCar), "Car is not in Use and therefore can not be returned.");

    carpool[identifierCar].timeRented = 0;
    //payable(carpool[identifierCar].owner).transfer(carpool[identifierCar].amountPaid);
    carpool[identifierCar].amountPaid = 0;
    carpool[identifierCar].inUse = false;
    carpool[identifierCar].leaser = address(0);
  }



//--------------------------------------------------------------------------------------------------------------------

//getter and setter for Car

  function getOwner(uint256 identifierCar) public view returns (address) {
    return carpool[identifierCar].owner;
  }

  /*
  Checks if a Car is already rented/ in Use.
  */
  function getInUse(uint256 identifierCar) public view returns (bool) {
    return carpool[identifierCar].inUse;
  }

  function getLeaser(uint256 identifierCar) public view returns (address) {
    return carpool[identifierCar].leaser;
  }

  function getTimeRented(uint256 identifierCar) public view returns (uint256) {
    return carpool[identifierCar].timeRented;
  }

  function getxCoordinate(uint256 identifierCar) public view returns (int256) {
    return carpool[identifierCar].xCoordinate;
  }

  function getyCoordinate(uint256 identifierCar) public view returns (int256) {
    return carpool[identifierCar].yCoordinate;
  }

  function getzCoordinate(uint256 identifierCar) public view returns (int256) {
    return carpool[identifierCar].zCoordinate;
  }

//--------------------------------------------------------------------------------------------------------------------

  /*
  Resets a car from the total Carpool.
  Just for debugging Reasons. Should not make to the final version.
  */
  function resetCar(uint256 identifierCar) public{
    Car memory defaultValueCar = Car({
      owner: address(0), inUse: false, leaser: address(0), timeRented: 0, amountPaid: 0, xCoordinate: 0, yCoordinate: 0, zCoordinate: 0
      });

    carpool[identifierCar] = defaultValueCar;
  }
}