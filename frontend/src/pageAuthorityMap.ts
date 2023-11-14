interface PageAuthorityMap {
    [path: string]: string | null;
}
export const pageAuthorityMap: PageAuthorityMap = {
    "/": null,
    "/raise-request": "RAISE_PREQUEST_PRIVILEGE",
    "/super/requests": "READ_PREQUEST_PRIVILEGE",
    "/super/logs": "READ_LOG_PRIVILEGE",
    "/user/logs": "READ_USER_LOG_PRIVILEGE",
    "/user/requests": "READ_USER_PREQUEST_PRIVILEGE",
    "/register-user": "ACCOUNT_SIGNUP_PRIVILEGE",
    "/role/create": "ROLES_PRIVILEGE",
    "/role/edit": "ROLES_PRIVILEGE",
    "/super/manual-log-register": "LOG_PRIVILEGE",
    "/register-maid": "REGISTER_MAID_PRIVILEGE",
    "/super/maid-logs": "READ_LOG_MAID_PRIVILEGE",
    "/user/maid-logs": "READ_LOG_USER_MAID_PRIVILEGE",
    "/super/users-details": "READ_ACCOUNT_PRIVILEGE",
    "/super/maid-details": "READ_MAID_DETAILS_PRIVILEGE",
    "/user/maid-details": "READ_MAID_DETAILS_USER_PRIVILEGE",
    "/scanner": "LOG_PRIVILEGE",
    "/change-password": "CHANGE_USER_PASSWORD_PRIVILEGE",
    "/profile": "READ_USER_ACCOUNT_PRIVILEGE",
    "/profile/edit": "UPDATE_USER_USER_PRIVILEGE",
    "/user-details": "READ_ACCOUNT_PRIVILEGE",
    "/user-details/edit": "UPDATE_USER_USER_PRIVILEGE",
};
