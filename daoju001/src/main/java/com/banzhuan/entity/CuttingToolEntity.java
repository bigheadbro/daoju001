package com.banzhuan.entity;

import java.io.Serializable;

import com.banzhuan.util.StringUtil;

public class CuttingToolEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4596714004296951702L;
	//
	private int id;
	//
	private int ishot;
	//
	private String code;
	//
	private String brand;
	//
	private String seriesname;
	//
	private String version;
	
	private String videoinfo;
	
	private String videolink;
	//
	private String material;
	//
	private int ctcount;
	//
	private int angle;
	//
	private String diameter;
	//
	private String usage;
	//
	private int cujing;
	//
	private double usefullength;
	//
	private double pipesize;
	//
	private String shank;
	//
	private String shanktype;
	//
	private String shape;
	//
	private int backangle;
	//
	private String workingtool;
	//
	private int edgeno;
	//
	private String edgelength;
	//
	private String totallength;
	//
	private String screwangle;
	//
	private String coatingtype;
	//
	private String rangle;
	//
	private int direction;
	//
	private double minworkdiameter;
	//
	private int innercooling;
	//
	private String diameterratio;
	//
	private String slotshape;
	//
	private String handlenorm;
	//
	private String taptype;
	//
	private String screwtype;
	//
	private String axistype;
	//
	private String axisdetail;
	
	private String suitcase;
	
	private String info;
	
	private String outline;
	
	private String cover;
	
	private String pic;
	
	private double thickness;
	
	private double maxslotdepth;
	
	private String interfacesize;
	
	private String necklength;
	
	//锥度
	private double taper;
	
	private double slotwidth;
	
	private double pointdiameter;
	
	private String handledsize;

	private String screwsize;
	
	private String screwdistance;
	
	private String accuracy;
	
	private String provider;

	private String samecolume;
	
	private String relativecollet;

	private String width;
	
	private String height;
	
	private String grooverange;
	
	private String drillrange;
	
	private String screwdirection;
	
	private String workingtype;
	
	private String workingrange;
	
	private String relative;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIshot() {
		return ishot;
	}

	public void setIshot(int ishot) {
		this.ishot = ishot;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getSeriesname() {
		return seriesname;
	}

	public void setSeriesname(String seriesname) {
		this.seriesname = seriesname;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}

	public int getCtcount() {
		return ctcount;
	}

	public void setCtcount(int ctcount) {
		this.ctcount = ctcount;
	}

	public int getAngle() {
		return angle;
	}

	public void setAngle(int angle) {
		this.angle = angle;
	}

	public String getDiameter() {
		return diameter;
	}

	public void setDiameter(String diameter) {
		this.diameter = diameter;
	}

	public String getUsage() {
		return usage;
	}

	public void setUsage(String usage) {
		this.usage = usage;
	}

	public int getCujing() {
		return cujing;
	}

	public void setCujing(int cujing) {
		this.cujing = cujing;
	}

	public double getUsefullength() {
		return usefullength;
	}

	public void setUsefullength(double usefullength) {
		this.usefullength = usefullength;
	}

	public double getPipesize() {
		return pipesize;
	}

	public void setPipesize(double pipesize) {
		this.pipesize = pipesize;
	}

	public String getShank() {
		return shank;
	}

	public void setShank(String shank) {
		this.shank = shank;
	}

	public String getShape() {
		return shape;
	}

	public void setShape(String shape) {
		this.shape = shape;
	}

	public int getBackangle() {
		return backangle;
	}

	public void setBackangle(int backangle) {
		this.backangle = backangle;
	}

	public String getWorkingtool() {
		return workingtool;
	}

	public void setWorkingtool(String workingtool) {
		this.workingtool = workingtool;
	}

	public int getEdgeno() {
		return edgeno;
	}

	public void setEdgeno(int edgeno) {
		this.edgeno = edgeno;
	}

	public String getEdgelength() {
		return edgelength;
	}

	public void setEdgelength(String edgelength) {
		this.edgelength = edgelength;
	}

	public String getTotallength() {
		return totallength;
	}

	public void setTotallength(String totallength) {
		this.totallength = totallength;
	}

	public String getScrewangle() {
		return screwangle;
	}

	public void setScrewangle(String screwangle) {
		this.screwangle = screwangle;
	}

	public String getCoatingtype() {
		return coatingtype;
	}

	public void setCoatingtype(String coatingtype) {
		this.coatingtype = coatingtype;
	}

	public String getRangle() {
		return rangle;
	}

	public void setRangle(String rangle) {
		this.rangle = rangle;
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	public double getMinworkdiameter() {
		return minworkdiameter;
	}

	public void setMinworkdiameter(double minworkdiameter) {
		this.minworkdiameter = minworkdiameter;
	}

	public int getInnercooling() {
		return innercooling;
	}

	public void setInnercooling(int innercooling) {
		this.innercooling = innercooling;
	}

	public String getSlotshape() {
		return slotshape;
	}

	public void setSlotshape(String slotshape) {
		this.slotshape = slotshape;
	}

	public String getHandlenorm() {
		return handlenorm;
	}

	public void setHandlenorm(String handlenorm) {
		this.handlenorm = handlenorm;
	}

	public String getTaptype() {
		return taptype;
	}

	public void setTaptype(String taptype) {
		this.taptype = taptype;
	}

	public String getScrewtype() {
		return screwtype;
	}

	public void setScrewtype(String screwtype) {
		this.screwtype = screwtype;
	}

	public String getAxistype() {
		return axistype;
	}

	public void setAxistype(String axistype) {
		this.axistype = axistype;
	}

	public String getAxisdetail() {
		return axisdetail;
	}

	public void setAxisdetail(String axisdetail) {
		this.axisdetail = axisdetail;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getShanktype() {
		return shanktype;
	}

	public void setShanktype(String shanktype) {
		this.shanktype = shanktype;
	}

	public String getDiameterratio() {
		return diameterratio;
	}

	public void setDiameterratio(String diameterratio) {
		this.diameterratio = diameterratio;
	}

	public String getOutline() {
		if(StringUtil.isEmpty(outline))
			return "";
		return outline;
	}

	public void setOutline(String outline) {
		this.outline = outline;
	}

	public String getCover() {
		return cover;
	}

	public void setCover(String cover) {
		this.cover = cover;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public String getSuitcase() {
		return suitcase;
	}

	public void setSuitcase(String suitcase) {
		this.suitcase = suitcase;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public double getThickness() {
		return thickness;
	}

	public void setThickness(double thickness) {
		this.thickness = thickness;
	}

	public double getMaxslotdepth() {
		return maxslotdepth;
	}

	public void setMaxslotdepth(double maxslotdepth) {
		this.maxslotdepth = maxslotdepth;
	}

	public double getTaper() {
		return taper;
	}

	public void setTaper(double taper) {
		this.taper = taper;
	}

	public double getSlotwidth() {
		return slotwidth;
	}

	public void setSlotwidth(double slotwidth) {
		this.slotwidth = slotwidth;
	}

	public double getPointdiameter() {
		return pointdiameter;
	}

	public void setPointdiameter(double pointdiameter) {
		this.pointdiameter = pointdiameter;
	}

	public String getScrewsize() {
		return screwsize;
	}

	public void setScrewsize(String screwsize) {
		this.screwsize = screwsize;
	}

	public String getScrewdistance() {
		return screwdistance;
	}

	public void setScrewdistance(String screwdistance) {
		this.screwdistance = screwdistance;
	}

	public String getHandledsize() {
		return handledsize;
	}

	public void setHandledsize(String handledsize) {
		this.handledsize = handledsize;
	}

	public String getAccuracy() {
		return accuracy;
	}

	public void setAccuracy(String accuracy) {
		this.accuracy = accuracy;
	}

	public String getInterfacesize() {
		return interfacesize;
	}

	public void setInterfacesize(String interfacesize) {
		this.interfacesize = interfacesize;
	}

	public String getNecklength() {
		return necklength;
	}

	public void setNecklength(String necklength) {
		this.necklength = necklength;
	}

	public String getSamecolume()
	{
		return samecolume;
	}

	public void setSamecolume(String samecolume)
	{
		this.samecolume = samecolume;
	}

	public String getRelativecollet()
	{
		return relativecollet;
	}

	public void setRelativecollet(String relativecollet)
	{
		this.relativecollet = relativecollet;
	}

	public String getVideoinfo()
	{
		return videoinfo;
	}

	public void setVideoinfo(String videoinfo)
	{
		this.videoinfo = videoinfo;
	}

	public String getVideolink()
	{
		return videolink;
	}

	public void setVideolink(String videolink)
	{
		this.videolink = videolink;
	}

	public String getWidth()
	{
		return width;
	}

	public void setWidth(String width)
	{
		this.width = width;
	}

	public String getHeight()
	{
		return height;
	}

	public void setHeight(String heigth)
	{
		this.height = heigth;
	}

	public String getGrooverange()
	{
		return grooverange;
	}

	public void setGrooverange(String grooverange)
	{
		this.grooverange = grooverange;
	}

	public String getDrillrange()
	{
		return drillrange;
	}

	public void setDrillrange(String drillrange)
	{
		this.drillrange = drillrange;
	}

	public String getScrewdirection()
	{
		return screwdirection;
	}

	public void setScrewdirection(String screwdirection)
	{
		this.screwdirection = screwdirection;
	}

	public String getWorkingtype()
	{
		return workingtype;
	}

	public void setWorkingtype(String workingtype)
	{
		this.workingtype = workingtype;
	}

	public String getWorkingrange()
	{
		return workingrange;
	}

	public void setWorkingrange(String workingrange)
	{
		this.workingrange = workingrange;
	}

	public String getRelative()
	{
		return relative;
	}

	public void setRelative(String relative)
	{
		this.relative = relative;
	}
}