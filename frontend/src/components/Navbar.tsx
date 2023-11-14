"use client";

import React, { useState } from "react";
import AppBar from "@mui/material/AppBar";
import SwipeableDrawer from "@mui/material/SwipeableDrawer";
import NavigationList from "@/components/NavigationList";
import UserAvatar from "@/components/UserAvatar";
import LogoutIcon from "@mui/icons-material/Logout";
import { Menu, MenuItem } from "@mui/material";
import { signOut } from "next-auth/react";
import { Container, Typography, Toolbar, IconButton } from "@mui/material";
import { useRouter } from "next/navigation";
import SettingsIcon from "@mui/icons-material/Settings";
import MenuIcon from "@mui/icons-material/Menu";
import { useSession } from "next-auth/react";

export default function Navbar() {
    const [navigationMenuState, setNavigationMenuState] = useState(false);
    const [anchorEl, setAnchorEl] = useState(null);
    const router = useRouter();

    const { data: session, status } = useSession({ required: true });
    if (status === "loading") {
        return <></>;
    }
    const authorities: String[] = session?.user.details.authorities;
    console.log(authorities);
    const defaultLinks = [
        {
            name: "Home",
            link: "/",
            authority: null,
        },
        {
            name: "Raise Request",
            link: "/raise-request",
            authority: "RAISE_PREQUEST_PRIVILEGE",
        },

        {
            name: "All Requests",
            link: "/super/requests",
            authority: "READ_PREQUEST_PRIVILEGE",
        },
        {
            name: "All Logs",
            link: "/super/logs",
            authority: "READ_LOG_PRIVILEGE",
        },
        {
            name: "User Logs",
            link: "/user/logs",
            authority: "READ_USER_LOG_PRIVILEGE",
        },
        {
            name: "User Requests",
            link: "/user/requests",
            authority: "READ_USER_PREQUEST_PRIVILEGE",
        },
        {
            name: "Register User",
            link: "/register-user",
            authority: "ACCOUNT_SIGNUP_PRIVILEGE",
        },
        {
            name: "Add new Role",
            link: "/role/create",
            authority: "ROLES_PRIVILEGE",
        },
        {
            name: "Edit Role",
            link: "/role/edit",
            authority: "ROLES_PRIVILEGE",
        },
        {
            name: "Manual Entry",
            link: "/super/manual-log-register",
            authority: "LOG_PRIVILEGE",
        },
        {
            name: "Register Maid",
            link: "/register-maid",
            authority: "REGISTER_MAID_PRIVILEGE",
        },
        {
            name: "All Maid Logs",
            link: "/super/maid-logs",
            authority: "READ_LOG_MAID_PRIVILEGE",
        },
        {
            name: "User Maid Logs",
            link: "/user/maid-logs",
            authority: "READ_LOG_USER_MAID_PRIVILEGE",
        },
        {
            name: "All Users",
            link: "/super/users-details",
            authority: "READ_ACCOUNT_PRIVILEGE",
        },
        {
            name: "All Maid Details",
            link: "/super/maid-details",
            authority: "READ_MAID_DETAILS_PRIVILEGE",
        },
        {
            name: "User Maid Details",
            link: "/user/maid-details",
            authority: "READ_MAID_DETAILS_USER_PRIVILEGE",
        },
        {
            name: "QR Scanner",
            link: "/scanner",
            authority: "LOG_PRIVILEGE",
        },
    ];

    const links = defaultLinks.filter((link) => {
        if (link.authority == null) {
            return true;
        } else {
            return authorities.includes(link.authority);
        }
    });

    const handleAvatarClick = (event: any) => {
        setAnchorEl(event.currentTarget);
    };

    const handleMenuClose = () => {
        setAnchorEl(null);
    };

    const handleLogout = () => {
        handleMenuClose();
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
                        variant="h5"
                        component="div"
                        sx={{ flexGrow: 1 }}
                    >
                        Sentry
                    </Typography>
                    <UserAvatar alt="User Avatar" onClick={handleAvatarClick} />
                    <Menu
                        anchorEl={anchorEl}
                        open={Boolean(anchorEl)}
                        onClose={handleMenuClose}
                    >
                        <MenuItem
                            onClick={() => {
                                router.push("/profile");
                                handleMenuClose();
                            }}
                        >
                            <IconButton color="inherit">
                                <SettingsIcon />
                            </IconButton>
                            Profile
                        </MenuItem>
                        {authorities.includes(
                            "CHANGE_USER_PASSWORD_PRIVILEGE"
                        ) ? (
                            <MenuItem
                                onClick={() => {
                                    router.push("/change-password");
                                    handleMenuClose();
                                }}
                            >
                                <IconButton color="inherit">
                                    <SettingsIcon />
                                </IconButton>
                                Change Password
                            </MenuItem>
                        ) : (
                            []
                        )}
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
