"use client";
import { useSession } from "next-auth/react";
import { redirect } from "next/navigation";
import { CircularProgress } from "@mui/material";
import { Container } from "@mui/material";
import PersonalDetailsWidget from "@/components/PersonalDetailsWidget";
export default function Home() {
    const { status } = useSession();
    if (status === "loading") {
        return (
            <>
                <Container
                    sx={{
                        display: "flex",
                        justifyContent: "center",
                        alignItems: "center",
                        height: "100vh", // Make the container take the full viewport height
                    }}
                >
                    <CircularProgress />
                </Container>
            </>
        );
    } else if (status === "unauthenticated") {
        redirect("/login");
    }
    return <PersonalDetailsWidget />;
}
