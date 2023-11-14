"use client";
import React from "react";
import { useSession } from "next-auth/react";
import { useRouter } from "next/navigation";
import { CircularProgress, Container } from "@mui/material";
import MaidLogs from "@/components/MaidLogs";

export default function GetAllPendingRequests() {
  const { data: session, status } = useSession();
  const router = useRouter();
  const [value, setValue] = React.useState(0);

  if (status === "unauthenticated") {
    router.push("/login");
  }

  if (status === "loading") {
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
  return <MaidLogs logType="all" isDelete={true} />;
}
