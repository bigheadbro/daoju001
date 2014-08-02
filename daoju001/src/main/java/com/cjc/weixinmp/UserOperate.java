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
		button.addButton(CustomMenu.TYPE.click, "小刀消息", "message", null);
		button.addButton(CustomMenu.TYPE.click, "小刀发布", "request", null)
				.addSubButton(CustomMenu.TYPE.view, "我要发布", null, "http://115017.ichengyun.net/wxnewrequest")
				.addSubButton(CustomMenu.TYPE.click, "今日发布", "todayrequest", null);
		button.addButton(CustomMenu.TYPE.click, "刀工具", "tools", null) //
		        .addSubButton(CustomMenu.TYPE.click, "材料查询", "material", null) //
		        .addSubButton(CustomMenu.TYPE.click, "按钮二", "anniu2", null) //
		        .addSubButton(CustomMenu.TYPE.view, "视频", null, "http://v.qq.com");
		this.controller.getCustomMenuService().updateMenu(button);
		TextResponse tr = new TextResponse();
    	tr.ToUserName = event.FromUserName;
    	tr.Content =  "欢迎关注刀师傅";
		return tr;
    }
	
	@Override
	public AbstractResponse onClickEvent(ClickEventRequest click) throws WeixinException {
		TextResponse tr = new TextResponse();
    	tr.ToUserName = click.ToUserName;
        if(StringUtil.isEqual(click.EventKey, "material"))
        {
        	tr.Content =  "输入一个刀具材料名称，会返回其他品牌的相关信息。如T9115";
        }
        if(StringUtil.isEqual(click.EventKey, "todayrequest"))
        {
        	ApplicationContext ac = new ClassPathXmlApplicationContext("spring-config.xml");
        	CommonService commonService = (CommonService) ac.getBean("commonService");
        	List<QuickrequestEntity> requests = commonService.getMainRequests();
        	tr.Content = "";
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
