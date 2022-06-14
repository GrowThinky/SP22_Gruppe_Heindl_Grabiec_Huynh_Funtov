package de.uni_marburg.iliasapp.login;


import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    private final String NAMESPACE = "urn:ilUserAdministration";
    final String SOAP_ACTION = "urn:ilUserAdministration#login";
    String URL = "https://ilias-test.hrz.uni-marburg.de:443/webservice/soap/server.php";
    String client_id ="mriliastest";
    SoapObject request;
    SoapSerializationEnvelope envelope;
    HttpTransportSE androidHttpTransport;

    public LoginResult<LoggedInUser> login(String username, String password)  {

        request = new SoapObject(NAMESPACE, "login");
        request.addProperty("client",client_id);
        request.addProperty("username",username);
        request.addProperty("password",password);

        envelope = new  SoapSerializationEnvelope(SoapEnvelope.VER11);

        envelope.setOutputSoapObject(request);
        System.out.println(request);
        androidHttpTransport = new HttpTransportSE(URL);
        androidHttpTransport.setReadTimeout(2000000000);
        try {
            new Thread(new Runnable(){
                @Override
                public void run() {
                    try {
                        androidHttpTransport.call(SOAP_ACTION, envelope);
                        String result=(String)envelope.getResponse(); //get response
                        System.out.println(result);

                    } catch (XmlPullParserException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

        } catch (Exception e) {
            System.out.println(e.getClass() + e.getMessage());

        }
        //move this back into catch block above, when done
        return new LoginResult.Error(new IOException("Error logging in"));
    }

    

    public void logout() {

        // TODO: revoke authentication
    }


}