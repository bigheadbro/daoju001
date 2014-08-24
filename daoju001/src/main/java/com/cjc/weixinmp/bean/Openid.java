package com.cjc.weixinmp.bean;

import java.io.Serializable;

/**
 * 用户信息
 * @author jianqing.cai@qq.com, https://github.com/caijianqing/weixinmp4java/
 */
public class Openid implements Serializable {

    private static final long serialVersionUID = 1L;

    public String access_token;

    public String expires_in;

    public String refresh_token;

    public String openid;

    public String scope;

    @Override
    public String toString() {
        return "Openid [access_token=" + access_token + ", expires_in=" + expires_in + ", refresh_token=" + refresh_token + ", openid=" + openid + ", scope=" + scope + "]";
    }

}
