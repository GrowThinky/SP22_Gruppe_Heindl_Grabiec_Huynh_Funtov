package de.uni_marburg.iliasapp.data;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;


/*
This class retrieves Module Infos for a given class from the Ilias API as an XML.
However, the ilias-test API does not contain any Modules other than "Chat Container".
So in the end we decided not to use it.
 */
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
            Document doc = parseResponse(xml);
            System.out.println(xml);
            Node node = doc.getElementsByTagName("Member").item(1);
            NamedNodeMap attributes = doc.getElementsByTagName("Member").item(0).getAttributes();

            System.out.println("voila: " + attributes.item(0).getNodeValue());
            String user_id = attributes.item(0).getNodeValue().split("_")[3];
            UserRequest userRequest = new UserRequest(sessionId);
            userRequest.makeRequest(user_id);
        }

        public Document parseResponse(String xml){

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder;
            InputSource is;
            Document doc = null;
            try {
                builder = factory.newDocumentBuilder();
                is = new InputSource(new StringReader(xml));
                 doc = builder.parse(is);
                NodeList list = doc.getElementsByTagName("sessionID");
                //System.out.println(list.item(0).getTextContent());
            } catch (ParserConfigurationException e) {
            } catch (SAXException e) {
            } catch (IOException e) {
            }
            return doc;
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
