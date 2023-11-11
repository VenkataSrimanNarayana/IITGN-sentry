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
      sx={{ margin: "1rem", display: "block" }}
      {...props}
    />
  );
};

const Form = () => {
  const [validFromTime, setValidFromTime] = useState();
  const [validFromDate, setValidFromDate] = useState();
  const [validUptoTime, setValidUptoTime] = useState();
  const [validUptoDate, setValidUptoDate] = useState();
  const [reason, setReason] = useState();
  const [firstName, setFirstName] = useState();
  const [lastName, setLastName] = useState();
  const [houseNo, setHouseNo] = useState();
  const [area, setArea] = useState();
  const [landmark, setLandmark] = useState();
  const [pinCode, setPinCode] = useState();
  const [townCity, setTownCity] = useState();
  const [state, setState] = useState();
  const [country, setCountry] = useState();
  const [mobileNo, setMobileNo] = useState();
  const [vehicleNo, setVehicleNo] = useState();

  const { data: session } = useSession();
  const router = useRouter();

  const handleSubmit = async (event: Event) => {
    event.preventDefault();

    const formData = {
      validFromTime,
      validFromDate,
      validUptoTime,
      validUptoDate,
      reason,
      firstName,
      lastName,
      houseNo,
      area,
      landmark,
      pinCode,
      townCity,
      state,
      country,
      mobileNo,
      vehicleNo,
    };

    // Send the form data to the API here
    const response = await fetch(
      process.env.NEXT_PUBLIC_BACKEND_URL + "/api/pending-requests/raise-other",
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
        <FormControl className="form-control">
          <CustomTextField
            id="validFromTime"
            label="Valid from time"
            type="time"
            value={validFromTime}
            onChange={(e) => {
              setValidFromTime(e.target.value);
            }}
          />
        </FormControl>

        <FormControl className="form-control">
          <CustomTextField
            id="validFromDate"
            label="Valid from date"
            type="date"
            value={validFromDate}
            onChange={(e) => {
              setValidFromDate(e.target.value);
            }}
          />
        </FormControl>

        <FormControl className="form-control">
          <CustomTextField
            id="validUptoTime"
            label="Valid upto time"
            type="time"
            value={validUptoTime}
            onChange={(e) => {
              setValidUptoTime(e.target.value);
            }}
          />
        </FormControl>

        <FormControl className="form-control">
          <CustomTextField
            id="validUptoDate"
            label="Valid upto date"
            type="date"
            value={validUptoDate}
            onChange={(e) => {
              setValidUptoDate(e.target.value);
            }}
          />
        </FormControl>

        <FormControl className="form-control">
          <CustomTextField
            id="vehicleNo"
            label="Vehicle number"
            value={vehicleNo}
            type="text"
            onChange={(e) => {
              setVehicleNo(e.target.value);
            }}
          />
        </FormControl>

        <FormControl className="form-control">
          <CustomTextField
            id="reason"
            label="Reason"
            type="text"
            onChange={(e) => {
              setReason(e.target.value);
            }}
          />
        </FormControl>

        <FormControl className="form-control">
          <CustomTextField
            id="firstName"
            label="First name"
            type="text"
            onChange={(e) => {
              setFirstName(e.target.value);
            }}
          />
        </FormControl>

        <FormControl className="form-control">
          <CustomTextField
            id="lastName"
            label="Last name"
            type="text"
            onChange={(e) => {
              setLastName(e.target.value);
            }}
          />
        </FormControl>

        <FormControl className="form-control">
          <CustomTextField
            id="mobileNo"
            label="Mobile number"
            type="text"
            onChange={(e) => {
              setMobileNo(e.target.value);
            }}
          />
        </FormControl>

        <FormControl className="form-control">
          <CustomTextField
            id="houseNo"
            label="House number"
            type="text"
            onChange={(e) => {
              setHouseNo(e.target.value);
            }}
          />
        </FormControl>

        <FormControl className="form-control">
          <CustomTextField
            id="area"
            label="Area"
            type="text"
            onChange={(e) => {
              setArea(e.target.value);
            }}
          />
        </FormControl>

        <FormControl className="form-control">
          <CustomTextField
            id="landmark"
            label="Landmark"
            type="text"
            onChange={(e) => {
              setLandmark(e.target.value);
            }}
          />
        </FormControl>

        <FormControl className="form-control">
          <CustomTextField
            id="pinCode"
            label="Pin code"
            type="text"
            onChange={(e) => {
              setPinCode(e.target.value);
            }}
          />
        </FormControl>

        <FormControl className="form-control">
          <CustomTextField
            id="townCity"
            label="Town/City"
            type="text"
            onChange={(e) => {
              setTownCity(e.target.value);
            }}
          />
        </FormControl>

        <FormControl className="form-control">
          <CustomTextField
            id="state"
            label="State"
            type="text"
            onChange={(e) => {
              setState(e.target.value);
            }}
          />
        </FormControl>

        <FormControl className="form-control">
          <CustomTextField
            id="country"
            label="Country"
            type="text"
            onChange={(e) => {
              setCountry(e.target.value);
            }}
          />
        </FormControl>
        <Button type="submit" onClick={handleSubmit}>
          Submit
        </Button>
      </form>
    </Container>
  );
};

export default Form;
