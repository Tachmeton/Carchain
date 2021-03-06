package de.dhbw.chaincar.data;


import android.content.Context;
import android.util.Log;

import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.Contract;
import org.web3j.tx.gas.ContractGasProvider;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.math.BigInteger;

import de.dhbw.chaincar.R;

public class BCConnector {

    private String contractAdress = "0xdb268b971C46d61bd512071D57706dEADe11369B";//"0xdb268b971C46d61bd512071D57706dEADe11369B";
    private String fromAddress = "0x28f814Ff05aF5DbFF0401A6c98AB0942DbA95a63";
    private String renterAddress = "0x3d48704143135059A1990dcDF9eEC5C73f750179";
    private String renterPrivKey = "0xef56dc06b9c5c9b38ac952627988397d9b735b902c7263e3600ef808e41193f9";
    private String carWallet = "0xfCB0306AadaFF0CD11A9576180bbd6a7787ca509";

    private BigInteger gas_price = Contract.GAS_PRICE;
    private BigInteger gas_limit = Contract.GAS_LIMIT;
    //private BigInteger gas_price = new BigInteger("20000000000");
    //private BigInteger gas_limit = new BigInteger("9007199254740991");


    private Context context;
    private static BCConnector bccon;
    private Carchain contract;

    public static BCConnector getInstance(Context context) {
        if (bccon == null) {
            bccon = new BCConnector(context);
        }
        return bccon;
    }

    private BCConnector(Context context) {
        this.context = context;
        Web3j web3 = Web3j.build(new HttpService("http://193.196.54.51:8545"));
        try {
            Web3ClientVersion clientVersion = web3.web3ClientVersion().sendAsync().get();
            if(!clientVersion.hasError()){
                //Connected
                Log.d("BCConnector", "Successufully connected to BC!!");
                Log.d("BCConnector", "Version: " + clientVersion.getWeb3ClientVersion());
            }
            else {
                Log.d("BCConnector", "NOT connected to BC!!");
                //Show Error
            }
        }
        catch (Exception e) {
            //Show Error
            e.printStackTrace();
        }
        //this.contract = Carchain.load(contractAdress, web3, Credentials.create(renterPrivKey, renterAddress), gas_price, gas_limit);

        this.contract = Carchain.load(contractAdress, web3, Credentials.create(renterPrivKey), new ContractGasProvider() {
            @Override
            public BigInteger getGasPrice(String contractFunc) {
                return gas_price;
            }

            @Override
            public BigInteger getGasPrice() {
                return gas_price;
            }

            @Override
            public BigInteger getGasLimit(String contractFunc) {
                return gas_limit;
            }

            @Override
            public BigInteger getGasLimit() {
                return gas_limit;
            }
        });
    }

    public Carchain getContract() {
        return contract;
    }


}