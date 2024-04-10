package taoXML;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

public class taoXML {
	public static void taoFile(String nameFile) throws TransformerException, ParserConfigurationException {
		DocumentBuilderFactory dbf= DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc= db.newDocument();
		Element students =doc.createElement("students");
		doc.appendChild(students);
		Element student = doc.createElement("student");
		students.appendChild(student);
		
		Attr attr= doc.createAttribute("nO");
		attr.setValue("1");
		student.setAttributeNode(attr);
		
		Element id= doc.createElement("id");
		id.appendChild(doc.createTextNode("a1"));
		student.appendChild(id);
		
		Element name= doc.createElement("name");
		name.appendChild(doc.createTextNode("vu hoa"));
		student.appendChild(name);
		
		Element address= doc.createElement("address");
		address.appendChild(doc.createTextNode("dien duong"));
		student.appendChild(address);
		
		LocalDate date= LocalDate.of(2000, 4, 28);
		String sdate= date.toString();
		Element dateofbird= doc.createElement("dateofBird");
		dateofbird.appendChild(doc.createTextNode(sdate));
		student.appendChild(dateofbird);
		
		TransformerFactory tff=TransformerFactory.newInstance();
		Transformer tf= tff.newTransformer();
		
		DOMSource soure= new DOMSource(doc);
		File file= new File(nameFile);
		StreamResult result= new StreamResult(file);
		
		tf.transform(soure, result);
	}

//	File file= new File("D:\\IT\\students.xml");
//	DocumentBuilderFactory dbf= DocumentBuilderFactory.newInstance();
//	DocumentBuilder db = dbf.newDocumentBuilder();
//	Document doc= db.parse(file);
//	
//	Element root= doc.getDocumentElement();
//	Element newelement= doc.createElement("student");
//	
//	Element id= doc.createElement("id");
//	id.appendChild(doc.createTextNode("b2"));
//	newelement.appendChild(id);
//	
//	Element name= doc.createElement("name");
//	name.appendChild(doc.createTextNode("thanh lang"));
//	newelement.appendChild(name);
//	
//	Element address= doc.createElement("address");
//	address.appendChild(doc.createTextNode("quang nam"));
//	newelement.appendChild(address);
//	
//	Element date= doc.createElement("date");
//	date.appendChild(doc.createTextNode("24/04"));
//	newelement.appendChild(date);
//	
//	root.appendChild(newelement);
//	
//	TransformerFactory tff=TransformerFactory.newInstance();
//	Transformer tf= tff.newTransformer();
//	DOMSource soure= new DOMSource(doc);
//	StreamResult result= new StreamResult(file);
//	tf.transform(soure, result);
}
