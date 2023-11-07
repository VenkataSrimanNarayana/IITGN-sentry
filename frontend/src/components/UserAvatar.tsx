"use client";
import { Avatar } from "@mui/material";
import { useEffect, useState } from "react";
import { useSession } from "next-auth/react";

function stringToColor(string: string) {
    let hash = 0;
    let i;

    /* eslint-disable no-bitwise */
    for (i = 0; i < string.length; i += 1) {
        hash = string.charCodeAt(i) + ((hash << 5) - hash);
    }

    let color = "#";

    for (i = 0; i < 3; i += 1) {
        const value = (hash >> (i * 8)) & 0xff;
        color += `00${value.toString(16)}`.slice(-2);
    }
    /* eslint-enable no-bitwise */

    return color;
}

function stringAvatar(name: string) {
    let shortenedName = name.split(" ")[0][0];
    if (name.split(" ").length > 1) {
        shortenedName += name.split(" ")[1][0];
    }
    return {
        sx: {
            bgcolor: stringToColor(name),
        },
        children: shortenedName,
    };
}

export default function UserAvatar(props: any) {
    const [userName, setUserName] = useState("");
    const { data: session } = useSession();
    useEffect(() => {
        if (session?.user?.accessToken) {
            fetch(process.env.NEXT_PUBLIC_BACKEND_URL + "/api/users", {
                method: "GET",
                headers: {
                    "Content-Type": "application/json",
                    Authorization: "Bearer " + session?.user?.accessToken,
                },
            })
                .then((res) => res.json())
                .then((data) => {
                    setUserName(data.firstName);
                });
        }
    });
    return <Avatar {...props} {...stringAvatar(userName)} />;
}
