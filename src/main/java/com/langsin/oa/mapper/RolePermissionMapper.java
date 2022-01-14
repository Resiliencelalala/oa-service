package com.langsin.oa.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.langsin.oa.dto.RolePermissionDto;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RolePermissionMapper extends BaseMapper<RolePermissionDto> {

    List<Long> selectPermissionIdByUserId(Long userId);

    int insertList(List<RolePermissionDto> list);

    int delByRoleId(Long roleId);
}
