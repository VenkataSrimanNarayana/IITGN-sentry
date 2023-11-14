import Navbar from "@/components/Navbar";
import { Container } from "@mui/material";
import Provider from "@/components/Provider";
import "public/global.css";
import type { Metadata } from "next";
import { getServerSession } from "next-auth";
import { getSession } from "next-auth/react";

export const metadata: Metadata = {
    title: "Sentry",
};

export default async function Layout({
    children,
}: {
    children: React.ReactNode;
}) {
    // TODO : // conditinal rendering of links

    return (
        <html>
            <head>
                <link
                    rel="shortcut icon"
                    href="/logo.png"
                    type="image/x-icon"
                />
            </head>
            <body>
                <Provider>
                    <Navbar />
                    <Container maxWidth="xl" sx={{ marginTop: "5rem" }}>
                        {children}
                    </Container>
                </Provider>
            </body>
        </html>
    );
}
