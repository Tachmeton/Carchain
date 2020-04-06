package de.dhbw.chaincar.data;


import android.content.Context;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

import de.dhbw.chaincar.R;

public class BCConnector {

    String contractAdress =     "0xdb268b971C46d61bd512071D57706dEADe11369B";
    String fromAddress =        "0x28f814Ff05aF5DbFF0401A6c98AB0942DbA95a63";
    String renterAddress =      "0x3d48704143135059A1990dcDF9eEC5C73f750179";
    String carWallet =          "0xfCB0306AadaFF0CD11A9576180bbd6a7787ca509";

    private Context context;
    private static BCConnector bccon;

    public static BCConnector getInstance(Context context) {
        if(bccon == null){
            bccon = new BCConnector(context);
        }
        return bccon;
    }
    private BCConnector(Context context){
        this.context = context;
        Web3j web3 = Web3j.build(new HttpService("http://193.196.54.51:8545"));
        
    }

    public int getConnector(){




        return 0;
    }


    private String readContractJSON(){
        InputStream is = context.getResources().openRawResource(R.raw.Carchain);
    }   Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        try {
            Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            int n;
}           while ((n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, n);