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
import { useSession } from "next-auth/react";

const UpdateRolePrivilege: React.FC = () => {
  const { data: session, status } = useSession();
  const [role, setRole] = useState([]);
  const [selectedRole, setSelectedRole] = useState("");
  const [privileges, setPrivileges] = useState([]);
  const [selectedPrivileges, setSelectedPrivileges] = useState([]);

  const fetchRoles = async () => {
    const res = await fetch(
      process.env.NEXT_PUBLIC_BACKEND_URL + "/api/roles/all",
      {
        method: "GET",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${session?.user.accessToken}`,
        },
      }
    );
    const data = await res.json();
    setRole(data);
  };

  const handleCheckboxChange = (privilege) => {
    const updatedPrivileges = selectedPrivileges.includes(privilege)
      ? selectedPrivileges.filter((p) => p !== privilege)
      : [...selectedPrivileges, privilege];
    setSelectedPrivileges(updatedPrivileges);
  };

  const handleSubmit = async (e: { preventDefault: () => void }) => {
    console.log(selectedRole);
    e.preventDefault();
    try {
      const response = await fetch(
        process.env.NEXT_PUBLIC_BACKEND_URL + "/api/roles/update",
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${session?.user.accessToken}`,
          },
          body: JSON.stringify({
            roleName: selectedRole,
            privilegeIds: selectedPrivileges,
          }),
        }
      ).then((res) => res.json());
      if (response.ok) {
        setSelectedPrivileges([]);
      }
      console.log("Role updated successfully!");
    } catch (error) {
      console.error("Error creating role:", error);
    }
  };

  const fetchPrivileges = async () => {
    const res = await fetch(
      process.env.NEXT_PUBLIC_BACKEND_URL + "/api/privileges/all",
      {
        method: "GET",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${session?.user.accessToken}`,
        },
      }
    );
    const data = await res.json();
    setPrivileges(data);
  };

  useEffect(() => {
    fetchRoles();
    fetchPrivileges();
  }, [status]);

  return (
    // give a map to show all the roles
    <div>
      <h1>Update Role Privilege</h1>
      <FormControl fullWidth>
        <InputLabel>Role</InputLabel>
        <Select
          defaultValue=""
          onChange={(value) => {
            console.log(value.target.value);
            setSelectedRole(value.target.value);
          }}
        >
          {role.map((r) => (
            <MenuItem key={r.id} value={r.name}>
              {r.name}  
            </MenuItem>
          ))}
        </Select>
      </FormControl>
      <FormControl fullWidth>
        <div style={{ marginBottom: "16px" }}>
          <h2>Privileges:</h2>
          {privileges.map((privilege) => (
            <div key={privilege.id} style={{ marginBottom: "8px" }}>
              <input
                type="checkbox"
                id={privilege.id}
                checked={selectedPrivileges.includes(privilege.id)}
                onChange={() => handleCheckboxChange(privilege.id)}
                style={{ marginRight: "8px" }}
              />
              <label htmlFor={privilege.id}>
                {privilege.name} - {privilege.description}
              </label>
            </div>
          ))}
        </div>
      </FormControl>
      <Button
        fullWidth
        variant="contained"
        color="primary"
        onClick={handleSubmit}
      >
        Update
      </Button>
    </div>
  );
};

export default UpdateRolePrivilege;
