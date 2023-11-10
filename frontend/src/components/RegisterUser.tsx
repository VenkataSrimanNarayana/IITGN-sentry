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

export default function RegisterUser() {
  const [formData, setFormData] = useState({
    id: 0,
    firstName: "",
    lastName: "",
    houseNo: "",
    area: "",
    landmark: "",
    pincode: 0,
    townCity: "",
    state: "",
    country: "",
    password: "",
    userType: "",
    role: "",
    mobileNo: "",
    email: "",
  });

  const [roles, setRoles] = useState([]);
  const { data: session, status } = useSession();

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await fetch(
          process.env.NEXT_PUBLIC_BACKEND_URL + `/api/roles/all`,
          {
            method: "GET",
            headers: {
              "Content-Type": "application/json",
              Authorization: `Bearer ${session?.user.accessToken}`,
            },
          }
        );
        const jsonData = await response.json();
        setRoles(jsonData);
      } catch (error) {
        console.error("Error fetching data: ", error);
      }
    };
    fetchData();
  }, [status]);


  const postData = async () => {
    try {
      const response = await fetch(
        process.env.NEXT_PUBLIC_BACKEND_URL + `/api/auth/signup`,
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
  }

  const handleChange = (e: { target: { name: any; value: any; }; }) => {
    const { name, value } = e.target;
    setFormData((prevData) => ({
      ...prevData,
      [name]: value,
    }));
  };

  const handleSubmit = (e: { preventDefault: () => void; }) => {
    e.preventDefault();
    postData();
  };

  return (
    <div style={{ maxWidth: "400px", margin: "auto", marginTop: "50px" }}>
      <h2>Register Form</h2>
      <form onSubmit={handleSubmit}>
        <TextField
          label="ID"
          name="id"
          value={formData.id}
          onChange={handleChange}
          fullWidth
          style={{ marginBottom: "20px" }}
        />

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
          label="Pincode"
          name="pincode"
          value={formData.pincode}
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
          label="Password"
          name="password"
          value={formData.password}
          onChange={handleChange}
          fullWidth
          style={{ marginBottom: "20px" }}
        />

        <TextField
          label="User Type"
          name="userType"
          value={formData.userType}
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

        <br />
        <br />

        <FormControl fullWidth style={{ marginBottom: "20px" }}>
          <InputLabel id="role-label">Role</InputLabel>
          <Select
            labelId="role-label"
            id="role"
            name="role"
            value={formData.role}
            onChange={handleChange}
          >
            {roles.map((role: Role) => (
              <MenuItem key={role.id} value={role.name}>
                {role.name}
              </MenuItem>
            ))}
          </Select>
        </FormControl>

        <Button
          variant="contained"
          color="secondary"
          type="reset"
          style={{ marginRight: "20px" }}
        >
          Reset
        </Button>
        <Button variant="contained" color="primary" type="submit" onSubmit={handleSubmit}>
          Submit
        </Button>
      </form>
    </div>
  );
}
