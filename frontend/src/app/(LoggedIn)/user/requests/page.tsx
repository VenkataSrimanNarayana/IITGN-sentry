"use client";
import React, { useState, useEffect } from "react";
import { useSession } from "next-auth/react";
import { Typography } from "@mui/material";
import { useRouter } from "next/navigation";

import Tabs from "@mui/material/Tabs";
import Tab from "@mui/material/Tab";
import Box from "@mui/material/Box";
import { CircularProgress, Container } from "@mui/material";
import Requests from "@/components/Requests/Requests";
import VehicleRequests from "@/components/Requests/VehicleRequests";
import VisitorRequests from "@/components/Requests/VisitorRequests";

interface TabPanelProps {
    children?: React.ReactNode;
    index: number;
    value: number;
}

function CustomTabPanel(props: TabPanelProps) {
    const { children, value, index, ...other } = props;

    return (
        <div
            role="tabpanel"
            hidden={value !== index}
            id={`simple-tabpanel-${index}`}
            aria-labelledby={`simple-tab-${index}`}
            {...other}
        >
            {value === index && (
                <Box sx={{ p: 3 }}>
                    <Typography>{children}</Typography>
                </Box>
            )}
        </div>
    );
}

export default function GetAllPendingRequests() {
    const { data: session, status } = useSession();
    const router = useRouter();
    const [value, setValue] = React.useState(0);

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

    const allowDelete: boolean = session?.user?.details.authorities.includes(
        "DELETE_USER_PREQUEST_PRIVILEGE"
    ) as boolean;

    function a11yProps(index: number) {
        return {
            id: `simple-tab-${index}`,
            "aria-controls": `simple-tabpanel-${index}`,
        };
    }

    const handleChange = (event: React.SyntheticEvent, newValue: number) => {
        setValue(newValue);
    };

    return (
        <Box sx={{ width: "100%" }}>
            <Box sx={{ borderBottom: 1, borderColor: "divider" }}>
                <Tabs
                    value={value}
                    onChange={handleChange}
                    aria-label="basic tabs example"
                >
                    <Tab label="user requests" {...a11yProps(0)} />
                    <Tab label="vehicle requests" {...a11yProps(1)} />
                    <Tab label="visitor requests" {...a11yProps(2)} />
                </Tabs>
            </Box>
            <CustomTabPanel value={value} index={0}>
                <Requests
                    allowAccept={false}
                    allowDelete={allowDelete}
                    requestType="self"
                />
            </CustomTabPanel>
            <CustomTabPanel value={value} index={1}>
                <VehicleRequests
                    allowAccept={false}
                    allowDelete={allowDelete}
                    requestType="self"
                />
            </CustomTabPanel>
            <CustomTabPanel value={value} index={2}>
                <VisitorRequests
                    allowAccept={false}
                    allowDelete={allowDelete}
                    requestType="self"
                />
            </CustomTabPanel>
        </Box>
    );
}
