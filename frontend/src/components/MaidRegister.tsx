"use client";
import { useState, useEffect } from "react";
import {
  TextField,
  Button,
  FormControl,
  InputLabel,
  Select,
  MenuItem,
} from "@mui/material";
import { Role } from "@/types/role";
import { useSession } from "next-auth/react";

export default function RegisterMaid() {
  const [formData, setFormData] = useState({
    firstName: "",
    lastName: "",
    houseNo: "",
    area: "",
    landmark: "",
    pinCode: "",
    townCity: "",
    state: "",
    country: "",
    workDoing: "",
    mobileNo: "",
    email: "",
  });

  const { data: session, status } = useSession();

  const postData = async () => {
    try {
      const response = await fetch(
        process.env.NEXT_PUBLIC_BACKEND_URL + `/api/maids/register/api`,
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${session?.user.accessToken}`,
          },
          body: JSON.stringify(formData),
        }
      );
      const jsonData = await response.json();
      console.log(jsonData);
    } catch (error) {
      console.error("Error fetching data: ", error);
    }
  };

  const handleChange = (e: { target: { name: any; value: any } }) => {
    const { name, value } = e.target;
    setFormData((prevData) => ({
      ...prevData,
      [name]: value,
    }));
  };

  const handleSubmit = (e: { preventDefault: () => void }) => {
    e.preventDefault();
    postData();
  };

  return (
    <div style={{ maxWidth: "400px", margin: "auto", marginTop: "50px" }}>
      <h2>Register Form</h2>
      <form onSubmit={handleSubmit}>
        <TextField
          label="First Name"
          name="firstName"
          value={formData.firstName}
          onChange={handleChange}
          fullWidth
          style={{ marginBottom: "20px" }}
        />

        <TextField
          label="Last Name"
          name="lastName"
          value={formData.lastName}
          onChange={handleChange}
          fullWidth
          style={{ marginBottom: "20px" }}
        />

        <TextField
          label="House No."
          name="houseNo"
          value={formData.houseNo}
          onChange={handleChange}
          fullWidth
          style={{ marginBottom: "20px" }}
        />

        <TextField
          label="Area"
          name="area"
          value={formData.area}
          onChange={handleChange}
          fullWidth
          style={{ marginBottom: "20px" }}
        />

        <TextField
          label="Landmark"
          name="landmark"
          value={formData.landmark}
          onChange={handleChange}
          fullWidth
          style={{ marginBottom: "20px" }}
        />

        <TextField
          label="Pin Code"
          name="pinCode"
          value={formData.pinCode}
          onChange={handleChange}
          fullWidth
          style={{ marginBottom: "20px" }}
        />

        <TextField
          label="Town/City"
          name="townCity"
          value={formData.townCity}
          onChange={handleChange}
          fullWidth
          style={{ marginBottom: "20px" }}
        />

        <TextField
          label="State"
          name="state"
          value={formData.state}
          onChange={handleChange}
          fullWidth
          style={{ marginBottom: "20px" }}
        />

        <TextField
          label="Country"
          name="country"
          value={formData.country}
          onChange={handleChange}
          fullWidth
          style={{ marginBottom: "20px" }}
        />

        <TextField
          label="Work Doing"
          name="workDoing"
          value={formData.workDoing}
          onChange={handleChange}
          fullWidth
          style={{ marginBottom: "20px" }}
        />

        <TextField
          label="Mobile No."
          name="mobileNo"
          value={formData.mobileNo}
          onChange={handleChange}
          fullWidth
          style={{ marginBottom: "20px" }}
        />

        <TextField
          label="Email"
          name="email"
          value={formData.email}
          onChange={handleChange}
          fullWidth
          style={{ marginBottom: "20px" }}
        />

        <Button
          variant="contained"
          color="secondary"
          type="reset"
          style={{ marginRight: "20px" }}
          onClick={() => {
            setFormData({
                firstName: "",
                lastName: "",
                houseNo: "",
                area: "",
                landmark: "",
                pinCode: "",
                townCity: "",
                state: "",
                country: "",
                workDoing: "",
                mobileNo: "",
                email: "",
                });
          }}
        >
          Reset
        </Button>
        <Button
          variant="contained"
          color="primary"
          type="submit"
          onSubmit={handleSubmit}
        >
          Submit
        </Button>
      </form>
    </div>
  );
}
