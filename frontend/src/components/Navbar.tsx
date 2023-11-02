"use client";

import React, { useState } from "react";
import AppBar from "@mui/material/AppBar";
import SwipeableDrawer from "@mui/material/SwipeableDrawer";
import NavigationList from "@/components/NavigationList";
import UserAvatar from "@/components/UserAvatar";
import LogoutIcon from "@mui/icons-material/Logout";
import { Menu, MenuItem } from "@mui/material";
import { signOut } from "next-auth/react";
import {
    Container,
    Typography,
    Toolbar,
    IconButton,
    Button,
    Link,
} from "@mui/material";
import MenuIcon from "@mui/icons-material/Menu";

export default function Navbar({ links }: { links: LinkName[] }) {
    const [navigationMenuState, setNavigationMenuState] = useState(false);
    const [anchorEl, setAnchorEl] = useState(null);

    const handleAvatarClick = (event: any) => {
        setAnchorEl(event.currentTarget);
    };

    const handleMenuClose = () => {
        setAnchorEl(null);
    };

    const handleLogout = () => {
        // Implement your logout logic here
        // For example, clear the user's session or access token
        // and navigate them to the login page.
        // You may use a library like react-router-dom for navigation.

        // After the user is logged out, close the menu
        signOut();
    };

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
                    <UserAvatar alt="User Avatar" onClick={handleAvatarClick} />
                    <Menu
                        anchorEl={anchorEl}
                        open={Boolean(anchorEl)}
                        onClose={handleMenuClose}
                    >
                        <MenuItem onClick={handleLogout}>
                            <IconButton color="inherit">
                                <LogoutIcon />
                            </IconButton>
                            Logout
                        </MenuItem>
                    </Menu>
                </Toolbar>
            </Container>
        </AppBar>
    );
}
