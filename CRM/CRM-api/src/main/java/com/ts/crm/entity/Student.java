package com.ts.crm.entity;

import java.io.Serializable;

import org.hibernate.validator.constraints.Length;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ts.api.base.BaseEntity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author dy
 * @since 2020-09-22
 */
@TableName("s_student")
@Data
@EqualsAndHashCode(callSuper = false)
public class Student extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Length(max = 18,min = 6,message = "用户名长度为6-18位！")
	@ApiModelProperty(value = "用户名")
	private String username;

	@ApiModelProperty(value = "密码")
	private String pwd;

	@ApiModelProperty(value = "状态")
	private State state;

	@ApiModelProperty(value = "身份证号码")
	private String idno;

	@ApiModelProperty(value = "姓名")
	private String name;

	@ApiModelProperty(value = "手机号码")
	private String phone;

	@ApiModelProperty(value = "电子邮箱")
	private String email;

	@ApiModelProperty(value = "班级")
	private Long clazz_id;

	@ApiModelProperty(value = "年级")
	private Long grade_id;

	@ApiModelProperty(value = "专业")
	private Long major_id;

	@ApiModelProperty(value = "生源地")
	private Long source_id;

	public enum State {
		NORMAL(0,"正常"), // 正常
		LOCKED(1,"冻结"), // 冻结
		WRITTEN_OFF(2,"注销");// 注销
		
		private int code;
		private String name;
		
		private State(int code, String name) {
			this.code = code;
			this.name = name;
		}

		public int getCode() {
			return code;
		}

		public String getName() {
			return name;
		}
		
	}

}
