"use client";
import React, { useState, useEffect } from "react";
import {
    Button,
    Paper,
    TableContainer,
    Table,
    TableRow,
    TableBody,
    TableCell,
    Divider,
} from "@mui/material";
import { useSession } from "next-auth/react";

const Profile = ({ userID }: { userID: string }) => {
    const [details, setDetails] = useState({} as Details);
    const { data: session } = useSession({ required: true });
    const fetchUrl =
        userID == session?.user.userID
            ? process.env.NEXT_PUBLIC_BACKEND_URL + "/api/users"
            : process.env.NEXT_PUBLIC_BACKEND_URL +
              "/api/users/" +
              userID +
              "/details";
    useEffect(() => {
        fetch(fetchUrl, {
            method: "GET",
            headers: {
                "Content-Type": "application/json",
                Authorization: "Bearer " + session?.user.accessToken,
            },
        })
            .then((res) => res.json())
            .then((data) => setDetails(data));
    }, []);

    return (
        <>
            <TableContainer component={Paper}>
                <Table>
                    <TableBody>
                        <TableRow>
                            <TableCell component="th" scope="row">
                                ID
                            </TableCell>
                            <TableCell>{details.id}</TableCell>
                        </TableRow>
                        <TableRow>
                            <TableCell component="th" scope="row">
                                First Name
                            </TableCell>
                            <TableCell>{details.firstName}</TableCell>
                        </TableRow>
                        <TableRow>
                            <TableCell component="th" scope="row">
                                Last Name
                            </TableCell>
                            <TableCell>{details.lastName}</TableCell>
                        </TableRow>
                        <TableRow>
                            <TableCell component="th" scope="row">
                                House No
                            </TableCell>
                            <TableCell>{details.houseNo}</TableCell>
                        </TableRow>
                        <TableRow>
                            <TableCell component="th" scope="row">
                                Area
                            </TableCell>
                            <TableCell>{details.area}</TableCell>
                        </TableRow>
                        <TableRow>
                            <TableCell component="th" scope="row">
                                Landmark
                            </TableCell>
                            <TableCell>{details.landmark}</TableCell>
                        </TableRow>
                        <TableRow>
                            <TableCell component="th" scope="row">
                                Pincode
                            </TableCell>
                            <TableCell>{details.pinCode}</TableCell>
                        </TableRow>
                        <TableRow>
                            <TableCell component="th" scope="row">
                                Town/City
                            </TableCell>
                            <TableCell>{details.townCity}</TableCell>
                        </TableRow>
                        <TableRow>
                            <TableCell component="th" scope="row">
                                State
                            </TableCell>
                            <TableCell>{details.state}</TableCell>
                        </TableRow>
                        <TableRow>
                            <TableCell component="th" scope="row">
                                Country
                            </TableCell>
                            <TableCell>{details.country}</TableCell>
                        </TableRow>
                        <TableRow>
                            <TableCell component="th" scope="row">
                                User Type
                            </TableCell>
                            <TableCell>{details.userType}</TableCell>
                        </TableRow>
                        <TableRow>
                            <TableCell component="th" scope="row">
                                Contact Numbers
                            </TableCell>
                            <TableCell>
                                {details.contactNumbers?.map(
                                    (contact, index) => (
                                        <>
                                            {index != 0 ? <Divider /> : <></>}
                                            <strong>Type: </strong>
                                            {contact.type}
                                            <br />
                                            <strong>Phone: </strong>
                                            {contact.phone}
                                        </>
                                    )
                                )}
                            </TableCell>
                        </TableRow>
                        <TableRow>
                            <TableCell component="th" scope="row">
                                Emails
                            </TableCell>
                            <TableCell>
                                {details.emails?.map((email, index) => (
                                    <>
                                        {index != 0 ? <Divider /> : <></>}
                                        <strong>Type: </strong> {email.type}
                                        <br />
                                        <strong>Email:</strong> {email.email}
                                    </>
                                ))}
                            </TableCell>
                        </TableRow>
                        <TableRow>
                            <TableCell component="th" scope="row">
                                Room
                            </TableCell>
                            <TableCell>
                                {details.room ? (
                                    <>
                                        <strong>Block Name:</strong>
                                        {details.room.blockName}
                                        <br />
                                        <strong>Room No:</strong>
                                        {details.room.roomNo}
                                    </>
                                ) : (
                                    "No room information available"
                                )}
                            </TableCell>
                        </TableRow>
                    </TableBody>
                </Table>
            </TableContainer>

            <Button
                variant="contained"
                href={
                    userID === session?.user.userID
                        ? "/profile/edit"
                        : "/user-details/" + userID + "/edit"
                }
                sx={{ margin: "1rem" }}
            >
                Edit
            </Button>
        </>
    );
};

export default Profile;
