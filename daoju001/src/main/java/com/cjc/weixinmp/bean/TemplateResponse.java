package com.cjc.weixinmp.bean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 文本消息的响应
 * @author jianqing.cai@qq.com, https://github.com/caijianqing/weixinmp4java/
 */
@XmlRootElement(name = "xml")
@XmlAccessorType(XmlAccessType.FIELD)
public class TemplateResponse extends AbstractResponse {

    private static final long serialVersionUID = 1L;

    public TemplateResponse() {
        MsgType = "event";
    }

    /** 回复的消息内容（换行：在content中能够换行，微信客户端就支持换行显示） （长度不超过2048字节） */
    public String Content;

    public String templateid;
    
    public String url;
    
    public String first;
    
    public String keyword1;
    
    public String keyword2;
    
    public String remark;
    
    @Override
    public String toString() {
        return "TextResponse [ToUserName=" + ToUserName + ", FromUserName=" + FromUserName + ", CreateTime=" + CreateTime + ", MsgType=" + MsgType
                + ", Content=" + Content + "]";
    }

}
