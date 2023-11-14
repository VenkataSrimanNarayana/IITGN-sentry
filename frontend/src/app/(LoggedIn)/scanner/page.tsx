"use client";
import React, { useState } from "react";
import QrReader from "react-qr-scanner";
import { useSession } from "next-auth/react";
import {
    Typography,
    Modal,
    Box,
    Button,
    Checkbox,
    FormControl,
} from "@mui/material";

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

const handleMaidSubmit = (
    requestId: string,
    isEntry: boolean,
    accessToken: string
) => {
    console.log(
        process.env.NEXT_PUBLIC_BACKEND_URL +
            `/api/maid-log/log/${requestId}/${isEntry ? "entry" : "exit"}`
    );
    console.log({
        method: "GET",
        headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${accessToken}`,
        },
    });
    fetch(process.env.NEXT_PUBLIC_BACKEND_URL + `/api/maid-log/log`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${accessToken}`,
        },
        body: JSON.stringify({
            id: requestId,
            entry: isEntry,
        }),
    })
        .then((response) => {
            console.log(response);
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
    const [open, setOpen] = useState(false); // For the modal
    const [selectedRequest, setSelectedRequest] = useState("");
    const [isEntry, setIsEntry] = useState(true); // For the modal
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
                    request.requestType != "other" &&
                    request.requestType != "maid"
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
                setTimeout(() => {
                    setIsScannerEnabled(true);
                }, 5000);
            } else if (request.requestType == "vehicle") {
                postData(
                    request.id,
                    process.env.NEXT_PUBLIC_BACKEND_URL +
                        "/api/user-vehicle-log/",
                    session?.user.accessToken as string
                );
                setTimeout(() => {
                    setIsScannerEnabled(true);
                }, 5000);
            } else if (request.requestType == "other") {
                postData(
                    request.id,
                    process.env.NEXT_PUBLIC_BACKEND_URL +
                        "/api/user-visitor-log/",
                    session?.user.accessToken as string
                );
                setTimeout(() => {
                    setIsScannerEnabled(true);
                }, 5000);
            } else if (request.requestType == "maid") {
                setSelectedRequest(request.id);
                setOpen(true);
            }
        }
    };

    const handleError = (error) => {
        console.error(error);
    };

    return (
        <div>
            <Modal
                open={open}
                onClose={() => {
                    setOpen(false);
                }}
            >
                <Box
                    sx={{
                        position: "absolute" as "absolute",
                        top: "50%",
                        left: "50%",
                        transform: "translate(-50%, -50%)",
                        minWidth: "10rem",
                        bgcolor: "background.paper",
                        border: "2px solid #000",
                        boxShadow: 24,
                        p: 4,
                    }}
                >
                    <FormControl
                        sx={{ marginBottom: "1rem", display: "block" }}
                    >
                        <Checkbox
                            checked={isEntry}
                            onChange={(event) => {
                                setIsEntry(event.target.checked);
                            }}
                        />
                        <Typography variant="h6">For Entry</Typography>
                    </FormControl>
                    <Button
                        variant="contained"
                        onClick={() => {
                            handleMaidSubmit(
                                selectedRequest,
                                isEntry,
                                session?.user.accessToken as string
                            );
                            setOpen(false);
                            setTimeout(() => {
                                setIsScannerEnabled(true);
                            }, 5000);
                        }}
                    >
                        Submit
                    </Button>
                </Box>
            </Modal>
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
                    Scanner on Pause
                </Typography>
            )}
        </div>
    );
};

export default QRScanner;
