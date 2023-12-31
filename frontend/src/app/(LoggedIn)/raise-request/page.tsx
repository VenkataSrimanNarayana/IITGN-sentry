"use client";
import React, { useState } from "react";
import { useSession } from "next-auth/react";
import { Typography, CircularProgress, Container } from "@mui/material";
import { useRouter } from "next/navigation";

import Tabs from "@mui/material/Tabs";
import Tab from "@mui/material/Tab";
import Box from "@mui/material/Box";
import UserRequestGenerate from "@/components/GenerateRequests/UserRequestGenerate";
import VehicleRequestGenerate from "@/components/GenerateRequests/VehicleRequestGenerate";
import VisitorRequestGenerate from "@/components/GenerateRequests/VisitorRequestGenerate";

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
  const { status } = useSession();
  const router = useRouter();
  const [value, setValue] = React.useState(0);

  if (status === "unauthenticated") {
    router.push("/login");
  }

  if (status === "loading") {
    return (
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
          <Tab label="Self" {...a11yProps(0)} />
          <Tab label="Vehicle" {...a11yProps(1)} />
          <Tab label="Visitor" {...a11yProps(2)} />
        </Tabs>
      </Box>
      <CustomTabPanel value={value} index={0}>
        <UserRequestGenerate />
      </CustomTabPanel>
      <CustomTabPanel value={value} index={1}>
        <VehicleRequestGenerate />
      </CustomTabPanel>
      <CustomTabPanel value={value} index={2}>
        <VisitorRequestGenerate />
      </CustomTabPanel>
    </Box>
  );
}
