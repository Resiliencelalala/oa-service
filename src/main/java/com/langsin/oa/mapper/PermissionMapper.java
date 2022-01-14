package com.langsin.oa.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.langsin.oa.dto.PermissionDto;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermissionMapper  extends BaseMapper<PermissionDto> {

    List<PermissionDto> selectPermissionListByRoleId(Long roleId);

    List<PermissionDto> selectPermsCodeByUserId(@Param(value = "userId") Long userId);

    List<PermissionDto> selectPermsButtonByUserId(@Param(value = "userId") Long userId);

}
