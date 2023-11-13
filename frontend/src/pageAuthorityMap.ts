interface PageAuthorityMap {
    [path: string]: string | null;
}
export const pageAuthorityMap: PageAuthorityMap = {
    "/": null,
    "/raise-request": "RAISE_PREQUEST_PRIVILEGE",
    "/user/logs": "READ_USER_LOG_PRIVILEGE",
    "/user/requests": "READ_USER_PREQUEST_PRIVILEGE",
    "/role/add": "ROLES_PRIVILEGE",
    "/role/edit": "ROLES_PRIVILEGE",
    "/super/requests": "READ_PREQUEST_PRIVILEGE",
    "/super/logs": "READ_LOG_PRIVILEGE",
};
