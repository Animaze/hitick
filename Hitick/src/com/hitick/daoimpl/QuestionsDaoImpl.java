package com.hitick.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.hitick.bean.QuestionsBean;
import com.hitick.dao.QuestionsDao;

@Component
public class QuestionsDaoImpl extends BaseDaoImpl implements QuestionsDao {

	String sqlQuery;
	Connection connection;
	PreparedStatement preparedStatement;
	ResultSet resultSet;
	
	@Override
	public int saveQuestionDetails(QuestionsBean questionsBean) {
	int questionId=-1;
    try{
			connection = getConnection();
			
			sqlQuery = "insert into questions values (null, ?,?,?,?,?,?,?)";
			
			preparedStatement = connection.prepareStatement(sqlQuery);	
				preparedStatement.setInt(1, questionsBean.getRefGroupId());
				preparedStatement.setString(2, questionsBean.getQuestion());
				preparedStatement.setInt(3, 0);
				preparedStatement.setInt(4,0);
				preparedStatement.setInt(5,questionsBean.getDidNotVote());
				preparedStatement.setInt(6,0);
				preparedStatement.setString(7,String.valueOf(System.currentTimeMillis()));
				preparedStatement.executeUpdate();
			
			sqlQuery = "select last_insert_id();";
			preparedStatement = connection.prepareStatement(sqlQuery);
			 resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				questionId = resultSet.getInt(1);
					}
				
    } catch (Exception e) {
		e.printStackTrace();
	} finally {
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	return questionId;
	}

	@Override
	public List<QuestionsBean> findQuestionsByGroupId(int groupId)
 {      List<QuestionsBean> questionsList = new ArrayList<QuestionsBean>();
		QuestionsBean questionsBean= null;
		try{
			connection = getConnection();

			 sqlQuery = "select * from questions where ref_group_id=?";

			 preparedStatement = connection.prepareStatement(sqlQuery);
			 preparedStatement.setInt(1, groupId);
			 resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				questionsBean = new QuestionsBean();
				questionsBean.setId(resultSet.getInt("id"));
				questionsBean.setQuestion(resultSet.getString("question"));
				questionsBean.setRefGroupId(groupId);
				questionsBean.setYesCount(resultSet.getInt("yes_count"));
				questionsBean.setNoCount(resultSet.getInt("no_count"));
				questionsBean.setDidNotVote(resultSet.getInt("did_not_vote"));
				questionsBean.setResult(resultSet.getInt("result"));
				questionsBean.setTimeOfPostingQuestion("time_of_posting_question");
				questionsList.add(questionsBean);
				}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
			return questionsList;
		}

	@Override
	public void setVoteForMember(String choice,int questionId) {
	if(choice.equalsIgnoreCase("YES"))sqlQuery="update questions set yes_count=yes_count+1,did_not_vote=did_not_vote-1 where id=?";
	else sqlQuery="update questions set no_count=no_count+1,did_not_vote=did_not_vote-1 where id=?";
	try {
		connection = getConnection();

			preparedStatement = connection.prepareStatement(sqlQuery);

			preparedStatement.setInt(1, questionId);

		preparedStatement.executeUpdate();

	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
		
	}

	@Override
	public void updateVoteForMember(String choice, int questionId) {
    if(choice.equalsIgnoreCase("YES"))sqlQuery="update questions set no_count=no_count-1 , yes_count=yes_count+1 where id=?";
    else sqlQuery="update questions set no_count=no_count+1 , yes_count=yes_count-1 where id=?";
    try {
		connection = getConnection();

			preparedStatement = connection.prepareStatement(sqlQuery);
			preparedStatement.setInt(1, questionId);
		preparedStatement.executeUpdate();

	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	}

	@Override
	public QuestionsBean findQuestionBeanByQuestionId(int questionId) {
		QuestionsBean questionsBean = new QuestionsBean();
		try{
			connection = getConnection();

			 sqlQuery = "select * from questions where id=?";

			 preparedStatement = connection.prepareStatement(sqlQuery);
			 preparedStatement.setInt(1, questionId);
			 resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				questionsBean = new QuestionsBean();
				questionsBean.setId(questionId);
				questionsBean.setQuestion(resultSet.getString("question"));
				questionsBean.setRefGroupId(resultSet.getInt("ref_group_id"));
				questionsBean.setYesCount(resultSet.getInt("yes_count"));
				questionsBean.setNoCount(resultSet.getInt("no_count"));
				questionsBean.setDidNotVote(resultSet.getInt("did_not_vote"));
				questionsBean.setResult(resultSet.getInt("result"));
				questionsBean.setTimeOfPostingQuestion("time_of_posting_question");

			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
			return questionsBean;
	}

	@Override
	public void updateResultForQuestion(int questionId, int result) {
		 sqlQuery="update questions set result =? where id = ? ";
			try {
				connection = getConnection();

					preparedStatement = connection.prepareStatement(sqlQuery);

					preparedStatement.setInt(1, result);
					preparedStatement.setInt(2, questionId);
			
				preparedStatement.executeUpdate();
				

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
		
	}

	@Override
	public int getQuestionResultByQuestionId(int questionId) {
		int resultStatus=0;
		try{

			connection = getConnection();
		

			sqlQuery="select * from questions where id=?";
			preparedStatement = connection.prepareStatement(sqlQuery);
			preparedStatement.setInt(1, questionId);
			resultSet=preparedStatement.executeQuery();
			while(resultSet.next())resultStatus=resultSet.getInt("result");	
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return resultStatus;
	}

	@Override
	public List<QuestionsBean> getMoreQuestionsByCount(int count, long until) {
		List<QuestionsBean> questionsList = new ArrayList<QuestionsBean>();
		QuestionsBean questionsBean= null;
		try{
			connection = getConnection();

			 sqlQuery = "select * from questions where time_of_posting_question < ? order by time_of_posting_question desc limit ?";

			 preparedStatement = connection.prepareStatement(sqlQuery);
			 preparedStatement.setLong(1, until);
			 preparedStatement.setLong(2, count);
			 resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				questionsBean = new QuestionsBean();
				questionsBean.setId(resultSet.getInt("id"));
				questionsBean.setQuestion(resultSet.getString("question"));
				questionsBean.setRefGroupId(resultSet.getInt("id"));
				questionsBean.setYesCount(resultSet.getInt("yes_count"));
				questionsBean.setNoCount(resultSet.getInt("no_count"));
				questionsBean.setDidNotVote(resultSet.getInt("did_not_vote"));
				questionsBean.setResult(resultSet.getInt("result"));
				questionsBean.setTimeOfPostingQuestion("time_of_posting_question");
				questionsList.add(questionsBean);
				}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
			return questionsList;
	
	}

}
