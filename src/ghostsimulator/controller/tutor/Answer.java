package ghostsimulator.controller.tutor;

import ghostsimulator.model.Territory;

import java.io.Serializable;

public class Answer implements Serializable {

	private static final long serialVersionUID = -869595987169167893L;
	private Territory territory;
	private String code;
	private int id;
	
	public Answer(int id, Territory territory, String code) {
		this.id = id;
		this.territory = territory;
		this.code = code;
	}

	public Territory getTerritory() {
		return territory;
	}

	public String getCode() {
		return code;
	}

	public int getId() {
		return id;
	}
}
