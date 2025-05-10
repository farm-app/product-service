package ru.rtln.productservice.service;

import ru.rtln.common.security.model.AuthenticatedUserModel;

public interface PermissionsService {

    boolean checkLibraryManagementPermission(AuthenticatedUserModel userModel);
}
