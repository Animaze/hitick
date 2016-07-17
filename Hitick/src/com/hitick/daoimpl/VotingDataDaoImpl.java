package com.hitick.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Component;

import com.hitick.bean.QuestionsBean;
import com.hitick.bean.VotingDataBean;
import com.hitick.dao.VotingDataDao;
import com.mysql.jdbc.Util;

@Component
public class VotingDataDaoImpl  extends BaseDaoImpl implements VotingDataDao{

	String sqlQuery;
	Connection connection;
	PreparedStatement preparedStatement;
	ResultSet resultSet;
	
	
	@Override
	public void saveVotingDetails(int questionId, int groupId,
			List<Integer> memberIds,long time) {
		
		try{
			connection = getConnection();
			
			sqlQuery = "insert into voting_data values (null, ?,?,?,?,?,?)";
			for(int mId: memberIds){
			preparedStatement = connection.prepareStatement(sqlQuery);	
				preparedStatement.setInt(1, mId);
				preparedStatement.setInt(2, groupId);
				preparedStatement.setInt(3, questionId);
				preparedStatement.setInt(4,0);
				preparedStatement.setString(5,String.valueOf(System.currentTimeMillis()));
				preparedStatement.setString(6,String.valueOf(time));
				preparedStatement.executeUpdate();
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
	
	}


	@Override
	public int getVoteStatusFromMemberId(int memberId,int questionId) {
		int voteStatus=0;
		try{

			connection = getConnection();
		

			sqlQuery="select * from voting_data where ref_member_id=? and ref_question_id=?";
			preparedStatement = connection.prepareStatement(sqlQuery);
			preparedStatement.setInt(1, memberId);
			preparedStatement.setInt(2, questionId);
			resultSet=preparedStatement.executeQuery();
			while(resultSet.next())voteStatus=resultSet.getInt("vote");	
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return voteStatus;
	}

	@Override
	public void updateVoteStatus(int voteStatus,int groupId,int memberId,int questionId) {
	   sqlQuery="update voting_data set vote =? where ref_member_id = ? and ref_group_id=? and ref_question_id=?";
		try {
			connection = getConnection();

				preparedStatement = connection.prepareStatement(sqlQuery);

				preparedStatement.setInt(1, voteStatus);
				preparedStatement.setInt(2, memberId);
				preparedStatement.setInt(3, groupId);
				preparedStatement.setInt(4, questionId);
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
	public VotingDataBean getVotingDataByQuestionId(int questionId) {
		VotingDataBean votingDataBean = new VotingDataBean();
		try{
			connection = getConnection();

			 sqlQuery = "select * from voting_data where ref_question_id=?";

			 preparedStatement = connection.prepareStatement(sqlQuery);
			 preparedStatement.setInt(1, questionId);
			 resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				votingDataBean = new VotingDataBean();
				votingDataBean.setId(questionId);
				votingDataBean.setRefQuestionId(questionId);
				votingDataBean.setRefGroupId(resultSet.getInt("ref_group_id"));
				votingDataBean.setRefMemberId(resultSet.getInt("ref_member_id"));
				votingDataBean.setVote(resultSet.getInt("vote"));
				votingDataBean.setStipulatedTimeForVoting(resultSet.getString("stipulated_time_for_voting"));
				votingDataBean.setTimeOfPostingQuestion(resultSet.getString("time_of_posting_question"));
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
			return votingDataBean;
	}


	@Override
	public void deleteVotingDataForQuestionByQuestionId(int questionId) {
		 sqlQuery="delete from voting_data where ref_question_id=?";
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



}
