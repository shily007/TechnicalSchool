package com.ts.api.form;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 
 * @author dy
 *
 */
@Data
@Accessors(chain = true)
public class ModelForm {

	// 展示字段
	private String[] list_display;
	// 列表过滤字段
	private String[] list_filters;
	// 搜索字段
	private String[] search_fields;
	// 默认分页大小
	private Integer list_per_page = 20;
	// 排序字段
	private String[][] ordering;
	//
	private String[] filter_horizontal;
	// 只读字段
	private String[] readonly_fields;
	// 操作
	private String[] actions;
	// 表是否只读
	private Boolean readonly_table = false;
	// form表单字段
	private String[] model_form_fields;

}
