"use client";
import { useSession } from "next-auth/react";
import { CircularProgress, Container } from "@mui/material";
import { redirect } from "next/navigation";
import ChangePasswordScreen from "src/components/ChangePasswordScreen";

export default function Settings() {
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
      <ChangePasswordScreen />
    </>
  );
}
