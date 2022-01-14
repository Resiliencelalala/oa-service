package com.langsin.oa.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.langsin.oa.dto.UserBackendDto;
import com.langsin.oa.dto.UserBackendSingleDto;
import com.langsin.oa.dto.UserBackendStatusDto;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserBackendMapper extends BaseMapper<UserBackendDto> {

    IPage<UserBackendDto> selectUserAndPage(Page page, @Param("realName") String realName);

    List<UserBackendSingleDto> selectUserByPermission(@Param("permissionCode") String permissionCode,@Param("roleCode") String roleCode);

    int updateByUserId(@Param("query") UserBackendDto userBackendDto);

    int updateUserByStatus(@Param("query") UserBackendStatusDto userBackendStatusDto);

    int updateUserByPassword(@Param("id") Long id, @Param("password") String password, @Param("salt") String salt);
}
