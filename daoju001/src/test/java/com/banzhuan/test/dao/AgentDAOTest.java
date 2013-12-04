package com.banzhuan.test.dao;


import javax.annotation.Resource;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.banzhuan.dao.AgentDAO;
import com.banzhuan.entity.AgentEntity;


public class AgentDAOTest extends BaseTest {
	@Resource
    private AgentDAO agentDAO;
	
	private int id;
	
	@Before
    public void init(){
		Assert.assertNotNull(agentDAO);
		AgentEntity agent = new AgentEntity();
		agent.setNick("bhb");
		agent.setAddress("NO.41");
		agent.setQqConnectId("connectid");
		agent.setPassword("123");
		agent.setCompanyName("shida");
		agent.setBrandName("sss");
		agent.setLogo("abc.com");
		agent.setPhone("123123123");
		agent.setFax("32132131");
		agent.setDescription("des");
		agent.setVerifiedLink("abc.com");
		agent.setContactEmail("fffddd.com");
		agent.setContactName("bhb");
		agent.setContactPhone("123123123");
		agent.setContactQq("34693");

		agentDAO.insertAgentEntity(agent);
		id = agent.getId();
	}

	@After
	 public void destory(){
		//agentDAO.deleteCompanyUserEntityById(id);
		//agentDAO.deleteCompanyEntityByUserId(id);
	 }
	 
	 @Test
	 public void queryCompanyUserEntityById()
	 {
		 AgentEntity agent = agentDAO.queryUserEntityById(3);
		 System.out.print("start:");
		 Assert.assertNotNull(agent);
		 System.out.print(agent.getNick());
	 }
}
