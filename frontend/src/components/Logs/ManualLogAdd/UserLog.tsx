"use client";
import React, { useState } from "react";
import FormControl from "@mui/material/FormControl";
import TextField from "@mui/material/TextField";
import Button from "@mui/material/Button";
import Container from "@mui/material/Container";
import { useSession } from "next-auth/react";
import { useRouter } from "next/navigation";

const CustomTextField = (props: any) => {
    return (
        <TextField
            label="Always Visible Label"
            InputLabelProps={{
                shrink: true,
            }}
            sx={{ display: "flex", width: "100%" }}
            {...props}
        />
    );
};

const CustomFormControl = (props: any) => {
    return (
        <FormControl
            sx={{ margin: "1rem", display: "block", alignContent: "center" }}
            {...props}
        />
    );
};

function getCurrentDateTime() {
    const now = new Date();
    const year = now.getFullYear();
    const month = String(now.getMonth() + 1).padStart(2, "0");
    const day = String(now.getDate()).padStart(2, "0");
    const hours = String(now.getHours()).padStart(2, "0");
    const minutes = String(now.getMinutes()).padStart(2, "0");

    const eventDate = `${year}-${month}-${day}`;
    const eventTime = `${hours}:${minutes}`;

    return {
        eventDate,
        eventTime,
    };
}

const UserLog = () => {
    const [vehicleNo, setVehicleNo] = useState();
    const [purpose, setPurpose] = useState();
    const [entry, setEntry] = useState();
    const [userId, setUserId] = useState();
    const [blockName, setBlockName] = useState();
    const [roomNo, setRoomNo] = useState();
    const { data: session } = useSession();
    const router = useRouter();

    const handleSubmit = async (event: Event) => {
        event.preventDefault();

        const { eventDate, eventTime } = getCurrentDateTime();

        const formData = {
            eventDate,
            eventTime,
            vehicleNo,
            purpose,
            entry,
            userId,
            blockName,
            roomNo,
        };

        // Send the form data to the API here
        const response = await fetch(
            process.env.NEXT_PUBLIC_BACKEND_URL +
                "/api/user-log/user/log-manual",
            {
                method: "POST",
                headers: {
                    Authorization: "Bearer " + session?.user.accessToken,
                    "Content-Type": "application/json",
                },
                body: JSON.stringify(formData),
            }
        );

        const data = await response.json();

        // Check if the response is ok
        if (response.ok) {
            // alert("Request generated successfully");
            router.push("/");
        } else {
            alert(data.message);
        }
    };

    return (
        <Container maxWidth="xs">
            <form onSubmit={handleSubmit}>
                <CustomFormControl>
                    <CustomTextField
                        id="user"
                        label="User Id"
                        type="text"
                        onChange={(e) => {
                            setUserId(e.target.value);
                        }}
                    />
                </CustomFormControl>

                <CustomFormControl>
                    <CustomTextField
                        id="vehicleNo"
                        label="Vehicle number"
                        value={vehicleNo}
                        type="text"
                        onChange={(e) => {
                            setVehicleNo(e.target.value);
                        }}
                    />
                </CustomFormControl>

                <CustomFormControl>
                    <CustomTextField
                        id="reason"
                        label="Reason"
                        type="text"
                        onChange={(e) => {
                            setPurpose(e.target.value);
                        }}
                    />
                </CustomFormControl>

                <CustomFormControl>
                    <CustomTextField
                        id="block"
                        label="Block"
                        type="text"
                        onChange={(e) => {
                            setBlockName(e.target.value);
                        }}
                    />
                </CustomFormControl>

                <CustomFormControl>
                    <CustomTextField
                        id="roomNo"
                        label="Room No"
                        type="text"
                        onChange={(e) => {
                            setRoomNo(e.target.value);
                        }}
                    />
                </CustomFormControl>

                <div style={{ display: "flex", flexDirection: "column" }}>
                    <CustomFormControl style={{ marginBottom: "10px" }}>
                        <div style={{ display: "inline-flex" }}>
                            <input
                                type="checkbox"
                                id="entry"
                                style={{
                                    marginLeft: "12.5px",
                                    marginRight: "5px",
                                }}
                                onChange={(e) => {
                                    if (e.target.value) {
                                        setEntry(1);
                                    } else {
                                        setEntry(0);
                                    }
                                }}
                            />
                            <label htmlFor="entry">Entry</label>
                        </div>
                    </CustomFormControl>

                    <CustomFormControl>
                        <Button type="submit" variant="outlined">
                            Submit
                        </Button>
                    </CustomFormControl>
                </div>
            </form>
        </Container>
    );
};

export default UserLog;
