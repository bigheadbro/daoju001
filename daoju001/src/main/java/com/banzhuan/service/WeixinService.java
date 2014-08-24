package com.banzhuan.service;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.cjc.weixinmp.AbstractUserOperate;
import com.cjc.weixinmp.AbstractWeixinmpController;
import com.cjc.weixinmp.UserOperate;

public class WeixinService extends AbstractWeixinmpController {
	private Logger logger = LoggerFactory.getLogger(WeixinService.class);
	final protected Map<String, AbstractUserOperate> userOperateMap = new HashMap<String, AbstractUserOperate>();

	private static WeixinService wxService;  

    public static WeixinService getInstance() {  
        if (wxService == null) {  
        	wxService = new WeixinService();  
        }  
        return wxService;  
    }  
	private WeixinService() {
        // 必须调用super进行初始化
        super();
        // 如果多公众号，请多开几个实例，编写不同的配置文件并且调用这个方法
        // super("weixinmp2.properties"); 
    }
	@Override
	public AbstractUserOperate getUserOperate(String FromUserNameOpenID) {
		synchronized (userOperateMap) {
			AbstractUserOperate operate = userOperateMap
					.get(FromUserNameOpenID);
			if (operate == null) {
				operate = new UserOperate(FromUserNameOpenID);
				userOperateMap.put(FromUserNameOpenID, operate);
			}
			return operate;
		}
	}

	
}
