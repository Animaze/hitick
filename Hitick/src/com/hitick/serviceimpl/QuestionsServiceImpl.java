package com.hitick.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hitick.bean.QuestionsBean;
import com.hitick.dao.QuestionsDao;
import com.hitick.service.QuestionsService;

@Service
public class QuestionsServiceImpl  implements QuestionsService {

	@Autowired QuestionsDao questionsDao;
	
	@Override
	public int saveQuestionDetails(QuestionsBean questionsBean) {
		return questionsDao.saveQuestionDetails(questionsBean);
	}

	@Override
	public List<QuestionsBean> findQuestionsByGroupId(int groupId) {
		return questionsDao.findQuestionsByGroupId(groupId);
	}

	@Override
	public void setVoteForMember(String choice, int questionId) {
		questionsDao.setVoteForMember(choice,questionId);
	}

	@Override
	public void updateVoteForMember(String choice, int questionId) {
		questionsDao.updateVoteForMember(choice,questionId);
		
	}

	@Override
	public QuestionsBean findQuestionBeanByQuestionId(int questionId) {
		return questionsDao.findQuestionBeanByQuestionId(questionId);
	}

	@Override
	public void updateResultForQuestion(int questionId, int result) {
		questionsDao.updateResultForQuestion(questionId,result);
		
	}

	@Override
	public int getQuestionResultByQuestionId(int questionId) {
		return questionsDao.getQuestionResultByQuestionId(questionId);
	}

	@Override
	public List<QuestionsBean> getMoreQuestionsByCount(int count, long until) {
		
		return questionsDao.getMoreQuestionsByCount(count,until);
	}

}
