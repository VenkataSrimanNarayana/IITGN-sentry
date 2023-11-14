"use client";
import { ChangeEvent, SetStateAction, useState } from "react";
import { useRouter } from "next/navigation";
import TextField from "@mui/material/TextField";
import Button from "@mui/material/Button";
import Container from "@mui/material/Container";
import Box from "@mui/material/Box";
import { useSession } from "next-auth/react";

export default function ChangePassword() {
    const [oldPassword, setOldPassword] = useState("");
    const [newPassword, setNewPassword] = useState("");
    const [error, setError] = useState("");
    const { data: session } = useSession();
    const router = useRouter();

    const apiUrl = process.env.NEXT_PUBLIC_BACKEND_URL + "/api/users/password";

    const handleSubmit = async (event) => {
        event.preventDefault();

        if (!oldPassword || !newPassword) {
            setError("Please fill in both old and new passwords.");
            return;
        }

        try {
            const response = await fetch(apiUrl, {
                method: "PUT",
                headers: {
                    "Content-Type": "application/json",
                    Authorization: "Bearer " + session?.user.accessToken,
                },
                body: JSON.stringify({ oldPassword, newPassword }),
            });

            if (response.ok) {
                alert("Password changed successfully");
                setError("");
                setOldPassword("");
                setNewPassword("");
                router.push("/");
            } else {
                const data = await response.json();
                setError(data.message || "Failed to change password.");
            }
        } catch (error) {
            console.error("Error changing password:", error);
            setError("An unexpected error occurred.");
        }
    };

    return (
        <Container component="main" maxWidth="xs">
            <Box
                sx={{
                    marginTop: 8,
                    display: "flex",
                    flexDirection: "column",
                    alignItems: "center",
                }}
            >
                <form onSubmit={handleSubmit} noValidate>
                    <TextField
                        margin="normal"
                        required
                        fullWidth
                        id="oldPassword"
                        label="Old Password"
                        type="password"
                        value={oldPassword}
                        onChange={(e) => {
                            setOldPassword(e.target.value);
                        }}
                    />
                    <TextField
                        margin="normal"
                        required
                        fullWidth
                        id="newPassword"
                        label="New Password"
                        type="password"
                        value={newPassword}
                        onChange={(e) => {
                            setNewPassword(e.target.value);
                        }}
                    />
                    {error && (
                        <p
                            style={{
                                color: "red",
                                textAlign: "center",
                                margin: "10px 0",
                            }}
                        >
                            {error}
                        </p>
                    )}
                    <Button
                        type="submit"
                        fullWidth
                        variant="contained"
                        sx={{ mt: 3, mb: 2 }}
                    >
                        Change Password
                    </Button>
                </form>
            </Box>
        </Container>
    );
}
