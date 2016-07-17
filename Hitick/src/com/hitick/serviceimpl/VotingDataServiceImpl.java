package com.hitick.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hitick.bean.VotingDataBean;
import com.hitick.dao.VotingDataDao;
import com.hitick.service.VotingDataService;

@Service
public class VotingDataServiceImpl  implements VotingDataService{

	@Autowired VotingDataDao votingDataDao;
	
	@Override
	public void saveVotingDetails(int questionId, int groupId,
			List<Integer> memberIds,long time) {
		votingDataDao.saveVotingDetails(questionId,groupId,memberIds,time);
	}

	@Override
	public int getVoteStatusFromMemberId(int memberId, int questionId) {
		return votingDataDao.getVoteStatusFromMemberId(memberId,questionId);
	}

	@Override
	public void updateVoteStatus(int newVoteStatus, int refGroupId, int memberId,int questionId) {

		votingDataDao.updateVoteStatus(newVoteStatus,refGroupId,memberId,questionId);
	}

	@Override
	public VotingDataBean getVotingDataByQuestionId(int questionId) {
		return votingDataDao.getVotingDataByQuestionId(questionId);
	}

	@Override
	public void deleteVotingDataForQuestionByQuestionId(int questionId) {
		votingDataDao.deleteVotingDataForQuestionByQuestionId(questionId);
	}

}
