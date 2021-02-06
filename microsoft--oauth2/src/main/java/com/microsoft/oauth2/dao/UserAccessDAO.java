package com.microsoft.oauth2.dao;



import com.microsoft.oauth2.entity.UserAccess;

import java.util.List;
import java.util.UUID;


public interface UserAccessDAO {


    List<UserAccess> securitySelectByUserId(UUID userId);

    List<UserAccess> securitySelectByUserIdWithFakeDoc(UUID userId);

    int securityInsertRecord(UserAccess record);

    int securityUpdateRecord(UserAccess record);

    int deleteByUserId(UUID userId);

}
