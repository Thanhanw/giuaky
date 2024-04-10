package conThread;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

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
	private long tuoi=1;
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
	
	t1.start();
	t2.start();
	t3.start();
	System.out.println("Thành công");
	}

	public synchronized void readFile(File file) throws SAXException, IOException, ParserConfigurationException {
		DocumentBuilderFactory dbf= DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc= db.parse(file);
		String id, name, addre, date;
		id= doc.getElementsByTagName("id").item(0).getTextContent();
		name= doc.getElementsByTagName("name").item(0).getTextContent();
		addre=doc.getElementsByTagName("address").item(0).getTextContent();
		date=doc.getElementsByTagName("dateofBird").item(0).getTextContent();
		
		LocalDate ldate= LocalDate.parse(date);
		
		st=new Student(id, name, addre, ldate);
		notifyAll();
	}
	public synchronized void tinhTuoi() throws InterruptedException {
		if(st==null) {
			wait();
		}
		LocalDate datenow= LocalDate.now();
		long between= ChronoUnit.DAYS.between(st.getDateOfBirth(),datenow );
		tuoi= between/365;
		notify();
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
		
		LocalDate datest= st.getDateOfBirth();
		int day= datest.getDayOfMonth();
		int month= datest.getMonthValue();
		int year= datest.getYear();
		String dayString= String.valueOf(day);
		String monthString= String.valueOf(month);
		String yearString= String.valueOf(year);
		int tong=tinhTong(dayString,monthString,yearString);
		boolean kiemtraNT= kiemtraNT(tong);
		
		String nguyento;
		if(kiemtraNT) {
			nguyento="true";
		}else {
			nguyento="false";
		}
		
		Element sum= doc.createElement("sum");
		sum.appendChild(doc.createTextNode(""+tong));
		student.appendChild(sum);
		
		Element element_NT= doc.createElement("isDigit");
		element_NT.appendChild(doc.createTextNode(nguyento));
		student.appendChild(element_NT);
		
		TransformerFactory tff=TransformerFactory.newInstance();
		Transformer tf= tff.newTransformer();
		
		DOMSource soure= new DOMSource(doc);
		File file= new File("D:\\kq.xml");
		StreamResult result= new StreamResult(file);
		
		tf.transform(soure, result);
	}
	private boolean kiemtraNT(int num) {
		if(num<=-1) {
			return false;
		}
		for(int i=2; i<=Math.sqrt(num);i++) {
			if(num%i==0) {
				return false;
			}
		}
		return true;
	}
	private int tinhTong(String dayString, String monthString, String yearString) {
		int sum=0;
		for(int i=0;i<dayString.length();i++) {
			sum+=Character.getNumericValue(dayString.charAt(i));
		}
		for(int i=0;i<monthString.length();i++) {
			sum+=Character.getNumericValue(monthString.charAt(i));
		}
		for(int i=0;i<yearString.length();i++) {
			sum+=Character.getNumericValue(yearString.charAt(i));
		}
		return sum;
	}
	
}
