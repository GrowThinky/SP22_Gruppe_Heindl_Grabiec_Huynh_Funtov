package de.uni_marburg.iliasapp.login;

import android.util.Log;

import org.apache.poi.ss.formula.functions.T;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;


import java.io.IOException;
import java.security.spec.ECField;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    private final String NAMESPACE = "urn:ilUserAdministration";
    final String SOAP_ACTION = "urn:ilUserAdministration#login";
    String URL = "https://ilias-test.hrz.uni-marburg.de:443/webservice/soap/server.php";
    String client_id = "mriliastest";
    SoapObject request;
    SoapSerializationEnvelope envelope;
    HttpTransportSE androidHttpTransport;

    public LoginResult<LoggedInUser> login(String username, String password) {

        request = new SoapObject(NAMESPACE, "login");
        request.addProperty("client", client_id);
        request.addProperty("username", username);
        request.addProperty("password", password);

        envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

        envelope.setOutputSoapObject(request);
        System.out.println(request);
        androidHttpTransport = new HttpTransportSE(URL);
        androidHttpTransport.setReadTimeout(2000000000);

        Foo f = new Foo();
        Thread t = new Thread(f);
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        LoginResult result = f.getValue();
        System.out.println("we in here!");
        return result;

    }




    public void logout() {

        // TODO: revoke authentication
    }

    public void getUserBySid(String sid) {
        request = new SoapObject(NAMESPACE, "getUserIdBySid");
        request.addProperty("sid", sid);


        envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

        envelope.setOutputSoapObject(request);
        System.out.println(request);
        androidHttpTransport = new HttpTransportSE(URL);
        androidHttpTransport.setReadTimeout(2000000);
        try {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        androidHttpTransport.call(SOAP_ACTION, envelope);
                        String result = (String) envelope.getResponse(); //get response
                        System.out.println(result);

                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println(e.getClass());
                    }
                }
            }).start();

        } catch (Exception e) {
            System.out.println(e.getClass() + e.getMessage());

        }


    }

    public class Foo implements Runnable {
        private volatile LoginResult result;

        @Override
        public void run() {
            try {
                androidHttpTransport.call(SOAP_ACTION, envelope);
                String result = (String) envelope.getResponse(); //get response
                System.out.println(result);
                result = null;

            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(e.getClass());
                result = new LoginResult.Error(new IOException("Error logging in"));
            }
            if(result == null) {

                LoggedInUser fakeUser =
                        new LoggedInUser(
                                java.util.UUID.randomUUID().toString(),
                                "user_name");
                result = new LoginResult.Success<>(fakeUser);
            }

        }


        public LoginResult getValue() {
            return result;
        }
    }


}







/*
                HttpClient httpClient = new DefaultHttpClient();
                HttpContext localContext = new BasicHttpContext();
                HttpGet httpGet = new HttpGet("https://docu.ilias.de:443/webservice/soap/server.php");
                HttpResponse response = httpClient.execute(httpGet, localContext);

                // this is just for testing purposes
                if (username.equals("hello")) {
                    //throw new IOException("user not in database");
                }


                    LoggedInUser fakeUser =
                            new LoggedInUser(
                                    java.util.UUID.randomUUID().toString(),
                                    "user_name");
                    return new LoginResult.Success<>(fakeUser);

                */

