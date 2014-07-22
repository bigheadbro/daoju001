package com.cjc.weixinmp;

import com.cjc.weixinmp.bean.AbstractResponse;
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
    	tr.Content = "成功調用！";
        controller.getMessageService().sendText(text.ToUserName, "成功調用！");
		return tr;
    }

}
