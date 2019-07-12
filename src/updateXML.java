import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;


public class updateXML {
	
	// for opening and parsing xml document 
	private String filepath;
	private DocumentBuilderFactory docFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	
	// Nodes
	private Node report;
	private Node job_location;
	private Node job_descriptor;
	private Node project_num;
	private Node report_num;
	private Node visit_date;
	private Node issued_date;
	private Node weather_date;
	private Node temp;
	private Node rain;
	private Node dew;
	private Node wind;
	private Node events;
	private Node issued_by;
	
	private String location_txt;
	private String descriptor_txt;
	private String project_num_txt;
	private String report_num_txt;
	private String visit_date_txt;
	private String issued_date_txt;
	private String weather_date_txt;
	private String temp_txt;
	private String rain_txt;
	private String dew_txt;
	private String wind_txt;
	private String events_txt;
	private String issued_by_txt;
	
	// for writing to the xml file
	private TransformerFactory transformerFactory;
	private Transformer transformer;
	private DOMSource source;
	private StreamResult result;
	
	
	public updateXML() throws JSONException, MalformedURLException, IOException {
		// fetch job data from the cloud
		JSONObject json = new JSONObject(IOUtils.toString(new URL("https://grantsingleton.github.io/Report-Builder/data/jobs.json"), Charset.forName("UTF-8")));
		
		// **FIXME** see individual comments
	    String job_number = "601-860"; // **FIXME** This job number will be user input, not static
	    String todays_date = "April 10, 2019"; // **FIXME** The date will be pulled from a date API
	    
		// Fill variable with data for that job number
		location_txt = json.getJSONObject(job_number).getString("location");
		descriptor_txt = json.getJSONObject(job_number).getString("description");
		project_num_txt = "OFPC Project No. " + job_number;
		report_num_txt = json.getJSONObject(job_number).getString("report_num");
		visit_date_txt = "Day of Visit: " + json.getJSONObject(job_number).getString("visit_date");;
		issued_date_txt = "Issued: " +  todays_date;
		weather_date_txt = "Weather Summary for 04/05/2019"; // **FIXME** change this to dynamic based on the date the inspection was conducted
		rain_txt = json.getJSONObject(job_number).getString("rainfall");
		dew_txt = json.getJSONObject(job_number).getString("dew");
		wind_txt = json.getJSONObject(job_number).getString("wind");
		events_txt = json.getJSONObject(job_number).getString("events");
		issued_by_txt = "Issued by: " + json.getJSONObject(job_number).getString("Issued_by");
		
		// initialize filepath
		filepath = "./resources/document.xml";
		
		// run function to update the xml
		update();
	}
	
	private void update() {
		try {
			docFactory = DocumentBuilderFactory.newInstance();
			docBuilder = docFactory.newDocumentBuilder();
			doc = docBuilder.parse(filepath);
			
			// Get root
			report = doc.getFirstChild();
			
			// get the nodes from the document
			job_location = doc.getElementsByTagName("w:t").item(0);
			job_descriptor = doc.getElementsByTagName("w:t").item(1);
			project_num = doc.getElementsByTagName("w:t").item(2);
			report_num = doc.getElementsByTagName("w:t").item(3);
			visit_date = doc.getElementsByTagName("w:t").item(4);
			issued_date = doc.getElementsByTagName("w:t").item(5);
			weather_date = doc.getElementsByTagName("w:t").item(6);
			temp = doc.getElementsByTagName("w:t").item(8);
			rain = doc.getElementsByTagName("w:t").item(10);
			dew = doc.getElementsByTagName("w:t").item(12);
			wind = doc.getElementsByTagName("w:t").item(16);
			events = doc.getElementsByTagName("w:t").item(20);
			issued_by = doc.getElementsByTagName("w:t").item(21);
			
			// update the text at those nodes
			job_location.setTextContent(location_txt);
			job_descriptor.setTextContent(descriptor_txt);
			project_num.setTextContent(project_num_txt);
			report_num.setTextContent(report_num_txt);
			visit_date.setTextContent(visit_date_txt);
			issued_date.setTextContent(issued_date_txt);
			weather_date.setTextContent(weather_date_txt);
			temp.setTextContent(temp_txt);
			rain.setTextContent(rain_txt);
			dew.setTextContent(dew_txt);
			wind.setTextContent(wind_txt);
			events.setTextContent(events_txt);
			issued_by.setTextContent(issued_by_txt);
			
			
			// write the content into xml file
			transformerFactory = TransformerFactory.newInstance();
			transformer = transformerFactory.newTransformer();
			source = new DOMSource(doc);
			result = new StreamResult(new File(filepath));
			transformer.transform(source, result);
			
			System.out.println("Done adding job info");
			
		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		   } catch (TransformerException tfe) {
			tfe.printStackTrace();
		   } catch (IOException ioe) {
			ioe.printStackTrace();
		   } catch (SAXException sae) {
			sae.printStackTrace();
		   }
	}
	
	
	public static void main(String argv[]) throws JSONException, MalformedURLException, IOException {
		updateXML xml = new updateXML();
	}
}
