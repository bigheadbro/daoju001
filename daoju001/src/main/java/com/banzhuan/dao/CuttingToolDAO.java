package com.banzhuan.dao;

import java.util.List;
import java.util.Map;

import com.banzhuan.entity.CuttingToolEntity;
import com.banzhuan.entity.IndexEntity;
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
	
	public List<CuttingToolEntity> querySeriesByCode(String code, boolean isLeaf);
	
	public List<CuttingToolEntity> getVersionsBySeries(String sn);
	
	public List<CuttingToolEntity> getAllSeries();
	
	public int updateCuttingToolById(CuttingToolEntity ct);
	
	public int getBrandCountByCode(String code);
	
	public int getSeriesnameCountByCode(String code);
	
	public List<CuttingToolEntity> queryCuttingToolByCode(String code);
	
	public List<CuttingToolEntity> getSeriesByParam(CuttingToolEntity ct, int type);
	
	public List<CuttingToolEntity> queryCuttingToolByCt(CuttingToolEntity ct);

	IndexEntity getIndexEntity();
	
	public List<CuttingToolEntity> getIndexCts(List<Integer> id);
	
}
