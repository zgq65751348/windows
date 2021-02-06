package com.microsoft.oauth2.dao;



import com.microsoft.oauth2.dto.UserRoleDTO;
import com.microsoft.oauth2.entity.UserRole;

import java.util.List;
import java.util.UUID;


public interface UserRoleDAO {

    Long insertUtRole(UserRole userRole);

    List<UserRole> selectByUserId(UUID userId);

    int updateById(UserRole record);


    int deleteByUserId(UUID userId);

    List<UserRoleDTO> selectUserRoleList(UUID userId);
}
