interface PageAuthorityMap {
    [path: string]: string | null;
}
export const pageAuthorityMap: PageAuthorityMap = {
    "/": null,
    "/raise-request": "RAISE_PREQUEST_PRIVLEGE",
};
