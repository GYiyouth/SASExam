package Server;

import java.util.HashMap;

/**
 * Created by geyao on 2016/12/12.
 */
public class Allbean {
	private HashMap<String, String>AllStates = new HashMap<>();
	private String time;

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public HashMap<String, String> getAllStates() {
		return AllStates;
	}

	public void setAllStates(HashMap<String, String> allStates) {
		AllStates = allStates;
	}

	@Override
	public String toString() {
		return "Allbean{" +
				"AllStates=" + AllStates +
				", \n time='" + time + '\'' +
				'}';
	}
}
