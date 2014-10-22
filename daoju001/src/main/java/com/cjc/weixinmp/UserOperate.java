package com.cjc.weixinmp;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.banzhuan.entity.QuickrequestEntity;
import com.banzhuan.service.CommonService;
import com.banzhuan.util.StringUtil;
import com.banzhuan.util.Util;
import com.cjc.weixinmp.bean.AbstractResponse;
import com.cjc.weixinmp.bean.CustomMenu;
import com.cjc.weixinmp.bean.CustomMenu.CustomButton;
import com.cjc.weixinmp.bean.ClickEventRequest;
import com.cjc.weixinmp.bean.NewsResponse;
import com.cjc.weixinmp.bean.SubscribeEventRequest;
import com.cjc.weixinmp.bean.TemplateRequest;
import com.cjc.weixinmp.bean.TemplateResponse;
import com.cjc.weixinmp.bean.TextRequest;
import com.cjc.weixinmp.bean.TextResponse;

public class UserOperate extends AbstractUserOperate {

	private Logger logger = LoggerFactory.getLogger(UserOperate.class);
	
	public UserOperate(String FromUserOpenID) {
		super(FromUserOpenID);
	}

	@Override
    public AbstractResponse onTextMessage(TextRequest text) throws WeixinException {
		TextResponse tr = new TextResponse();
		logger.error("aaa:"+text.ToUserName);
    	tr.ToUserName = text.ToUserName;
    	if(StringUtil.isEqual(text.Content.substring(0,2), "材质"))
    	{
    		tr.Content =  Util.queryMaterial(text.Content);
    	}
    	else if(StringUtil.isEqual(text.Content.substring(0,2), "钢材"))
    	{
    		tr.Content =  Util.querySteel(text.Content);
    	}
    	else if(StringUtil.isEqual(text.Content.substring(0,4), "我要名片") && StringUtil.isEqual(text.ToUserName, "gh_b8c3f27889ae"))
    	{
    		logger.error("bbbb");
    		NewsResponse news = new NewsResponse();
    		news.ToUserName = text.ToUserName;
    		news.FromUserName = text.FromUserName;
    		news.addArticle("二维码名片来了！", "你是否有担心过自己的名片被遗忘在角落？产品有特色,无奈同行太多，如何让自己的名片脱颖而出呢？", "https://mmbiz.qlogo.cn/mmbiz/0icHn6Syic8bica2R8nUia7WRH7ILXQy4zibxib9RmTcic5t4j7YmAsvjEuIicKun7FAcTV1ICuLaedF5ibIic3FybGLdvlw/0", "http://mp.weixin.qq.com/s?__biz=MzAwNTAyOTg5Ng==&mid=205863905&idx=1&sn=6d96888d7c26e5ea9401d28153608b32#rd");
        	return news;
    	}
    	logger.error("ccccc");
		return tr;
    }
	
	@Override
    public AbstractResponse onTemplateMessage(TemplateRequest template) throws WeixinException {
		logger.error("template message");
		TemplateResponse tr = new TemplateResponse();
		return tr;
    }
	
	@Override
	public AbstractResponse onSubscribeEvent(SubscribeEventRequest event) throws WeixinException {
		CustomButton button = new CustomButton();
		TextResponse tr = new TextResponse();
		logger.error(event.ToUserName);
		if(StringUtil.isEqual(event.ToUserName, "gh_62d1696b4731"))
		{
			button.addButton(CustomMenu.TYPE.view, "刀具名片", null,"http://mp.weixin.qq.com/s?__biz=MzAwNTAyOTg5Ng==&mid=205688315&idx=1&sn=41606c69afcd36a7d4f551c486f8dbaa#rd");
			button.addButton(CustomMenu.TYPE.click, "小刀发布", "request", null)
					.addSubButton(CustomMenu.TYPE.view, "刀具需求发布", null, "http://www.daoshifu.com/wxnewrequest")
					.addSubButton(CustomMenu.TYPE.click, "查看今日需求", "todayrequest", null)
					.addSubButton(CustomMenu.TYPE.view, "清仓产品发布", null, "http://www.daoshifu.com/wxstockadd")
					.addSubButton(CustomMenu.TYPE.view, "清仓产品列表", null, "http://www.daoshifu.com/wxstocklist");
			button.addButton(CustomMenu.TYPE.click, "小刀工具", "tools", null) 
			        .addSubButton(CustomMenu.TYPE.click, "材质查询", "material", null) 
			        .addSubButton(CustomMenu.TYPE.click, "钢材查询", "steel", null) 
			        .addSubButton(CustomMenu.TYPE.click, "螺纹查询","luowen", null);
			this.controller.getCustomMenuService().updateMenu(button);
			
	    	tr.ToUserName = event.FromUserName;
	    	tr.Content =  "欢迎关注刀师傅！\n\n刀师傅是刀具行业的新兵，希望借助互联网和移动互联网的力量，帮助大家高效快捷的解决问题。\n\n如果你有行业的困惑，如果你有改良的创意，如果你有棘手的问题，都欢迎和我们交流。让我们一起，每天进步一点！";
		}
		if(StringUtil.isEqual(event.ToUserName, "gh_b8c3f27889ae"))
		{
			button.addButton(CustomMenu.TYPE.view, "进入微名片", null, "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxb5f0887883f34822&redirect_uri=http://www.daoshifu.com/wxindex&response_type=code&scope=snsapi_base&state=s1#wechat_redirect");

			button.addButton(CustomMenu.TYPE.click, "刀具供应商", "provider", null)
					.addSubButton(CustomMenu.TYPE.view, "搜索供应商", null, "http://www.daoshifu.com/wxsearch");
			button.addButton(CustomMenu.TYPE.click, "问题反馈", "feedback", null);
			this.controller.getCustomMenuService().updateMenu(button);
			
	    	tr.ToUserName = event.FromUserName;
			tr.Content =  "欢迎关注麦辛刀具名片!\n\n麦辛刀具名片由刀师傅网IT团队开发，是唯一专注于刀具行业信息传播、技术沟通的微名片工具。不仅可以在这里搜索到您需要的品牌代理商信息，更能介绍和推广自己的品牌及特点。\n\n您可以从下方菜单中查看自己的微名片，并发给同行的朋友们，从而提升人脉和全国排名。而完善代理品牌和特色信息后，更将使有需要的用户/经销商找到您。让我们紧紧相拥，度过这个刀具界的寒冬！";
		}
		return tr;
    }
	
