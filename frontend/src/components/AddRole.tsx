import { useSession } from "next-auth/react";
import { useRouter } from "next/navigation";
import { useState, useEffect, SetStateAction } from "react";
import {
    Button,
    Typography,
    TextField,
    Checkbox,
    FormControlLabel,
} from "@mui/material";
import { CheckBox } from "@mui/icons-material";

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

const PrivilegesPage = () => {
    const [privileges, setPrivileges] = useState([]);
    const [newRoleName, setNewRoleName] = useState("");
    const [selectedPrivileges, setSelectedPrivileges] = useState([]);
    const { data: session, status } = useSession();

    const router = useRouter();

    useEffect(() => {
        const fetchPrivileges = async () => {
            try {
                const response = await fetch(
                    process.env.NEXT_PUBLIC_BACKEND_URL + "/api/privileges/all",
                    {
                        method: "GET",
                        headers: {
                            "Content-Type": "application/json",
                            Authorization: `Bearer ${session?.user.accessToken}`,
                        },
                    }
                );
                const data = await response.json();
                setPrivileges(data);
            } catch (error) {
                console.error("Error fetching privileges:", error);
            }
        };

        fetchPrivileges();
    }, [status]);

    const handleCheckboxChange = (privilege) => {
        const updatedPrivileges = selectedPrivileges.includes(privilege)
            ? selectedPrivileges.filter((p) => p !== privilege)
            : [...selectedPrivileges, privilege];
        setSelectedPrivileges(updatedPrivileges);
    };

    const removeAllChecked = () => {
        setSelectedPrivileges([]);
    };

    console.log(selectedPrivileges);

    const handleRoleNameChange = (e: {
        target: { value: SetStateAction<string> };
    }) => {
        setNewRoleName(e.target.value);
    };

    const handleSubmit = async (e: { preventDefault: () => void }) => {
        e.preventDefault();

        try {
            const response = await fetch(
                process.env.NEXT_PUBLIC_BACKEND_URL + "/api/roles/add",
                {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json",
                        Authorization: `Bearer ${session?.user.accessToken}`,
                    },
                    body: JSON.stringify({
                        roleName: newRoleName,
                        privilegeIds: selectedPrivileges,
                    }),
                }
            ).then((res) => res.json());

            console.log(response);
            if (response.ok) {
                setNewRoleName("");
                setSelectedPrivileges([]);
                removeAllChecked();
            }
            alert(response.message);
            router.push("/");
        } catch (error) {
            console.error("Error creating role:", error);
        }
    };

    return (
        <div style={{ maxWidth: "600px", margin: "auto", padding: "20px" }}>
            <form
                style={{ display: "flex", flexDirection: "column" }}
                onSubmit={handleSubmit}
            >
                <CustomTextField
                    type="text"
                    label="Role Name"
                    name="roleName"
                    value={newRoleName}
                    sx={{ marginBottom: "1rem" }}
                    onChange={handleRoleNameChange}
                />
                <div style={{ marginBottom: "16px" }}>
                    <Typography
                        sx={{ margin: "1rem 1rem 1rem 0" }}
                        variant="h5"
                    >
                        Select Privileges:
                    </Typography>
                    {privileges.map((privilege) => (
                        <div key={privilege.id} style={{ marginBottom: "8px" }}>
                            <FormControlLabel
                                label={
                                    privilege.name +
                                    " - " +
                                    privilege.description
                                }
                                control={
                                    <Checkbox
                                        id={privilege.id}
                                        checked={selectedPrivileges.includes(
                                            privilege.id
                                        )}
                                        onChange={() =>
                                            handleCheckboxChange(privilege.id)
                                        }
                                    />
                                }
                            />
                        </div>
                    ))}
                </div>
                <Button type="submit" variant="outlined">
                    Create Role
                </Button>
            </form>
        </div>
    );
};

export default PrivilegesPage;
