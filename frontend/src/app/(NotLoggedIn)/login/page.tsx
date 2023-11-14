"use client";
import * as React from "react";
import { useState } from "react";
import Button from "@mui/material/Button";
import Alert from "@mui/material/Alert";
import TextField from "@mui/material/TextField";
import Link from "@mui/material/Link";
import Grid from "@mui/material/Grid";
import Box from "@mui/material/Box";
import Typography from "@mui/material/Typography";
import Container from "@mui/material/Container";
import { signIn } from "next-auth/react";
import { useRouter } from "next/navigation";
import { redirect } from "next/dist/server/api-utils";

export default function Login() {
    const [alertData, setAlertData] = useState({
        message: "",
        error: false,
    });
    const router = useRouter();
    const [userId, setUserId] = useState("");
    const [password, setPassword] = useState("");

    const handleSubmit = async () => {
        const response = await signIn("credentials", {
            userId: userId,
            password: password,
            redirect: false,
        });
        if (response && !response.ok) {
            setAlertData({
                message: "Invalid Credentials!",
                error: true,
            });
        }
        router.push("/");
    };

    return (
        <>
            <Container component="main" maxWidth="xs">
                <Box
                    sx={{
                        marginTop: 8,
                        display: "flex",
                        flexDirection: "column",
                        alignItems: "center",
                    }}
                >
                    <Typography component="h1" variant="h5">
                        Sign in
                    </Typography>

                    <Box sx={{ mt: 1 }}>
                        <TextField
                            margin="normal"
                            required
                            fullWidth
                            id="id"
                            type="text"
                            label="User ID"
                            name="userId"
                            value={userId}
                            onChange={(e) => setUserId(e.target.value)}
                            autoFocus
                        />
                        <TextField
                            margin="normal"
                            required
                            fullWidth
                            name="password"
                            label="Password"
                            type="password"
                            id="password"
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                            autoComplete="current-password"
                        />

                        <Alert
                            onClose={() => {
                                setAlertData({ message: "", error: false });
                            }}
                            severity="error"
                            sx={{
                                display: alertData.message ? "flex" : "none",
                                mt: 1,
                                mb: 1,
                            }}
                        >
                            {alertData.message}
                        </Alert>
                        <Button
                            onClick={handleSubmit}
                            fullWidth
                            variant="contained"
                            sx={{ mt: 1, mb: 2 }}
                        >
                            Sign In
                        </Button>
                        <Grid container>
                            <Grid item xs></Grid>
                            <Grid item>
                                <Link href="#" variant="body2">
                                    {"Don't have an account? Register"}
                                </Link>
                            </Grid>
                        </Grid>
                    </Box>
                </Box>
            </Container>
        </>
    );
}
