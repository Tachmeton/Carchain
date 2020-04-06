package de.dhbw.chaincar.data;


import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

public class BCConnector {

    String contractAdress =     "0xdb268b971C46d61bd512071D57706dEADe11369B";
    String fromAddress =        "0x28f814Ff05aF5DbFF0401A6c98AB0942DbA95a63";
    String renterAddress =      "0x3d48704143135059A1990dcDF9eEC5C73f750179";
    String carWallet =          "0xfCB0306AadaFF0CD11A9576180bbd6a7787ca509";


    private static BCConnector bccon;

    public static BCConnector getInstance() {
        if(bccon == null){
            bccon = new BCConnector();
        }
        return bccon;
    }
    private BCConnector(){
        Web3j web3 = Web3j.build(new HttpService("http://193.196.54.51:8545"));
        
    }

    public int getConnector(){




        return 0;
    }




}
