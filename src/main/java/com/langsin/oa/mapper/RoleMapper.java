package com.langsin.oa.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.langsin.oa.dto.RoleDto;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleMapper extends BaseMapper<RoleDto> {

    List<RoleDto> selectByUserId(Long userId);
}
