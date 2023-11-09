import NextAuth from "next-auth";
import CredentialsProvider from "next-auth/providers/credentials";

// Function to parse the jwt token to get the Subject(userId) from it
function parseJwt(token: string) {
    return JSON.parse(Buffer.from(token.split(".")[1], "base64").toString());
}

const handler = NextAuth({
    providers: [
        CredentialsProvider({
            // The name to display on the sign in form (e.g. "Sign in with...")
            name: "Custom JWT",
            // `credentials` is used to generate a form on the sign in page.
            // You can specify which fields should be submitted, by adding keys to the `credentials` object.
            // e.g. domain, username, password, 2FA token, etc.
            // You can pass any HTML attribute to the <input> tag through the object.
            credentials: {
                userId: { label: "User Id", type: "text" },
                password: { label: "Password", type: "password" },
            },
            async authorize(credentials, req) {
                const backend_login_url =
                    process.env.NEXT_PUBLIC_BACKEND_URL + "/api/auth/login";
                const res = await fetch(backend_login_url, {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json",
                    },
                    body: JSON.stringify({
                        userId: credentials?.userId,
                        password: credentials?.password,
                    }),
                });
                const user = await res.json();
                if (!res.ok) {
                    throw new Error(user.message);
                }
                if (res.ok && user) {
                    console.log(user);
                    return user;
                } else {
                    return null;
                }
            },
        }),
    ],
    callbacks: {
        async jwt({ token, user, account }) {
            if (account && user) {
                return {
                    ...token,
                    accessToken: user.accessToken,
                };
            }

            return token;
        },

        async session({ session, token }) {
            if (session.user) {
                session.user = {
                    ...session.user,
                    accessToken: token.accessToken,
                    userID: parseJwt(token.accessToken as string).sub,
                } as { [key: string]: unknown };
            }

            return session;
        },
    },
    secret: process.env.JWT_SECRET,
    debug: process.env.NODE_ENV === "development",
});
export { handler as GET, handler as POST };
