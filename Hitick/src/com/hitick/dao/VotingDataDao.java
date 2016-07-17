package com.hitick.dao;

import java.util.List;

import com.hitick.bean.VotingDataBean;

public interface VotingDataDao {

	void saveVotingDetails(int questionId, int groupId, List<Integer> memberIds, long time);

	int getVoteStatusFromMemberId(int memberId, int questionId);

	void updateVoteStatus(int newVoteStatus, int refGroupId, int memberId, int questionId);

	VotingDataBean getVotingDataByQuestionId(int questionId);

	void deleteVotingDataForQuestionByQuestionId(int questionId);

}
