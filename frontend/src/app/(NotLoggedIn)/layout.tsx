import { Container, Box } from "@mui/material";

export default function LoginPage({ children }: { children: React.ReactNode }) {
    return (
        <html>
            <body>
                <Container maxWidth="xl">
                    <Box sx={{ my: "2em" }}>{children}</Box>
                </Container>
            </body>
        </html>
    );
}
