package com.banzhuan.service;

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
import com.banzhuan.common.Result;
import com.banzhuan.form.GoodcaseForm;
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
	
	public Map<Integer,List<AgentEntity>> getAllAgents()
	{
		List<AgentEntity> agents = agentDAO.getAllagents();
		
		Map<Integer,List<AgentEntity>> agentMap = new HashMap<Integer,List<AgentEntity>>();
		for(int i = 0;i < agents.size(); i++)
		{
			List<AgentEntity> tmp = agentMap.get(ChineseSpelling.letterToNum(ChineseSpelling.getFirstLetter(((AgentEntity)agents.get(i)).getCompanyName())));
			tmp.add((AgentEntity)agents.get(i));
			agentMap.put(ChineseSpelling.letterToNum(ChineseSpelling.getFirstLetter(((AgentEntity)agents.get(i)).getCompanyName())), tmp);
		}

		return agentMap;
	}
	

}
