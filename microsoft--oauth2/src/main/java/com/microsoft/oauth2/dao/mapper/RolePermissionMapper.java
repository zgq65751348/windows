package com.microsoft.oauth2.dao.mapper;


import com.microsoft.oauth2.entity.RolePermission;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.UUID;


public interface RolePermissionMapper {

    int deleteByPrimaryKey(Long id);

    Long insert(RolePermission record);

    List<RolePermission> selectByRoleId(@Param("roleId") UUID roleId);

    int updateByPrimaryKey(RolePermission record);

    void deleteByRoleIdAndPermissionId(@Param("roleId") UUID roleId, @Param("permissionId") UUID permissionId);

}
