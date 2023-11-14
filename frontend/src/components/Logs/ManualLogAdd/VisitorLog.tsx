"use client";
import React, { useState } from "react";
import FormControl from "@mui/material/FormControl";
import TextField from "@mui/material/TextField";
import Button from "@mui/material/Button";
import Container from "@mui/material/Container";
import Grid from "@mui/material/Grid";
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

    const validFromDate = `${year}-${month}-${day}`;
    const validFromTime = `${hours}:${minutes}`;

    return {
        validFromDate,
        validFromTime,
    };
}

const Form = () => {
    const [reason, setReason] = useState("");
    const [firstName, setFirstName] = useState("");
    const [lastName, setLastName] = useState("");
    const [houseNo, setHouseNo] = useState("");
    const [area, setArea] = useState("");
    const [landmark, setLandmark] = useState("");
    const [pinCode, setPinCode] = useState("");
    const [townCity, setTownCity] = useState("");
    const [state, setState] = useState("");
    const [country, setCountry] = useState("");
    const [mobileNo, setMobileNo] = useState("");
    const [vehicleNo, setVehicleNo] = useState("");
    const [validUptoDate, setValidUptoDate] = useState("");
    const [validUptoTime, setValidUptoTime] = useState("");

    const { data: session } = useSession();
    const router = useRouter();

    const loggerFunction = async (id: string) => {
        try {
            const response = await fetch(
                process.env.NEXT_PUBLIC_BACKEND_URL +
                    `/api/user-visitor-log/${id}`,
                {
                    method: "POST",
                    headers: {
                        Authorization: "Bearer " + session?.user.accessToken,
                        "Content-Type": "application/json",
                    },
                }
            );
            if (response.ok) {
                alert("Request Raised successfully");
                const data = await response.json();
                setValidUptoDate("");
                setValidUptoTime("");
                setVehicleNo("");
                setReason("");
                setFirstName("");
                setLastName("");
                setHouseNo("");
                setArea("");
                setLandmark("");
                setPinCode("");
                setTownCity("");
                setState("");
                setCountry("");
                setMobileNo("");
                console.log(data);
            }
        } catch (error) {
            console.log(error);
        }
    };

    const handleSubmit = async (event: Event) => {
        event.preventDefault();
        const { validFromDate, validFromTime } = getCurrentDateTime();

        const formData = {
            validFromDate,
            validFromTime,
            validUptoTime,
            validUptoDate,
            vehicleNo,
            reason,
            firstName,
            lastName,
            mobileNo,
            houseNo,
            area,
            landmark,
            pinCode,
            townCity,
            state,
            country,
        };

        const response = await fetch(
            process.env.NEXT_PUBLIC_BACKEND_URL +
                "/api/pending-requests/raise-other",
            {
                method: "POST",
                headers: {
                    Authorization: "Bearer " + session?.user.accessToken,
                    "Content-Type": "application/json",
                },
                body: JSON.stringify(formData),
            }
        );

        if (response.ok) {
            const data = await response.json();
            loggerFunction(data.requestId);
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
                                id="validUptoTime"
                                label="Valid upto time"
                                type="time"
                                value={validUptoTime}
                                onChange={(e) => {
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
                                onChange={(e) => {
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
                            setReason(e.target.value);
                        }}
                    />
                </CustomFormControl>

                <CustomFormControl>
                    <CustomTextField
                        id="firstName"
                        label="First name"
                        type="text"
                        onChange={(e) => {
                            setFirstName(e.target.value);
                        }}
                    />
                </CustomFormControl>

                <CustomFormControl>
                    <CustomTextField
                        id="lastName"
                        label="Last name"
                        type="text"
                        onChange={(e) => {
                            setLastName(e.target.value);
                        }}
                    />
                </CustomFormControl>

                <CustomFormControl>
                    <CustomTextField
                        id="mobileNo"
                        label="Mobile number"
                        type="text"
                        onChange={(e) => {
                            setMobileNo(e.target.value);
                        }}
                    />
                </CustomFormControl>

                <CustomFormControl>
                    <CustomTextField
                        id="houseNo"
                        label="House number"
                        type="text"
                        onChange={(e) => {
                            setHouseNo(e.target.value);
                        }}
                    />
                </CustomFormControl>

                <CustomFormControl>
                    <CustomTextField
                        id="area"
                        label="Area"
                        type="text"
                        onChange={(e) => {
                            setArea(e.target.value);
                        }}
                    />
                </CustomFormControl>

                <CustomFormControl>
                    <CustomTextField
                        id="landmark"
                        label="Landmark"
                        type="text"
                        onChange={(e) => {
                            setLandmark(e.target.value);
                        }}
                    />
                </CustomFormControl>

                <CustomFormControl>
                    <CustomTextField
                        id="pinCode"
                        label="Pincode"
                        type="text"
                        onChange={(e) => {
                            setPinCode(e.target.value);
                        }}
                    />
                </CustomFormControl>

                <CustomFormControl>
                    <CustomTextField
                        id="townCity"
                        label="Town/City"
                        type="text"
                        onChange={(e) => {
                            setTownCity(e.target.value);
                        }}
                    />
                </CustomFormControl>

                <CustomFormControl>
                    <CustomTextField
                        id="state"
                        label="State"
                        type="text"
                        onChange={(e) => {
                            setState(e.target.value);
                        }}
                    />
                </CustomFormControl>

                <CustomFormControl>
                    <CustomTextField
                        id="country"
                        label="Country"
                        type="text"
                        onChange={(e) => {
                            setCountry(e.target.value);
                        }}
                    />
                </CustomFormControl>

                <CustomFormControl>
                    <Button type="submit" variant="outlined">
                        Submit
                    </Button>
                </CustomFormControl>
            </form>
        </Container>
    );
};

export default Form;
