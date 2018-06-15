package com.mrbt.lingmoney.wechat.model;

/**
 * 微信分享实体类
 * @author Administrator
 *
 */
public class TicketJson {  
    private int errcode;  
    private String errmsg;  
    private String ticket;  
    private String expires_in;  
    public int getErrcode() {  
        return errcode;  
    }  
    public void setErrcode(int errcode) {  
        this.errcode = errcode;  
    }  
    public String getErrmsg() {  
        return errmsg;  
    }  
    public void setErrmsg(String errmsg) {  
        this.errmsg = errmsg;  
    }  
    public String getTicket() {  
        return ticket;  
    }  
    public void setTicket(String ticket) {  
        this.ticket = ticket;  
    }  
    public String getExpires_in() {  
        return expires_in;  
    }  
    public void setExpires_in(String expires_in) {  
        this.expires_in = expires_in;  
    }  
}  