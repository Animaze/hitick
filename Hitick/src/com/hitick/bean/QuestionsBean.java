package com.hitick.bean;

public class QuestionsBean extends BaseBean {
	
	private String question;
	private int refGroupId;
	private int yesCount;
	private int noCount;
	private int didNotVote;
	private int result;
	private String timeOfPostingQuestion;
	
	
	
	public String getTimeOfPostingQuestion() {
		return timeOfPostingQuestion;
	}
	public void setTimeOfPostingQuestion(String timeOfPostingQuestion) {
		this.timeOfPostingQuestion = timeOfPostingQuestion;
	}
	public int getResult() {
		return result;
	}
	public void setResult(int result) {
		this.result = result;
	}
	public int getDidNotVote() {
		return didNotVote;
	}
	public void setDidNotVote(int didNotVote) {
		this.didNotVote = didNotVote;
	}
	public int getYesCount() {
		return yesCount;
	}
	public void setYesCount(int yesCount) {
		this.yesCount = yesCount;
	}
	public int getNoCount() {
		return noCount;
	}
	public void setNoCount(int noCount) {
		this.noCount = noCount;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public int getRefGroupId() {
		return refGroupId;
	}
	public void setRefGroupId(int refGroupId) {
		this.refGroupId = refGroupId;
	}
	

}
