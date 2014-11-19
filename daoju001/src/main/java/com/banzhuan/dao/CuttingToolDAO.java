package com.banzhuan.dao;

import java.util.List;
import java.util.Map;

import com.banzhuan.entity.CuttingToolEntity;
/**
 * 代理商DAO
 * @author guichaoqun
 *
 */
public interface CuttingToolDAO {
	/**
	 * @param id
	 * @return
	 */
	CuttingToolEntity queryCuttingToolById(int id);
	
	public int insertCuttingToolEntity(CuttingToolEntity hotct);
	
	public void test();
	
	public List<CuttingToolEntity> searchCuttingTool(Map<String, String> map);

	public List<CuttingToolEntity> getAllItems();
}
