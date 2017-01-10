package com.senthink.www.response;


import java.util.HashMap;
import java.util.Map;

public class Message {

    private String code;
    private String msg;
    private Object data;

    public Message() {
    }

	public static final String CODE_SUCCESS = "8001";
	public static final String MSG_SUCCESS = "操作成功";

	public static final String CODE_ERROR = "4001";
	public static final String MSG_ERROR = "操作失败";

	public static Map<String, String> map = new HashMap<String, String>();

	static {
		map.put(CODE_SUCCESS, MSG_SUCCESS);
		map.put(CODE_ERROR, MSG_ERROR);
	}
    public static final Message ERROR   = new Message(Message.CODE_ERROR);

    public static final Message SUCCESS = new Message(Message.CODE_SUCCESS);

    public Message(String code) {
        this.code = code;
		this.msg = getMsgByCode(code);
    }

    public Message(Object data) {
        this.data = data;

    }

    public Message(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Message(String code, Object data) {
        this.code = code;
        this.msg = getMsgByCode(code);
        this.data = data;
    }

    public Message(String code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }


    public String getMsgByCode(String code) {
        return map.get(code);
    }
}
