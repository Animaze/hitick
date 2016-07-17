package com.hitick.bean;

public class GroupBean extends BaseBean{
	
	private int memberCount;
	private int refAdminId;
	private String groupName;
	private String groupPassword;

	public int getMemberCount() {
		return memberCount;
	}
	public void setMemberCount(int memberCount) {
		this.memberCount = memberCount;
	}
	public int getRefAdminId() {
		return refAdminId;
	}
	public void setRefAdminId(int refAdminId) {
		this.refAdminId = refAdminId;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getGroupPassword() {
		return groupPassword;
	}
	public void setGroupPassword(String groupPassword) {
		this.groupPassword = groupPassword;
	}
	

}
