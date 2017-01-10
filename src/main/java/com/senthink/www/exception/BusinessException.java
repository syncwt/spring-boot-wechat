package com.senthink.www.exception;

import java.util.HashMap;
import java.util.Map;

/**
 * @Project : Columbia
 * @Package Name  : com.senthink.columbia
 * @Class Name    : BusinessException
 * @Description :
 * @Author : lingcy@lierda.com
 * @Creation Date : 2016年12月19日 下午5:36
 * @ModificationHistory Who        When          What
 * --------   ----------    -----------------------------------
 */
public class BusinessException extends Exception {

    private static final long serialVersionUID = -238091758285157331L;

    private String errCode;
    private String errMsg;

    public BusinessException() {
        super();
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public BusinessException(String _errCode) {
        this.errCode = _errCode;
        this.errMsg = getMsgByCode(_errCode);
    }

    public BusinessException(Throwable cause) {
        super(cause);
    }

    public BusinessException(String errCode, String errMsg) {
        super(errCode + ":" + errMsg);
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    public String getErrCode() {
        return this.errCode;
    }

    public String getErrMsg() {
        return this.errMsg;
    }

    public static final String CODE_SUCCESS = "8001";
    public static final String MSG_SUCCESS = "操作成功";

    public static final String CODE_ERROR = "4001";
    public final String MSG_ERROR = "操作失败";

    public static final String CODE_PARAM_NULL = "4002";
    public static final String MSG_PARAM_NULL = "请求参数有误";

    public static final String CODE_SEARCH_PK_ERROR = "4003";
    public static final String MSG_SEARCH_PK_ERROR = "主键查询失败";

    public static final String CODE_SEARCH_PARAM_ERROR = "4004";
    public static final String MSG_SEARCH_PARAM_ERROR = "条件查询失败";

    public static final String CODE_COUNT_PARAM_ERROR = "4005";
    public static final String MSG_COUNT_PARAM_ERROR = "统计查询失败";

    public static final String CODE_SAVE_ERROR = "4006";
    public static final String MSG_SAVE_ERROR = "保存失败";

    public static final String CODE_UPDATE_ERROR = "4007";
    public static final String MSG_UPDATE_ERROR = "更新失败";

    public static final String CODE_DELETE_ERROR = "4008";
    public static final String MSG_DELETE_ERROR = "删除失败";

    public static final String CODE_DELETE_BATCH_ERROR = "4009";
    public static final String MSG_DELETE_BATCH_ERROR = "批量删除失败";

    public static final String CODE_LOGIN_OUT_OF_TIME = "4012";
    public final String MSG_LOGIN_OUT_OF_TIME = "登录过期";

    public static final String CODE_BARCODE_ISEXIST = "4013";
    public static final String MSG_BARCODE_ISEXIST = "条码已经存在";

    public static final String CODE_ASSERT_ISEXIST = "4014";
    public static final String MSG_ASSERT_ISEXIST = "设备号码已经存在";

    public static final String CODE_NO_FILE = "4015";
    public static final String MSG_NO_FILE = "文件不存在";

    public static final String CODE_DATA_NULL = "6025";
    public static final String MSG_DATA_NULL = "数据为空";

    public static final String CODE_COMMAND_NOT_CONFIG = "6019";
    public static final String MSG_COMMAND_NOT_CONFIG = "命令解析未配置";

    public static final String CODE_COMMAND_NOT_MODULE_ID = "6020";
    public static final String MSG_COMMAND_NOT_MODULE_ID = "命令解析配置未匹配模块ID";

    public static final String CODE_USER_NOT_FOUND = "6021";
    public static final String MSG_USER_NOT_FOUND = "用户不存在";

    public static final String CODE_MATCH_NOT_FOUND = "6031";
    public static final String MSG_MATCH_NOT_FOUND = "设备类型找不到匹配的通讯协议";

    public static final String CODE_DEV_HTTP_UNDEFINE ="6041";
    public static final String MSG_DEV_HTTP_UNDEFINE ="该设备协议未定义";

    public static final String CODE_DVCTYPE_NOT_FOUND = "6015";
    public static final String MSG_DVCTYPE_NOT_FOUND = "设备编号找不到匹配的设备类型";

    public static final String CODE_HTTP_NO_CONFIG = "6018";
    public final String MSG_HTTP_NO_CONFIG = "协议中未配置命令";


    public String getMsgByCode(String code) {
        Map<String, String> map = new HashMap<String, String>();
        map.put(CODE_SUCCESS, MSG_SUCCESS);
        map.put(CODE_ERROR, MSG_ERROR);
        map.put(CODE_PARAM_NULL, MSG_PARAM_NULL);

        map.put(CODE_SEARCH_PK_ERROR, MSG_SEARCH_PK_ERROR);
        map.put(CODE_SEARCH_PARAM_ERROR, MSG_SEARCH_PARAM_ERROR);
        map.put(CODE_COUNT_PARAM_ERROR, MSG_COUNT_PARAM_ERROR);
        map.put(CODE_SAVE_ERROR, MSG_SAVE_ERROR);
        map.put(CODE_UPDATE_ERROR, MSG_UPDATE_ERROR);
        map.put(CODE_DELETE_ERROR, MSG_DELETE_ERROR);
        map.put(CODE_DELETE_BATCH_ERROR, MSG_DELETE_BATCH_ERROR);
        map.put(CODE_LOGIN_OUT_OF_TIME, MSG_LOGIN_OUT_OF_TIME);

        map.put(CODE_BARCODE_ISEXIST, MSG_BARCODE_ISEXIST);
        map.put(CODE_ASSERT_ISEXIST, MSG_ASSERT_ISEXIST);
        map.put(CODE_NO_FILE, MSG_NO_FILE);
        map.put(CODE_DATA_NULL, MSG_DATA_NULL);
        map.put(CODE_BARCODE_ISEXIST, MSG_BARCODE_ISEXIST);
        map.put(CODE_ASSERT_ISEXIST, MSG_ASSERT_ISEXIST);
        map.put(CODE_NO_FILE, MSG_NO_FILE);
        map.put(CODE_DATA_NULL, MSG_DATA_NULL);
        map.put(CODE_COMMAND_NOT_CONFIG, MSG_COMMAND_NOT_CONFIG);
        map.put(CODE_COMMAND_NOT_MODULE_ID, MSG_COMMAND_NOT_MODULE_ID);
        map.put(CODE_USER_NOT_FOUND, MSG_USER_NOT_FOUND);
        map.put(CODE_MATCH_NOT_FOUND, MSG_MATCH_NOT_FOUND);
        map.put(CODE_DEV_HTTP_UNDEFINE, MSG_DEV_HTTP_UNDEFINE);
        map.put(CODE_DVCTYPE_NOT_FOUND, MSG_DVCTYPE_NOT_FOUND);
        map.put(CODE_HTTP_NO_CONFIG, MSG_HTTP_NO_CONFIG);

        return map.get(code);
    }

    public String toString() {
        return "错误码：" + errCode + "，错误信息：" + errMsg;
    }
}
