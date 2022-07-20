package de.uni_marburg.iliasapp.data;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class UserRequest {


    private final String NAMESPACE = "urn:ilUserAdministration";
    final String SOAP_ACTION = "urn:ilUserAdministration#searchUser";
    String URL = "https://ilias-test.hrz.uni-marburg.de:443/webservice/soap/server.php";
    String client_id = "mriliastest";
    SoapObject request;
    SoapSerializationEnvelope envelope;
    HttpTransportSE androidHttpTransport;
    String xml;
    String sessionId;

    public UserRequest(String sid) {
        this.sessionId = sid;

    }

    public void makeRequest(String user_id) {

        request = new SoapObject(NAMESPACE, "searchUser");
        request.addProperty("sid", sessionId);
        String[] value = {"user_id"};
        request.addProperty("key_fields", "user_id");
        String[] value1 = {user_id};
        request.addProperty("key_values", user_id);
        request.addProperty("attach_roles", 1);
        request.addProperty("active", -1);

        envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

        envelope.setOutputSoapObject(request);
        System.out.println(request);
        androidHttpTransport = new HttpTransportSE(URL);
        androidHttpTransport.setReadTimeout(200000);

        UserRequestRunnable f = new UserRequestRunnable();
        Thread t = new Thread(f);
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        System.out.println("user_data: " + xml );
    }

    public class UserRequestRunnable implements Runnable {

        @Override
        public void run() {
            try {
                androidHttpTransport.call(SOAP_ACTION, envelope);
                xml = (String) envelope.getResponse(); //get response

            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(e.getClass());

            }

        }

    }

}
