package ghostsimulator.controller.tutor;


public interface TutorServerI {
	public boolean hasRequest();
	public Request getLastRequest();
	public void answerRequest(int id, String territory, String code);
}
