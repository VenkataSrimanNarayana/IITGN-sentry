"use client";

import React from "react";
import AppBar from "@mui/material/AppBar";
import SwipeableDrawer from "@mui/material/SwipeableDrawer";
import NavigationList from "@/components/NavigationList";
import {
    Container,
    Typography,
    Toolbar,
    IconButton,
    Button,
} from "@mui/material";
import MenuIcon from "@mui/icons-material/Menu";

export default function Navbar({ links }: { links: LinkName[] }) {
    const [navigationMenuState, setNavigationMenuState] = React.useState(false);

    // Function to set the value of the navigation menu state
    const toggleDrawer =
        (open: boolean) => (event: React.KeyboardEvent | React.MouseEvent) => {
            if (
                event.type === "keydown" &&
                ((event as React.KeyboardEvent).key === "Tab" ||
                    (event as React.KeyboardEvent).key === "Shift")
            ) {
                return;
            }

            setNavigationMenuState(open);
        };

    return (
        <AppBar position="fixed">
            <Container maxWidth="xl">
                <Toolbar disableGutters>
                    <IconButton
                        size="large"
                        edge="start"
                        color="inherit"
                        aria-label="menu"
                        sx={{ mr: 2 }}
                        onClick={toggleDrawer(true)}
                    >
                        <MenuIcon />
                    </IconButton>

                    <SwipeableDrawer
                        anchor="left"
                        open={navigationMenuState}
                        onClose={toggleDrawer(false)}
                        onOpen={toggleDrawer(true)}
                    >
                        {NavigationList(links, toggleDrawer)}
                    </SwipeableDrawer>

                    <Typography
                        variant="h6"
                        component="div"
                        sx={{ flexGrow: 1 }}
                    >
                        IITGN LOGO
                    </Typography>
                    <Button color="inherit">Login</Button>
                </Toolbar>
            </Container>
        </AppBar>
    );
}
