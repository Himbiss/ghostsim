package ghostsimulator.controller.tutor;

import java.io.Serializable;

public class Request implements Serializable {

	private static final long serialVersionUID = -141684532339044419L;
	private int id;
	private String territory;
	private String code;
	
	public Request(int id, String territory, String code) {
		this.id = id;
		this.territory = territory;
		this.code = code;
	}

	public int getId() {
		return id;
	}

	public String getTerritory() {
		return territory;
	}

	public String getCode() {
		return code;
	}
}
