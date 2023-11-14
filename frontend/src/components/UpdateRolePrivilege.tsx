"use client";
import { useState, useEffect } from "react";
import {
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
        console.log(data);
        setRole(data);
    };

    const handleCheckboxChange = (privilege) => {
        const updatedPrivileges = selectedPrivileges.includes(privilege)
            ? selectedPrivileges.filter((p) => p !== privilege)
            : [...selectedPrivileges, privilege];
        setSelectedPrivileges(updatedPrivileges);
    };

    const handleSubmit = async (e: { preventDefault: () => void }) => {
        e.preventDefault();
        try {
            const res = await fetch(
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
            );

            const response = await res.json();

            if (res.ok) {
                console.log("Role updated successfully!");
                fetchRoles();
                setSelectedPrivileges([]);
                setSelectedRole("");
                alert(response.message);
            }
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
    console.log("selected", selectedPrivileges);

    return (
        // give a map to show all the roles
        <div>
            <FormControl fullWidth>
                <InputLabel>Role</InputLabel>
                <Select
                    value={selectedRole}
                    onChange={(value) => {
                        setSelectedRole(value.target.value);
                        // Set the selected privileges to the privileges of the selected role
                        const selectedRole = role.find(
                            (r) => r.name === value.target.value
                        );
                        setSelectedPrivileges(
                            selectedRole.privileges.map((p) => p.id)
                        );
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
                                checked={selectedPrivileges.includes(
                                    privilege.id
                                )}
                                onChange={() =>
                                    handleCheckboxChange(privilege.id)
                                }
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
