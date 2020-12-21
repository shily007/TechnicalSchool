package com.ts.crm.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ts.crm.entity.Student;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author dy
 * @since 2020-09-22
 */
@Mapper
public interface StudentMapper extends BaseMapper<Student> {

}
