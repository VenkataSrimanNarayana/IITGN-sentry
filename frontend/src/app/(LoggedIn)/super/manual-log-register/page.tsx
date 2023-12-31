"use client";
import React, { useState } from "react";
import { useSession } from "next-auth/react";
import {
    Typography,
    CircularProgress,
    Container,
    Box,
    Tabs,
    Tab,
} from "@mui/material";
import { useRouter } from "next/navigation";

import UserLog from "@/components/Logs/ManualLogAdd/UserLog";

import VehicleLog from "@/components/Logs/ManualLogAdd/VehicleLog";
import VisitorLog from "@/components/Logs/ManualLogAdd/VisitorLog";

function CustomTabPanel(props: any) {
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

export default function ManualLogAdd() {
    const { status } = useSession();
    const router = useRouter();
    const [value, setValue] = useState(0);

    if (status === "unauthenticated") {
        router.push("/login");
    }

    if (status == "loading") {
        return (
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
        );
    }

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
                    <Tab label="user manual" {...a11yProps(0)} />
                    <Tab label="vehicle manual" {...a11yProps(1)} />
                    <Tab label="visitor manual" {...a11yProps(2)} />
                </Tabs>
            </Box>
            <CustomTabPanel value={value} index={0}>
                <UserLog></UserLog>
            </CustomTabPanel>
            <CustomTabPanel value={value} index={1}>
                <VehicleLog></VehicleLog>
            </CustomTabPanel>
            <CustomTabPanel value={value} index={2}>
                <VisitorLog></VisitorLog>
            </CustomTabPanel>
        </Box>
    );
}
