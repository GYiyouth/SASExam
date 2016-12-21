package action;

import Server.*;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import java.util.Map;

/**
 * Created by geyao on 2016/12/12.
 */
public class freshAction extends ActionSupport {
	private Allbean allbean = new Allbean();
	private Map application;
	private static Server server = new Server();

	public Allbean getAllbean() {
		return allbean;
	}

	public void setAllbean(Allbean allbean) {
		this.allbean = allbean;
	}

	@Override
	public String execute() throws Exception {
		ActionContext actionContext = ActionContext.getContext();
		application = actionContext.getApplication();

		setAllbean(MyXmlReader.readXml());
		application.put("allbean", allbean);
		return "success";
	}

	public String freshAll(){
		ActionContext actionContext = ActionContext.getContext();
		application = actionContext.getApplication();
		server.AskAllUpdate();
		setAllbean(MyXmlReader.readXml());
		application.put("allbean", allbean);
		return "success";
	}

	public String clearXML() throws Exception {
		ActionContext actionContext = ActionContext.getContext();
		application = actionContext.getApplication();
		server.delete();
		setAllbean(MyXmlReader.readXml());
		application.put("allbean", allbean);
		return "success";
	}
}
