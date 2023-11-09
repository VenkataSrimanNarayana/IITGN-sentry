"use client";
import { useSession } from "next-auth/react";
import { CircularProgress } from "@mui/material";
import { Container } from "@mui/material";
import {useRouter} from "next/navigation";
import PersonalDetailsWidget from "@/components/PersonalDetailsWidget";
export default function Home() {
    const router = useRouter();
    const { status } = useSession();
    console.log(status);
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
        router.push("/login");
    }
    return <PersonalDetailsWidget />;
}
