"use client";
import { redirect } from "next/navigation";
import { useSession } from "next-auth/react";

export default function Home() {
    const { data: session } = useSession();
    console.log(session);
    return <div>Success</div>;
}
