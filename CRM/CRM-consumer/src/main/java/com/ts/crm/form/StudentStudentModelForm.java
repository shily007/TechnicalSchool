package com.ts.crm.form;

import org.springframework.stereotype.Component;

import com.ts.api.form.ModelForm;

/**
 * 
 * @author dy
 *
 */
@Component("StudentStudentModelForm")
public class StudentStudentModelForm extends ModelForm {

	{
		setModel_form_fields(
				new String[] { "id", "username", "state", "grade_id", "major_id", "clazz_id", "source_id" });
	}

}
