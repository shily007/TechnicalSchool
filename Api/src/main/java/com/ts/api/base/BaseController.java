package com.ts.api.base;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ts.api.form.FormField;
import com.ts.api.form.ModelFormConfigure;
import com.ts.api.util.JsonResult;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 
 * 
 * @param <M>
 * @param <T>
 * @author dy 2020年11月5日
 */
@Transactional(rollbackFor = Exception.class)
public class BaseController<M extends BaseService<T>, T> {

	@Autowired
	protected M service;
	@Autowired
	protected ModelFormConfigure modelFormConfigure;
	
	@ApiOperation(value = "新增获取空表单")
	@GetMapping
	public JsonResult<Object> add(T t) {
		List<FormField> formFields = modelFormConfigure.getFormField(t);
		return new JsonResult<>(formFields);
	}

	@ApiOperation(value = "增")
	@PostMapping
	public JsonResult<Object> add(@Validated T entity, BindingResult errors) {
		JsonResult<Object> bindingResult = checkBindingResult(errors);
		if (bindingResult != null) {
			return bindingResult;
		}
		boolean result = service.insert(entity);
		List<FormField> formFields = modelFormConfigure.getFormField(entity);
		if (result) {
			return new JsonResult<>(formFields);
		}
		return new JsonResult<>(ErrorMsg.FAIL_SAVE);
	}

	/**
	 * 检查绑定信息 checkBindingResult
	 * 
	 * @param errors
	 * @return
	 * @author dy 2020年11月10日
	 */
	public JsonResult<Object> checkBindingResult(BindingResult errors) {
		if (errors.hasErrors()) {
			Set<String> msgs = new HashSet<>();
			for (ObjectError error : errors.getAllErrors()) {
				msgs.add(error.getDefaultMessage());
			}
			if (msgs.size() > 0) {
				return new JsonResult<>(ErrorMsg.ERROR_FORMAT, msgs);
			}
		}
		return null;
	}

	@ApiOperation(value = "改")
	@PutMapping
	public JsonResult<Object> update(@Validated T entity, BindingResult errors) {
		if (errors.hasErrors()) {
			Set<String> msgs = new HashSet<>();
			for (ObjectError error : errors.getAllErrors()) {
				msgs.add(error.getDefaultMessage());
			}
			if (msgs.size() > 0) {
				return new JsonResult<>(ErrorMsg.ERROR_FORMAT, msgs);
			}
		}
		boolean result = service.updateById(entity);
		if (result) {
			return new JsonResult<>();
		}
		return new JsonResult<>(ErrorMsg.FAIL_UPDATE);
	}

	@ApiOperation(value = "查")
	@GetMapping("/{id}")
	public JsonResult<T> getById(@PathVariable Serializable id) {
		return new JsonResult<>(service.getById(id));
	}

	@ApiOperation(value = "分页")
	@GetMapping("page")
	public JsonResult<IPage<T>> page(
			@ApiParam(value = "当前页，默认1") @RequestParam(required = false, defaultValue = "1") Integer current,
			@ApiParam(value = "每页记录条数，默认10") @RequestParam(required = false, defaultValue = "10") Integer size,
			T entity) {
		QueryWrapper<T> queryWrapper = getQueryWrapper(entity);
		if (queryWrapper != null) {
			return new JsonResult<>(service.page(new Page<>(current, size), queryWrapper));
		}
		return new JsonResult<>(service.page(new Page<>(current, size)));
	}

	/**
	 * @Description 封装查询条件
	 * @param t
	 * @return
	 * @author dy
	 * @date 2019年11月20日
	 */
	public QueryWrapper<T> getQueryWrapper(T t) {
		QueryWrapper<T> queryWrapper = new QueryWrapper<>();
		Class<?> clazz = t.getClass();
		List<Field> fieldList = new ArrayList<>();
		// 获取所有父类字段
		while (clazz != null) {
			fieldList.addAll(new ArrayList<>(Arrays.asList(clazz.getDeclaredFields())));
			clazz = clazz.getSuperclass();
		}
		boolean flag = false;
		for (int i = 0; i < fieldList.size(); i++) {
			Field field = fieldList.get(i);
			if (field.getName().equals("serialVersionUID")
//					|| field.getName().equals("cr")
//					|| field.getName().equals("ur") || field.getName().equals("time")
//					|| field.getName().equals("utime")
			)
				continue;
			field.setAccessible(true);
			try {
				Object object = field.get(t);
				if (object != null && !object.equals("")) {
					if (field.getType().getSimpleName().equals("String"))
						queryWrapper.like(field.getName(), object);
					else
						queryWrapper.eq(field.getName(), object);
					flag = true;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (flag)
			return queryWrapper;
		return queryWrapper;
	}
	
	/**
	 * 获取表明
	 * getTableName
	 * @param type
	 * @return
	 * @author dy
	 * 2020年11月20日
	 */
	protected String getTableName(Class<?> type) {
		String tName = null;
		TableName tableName = type.getAnnotation(TableName.class);
		if(tableName!=null) {
			tName = tableName.value();
		}else {
			tName = type.getSimpleName();
		}
		return tName;
	}

}
