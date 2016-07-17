package com.hitick.controllers;

import org.springframework.stereotype.Controller;

@Controller
public class adminController {

}

/*
 * package com.hitick.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.hitick.bean.GroupAdminMapBean;
import com.hitick.bean.GroupBean;
import com.hitick.bean.GroupMemberMapBean;
import com.hitick.bean.QuestionsBean;
import com.hitick.bean.UserBean;
import com.hitick.bean.VotingDataBean;
import com.hitick.service.GroupAdminMapService;
import com.hitick.service.GroupMemberMapService;
import com.hitick.service.GroupService;
import com.hitick.service.QuestionsService;
import com.hitick.service.UserService;
import com.hitick.service.VotingDataService;

@Controller
public class memberController {
	
	ModelAndView mav = null;
	String result = "";
	String sessionTimeOutMsg = "";
	@Autowired UserService userService;
	@Autowired GroupService groupService;
	@Autowired GroupAdminMapService groupAdminMapService;
	@Autowired QuestionsService questionsService;
	@Autowired VotingDataService votingDataService;
	@Autowired GroupMemberMapService groupMemberMapService; 

	
	
	
	

	
	
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView home(HttpSession session){
	 mav = new ModelAndView("home");
	session.setMaxInactiveInterval(60 * 30);
	if (session.isNew()) {
		
		session.setAttribute("loginStatus", "loggedOut");
		
		session.setAttribute("id", -1);
		session.setAttribute("name", "");
	}
		
		return mav;
	}
	
	@RequestMapping(value = "/loginUserPage", method = RequestMethod.GET)
	public ModelAndView loginUserPage(HttpSession session){

		mav = new ModelAndView("loginUser");
		
		return mav;
	}
	@RequestMapping(value = "/login-user", method = RequestMethod.POST)
	public ModelAndView validateUser(@RequestParam("mobileNumber")String mobileNumber,@RequestParam("password")String password,HttpSession session){
	 int userId=userService.validateUser(mobileNumber,password);
	 if(userId!=0){
	 UserBean user = userService.findUserById(userId);
	 
	 
	 session.setAttribute("name", user.getFirstName());
	 session.setAttribute("loginStatus", "loggedIn");
	 session.setAttribute("id", userId);
	 
	 
	 }else 	 session.setAttribute("loginStatus", "loggedOut");

		mav = new ModelAndView("home");
		
		return mav;
	}

	@RequestMapping(value="/logout-user", method = RequestMethod.GET)
	public ModelAndView logout(HttpSession session,HttpServletRequest request) {

		session.invalidate();
		HttpSession newSession = request.getSession(true);
		newSession.setAttribute("loginStatus", "loggedOut");
		mav = new ModelAndView("home");
		return mav;
	}
	
	@RequestMapping(value = "/signUpPage", method = RequestMethod.GET)
	public ModelAndView signUpPage(HttpSession session){

		mav = new ModelAndView("signUp");
		mav.addObject("userBean", new UserBean());
		return mav;
	}
	
	@RequestMapping(value = "/register-user", method = RequestMethod.POST)
	public ModelAndView registerUser(@ModelAttribute("userBean")UserBean user,HttpSession session){

		userService.addUser(user);
		mav = new ModelAndView("success");
		return mav;
	}

	@RequestMapping(value = "/register-group", method = RequestMethod.GET)
	public ModelAndView registerGroup(HttpSession session){
		mav = new ModelAndView("groupRegistration");
		mav.addObject("groupBean", new GroupBean());
		return mav;
	}
	
	@RequestMapping(value = "/save-group-details", method = RequestMethod.POST)
	public ModelAndView saveGroupDetails(@ModelAttribute("groupBean")GroupBean group,HttpSession session){

		int groupId=groupService.addGroup(group);
		groupAdminMapService.saveMappingDetails((int)session.getAttribute("id"), groupId);
		groupMemberMapService.saveMappingDetails((int)session.getAttribute("id"), groupId);
		mav = new ModelAndView("success");
		return mav;
	}
	@RequestMapping(value = "/join-group", method = RequestMethod.GET)
	public ModelAndView joinGroup(HttpSession session){
		mav = new ModelAndView("joinGroup");
		mav.addObject("groupBean", new GroupBean());
		return mav;
	}
	@RequestMapping(value = "/save-group-member-mapping-details", method = RequestMethod.POST)
	public ModelAndView saveGroupMemberMappingDetails(@ModelAttribute("groupBean")GroupBean group,HttpSession session){
        int groupId=groupService.checkForGroup(group.getGroupName(),group.getGroupPassword());
	
        if(groupId!=0){
        	
        groupMemberMapService.saveMappingDetails((int)session.getAttribute("id"),groupId);
        groupService.alterMemberCount(groupId,+1);
        
        mav = new ModelAndView("success");
        }
        else{
        mav = new ModelAndView("joinGroup");
        mav.addObject("errorMsg", "Group Name or Group Password is incorrect , please recheck and try again!!!");
        }
		return mav;
	}
	
	@RequestMapping(value = "/select-group-for-question-posting", method = RequestMethod.GET)
	public ModelAndView selectGroupForQuestionPosting(HttpSession session){
		GroupBean groupBean= null;
        List<GroupBean>listOfGroups = new ArrayList<GroupBean>(); 
        List<GroupAdminMapBean>listOfGroupAdminMapBean	=groupAdminMapService.getListByAdminId((int)session.getAttribute("id"));
        for( GroupAdminMapBean groupAdminMapBean : listOfGroupAdminMapBean ){
		groupBean= new GroupBean();
		groupBean=groupService.findGroupById(groupAdminMapBean.getRefGroupId());
		listOfGroups.add(groupBean);
	}
	  mav = new ModelAndView("groupsForQuestionForAdmin");
	  mav.addObject("listOfGroups",listOfGroups);
      mav.addObject("listOfGroupsSize", listOfGroups.size());
		
	
		return mav;
	}
	
	
	@RequestMapping(value = "/post-in-group", method = RequestMethod.GET)
	public ModelAndView postInGroup(@RequestParam("groupId")int groupId,HttpSession session){
       
		mav = new ModelAndView("questionPosting");
		mav.addObject("questionsBean", new QuestionsBean());
		mav.addObject("groupId", groupId);
		
		return mav;
	}

	@RequestMapping(value = "/save-question-group-mapping-details", method = RequestMethod.POST)
	public ModelAndView saveQuestionGroupMappingDetails(@ModelAttribute("questionsBean")QuestionsBean questionsBean,HttpSession session,@RequestParam("hours") String hours,
			@RequestParam("minutes") String minutes){
		long time=0;
		if(Long.parseLong(hours)==0 && Long.parseLong(minutes)==0)
		time=2*60*1000;
		else time = Long.parseLong(hours)*60*60*1000+Long.parseLong(minutes)*60*1000;
		GroupBean groupBean = groupService.findGroupById(questionsBean.getRefGroupId());
        questionsBean.setDidNotVote(groupBean.getMemberCount());
        List<Integer> memberIds = groupMemberMapService.getMembersFromGroupId(groupBean.getId());
	    int questionId=	questionsService.saveQuestionDetails(questionsBean);
	    votingDataService.saveVotingDetails(questionId,groupBean.getId(),memberIds,time);
		mav = new ModelAndView("success");
		
		return mav;
	}
	
	@RequestMapping(value = "/select-group-for-voting-on-question", method = RequestMethod.GET)
	public ModelAndView selectGroupForVotingOnQuestion(HttpSession session){
		GroupBean groupBean= null;
        List<GroupBean>listOfGroups = new ArrayList<GroupBean>(); 
        List<GroupMemberMapBean>listOfGroupMemberMapBean=groupMemberMapService.getListByMemberId((int)session.getAttribute("id"));
        for( GroupMemberMapBean groupMemberMapBean : listOfGroupMemberMapBean ){
		groupBean= new GroupBean();
		groupBean=groupService.findGroupById(groupMemberMapBean.getRefGroupId());
		listOfGroups.add(groupBean);
	}
	  mav = new ModelAndView("groupsOfVotingForMembers");
	  mav.addObject("listOfGroups",listOfGroups);
      mav.addObject("listOfGroupsSize", listOfGroups.size());
		
	
		return mav;
	}
	

	@RequestMapping(value = "/vote-in-group", method = RequestMethod.GET)
	public ModelAndView voteInGroup(@RequestParam("groupId")int groupId,@RequestParam("choice")String choice,HttpSession session){
      if(choice.equalsIgnoreCase("Vote in the selected group")){
	  List<QuestionsBean> totalQuestionsList = questionsService.findQuestionsByGroupId(groupId);
       List<QuestionsBean> questionsList = new ArrayList<QuestionsBean>();
       for(QuestionsBean qb : totalQuestionsList)
       if(qb.getResult()==0)questionsList.add(qb);
       mav = new ModelAndView("voteInGroup");
		mav.addObject("groupId", groupId);
		mav.addObject("newQuestionsBean", new QuestionsBean());
		if(questionsList.size()!=0)mav.addObject("questionsList", questionsList);
		else mav.addObject("questionStatus", "isEmpty");
      }
      else{
    	    groupMemberMapService.deleteMappingDetails((int) session.getAttribute("id"),groupId);
    	    groupService.alterMemberCount(groupId,-1);
    	    mav=new ModelAndView("success"); 
      }
		
		return mav;
	}
	
	@RequestMapping(value = "/save-group-question-voting-mapping-details", method = RequestMethod.POST)
	public ModelAndView saveGroupQuestionVotingMappingDetails(@ModelAttribute("newQuestionsBean")QuestionsBean questionsBean,@RequestParam("choice")String choice,HttpSession session){
       
		int oldVoteStatus = votingDataService.getVoteStatusFromMemberId((int)session.getAttribute("id"),questionsBean.getId());
		int newVoteStatus=0;
		if(choice.equalsIgnoreCase("YES"))newVoteStatus=1;
		else newVoteStatus=2;
	
    switch(oldVoteStatus){
		case 0:
			questionsService.setVoteForMember(choice,questionsBean.getId());
			votingDataService.updateVoteStatus(newVoteStatus,questionsBean.getRefGroupId(),(int)session.getAttribute("id"),questionsBean.getId());
		break;
		case 1:
			questionsService.updateVoteForMember(choice,questionsBean.getId());
			votingDataService.updateVoteStatus(newVoteStatus,questionsBean.getRefGroupId(),(int)session.getAttribute("id"),questionsBean.getId());
		break;
		case 2:
			questionsService.updateVoteForMember(choice,questionsBean.getId());
			votingDataService.updateVoteStatus(newVoteStatus,questionsBean.getRefGroupId(),(int)session.getAttribute("id"),questionsBean.getId());
		break;
		}
		
		
		mav = new ModelAndView("success");
		return mav;
	}
	
	@RequestMapping(value = "/members-in-groups", method = RequestMethod.GET)
	public ModelAndView membersInGroups(HttpSession session){
		GroupBean groupBean= null;
        List<GroupBean>listOfGroups = new ArrayList<GroupBean>(); 
        List<GroupMemberMapBean>listOfGroupMemberMapBean=groupMemberMapService.getListByMemberId((int)session.getAttribute("id"));
        for( GroupMemberMapBean groupMemberMapBean : listOfGroupMemberMapBean ){
		groupBean= new GroupBean();
		groupBean=groupService.findGroupById(groupMemberMapBean.getRefGroupId());
		listOfGroups.add(groupBean);
	}
	  mav = new ModelAndView("groupsOfMembers");
	  mav.addObject("listOfGroups",listOfGroups);
      mav.addObject("listOfGroupsSize", listOfGroups.size());
	  
	
		return mav;
	}
	@RequestMapping(value = "/members-in-group", method = RequestMethod.GET)
	public ModelAndView membersInGroup(@RequestParam("groupId")int groupId,HttpSession session){
		
		List<UserBean>listOfMembers=new ArrayList<UserBean>();
		List<Integer>listOfIdsOfMembers = groupMemberMapService.getMembersFromGroupId(groupId);
        for(int idOfMember : listOfIdsOfMembers)listOfMembers.add(userService.findUserById(idOfMember));
        mav=new ModelAndView("membersList");
        mav.addObject("listOfMembers", listOfMembers);	
        mav.addObject("adminId",groupService.findGroupById(groupId).getRefAdminId());
        mav.addObject("groupId",groupId);
		return mav;
	}
	@RequestMapping(value = "/kick-members", method = RequestMethod.GET)
	public ModelAndView kickMembers(@RequestParam("memberId")int memberId,@RequestParam("groupId")int groupId,HttpSession session){
			
	groupMemberMapService.deleteMappingDetails(memberId,groupId);
    groupService.alterMemberCount(groupId,-1);
    mav=new ModelAndView("success");
    
		return mav;
	}
	
	@RequestMapping(value = "/voting-results", method = RequestMethod.GET)
	public ModelAndView votingResults(HttpSession session){
		GroupBean groupBean= null;
        List<GroupBean>listOfGroups = new ArrayList<GroupBean>(); 
        List<GroupMemberMapBean>listOfGroupMemberMapBean=groupMemberMapService.getListByMemberId((int)session.getAttribute("id"));
        for( GroupMemberMapBean groupMemberMapBean : listOfGroupMemberMapBean ){
		groupBean= new GroupBean();
		groupBean=groupService.findGroupById(groupMemberMapBean.getRefGroupId());
		listOfGroups.add(groupBean);
	}
	  mav = new ModelAndView("groupsForResults");
	  mav.addObject("listOfGroups",listOfGroups);
      mav.addObject("listOfGroupsSize", listOfGroups.size());
		
	
		return mav;
	}

	@RequestMapping(value = "/result-of-group", method = RequestMethod.GET)
	public ModelAndView resultOfGroup(@RequestParam("groupId")int groupId,HttpSession session){
       List<QuestionsBean> questionsList = questionsService.findQuestionsByGroupId(groupId);
		mav = new ModelAndView("chooseQuestionsForResults");
		mav.addObject("emptyQuestionsBean", new QuestionsBean());
		if(questionsList.size()!=0)mav.addObject("questionsList", questionsList);
		else mav.addObject("questionStatus", "isEmpty");
		mav.addObject("groupId", groupId);
		return mav;
	}
	
	@RequestMapping(value = "/view-result", method = RequestMethod.POST)
	public ModelAndView viewResult(@ModelAttribute("emptyQuestionsBean")QuestionsBean questionsBean,HttpSession session){
		mav = new ModelAndView("result");
        
		VotingDataBean votingDataBean = votingDataService.getVotingDataByQuestionId(questionsBean.getId());
		long currentTime=System.currentTimeMillis();
		long givenTimeForVoting=Long.parseLong(votingDataBean.getStipulatedTimeForVoting());
		long questionPostedTime=Long.parseLong(votingDataBean.getTimeOfPostingQuestion());
		if((currentTime-questionPostedTime)>givenTimeForVoting){
			QuestionsBean resultQuestionsBean = questionsService.findQuestionBeanByQuestionId(questionsBean.getId());
			int yesCount = resultQuestionsBean.getYesCount();
			int noCount = resultQuestionsBean.getNoCount();
			int result =0;
			if((yesCount-noCount)>0)result=1;
			else if((yesCount-noCount)<0)result=2;
			else result =3;
			questionsService.updateResultForQuestion(questionsBean.getId(),result);
			
			int updatedResult=questionsService.getQuestionResultByQuestionId(questionsBean.getId());
			if(updatedResult==1)mav.addObject("resultOfQuestion", "YES!!!!");
			else if(updatedResult==2)mav.addObject("resultOfQuestion", "NO!!!");
			else mav.addObject("resultOfQuestion", "Its a tie!!!!");
			
			votingDataService.deleteVotingDataForQuestionByQuestionId(questionsBean.getId());
			
		}else {
			mav.addObject("resultOfQuestion", "The Stipulated time for question isn't over yet , result will be declared shortly!!");

		}
	
		return mav;
	}
	
	
	

	
	@RequestMapping(value = "/return-json", method = RequestMethod.GET)
	@ResponseBody
	public String testMethod(HttpSession session){
		GroupBean groupBean= null;
        List<GroupBean>listOfGroups = new ArrayList<GroupBean>(); 
        List<GroupMemberMapBean>listOfGroupMemberMapBean=groupMemberMapService.getListByMemberId((int)session.getAttribute("id"));
        for( GroupMemberMapBean groupMemberMapBean : listOfGroupMemberMapBean ){
		groupBean= new GroupBean();
		groupBean=groupService.findGroupById(groupMemberMapBean.getRefGroupId());
		listOfGroups.add(groupBean);
	}
        Gson gson = new Gson();
     return gson.toJson(listOfGroups);   
	}
}

 * 
 * 
 */










