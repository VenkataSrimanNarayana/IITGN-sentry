"use client";
import { useEffect, useState } from "react";
import { useSession } from "next-auth/react";
import {
    Paper,
    Typography,
    Table,
    TableBody,
    TableCell,
    TableRow,
} from "@mui/material";

export default function PersonalDetailsWidget() {
    // fetch the details from the server
    const [details, setDetails] = useState({} as Details);
    const { data: session } = useSession();
    useEffect(() => {
        fetch(
            process.env.NEXT_PUBLIC_BACKEND_URL +
                "/api/users/" +
                session?.user.userID,
            {
                method: "GET",
                headers: {
                    "Content-Type": "application/json",
                    Authorization: "Bearer " + session?.user.accessToken,
                },
            }
        )
            .then((res) => res.json())
            .then((data) => setDetails(data));
    }, []);
    return (
        <Paper
            elevation={3}
            style={{
                padding: "16px",
                backgroundColor: "#f5f5f5",
                display: "inline-block",
            }}
        >
            <Typography variant="h6" style={{ marginBottom: "16px" }}>
                Personal Details
            </Typography>
            <Table>
                <TableBody>
                    <TableRow>
                        <TableCell>ID</TableCell>
                        <TableCell>{details.id}</TableCell>
                    </TableRow>
                    <TableRow>
                        <TableCell>First Name</TableCell>
                        <TableCell>{details.firstName}</TableCell>
                    </TableRow>
                    <TableRow>
                        <TableCell>Last Name</TableCell>
                        <TableCell>{details.lastName}</TableCell>
                    </TableRow>
                    <TableRow>
                        <TableCell>House No</TableCell>
                        <TableCell>{details.houseNo}</TableCell>
                    </TableRow>
                    <TableRow>
                        <TableCell>Area</TableCell>
                        <TableCell>{details.area}</TableCell>
                    </TableRow>
                    <TableRow>
                        <TableCell>Landmark</TableCell>
                        <TableCell>{details.landmark}</TableCell>
                    </TableRow>
                    <TableRow>
                        <TableCell>Pincode</TableCell>
                        <TableCell>{details.pincode}</TableCell>
                    </TableRow>
                    <TableRow>
                        <TableCell>Town/City</TableCell>
                        <TableCell>{details.townCity}</TableCell>
                    </TableRow>
                    <TableRow>
                        <TableCell>State</TableCell>
                        <TableCell>{details.state}</TableCell>
                    </TableRow>
                    <TableRow>
                        <TableCell>Country</TableCell>
                        <TableCell>{details.country}</TableCell>
                    </TableRow>
                    <TableRow>
                        <TableCell>User Type</TableCell>
                        <TableCell>{details.userType}</TableCell>
                    </TableRow>
                </TableBody>
            </Table>
        </Paper>
    );
}
