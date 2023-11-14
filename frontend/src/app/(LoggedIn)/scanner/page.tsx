"use client";
import React, { useState } from "react";
import QrReader from "react-qr-scanner";
import { useSession } from "next-auth/react";
import { Typography } from "@mui/material";

const postData = (requestId: string, link: string, accessToken: string) => {
    console.log("sending request");
    console.log(accessToken);
    fetch(link + requestId, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${accessToken}`,
        },
        body: JSON.stringify({ requestId }),
    })
        .then((response) => {
            if (!response.ok) {
                throw new Error("Invalid Request");
            }
            alert("Successfully logged the request");
        })
        .catch((error) => {
            alert(error);
        });
};

const QRScanner = () => {
    const [isScannerEnabled, setIsScannerEnabled] = useState(true);
    const { data: session, status } = useSession({ required: true });

    const handleScan = (data) => {
        if (data) {
            setIsScannerEnabled(false);
            // Stop scanning
            const request = JSON.parse(data.text);
            // Check if it is a valid request
            try {
                if (
                    request.requestType != "self" &&
                    request.requestType != "vehicle" &&
                    request.requestType != "other"
                ) {
                    throw new Error("Invalid request type");
                }
            } catch (error) {
                alert("Invalid request");
                setTimeout(() => {
                    setIsScannerEnabled(true);
                }, 5000);
                return;
            }
            if (request.requestType == "self") {
                postData(
                    request.id as string,
                    (process.env.NEXT_PUBLIC_BACKEND_URL +
                        "/api/user-log/") as string,
                    session?.user.accessToken as string
                );
            } else if (request.requestType == "vehicle") {
                postData(
                    request.id,
                    process.env.NEXT_PUBLIC_BACKEND_URL +
                        "/api/user-vehicle-log/",
                    session?.user.accessToken as string
                );
            } else if (request.requestType == "other") {
                postData(
                    request.id,
                    process.env.NEXT_PUBLIC_BACKEND_URL +
                        "/api/user-visitor-log/",
                    session?.user.accessToken as string
                );
            }
            setTimeout(() => {
                setIsScannerEnabled(true);
            }, 5000);
        }
    };

    const handleError = (error) => {
        console.error(error);
    };

    return (
        <div>
            {isScannerEnabled ? (
                <QrReader
                    delay={300}
                    onError={handleError}
                    onScan={handleScan}
                    style={{ width: "100%" }}
                />
            ) : (
                // Loading screen
                <Typography variant="h4" align="center">
                    Sacanner on Pause
                </Typography>
            )}
        </div>
    );
};

export default QRScanner;
