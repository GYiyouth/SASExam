package Server;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by geyao on 2016/12/12.
 */
public class MyXmlWriter {
	private File file = new File("/Users/geyao/IdeaProjects/SoftWareArchitecture 2/src/states.xml");

	public void WriteXml(HashMap<String, Integer> AllStateMap){
		Date date = new Date();
		String time = String.format("%tY-%tm-%td  %tH:%tM:%tS", date,date,date,date,date,date);



		for (HashMap.Entry<String, Integer> entry : AllStateMap.entrySet()) {
			if (entry.getValue() != 0 && entry!= null){
				write(time, entry.getKey(), entry.getValue());
			}
		}



	}

	public void write(String time, String id, int state){
		SAXReader reader = new SAXReader();
		try {

			org.dom4j.Document document = reader.read(file);
			Element root = document.getRootElement();
			Element states = root.addElement("state");
			Element Eid = states.addElement("id");
			Eid.setText(id);
			Element sTime = states.addElement("time");
			sTime.setText(time);
			Element Estate = states.addElement("stateCode");
			Estate.setText(state+"");

			writer(document);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public void delete() throws Exception{
		try {
			SAXReader reader = new SAXReader();
			File file = new File("/Users/geyao/IdeaProjects/SoftWareArchitecture 2/src/states.xml");
			Document document = reader.read(file);
			Element root = document.getRootElement();
			//存放actionBean,interceptorBean的哈希表
			Allbean allbean = new  Allbean();
			//拦截器放入controllerBean中
			Iterator<Element> states = root.elementIterator("state");
			while (states.hasNext()){
				Element state = states.next();
				state.getParent().remove(state);
				writer(document);
			}

		}catch (Exception e){
			e.printStackTrace();

		}
	}


	public void writer(Document document) throws Exception {


		OutputFormat format = OutputFormat.createPrettyPrint();

		format.setEncoding("UTF-8");

		XMLWriter writer = new XMLWriter(new OutputStreamWriter(
				new FileOutputStream(file), "UTF-8"), format);

		writer.write(document);

		writer.flush();

		writer.close();
	}
}
