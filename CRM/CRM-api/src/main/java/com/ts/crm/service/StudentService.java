package com.ts.crm.service;

import org.springframework.cloud.openfeign.FeignClient;

import com.ts.api.base.BaseService;
import com.ts.crm.entity.Student;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author dy
 * @since 2020-09-22
 */
@FeignClient(value = "CRM-PROVIDER")
public interface StudentService extends BaseService<Student> {
	
}
