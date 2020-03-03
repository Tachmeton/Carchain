const carchain = artifacts.require("./carchain.sol");

module.exports = function(deployer, network, accounts) {
  deployer.deploy(carchain);
};
