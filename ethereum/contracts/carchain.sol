pragma solidity >=0.4.21 <0.7.0;


contract Carchain {

  //every Car has to have an owner!
  struct Car{
    uint256 owner;
    string model;
    bool inUse;
    uint256 leaser;
    uint256 timeRented;
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
  function addCar(uint256 identifierCar, uint256 identifierOwner, string memory model) public{
    require(
      carpool[identifierCar].owner == 0, "Car is already in Carpool and a new Car can not be added."
      );

    Car memory newCar = Car({
      owner: identifierOwner, model: model, inUse: false, leaser: 0, timeRented: 0, xCoordinate: 0, yCoordinate: 0, zCoordinate: 0
      });

    carpool[identifierCar] = newCar;
  }

  /*
  Removes a car which is not currently in Use from the total Carpool.
  */
  function removeCar(uint256 identifierCar, uint256 identifierOwner) public{
    require(
      carpool[identifierCar].inUse == false && carpool[identifierCar].leaser == 0 && carpool[identifierCar].timeRented == 0,"Car still in use and can not be removed."
      );
    require(
      carpool[identifierCar].owner == identifierOwner, "Wrong Owner of Car."
    );

    Car memory defaultValueCar = Car({
      owner: 0, model: "", inUse: false, leaser: 0, timeRented: 0, xCoordinate: 0, yCoordinate: 0, zCoordinate: 0
      });

    carpool[identifierCar] = defaultValueCar;
  }

//--------------------------------------------------------------------------------------------------------------------

  /*
  Marks a car as rented and safes the point from where the car was rented.
  */
  function rentCar(uint256 identifierCar, uint256 identifierLeaser) public {
    require(carpool[identifierCar].owner > 0, "Car is not in carpool");
    require(carpool[identifierCar].leaser == 0 && getInUse(identifierCar) == false, "Car has no one who leased it.");

    carpool[identifierCar].inUse = true;
    carpool[identifierCar].leaser = identifierLeaser;
  }

  /*
  After a Car was rented the car is given back to the free carpool and given the state not rented.
  */
  function returnCarToCarpool(uint256 identifierCar) public {
    require(carpool[identifierCar].leaser > 0 && getInUse(identifierCar), "Car is not in Use and therefore can not be returned.");

    carpool[identifierCar].inUse = false;
    carpool[identifierCar].leaser = 0;
  }



//--------------------------------------------------------------------------------------------------------------------

//getter and setter for Car

  function getOwner(uint256 identifierCar) public view returns (uint256) {
    return carpool[identifierCar].owner;
  }

  function getModel(uint256 identifierCar) public view returns (string memory) {
    return carpool[identifierCar].model;
  }

  /*
  Checks if a Car is already rented/ in Use.
  */
  function getInUse(uint256 identifierCar) public view returns (bool) {
    return carpool[identifierCar].inUse;
  }

  function getLeaser(uint256 identifierCar) public view returns (uint256) {
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
      owner: 0, model: "", inUse: false, leaser: 0, timeRented: 0, xCoordinate: 0, yCoordinate: 0, zCoordinate: 0
      });

    carpool[identifierCar] = defaultValueCar;
  }
}