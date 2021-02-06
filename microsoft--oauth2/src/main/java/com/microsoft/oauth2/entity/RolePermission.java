package com.microsoft.oauth2.entity;

import lombok.Data;

import java.util.UUID;


@Data
public class RolePermission {

    private Long id;

    private UUID roleId;

    private UUID permissionId;
}
