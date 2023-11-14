"use client";

import UserDetails from "@/components/UserDetails";
import { CircularProgress, Container } from "@mui/material";
import { useSession } from "next-auth/react";
import { useRouter } from "next/navigation";

export default function UserDetailsPage(){

    const router = useRouter();
    const {data : session, status} = useSession();

    if(status === 'unauthenticated') router.push('/login');

    if(status === 'loading') {
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

      return <UserDetails></UserDetails>;
}