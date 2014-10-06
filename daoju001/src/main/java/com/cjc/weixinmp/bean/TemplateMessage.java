package com.cjc.weixinmp.bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 主动文本消息<br>
 * msgtype = "text"
 * @author jianqing.cai@qq.com, https://github.com/caijianqing/weixinmp4java/
 */
public class TemplateMessage extends AbstractMessage {

    private static final long serialVersionUID = 1L;

    public TemplateMessage() {
        msgtype = "template";
    }
    
    public String template_id;
    
    public String url;
    
    public String topcolor;
    
    public Map<String,TemplateItem> data;
    
    public TemplateItem first;
    
    public TemplateItem keyword1;
    
    public TemplateItem keyword2;
    
    public TemplateItem remark;
    
    public void addFirst(String value, String color)
    {
    	first = new TemplateItem();
    	first.value = value;
    	first.color = color;
    }
    
    public void addKeyword1(String value, String color)
    {
    	keyword1 = new TemplateItem();
    	keyword1.value = value;
    	keyword1.color = color;
    }
    
    public void addKeyword2(String value, String color)
    {
    	keyword2 = new TemplateItem();
    	keyword2.value = value;
    	keyword2.color = color;
    }
    
    public void addRemark(String value, String color)
    {
    	remark = new TemplateItem();
    	remark.value = value;
    	remark.color = color;
    }
    
    public void fillData()
    {
    	data = new HashMap<String,TemplateItem>();
    	data.put("first", first);
    	data.put("keyword1", keyword1);
    	data.put("keyword2", keyword2);
    	data.put("remark", remark);
    }
    
    @Override
    public String toString() {
        return "TemplateMessage [touser=" + touser + ", msgtype=" + msgtype + "]";
    }

    public static class TemplateItem implements Serializable {

        private static final long serialVersionUID = 1L;

        public String value;
        
        public String color;

        @Override
        public String toString() {
            return "TemplateItem [value=" + value + "color=" + color + "]";
        }

    }

}
