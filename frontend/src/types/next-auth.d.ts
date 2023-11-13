import NextAuth from "next-auth";

declare module "next-auth" {
    interface Session {
        user: {
            /** The user's postal address. */
            userID: string;
            accessToken: string;
            details: Details;
        };
    }
}
