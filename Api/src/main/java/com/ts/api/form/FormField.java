package com.ts.api.form;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 
 * @author dy
 *
 */
@Data
@Accessors(chain = true)
public class FormField implements Serializable {
	private static final long serialVersionUID = 1L;

	// 字段名称
	private String name;
	// 字段
	private String label;
	// 字段值
	private Object value;
	// 是否可编辑
	private String disabled;
	// 字段类型
	private Class<?> type;
	// 是否可以为空 默认可以为空
	private Boolean notBlank = false;
	// 是否可以为空 提示信息
	private String notBlank_msg;
	// 最大长度
	private Integer max;
	// 最小长度
	private Integer min;
	// 长度提示信息
	private String length_msg;
	// 是否是复选
	private Boolean isChoices = false;
	// 选择项内容
	private List<FormField> choices;
	// 获取集合的uri
	private String uri;
}
