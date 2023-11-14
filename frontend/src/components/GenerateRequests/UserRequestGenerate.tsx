"use client";
import React, { useState } from "react";
import FormControl from "@mui/material/FormControl";
import TextField from "@mui/material/TextField";
import Button from "@mui/material/Button";
import Container from "@mui/material/Container";
import { useSession } from "next-auth/react";
import { useRouter } from "next/navigation";
import Grid from "@mui/material/Grid";

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

const Form = () => {
    const [validFromTime, setValidFromTime] = useState("");
    const [validFromDate, setValidFromDate] = useState("");
    const [validUptoTime, setValidUptoTime] = useState("");
    const [validUptoDate, setValidUptoDate] = useState("");
    const [vehicleNo, setVehicleNo] = useState("");
    const [reason, setReason] = useState("");
    const [entry, setEntry] = useState(0);
    const { data: session } = useSession();
    const router = useRouter();

    const handleSubmit = async (event: Event) => {
        event.preventDefault();

        // check for required
        if (
            !validFromTime ||
            !validFromDate ||
            !validUptoTime ||
            !validUptoDate ||
            !vehicleNo ||
            !reason
        ) {
            // show error to fill all fields
            alert("Please fill all the fields");
        }

        const formData = {
            validFromTime,
            validFromDate,
            validUptoTime,
            validUptoDate,
            vehicleNo,
            reason,
            entry,
        };

        // Send the form data to the API here
        const response = await fetch(
            process.env.NEXT_PUBLIC_BACKEND_URL +
                "/api/pending-requests/raise-self",
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
            alert("Request generated successfully");
            router.push("/");
        } else {
            alert(data.message);
        }
    };

    return (
        <Container maxWidth="xs">
            <form onSubmit={handleSubmit}>
                <Grid container>
                    <Grid item xs={12} sm={5}>
                        <CustomFormControl>
                            <CustomTextField
                                id="validFromTime"
                                label="Valid from time"
                                type="time"
                                value={validFromTime}
                                onChange={(e: { target: HTMLInputElement }) => {
                                    setValidFromTime(e.target?.value);
                                }}
                            />
                        </CustomFormControl>
                    </Grid>
                    <Grid item xs={12} sm={7}>
                        <CustomFormControl>
                            <CustomTextField
                                id="validFromDate"
                                label="Valid from date"
                                type="date"
                                value={validFromDate}
                                onChange={(e: { target: HTMLInputElement }) => {
                                    setValidFromDate(e.target.value);
                                }}
                            />
                        </CustomFormControl>
                    </Grid>
                </Grid>
                <Grid container>
                    <Grid item xs={12} sm={5}>
                        <CustomFormControl>
                            <CustomTextField
                                id="validUptoTime"
                                label="Valid upto time"
                                type="time"
                                value={validUptoTime}
                                onChange={(e: { target: HTMLInputElement }) => {
                                    setValidUptoTime(e.target.value);
                                }}
                            />
                        </CustomFormControl>
                    </Grid>

                    <Grid item xs={12} sm={7}>
                        <CustomFormControl>
                            <CustomTextField
                                id="validUptoDate"
                                label="Valid upto date"
                                type="date"
                                value={validUptoDate}
                                onChange={(e: { target: HTMLInputElement }) => {
                                    setValidUptoDate(e.target.value);
                                }}
                            />
                        </CustomFormControl>
                    </Grid>
                </Grid>

                <CustomFormControl>
                    <CustomTextField
                        id="vehicleNo"
                        label="Vehicle number"
                        value={vehicleNo}
                        type="text"
                        onChange={(e: { target: HTMLInputElement }) => {
                            setVehicleNo(e.target.value);
                            // console.log(vehicleNo);
                        }}
                    />
                </CustomFormControl>

                <CustomFormControl>
                    <CustomTextField
                        id="reason"
                        label="Reason"
                        type="text"
                        onChange={(e: { target: HTMLInputElement }) => {
                            setReason(e.target.value);
                        }}
                    />
                </CustomFormControl>

                <CustomFormControl>
                    <div style={{ display: "inline-flex" }}>
                        <input
                            type="checkbox"
                            id="entry"
                            style={{
                                marginLeft: "12.5px",
                                marginRight: "5px",
                            }}
                            onChange={(e: { target: HTMLInputElement }) => {
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
                    <Button variant="outlined" type="submit">
                        Submit
                    </Button>
                </CustomFormControl>
            </form>
        </Container>
    );
};

export default Form;
