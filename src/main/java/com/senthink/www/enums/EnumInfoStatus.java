/**
 * 
 */
package com.senthink.www.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName           : EnumInfoStatus
 * @Description         :
 * @Author              : xujialin
 * @CreationDate        : 2016年12月26日下午4:42:27
 */
public enum EnumInfoStatus {
	unauthorize(0, "未授权"), unbind(1, "未绑定"), bind_no_all(2, "已绑定未完善"), bind_all(3, "已绑定完善");
	private Integer code;
	private String name;

	private EnumInfoStatus(Integer code, String name) {
		this.code = code;
		this.name = name;

	}

	public static Map<Integer, EnumInfoStatus> pool = new HashMap<Integer, EnumInfoStatus>();

	static {
		for (EnumInfoStatus status : EnumInfoStatus.values()) {
			pool.put(status.getCode(), status);
		}
	}

	public String getNameByCode(String code) {
		return pool.get("code").name;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
