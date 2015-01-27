package ghostsimulator.controller.tutor;

import ghostsimulator.model.Territory;

public interface TutorServerI {
	public boolean hasRequest();
	public Request getLastRequest();
	public void answerRequest(int id, Territory territory, String code);
}
