package com.banzhuan.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.banzhuan.dao.AgentDAO;
import com.banzhuan.dao.BuyerDAO;
import com.banzhuan.dao.GoodcaseDAO;
import com.banzhuan.dao.QuestionDAO;
import com.banzhuan.entity.AgentEntity;
import com.banzhuan.entity.GoodcaseEntity;
import com.banzhuan.entity.QuestionEntity;
import com.banzhuan.common.Result;
import com.banzhuan.form.GoodcaseForm;
import com.banzhuan.form.QuestionForm;
import com.banzhuan.util.ChineseSpelling;

/**
 * @author guichaoqun
 *
 */
@Service("commonService")
public class CommonService {
	@Autowired
	@Qualifier("buyerDAO")
	private BuyerDAO buyerDAO;

	@Autowired
	@Qualifier("questionDAO")
	private QuestionDAO questionDAO;
	
	@Autowired
	@Qualifier("agentDAO")
	private AgentDAO agentDAO;
	
	@Autowired
	@Qualifier("gcDAO")
	private GoodcaseDAO gcDAO;
	
	public Result getAllGoodcases(GoodcaseForm form)
	{
		Result result = new Result();
		GoodcaseEntity gc = new GoodcaseEntity();
		if(form.getIndustry() != 0)
    	{
    		gc.setIndustry(form.getIndustry());
    	}
    	if(form.getProcessMethod() != 0)
    	{
    		gc.setProcessMethod(form.getProcessMethod());
    	}
    	if(form.getWpHardness() != 0)
    	{
    		gc.setWorkSolidity(form.getWpHardness());
    	}
    	if(form.getWpMaterial() != 0)
    	{
    		gc.setWorkMaterial(form.getWpMaterial());
    	}
		List<GoodcaseEntity> goodcases = gcDAO.getAllGoodcasesByType(gc);
		result.add("goodcases", goodcases);
		return result;
	}
	
	public Result getAllquestions(QuestionForm form)
	{
		Result result = new Result();
		QuestionEntity ques = new QuestionEntity();
		if(form.getIndustry() != 0)
    	{
			ques.setIndustry(form.getIndustry());
    	}
    	if(form.getProcessMethod() != 0)
    	{
    		ques.setProcessMethod(form.getProcessMethod());
    	}
    	if(form.getWpHardness() != 0)
    	{
    		ques.setWpHardness(form.getWpHardness());
    	}
    	if(form.getWpMaterial() != 0)
    	{
    		ques.setWpMaterial(form.getWpMaterial());
    	}
		List<QuestionEntity> questions = questionDAO.getAllquestions(ques);
		result.add("questions", questions);
		return result;
	}
	
	public Map<Integer,List<AgentEntity>> getAllAgents()
	{
		List<AgentEntity> agents = agentDAO.getAllagents();
		
		Map<Integer,List<AgentEntity>> agentMap = new HashMap<Integer,List<AgentEntity>>();

		for(int i = 0;i < agents.size(); i++)
		{
			List<AgentEntity> tmp = agentMap.get(ChineseSpelling.letterToNum(ChineseSpelling.getFirstLetter(((AgentEntity)agents.get(i)).getCompanyName())));
			if(tmp == null)
			{
				tmp = new ArrayList<AgentEntity>();
			}
			tmp.add((AgentEntity)agents.get(i));
			agentMap.put(ChineseSpelling.letterToNum(ChineseSpelling.getFirstLetter(((AgentEntity)agents.get(i)).getCompanyName())), tmp);
		}

		return agentMap;
	}
	
	public Result getMainquestions()
	{
		Result result = new Result();
		List<QuestionEntity> questions = questionDAO.getMainquestions();
		result.add("questions", questions);
		return result;
	}
	
	public Result getMainagents()
	{
		Result result = new Result();
		List<AgentEntity> agents = agentDAO.getMainagents();
		result.add("agents", agents);
		return result;
	}
	
	public Result getMaingoodcases()
	{
		Result result = new Result();
		List<GoodcaseEntity> goodcases = gcDAO.getMainGoodcasesByType();
		result.add("goodcases", goodcases);
		return result;
	}
	

}