	@Override
	public AbstractResponse onClickEvent(ClickEventRequest click) throws WeixinException {
		TextResponse tr = new TextResponse();
    	tr.ToUserName = click.ToUserName;
    	if(StringUtil.isEqual(click.EventKey, "card"))
        {
    		//NewsResponse news = new NewsResponse();
    		//news.ToUserName = click.ToUserName;
    		//news.addArticle("点击召唤微名片", "刀师傅为刀具界人士量身打造的微名片，帮助刀具企业与用户更好的连接。", "http://img1.178.com/mm/201205/131677208040/131677329126.jpg", "http://www.daoshifu.com/wxcard?openid="+ click.FromUserName);
        	//return news;
    		tr.Content =  "紧张制作中，敬请期待哟！";
        }
    	if(StringUtil.isEqual(click.EventKey, "latestarticle"))
        {
        	tr.Content =  "还不知道怎么弄，呜呜呜";
        }
    	if(StringUtil.isEqual(click.EventKey, "feedback"))
        {
        	tr.Content =  "意见反馈请直接点击左下角的键盘，然后在输入框里给我们留言。我们会尽快回复您。";
        }
    	if(StringUtil.isEqual(click.EventKey, "aboutus"))
        {
        	tr.Content =  "刀师傅是刀具行业的B2B平台，希望利用互联网和移动互联网，帮助行业中的每个人更高效快捷的连接到一起！";
        }
        if(StringUtil.isEqual(click.EventKey, "material"))
        {
        	tr.Content =  "输入一个刀具材质，将会返回该材质相关信息。\n请输入\"材质T9115\" 或 \"材质WKP35S\"或\"材质YBC251\" 试试吧! ";
        }
        if(StringUtil.isEqual(click.EventKey, "steel"))
        {
        	tr.Content =  "输入一个钢铁材质名称，将会返回信息和其他国家标准的名称。\n请输入\"钢材40#\"或\"钢材1.4837\"或\"钢材42CrMo\" 试试吧!";
        }
        if(StringUtil.isEqual(click.EventKey, "luowen"))
        {
        	tr.Content =  "螺纹查询的材料正在整理中，请耐心等待哟~";
        }
        if(StringUtil.isEqual(click.EventKey, "todayrequest"))
        {
        	ApplicationContext ac = new ClassPathXmlApplicationContext("spring-config.xml");
        	CommonService commonService = (CommonService) ac.getBean("commonService");
        	List<QuickrequestEntity> requests = commonService.getMainRequests();
        	tr.Content = "因为微信字数限制，只显示最新的四条需求\n\n";
        	for(int i = 0;i<requests.size();i++)
        	{
        		String type = requests.get(i).getType()==1?"方案":"找货";
        		String qq = "";
        		String phone = "";
        		if(StringUtil.isNotEmpty(requests.get(i).getQq()))
        		{
        			qq = "QQ:"+requests.get(i).getQq()+",";
        		}
        		if(StringUtil.isNotEmpty(requests.get(i).getPhone()))
        		{
        			phone = "电话:"+requests.get(i).getPhone();
        		}
        		tr.Content += type+","+ requests.get(i).getContent() + "," + requests.get(i).getArea() + "," + qq +phone+"\n\n";
        	}
        	
        	tr.Content += "更多内容请登录刀师傅查看www.daoshifu.com";
        }
        return tr;
    }

}
