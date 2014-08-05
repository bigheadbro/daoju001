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
import com.cjc.weixinmp.bean.SubscribeEventRequest;
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
    	tr.ToUserName = text.ToUserName;
    	tr.Content =  Util.queryMaterial(text.Content);
		return tr;
    }
	
	@Override
	public AbstractResponse onSubscribeEvent(SubscribeEventRequest event) throws WeixinException {
		CustomButton button = new CustomButton();
		button.addButton(CustomMenu.TYPE.click, "小刀消息", "message", null)
				.addSubButton(CustomMenu.TYPE.click, "最近文章", "latestarticle", null) 
		        .addSubButton(CustomMenu.TYPE.click, "关于我们", "aboutus", null) ;
		button.addButton(CustomMenu.TYPE.click, "小刀发布", "request", null)
				.addSubButton(CustomMenu.TYPE.view, "我要发布", null, "http://115017.ichengyun.net/wxnewrequest")
				.addSubButton(CustomMenu.TYPE.click, "今日发布", "todayrequest", null);
		button.addButton(CustomMenu.TYPE.click, "小刀工具", "tools", null) 
		        .addSubButton(CustomMenu.TYPE.click, "材料查询", "material", null) 
		        .addSubButton(CustomMenu.TYPE.click, "钢材查询", "steel", null) 
		        .addSubButton(CustomMenu.TYPE.click, "螺纹查询","luowen", null);
		this.controller.getCustomMenuService().updateMenu(button);
		TextResponse tr = new TextResponse();
    	tr.ToUserName = event.FromUserName;
    	tr.Content =  "欢迎关注刀师傅！\n\n刀师傅是刀具行业的新兵，希望借助互联网和移动互联网的力量，帮助大家高效快捷的解决问题。\n\n如果你有行业的困惑，如果你有改良的创意，如果你有棘手的问题，都欢迎和我们交流。让我们一起，每天进步一点！";
		return tr;
    }
	
	@Override
	public AbstractResponse onClickEvent(ClickEventRequest click) throws WeixinException {
		TextResponse tr = new TextResponse();
    	tr.ToUserName = click.ToUserName;
    	if(StringUtil.isEqual(click.EventKey, "latestarticle"))
        {
        	tr.Content =  "还不知道怎么弄，呜呜呜";
        }
    	if(StringUtil.isEqual(click.EventKey, "aboutus"))
        {
        	tr.Content =  "刀师傅是刀具行业的B2B平台，希望利用互联网和移动互联网，帮助行业中的每个人更高效快捷的连接到一起！";
        }
        if(StringUtil.isEqual(click.EventKey, "material"))
        {
        	tr.Content =  "输入一个刀具材料名称，会返回其他品牌的相关信息。\n如查询T9115，可以得到泰珂洛CVD材质, 加工范围M10 ";
        }
        if(StringUtil.isEqual(click.EventKey, "steel"))
        {
        	tr.Content =  "输入一个钢铁材质名称，会返回其他国家标准的名称。\n如查询40CrNi，可以得到合金结构钢, 日本JiS SNC236、美国AiSi / SAE 3135、德国W.nr 1.571、德国DiN 36NiCr6、";
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
