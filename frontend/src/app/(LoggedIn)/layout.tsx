import Navbar from "@/components/Navbar";
import { Container } from "@mui/material";
import Provider from "@/components/Provider";
import "public/global.css";

export default function Layout({ children }: { children: React.ReactNode }) {
    // Code to generate links
    const links = [
        {
            name: "home",
            link: "/",
        },

        {
            name : "generate request",
            link : "/generate-request"
        },
        {
            name : "all requests",
            link : "/all-requests"
        },

        {
            name : "all users",
            link : "/all-logs"
        }
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
