cd .\ethereum
truffle --reset
truffle migrate --network ganache
cd ..
node carchain_interact.js