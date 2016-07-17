package com.hitick.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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

import com.google.android.gcm.server.InvalidRequestException;
import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.MulticastResult;
import com.google.android.gcm.server.Sender;
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
	UserBean user = null;
	@Autowired GroupService groupService;
	GroupBean groupBean = null;
	@Autowired GroupAdminMapService groupAdminMapService;
	@Autowired QuestionsService questionsService;
	QuestionsBean question = null;
	@Autowired VotingDataService votingDataService;
	VotingDataBean votingDataBean = null;
	@Autowired GroupMemberMapService groupMemberMapService; 
	final String GCM_API_KEY = "AlzaSyCeUI8Ws4WWXBtQLgvcSn7ZHjL3yh9T_Fg";
	Map<String,Object> jsonResponseForApp=null;
	
	
	
	
	/* 
	 * 
	 *  
	 *  
	 *  
	 * Methods For WebSite Only
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 */
	
	
	
	
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
	 if(userId > 0){
	 user = userService.findUserById(userId);
	 
	 
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
		if(groupId>0){
		groupAdminMapService.saveMappingDetails((int)session.getAttribute("id"), groupId);
		groupMemberMapService.saveMappingDetails((int)session.getAttribute("id"), groupId);
		mav = new ModelAndView("success");
		}else mav=new ModelAndView("failure");
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
	
        if(groupId>0){
        	
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
		groupBean = groupService.findGroupById(questionsBean.getRefGroupId());
        questionsBean.setDidNotVote(groupBean.getMemberCount());
        List<Integer> memberIds = groupMemberMapService.getMembersFromGroupId(groupBean.getId());
	    int questionId=	questionsService.saveQuestionDetails(questionsBean);
	    votingDataService.saveVotingDetails(questionId,groupBean.getId(),memberIds,time);
		mav = new ModelAndView("success");
		
		return mav;
	}
	
	@RequestMapping(value = "/select-group-for-voting-on-question", method = RequestMethod.GET)
	public ModelAndView selectGroupForVotingOnQuestion(HttpSession session){
		
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
        
		votingDataBean = votingDataService.getVotingDataByQuestionId(questionsBean.getId());
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
	
	
	
	/* 
	 * 
	 *  
	 *  
	 *  
	 * Methods For Mobile App Only
	 * 
	 * 
	 *  
	 * 
	 */
	
	@RequestMapping(value = "/return-json", method = RequestMethod.GET)
	@ResponseBody
	public String testMethod(HttpSession session){
        List<GroupBean>listOfGroups = new ArrayList<GroupBean>(); 
        List<GroupMemberMapBean>listOfGroupMemberMapBean=groupMemberMapService.getListByMemberId((int)session.getAttribute("id"));
        for( GroupMemberMapBean groupMemberMapBean : listOfGroupMemberMapBean ){
		groupBean= new GroupBean();
		groupBean=groupService.findGroupById(groupMemberMapBean.getRefGroupId());
		listOfGroups.add(groupBean);
	}
        GroupBean group1= new GroupBean();
        group1=listOfGroups.get(0);
        ArrayList<QuestionsBean> questions = new ArrayList<QuestionsBean>();
        //questions=groupService.fetchQuestions(group1.getId(),timestamp);
        ArrayList<Integer> listOfNos = new ArrayList<Integer>();
        for(int i=0;i<20;i++)listOfNos.add(i);
        UserBean user = userService.findUserById((int)session.getAttribute("id"));
        ArrayList<Object> tryObject = new ArrayList<Object>();
        tryObject.add(user);
        jsonResponseForApp=new LinkedHashMap<String,Object>();
        jsonResponseForApp.put("person", user);
        jsonResponseForApp.put("listOfGroups", listOfGroups);
        tryObject.add(listOfGroups);
        groupBean=groupService.findGroupById(1);
        Gson gson = new Gson();

     return gson.toJson(groupBean);   
	}
	
	//Trying GCM Service 
	
	public boolean pushNotification(HttpSession session){	
		
	user = userService.findUserById((int)session.getAttribute("Id"));	
	
    final int retries = 3;
    Sender sender = new Sender(GCM_API_KEY);
    Message msg = new Message.Builder().addData("message","some data got updated").build();

    try {
    	
        if(user.getGcmRegistrationId()!=null) {
            MulticastResult result = sender.send(msg,new ArrayList<String>(),retries);
            if (result.getSuccess()!=0) {
                System.out.println("GCM Notification is sent successfully to "+ result.getSuccess()+" devices");
                return true;
            }

            System.out.println("Error occurred while sending push notification ");

        }
    	} catch (InvalidRequestException e) {
    		System.out.println("Invalid Request");
    	} catch (IOException e) {
    		System.out.println("IO Exception");
    	}
    	return false;

	}	

	@RequestMapping(value = "/app-sign-up", method = RequestMethod.POST, headers = {"Content-type=application/json"})
	@ResponseBody
	public String appSignUp(@RequestParam("firstName")String firstName,
							@RequestParam("lastName")String lastName,
							@RequestParam("mobileNumber")String mobileNumber,
							@RequestParam("password")String password,
							@RequestParam("email")String email,
							@RequestParam("institution")String institution,
							@RequestParam("gcmRegistrationId")String gcmRegistrationId
							)
	{
	
	UserBean user = new UserBean();
	user.setFirstName(firstName);
	user.setLastName(lastName);
	user.setMobileNumber(mobileNumber);
	user.setPassword(password);
	user.setEmail(email);
	user.setInstitution(institution);
	user.setGcmRegistrationId(gcmRegistrationId);
	user.setId(userService.addUserApp(user));
	
	jsonResponseForApp=new LinkedHashMap<String,Object>();
	
	jsonResponseForApp.put("person", user);
	
    List<GroupBean>listOfGroups = new ArrayList<GroupBean>(); 
    if(user.getId()>0){
    	List<GroupMemberMapBean>listOfGroupMemberMapBean=groupMemberMapService.getListByMemberId(user.getId());
    	for( GroupMemberMapBean groupMemberMapBean : listOfGroupMemberMapBean ){
    		groupBean= new GroupBean();
    		groupBean=groupService.findGroupById(groupMemberMapBean.getRefGroupId());
    		listOfGroups.add(groupBean);
    	}
    }	
	jsonResponseForApp.put("listOfHisGroups", listOfGroups);
	
     Gson gson = new Gson();
     return gson.toJson(jsonResponseForApp);   
	}
	
	@RequestMapping(value = "/app-sign-in", method = RequestMethod.GET)
	@ResponseBody
	public String appSignIn(@RequestParam("mobileNumber")String mobileNumber,
							@RequestParam("password")String password,
							@RequestParam("gcmRegistrationId")String gcmRegistrationId)

	{
	int userId=userService.validateUser(mobileNumber,password);
	user = new UserBean();
	user.setId(userId);
	if(userId > 0){
		user = userService.findUserById(userId);
		user.setGcmRegistrationId(gcmRegistrationId);
		userService.updateGcmRegistrationId(userId,gcmRegistrationId);
		}
	jsonResponseForApp=new LinkedHashMap<String,Object>();
	jsonResponseForApp.put("person", user);
	
    List<GroupBean>listOfGroups = new ArrayList<GroupBean>(); 
    if(userId>0){
    	List<GroupMemberMapBean>listOfGroupMemberMapBean=groupMemberMapService.getListByMemberId(user.getId());
    	for( GroupMemberMapBean groupMemberMapBean : listOfGroupMemberMapBean ){
    		groupBean= new GroupBean();
    		groupBean=groupService.findGroupById(groupMemberMapBean.getRefGroupId());
    		listOfGroups.add(groupBean);
    	}
    }
	jsonResponseForApp.put("listOfHisGroups", listOfGroups);	

		
     Gson gson = new Gson();
     return gson.toJson(jsonResponseForApp);   
	}

	@RequestMapping(value = "/app-fetch-question", method = RequestMethod.GET)
	@ResponseBody
	public String appFetchQuesting( @RequestParam("userId")int userId,
									@RequestParam("groupId")int groupId,
									@RequestParam("count")int count,
									@RequestParam("until")long until )
	{
		
	 List<QuestionsBean> totalQuestionsList = questionsService.getMoreQuestionsByCount(count,until);
	 jsonResponseForApp=new LinkedHashMap<String,Object>();
	 jsonResponseForApp.put("questions", totalQuestionsList);		
     Gson gson = new Gson();
     return gson.toJson(jsonResponseForApp);   
	}
	
	@RequestMapping(value = "/app-join-group", method = RequestMethod.POST)
	@ResponseBody
	public String appJoinGroup( @RequestParam("groupName")String groupName,
								@RequestParam("groupPassword")String groupPassword,
								@RequestParam("userId")int userId)
	{
		int groupId=groupService.checkForGroup(groupName,groupPassword);
		groupBean=new GroupBean();
		groupBean.setId(groupId);
        if(groupId>0){
        	
        groupMemberMapService.saveMappingDetails(userId,groupId);
        groupService.alterMemberCount(groupId,+1);
        groupBean=groupService.findGroupById(groupId);
        
        }
		
     Gson gson = new Gson();
     return gson.toJson(groupBean);   
	}
	
	 
	@RequestMapping(value = "/app-create-group", method = RequestMethod.POST)
	@ResponseBody
	public String appCreateGroup(   @RequestParam("groupName")String groupName,
									@RequestParam("groupPassword")String groupPassword,
									@RequestParam("userId")int userId)
{
		groupBean=new GroupBean();
		groupBean.setGroupName(groupName);
		groupBean.setGroupPassword(groupPassword);
		groupBean.setRefAdminId(userId);
		
		int groupId=groupService.addGroup(groupBean);
		groupBean.setId(groupId);

		if(groupId>0){
		groupAdminMapService.saveMappingDetails(userId, groupId);
		groupMemberMapService.saveMappingDetails(userId, groupId);
		}

		Gson gson = new Gson();
		return gson.toJson(groupBean);     
	}

	@RequestMapping(value = "/app-voting-data-of-user", method = RequestMethod.POST)
	@ResponseBody
	public String appVotingDataOfUser(  @RequestParam("questionId")int questionId,
										@RequestParam("userId")int userId,
										@RequestParam("response")boolean response,
										@RequestParam("groupId")int groupId)
	{	       							
	int oldVoteStatus = votingDataService.getVoteStatusFromMemberId(userId,questionId);
	int newVoteStatus=0;
	String choice =response?"YES":"NO";
	if(response)newVoteStatus=1;
	else newVoteStatus=2;
		
	switch(oldVoteStatus){
			case 0:
				questionsService.setVoteForMember(choice,questionId);
				votingDataService.updateVoteStatus(newVoteStatus,groupId,userId,questionId);
			break;
			case 1:
				questionsService.updateVoteForMember(choice,questionId);
				votingDataService.updateVoteStatus(newVoteStatus,groupId,userId,questionId);
			break;
			case 2:
				questionsService.updateVoteForMember(choice,questionId);
				votingDataService.updateVoteStatus(newVoteStatus,groupId,userId,questionId);
			break;
			}
	question=questionsService.findQuestionBeanByQuestionId(questionId);	
     Gson gson = new Gson();
     return gson.toJson(question);   
	}

	@RequestMapping(value = "/app-post-question", method = RequestMethod.POST)
	@ResponseBody
	public String appPostQuestion(  @RequestParam("userId")int userId,
									@RequestParam("questionString")String questionString,
									@RequestParam("questionStipulatedTime")String questionStipulatedTime,
									@RequestParam("groupId")int groupId)
	{
		long time=Long.parseLong(questionStipulatedTime);
		groupBean = groupService.findGroupById(groupId);
		question = new QuestionsBean();
		question.setQuestion(questionString);
		question.setRefGroupId(groupId);
        question.setDidNotVote(groupBean.getMemberCount());
        int questionId=	questionsService.saveQuestionDetails(question);
        question.setId(questionId);
        if(questionId>0){
        List<Integer> memberIds = groupMemberMapService.getMembersFromGroupId(groupBean.getId());
	    votingDataService.saveVotingDetails(questionId,groupBean.getId(),memberIds,time);
        }
     Gson gson = new Gson();
     return gson.toJson(question);   
	}
	

}
