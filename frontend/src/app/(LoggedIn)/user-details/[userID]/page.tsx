"use client";
import { useSession } from "next-auth/react";
import { CircularProgress, Container } from "@mui/material";
import { redirect } from "next/navigation";
import ProfileDisplay from "@/components/ProfileDisplay";

export default function UserDetails({
    params,
}: {
    params: { userID: string };
}) {
    const userID = params.userID;
    const { status } = useSession();
    if (status === "loading") {
        return (
            <>
                <Container
                    sx={{
                        display: "flex",
                        justifyContent: "center",
                        alignItems: "center",
                        height: "100vh",
                    }}
                >
                    <CircularProgress />
                </Container>
            </>
        );
    } else if (status === "unauthenticated") {
        redirect("/login");
    }

    return (
        <>
            <ProfileDisplay userID={userID} />
        </>
    );
}
