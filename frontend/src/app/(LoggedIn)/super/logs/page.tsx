"use client";
import React, { useState } from "react";
import { useSession } from "next-auth/react";
import { Typography, CircularProgress, Container } from "@mui/material";
import { useRouter } from "next/navigation";

import Tabs from "@mui/material/Tabs";
import Tab from "@mui/material/Tab";
import Box from "@mui/material/Box";
import Logs from "@/components/Logs/Logs";
import VisiterLogs from "@/components/Logs/VisitorLogs";
import VehicleLogs from "@/components/Logs/VehicleLogs";

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
    const [requests, setRequests] = useState([]);
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
                        height: "100vh",
                    }}
                >
                    <CircularProgress />
                </Container>
            </>
        );
    }

    // Decide on delete button
    const allowDelete: boolean = session?.user?.details.authorities.includes(
        "DELETE_LOG_PRIVILEGE"
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
                    <Tab label="user logs" {...a11yProps(0)} />
                    <Tab label="vehicle logs" {...a11yProps(1)} />
                    <Tab label="visitor logs" {...a11yProps(2)} />
                </Tabs>
            </Box>
            <CustomTabPanel value={value} index={0}>
                <Logs allowDelete={allowDelete} logType="all" />
            </CustomTabPanel>
            <CustomTabPanel value={value} index={1}>
                <VehicleLogs allowDelete={allowDelete} logType="all" />
            </CustomTabPanel>
            <CustomTabPanel value={value} index={2}>
                <VisiterLogs allowDelete={allowDelete} logType="all" />
            </CustomTabPanel>
        </Box>
    );
}
