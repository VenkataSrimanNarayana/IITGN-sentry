import Navbar from "@/components/Navbar";
import { Container } from "@mui/material";
import Provider from "@/components/Provider";
import "public/global.css";

export default function Layout({ children }: { children: React.ReactNode }) {
    // TODO : // conditinal rendering of links

    const links = [
        {
            name: "Home",
            link: "/",
        },

        {
            name: "Raise Request",
            link: "/raise-request",
        },

        {
            name: "Super Requests",
            link: "/super/all-requests",
        },
        {
            name: "Super Logs",
            link: "/super/all-logs",
        },
        {
            name: "User Logs",
            link: "/user/my-logs",
        },
        {
            name: "User Requests",
            link: "/user/my-requests",
        },
        {
            name: "Register User",
            link: "/register-user",
        },
        {
            name: "Add new Role",
            link: "/super/add-role",
        },
        {
            name: "Edit Role",
            link: "/super/edit-role",
        },
        {
            name: "Manual Entry",
            link: "/super/manual-log-register",
        },
    ];

    return (
        <html>
            <body>
                <Provider>
                    <Navbar links={links} />
                    <Container maxWidth="xl" sx={{ marginTop: "5rem" }}>
                        {children}
                    </Container>
                </Provider>
            </body>
        </html>
    );
}
