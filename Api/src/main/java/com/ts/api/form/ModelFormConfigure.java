package com.ts.api.form;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ts.api.base.BaseEntity;
import com.ts.api.util.WebUtil;
import com.ts.auth.interceptor.AuthUser.Role;
import com.ts.auth.util.JwtUtils;

import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * @author dy
 *
 */
@Component
public class ModelFormConfigure {

	@Autowired
	private JwtUtils jwtUtils;
	@Autowired
	private Map<String, ModelForm> modelForms;

	public <T> List<FormField> getFormField(T t) {
		Role role = jwtUtils.getRole();
		ModelForm modelForm = null;
		String simpleName = t.getClass().getSimpleName();
		if (role == Role.ADMIN) {
			modelForm = modelForms.get(simpleName + "AdminModelForm");
		} else if (role == Role.TEACHER) {
			modelForm = modelForms.get(simpleName + "TeacherModelForm");
		} else if (role == Role.STUDENT) {
			modelForm = modelForms.get(simpleName + "StudentModelForm");
		}
		String[] model_form_fields = modelForm.getModel_form_fields();
		List<FormField> formFields = new ArrayList<>();
		for (int i = 0; i < model_form_fields.length; i++) {
			String field_name = model_form_fields[i];
			Field field = getFieldByName(t.getClass(), field_name);
			if (field != null) {
				FormField ff = new FormField();
				ff.setName(field_name);
				ApiModelProperty apiModelProperty = field.getAnnotation(ApiModelProperty.class);
				if (apiModelProperty != null) {
					ff.setLabel(apiModelProperty.value());
				} else {
					ff.setLabel(field_name.toUpperCase());
				}
				NotBlank notBlank = field.getAnnotation(NotBlank.class);
				if (notBlank != null) {
					ff.setNotBlank_msg(notBlank.message());
					ff.setNotBlank(true);
				}
				Length length = field.getAnnotation(Length.class);
				if (length != null) {
					ff.setMax(length.max());
					ff.setMin(length.min());
					ff.setLength_msg(length.message());
				}
				Class<?> type = field.getType();
				if (type.isEnum()) {
					List<FormField> choices = new ArrayList<>();
					Object[] enumConstants = type.getEnumConstants();
					try {
						Method getCode = type.getMethod("getCode");
						Method getName = type.getMethod("getName");
						for (Object enumConstant : enumConstants) {
							FormField choice = new FormField();
							choice.setLabel(getName.invoke(enumConstant) + "");
							choice.setValue(getCode.invoke(enumConstant));
							choices.add(choice);
						}
						ff.setChoices(choices);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				if (field_name.endsWith("_id")) {
					String uri = WebUtil.getUri();
					ff.setUri("/" + uri.split("/")[1] + "/" + field_name.replace("_id", "") + "/list");
				}
				formFields.add(ff);
			}
		}
		return formFields;
	}

	/**
	 * 
	 * @param type 类
	 * @param name 属性名称
	 * @return
	 */
	private Field getFieldByName(Class<? extends Object> type, String name) {
		try {
			return type.getDeclaredField(name);
		} catch (NoSuchFieldException e) {
			if (!type.isInstance(BaseEntity.class)) {
				return getFieldByName(type.getSuperclass(), name);
			}
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		return null;
	}

}
