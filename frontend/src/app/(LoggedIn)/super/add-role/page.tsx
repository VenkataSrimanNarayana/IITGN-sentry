"use client";

import AddRole from "@/components/AddRole";

import { CircularProgress, Container } from "@mui/material";
import { useSession } from "next-auth/react";
import router, { useRouter } from "next/navigation";

export default function AddRoleForm() {
  const router = useRouter();
  const { data: session, status } = useSession();

  if (status === "unauthenticated") {
    router.push("/login");
  }

  if (status == "loading") {
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
  }

  return <AddRole></AddRole>;
}
