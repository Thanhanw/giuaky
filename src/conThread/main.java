package conThread;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import taoXML.Student;
import taoXML.taoXML;

public class main {
	private Student st=null;
	private int tuoi=1;
	public main() {
		// TODO Auto-generated constructor stub
	}
public static void main(String[] args) throws TransformerException, ParserConfigurationException {
	taoXML.taoFile("D:\\student.xml");
	File file= new File("D:\\student.xml");
	main m1=new main();
	Thread t1= new Thread(()->{
		try {
			m1.readFile(file);
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	});
	Thread t2= new Thread(()->{
			try {
				m1.tinhTuoi();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	});
	Thread t3= new Thread(()->{
			try {
				m1.taoKQ();
			} catch (TransformerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	});
	}

	public synchronized void readFile(File file) throws SAXException, IOException, ParserConfigurationException {
		DocumentBuilderFactory dbf= DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc= db.parse(file);
		String id, name, addre, date;
		id= doc.getElementsByTagName("id").item(0).getTextContent();
		name= doc.getElementsByTagName("name").item(0).getTextContent();
		addre=doc.getElementsByTagName("address").item(0).getTextContent();
		date=doc.getElementsByTagName("date").item(0).getTextContent();
		st=new Student(id, name, addre, date);
		notifyAll();
	}
	public synchronized void tinhTuoi() throws InterruptedException {
		if(st==null) {
			wait();
		}
		tuoi=2024 - Integer.parseInt(st.getDateOfBirth());
		notifyAll();
	}
	public synchronized void taoKQ() throws TransformerException, ParserConfigurationException, InterruptedException{
		if(tuoi==1) {
			wait();
		}
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
		
		Element age= doc.createElement("age");
		age.appendChild(doc.createTextNode(""+tuoi));
		student.appendChild(age);
		
		
		TransformerFactory tff=TransformerFactory.newInstance();
		Transformer tf= tff.newTransformer();
		
		DOMSource soure= new DOMSource(doc);
		File file= new File("D:\\kq.xml");
		StreamResult result= new StreamResult(file);
		
		tf.transform(soure, result);
	}
}
