package Server;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.Iterator;

/**
 * Created by geyao on 2016/12/12.
 */
public class MyXmlReader {
	public static Allbean readXml(){
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
				String id = state.element("id").getText();
				String time = state.element("time").getText();
				String stateCode = state.element("stateCode").getText();
				if (allbean.getAllStates().containsKey(id)){
					allbean.getAllStates().remove(id);
				}
				allbean.getAllStates().put(id, stateCode);
				allbean.setTime(time);
			}

			return allbean;
		}catch (Exception e){
			e.printStackTrace();
			return null;
		}

	}
}
