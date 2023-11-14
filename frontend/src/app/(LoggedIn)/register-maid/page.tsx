"use client";

import RegisterMaid from "@/components/MaidRegister";
import { CircularProgress, Container } from "@mui/material";
import { useSession } from "next-auth/react";
import { useRouter } from "next/navigation";

const RegisterForm = () => {
  const { data: session, status } = useSession();
  const router = useRouter();

  if (status === "unauthenticated") {
    router.push("/login");
  }

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
  }

  return <RegisterMaid></RegisterMaid>
};

export default RegisterForm;
