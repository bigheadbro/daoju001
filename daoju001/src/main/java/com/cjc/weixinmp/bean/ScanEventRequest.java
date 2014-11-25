package com.cjc.weixinmp.bean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 扫描带参数二维码事件 的请求<br>
 * MsgId 字段无效
 * @author jianqing.cai@qq.com, https://github.com/caijianqing/weixinmp4java/
 */
@XmlRootElement(name = "xml")
@XmlAccessorType(XmlAccessType.FIELD)
public class ScanEventRequest extends AbstractEventRequest {

    private static final long serialVersionUID = 1L;

    /** 事件KEY值，qrscene_为前缀，后面为二维码的参数值 */
    public String EventKey;

    /** 二维码的ticket，可用来换取二维码图片 */
    public String Ticket;

    @Override
    public String toString() {
        return "ScanEventRequest [Event=" + Event + ", ToUserName=" + ToUserName + ", FromUserName=" + FromUserName + ", CreateTime=" + CreateTime
                + ", MsgType=" + MsgType + ", MsgId=" + MsgId + ", EventKey=" + EventKey + ", Ticket=" + Ticket + "]";
    }

}
