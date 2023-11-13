import { withAuth } from "next-auth/middleware";
import { NextResponse } from "next/server";
import { pageAuthorityMap } from "./pageAuthorityMap";

export default withAuth(
    function middleware(req) {
        const page: string = req.nextUrl.pathname;
        console.log("page", page);
        const userAuthorities = (
            req.nextauth.token as { authorities: string[] } | undefined
        )?.authorities as string[];
        const pageAuthority = pageAuthorityMap[page];
        if (
            pageAuthority &&
            userAuthorities &&
            !userAuthorities.includes(pageAuthority)
        ) {
            return new NextResponse("You are not allowed to access this page");
        }
    },
    {
        callbacks: {
            authorized: ({ token }) => {
                return !!(token && token.accessToken);
            },
        },
    }
);

export const config = {
    matcher: Object.keys(pageAuthorityMap),
};
