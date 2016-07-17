package com.hitick.bean;

public class VotingDataBean extends BaseBean{

	private int refMemberId;
	private int refGroupId;
	private int refQuestionId;
	private int vote;
	private String timeOfPostingQuestion;
	private String stipulatedTimeForVoting;
	
	
	public String getTimeOfPostingQuestion() {
		return timeOfPostingQuestion;
	}
	public void setTimeOfPostingQuestion(String timeOfPostingQuestion) {
		this.timeOfPostingQuestion = timeOfPostingQuestion;
	}
	public String getStipulatedTimeForVoting() {
		return stipulatedTimeForVoting;
	}
	public void setStipulatedTimeForVoting(String stipulatedTimeForVoting) {
		this.stipulatedTimeForVoting = stipulatedTimeForVoting;
	}
	public int getRefMemberId() {
		return refMemberId;
	}
	public void setRefMemberId(int refMemberId) {
		this.refMemberId = refMemberId;
	}
	public int getRefGroupId() {
		return refGroupId;
	}
	public void setRefGroupId(int refGroupId) {
		this.refGroupId = refGroupId;
	}
	public int getRefQuestionId() {
		return refQuestionId;
	}
	public void setRefQuestionId(int refQuestionId) {
		this.refQuestionId = refQuestionId;
	}
	public int getVote() {
		return vote;
	}
	public void setVote(int vote) {
		this.vote = vote;
	}
	
	
}
