package com.cjc.weixinmp;

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
		button.addButton(CustomMenu.TYPE.view, "优酷", null, "http://www.youku.com");
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
        return tr;
    }

}
