package ru.rtln.productservice.service.impl;

import org.springframework.stereotype.Service;
import ru.rtln.common.security.model.AuthenticatedUserModel;
import ru.rtln.productservice.service.PermissionsService;

import static ru.rtln.productservice.util.Constants.ADMIN_MANAGEMENT_PERMISSION;
import static ru.rtln.productservice.util.Constants.PRODUCT_MANAGEMENT_PERMISSION;

@Service
public class PermissionsServiceImpl implements PermissionsService {

    @Override
    public boolean checkLibraryManagementPermission(AuthenticatedUserModel userModel) {
        return userModel.getPermissions().contains(PRODUCT_MANAGEMENT_PERMISSION) ||
                userModel.getPermissions().contains(ADMIN_MANAGEMENT_PERMISSION);
    }
}
