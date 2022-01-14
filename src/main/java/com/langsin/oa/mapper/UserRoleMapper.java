package com.langsin.oa.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.langsin.oa.dto.UserRoleDto;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRoleMapper extends BaseMapper<UserRoleDto> {

    int delByUserId(Long userId);

    int insertList(@Param("userId") Long userId,@Param("list") List<Long> list);
}
