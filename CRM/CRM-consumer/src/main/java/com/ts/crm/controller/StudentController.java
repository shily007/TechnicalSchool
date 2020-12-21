package com.ts.crm.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ts.api.base.BaseController;
import com.ts.crm.entity.Student;
import com.ts.crm.service.StudentService;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author dy
 * @since 2020-09-22
 */
@RestController
@RequestMapping("/crm/student")
public class StudentController extends BaseController<StudentService,Student> {

}

