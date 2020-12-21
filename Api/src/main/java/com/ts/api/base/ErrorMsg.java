package com.ts.api.base;

/**
 * 
 * 
 * @author dy
 * 2020年9月22日
 */
public enum ErrorMsg {
	
	OK(0,"操作成功！"),
	FAIL(1000,"请求错误："),
	FAIL_SERVICE_CALL(1001, "服务偷懒了，请稍后再试！"),
	FAIL_INSERT(1002,"新增失败！"),
	FAIL_SAVE(1003,"保存失败！"),
	FAIL_DELETE(1004,"删除失败！"),
	FAIL_UPDATE(1005,"修改失败！"),
	ERROR_FORMAT(1006,"格式错误！"),
	ERROR_PASSWORD(1006,"原密码错误！"),
	ERROR_PARAM(1007,"传参有误！"),
	;

	private int code;
	private String msg;

	ErrorMsg(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public int getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}

}
