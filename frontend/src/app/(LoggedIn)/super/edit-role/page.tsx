"use client";
import { useSession } from "next-auth/react";
import { Container, CircularProgress } from "@mui/material";
import { useRouter } from "next/navigation";
import UpdateRolePrivilege from "@/components/UpdateRolePrivilege";

export default function EditRolePage() {

    const {status} = useSession();
    const router = useRouter();

    if(status == "unauthenticated"){
        router.push("/login");
    }

    if(status == "loading"){
        return (
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
        );
    }


    return (
        <div>
            <UpdateRolePrivilege />
        </div>
    )
}