package de.uni_marburg.iliasapp.data;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.net.URL;

public class DataAPIRequest {

    private final String NAMESPACE = "urn:ilUserAdministration";
    final String SOAP_ACTION = "urn:ilUserAdministration#getCourseXML";
    String URL = "https://ilias-test.hrz.uni-marburg.de:443/webservice/soap/server.php";
    String client_id = "mriliastest";
    SoapObject request;
    SoapSerializationEnvelope envelope;
    HttpTransportSE androidHttpTransport;
    String xml;
    String sessionId;

    public DataAPIRequest(String sid) {
        this.sessionId = sid;

    }

        public void makeRequest(int course_id) {

            request = new SoapObject(NAMESPACE, "getCourseXML");
            request.addProperty("sid", sessionId);
            request.addProperty("course_id", course_id);


            envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

            envelope.setOutputSoapObject(request);
            System.out.println(request);
            androidHttpTransport = new HttpTransportSE(URL);
            androidHttpTransport.setReadTimeout(200000);

            ModulApiRunnable f = new ModulApiRunnable();
            Thread t = new Thread(f);
            t.start();
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("voila: " + xml);
        }




    public class ModulApiRunnable implements Runnable {


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
