package com.hitick.dao;

import java.util.List;

import com.hitick.bean.QuestionsBean;

public interface QuestionsDao {

	int saveQuestionDetails(QuestionsBean questionsBean);

	void setVoteForMember(String choice, int questionId);

	void updateVoteForMember(String choice, int questionId);

	List<QuestionsBean> findQuestionsByGroupId(int groupId);

	QuestionsBean findQuestionBeanByQuestionId(int questionId);

	void updateResultForQuestion(int questionId, int result);

	int getQuestionResultByQuestionId(int questionId);

	List<QuestionsBean> getMoreQuestionsByCount(int count, long until);

}
