package com.banzhuan.service;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cjc.weixinmp.AbstractUserOperate;
import com.cjc.weixinmp.AbstractWeixinmpController;
import com.cjc.weixinmp.UserOperate;

public class WeixinService extends AbstractWeixinmpController {
	private Logger logger = LoggerFactory.getLogger(WeixinService.class);
	final protected Map<String, AbstractUserOperate> userOperateMap = new HashMap<String, AbstractUserOperate>();

	public WeixinService() {
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
			logger.error("2323\n");
			if (operate == null) {
				logger.error("bbbbb\n");
				operate = new UserOperate(FromUserNameOpenID);
				logger.error("cccc\n");
				userOperateMap.put(FromUserNameOpenID, operate);
			}
			return operate;
		}
	}

	
}
