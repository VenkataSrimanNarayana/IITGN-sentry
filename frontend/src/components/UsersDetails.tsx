"use client";

import { DataGrid } from "@mui/x-data-grid";
import { useSession } from "next-auth/react";
import { useEffect, useState } from "react";
import {
    Button,
    Modal,
    Box,
    FormControl,
    Select,
    InputLabel,
    MenuItem,
} from "@mui/material";

export default function UsersDetails() {
    const { data: session, status } = useSession({ required: true });
    const [jsonData, setJsonData] = useState([]);
    const [modalOpen, setModalOpen] = useState(false);
    const [selectedRole, setSelectedRole] = useState("");
    const [selectedId, setSelectedId] = useState("");
    const [roles, setRoles] = useState([]);

    async function roleChange(id: string) {
        setSelectedId(id);
        // Fetch all the roles
        await fetch(process.env.NEXT_PUBLIC_BACKEND_URL + `/api/roles/all`, {
            method: "GET",
            headers: {
                "Content-Type": "application/json",
                Authorization: `Bearer ${session?.user.accessToken}`,
            },
        })
            .then((response) => response.json())
            .then((data) => {
                setRoles(data.map((role) => role.name));
            });
        // Fetch the user role
        await fetch(
            process.env.NEXT_PUBLIC_BACKEND_URL + `/api/users/${id}/role`,
            {
                method: "GET",
                headers: {
                    "Content-Type": "application/json",
                    Authorization: `Bearer ${session?.user.accessToken}`,
                },
            }
        )
            .then((response) => response.json())
            .then((data) => {
                setSelectedRole(data.role);
            });
        setModalOpen(true);
    }

    function handleRoleSubmit() {
        // Send the request to change the role
        fetch(
            process.env.NEXT_PUBLIC_BACKEND_URL +
                `/api/users/${selectedId}/role`,
            {
                method: "PUT",
                headers: {
                    "Content-Type": "application/json",
                    Authorization: `Bearer ${session?.user.accessToken}`,
                },
                body: JSON.stringify({ newRole: selectedRole }),
            }
        )
            .then((response) => {
                if (!response.ok) {
                    throw new Error("Invalid Request");
                }
                alert("Successfully changed the role");
                setModalOpen(false);
            })
            .catch((error) => {
                alert(error);
            });
    }

    const fetchData = async () => {
        try {
            const response = await fetch(
                process.env.NEXT_PUBLIC_BACKEND_URL +
                    `/api/users/all?offset=0&limit=10`,
                {
                    method: "GET",
                    headers: {
                        "Content-Type": "application/json",
                        Authorization: `Bearer ${session?.user.accessToken}`,
                    },
                }
            );
            const jsonData = await response.json();
            setJsonData(jsonData);
        } catch (error) {
            console.error("Error fetching data: ", error);
        }
    };

    useEffect(() => {
        fetchData();
    }, [status]);

    const defaultColumns = [
        {
            field: "id",
            headerName: "ID",
            width: 100,
        },
        {
            field: "firstName",
            headerName: "First Name",
            width: 150,
        },
        {
            field: "lastName",
            headerName: "Last Name",
            width: 150,
        },
        {
            field: "houseNo",
            headerName: "House No.",
            width: 150,
        },
        {
            field: "area",
            headerName: "Area",
            width: 150,
        },
        {
            field: "landmark",
            headerName: "Landmark",
            width: 150,
        },
        {
            field: "pinCode",
            headerName: "Pin Code",
            width: 150,
        },
        {
            field: "townCity",
            headerName: "Town/City",
            width: 150,
        },
        {
            field: "state",
            headerName: "State",
            width: 150,
        },
        {
            field: "country",
            headerName: "Country",
            width: 150,
        },
        {
            field: "userType",
            headerName: "User Type",
            width: 150,
        },
        {
            field: "contactNumbers",
            valueGetter: (params: { row: { contactNumbers: any[] } }) => {
                return params.row.contactNumbers.map((contactNumber: any) => {
                    if (contactNumber.type === "personal") {
                        return contactNumber.phone;
                    }
                });
            },
            headerName: "Contact Numbers",
            width: 150,
        },
        {
            field: "emails",
            headerName: "Emails",
            valueGetter: (params: { row: { emails: any[] } }) => {
                return params.row.emails.map((email: any) => {
                    if (email.type === "college") {
                        return email.email;
                    }
                });
            },
            width: 200,
        },
        {
            field: "view",
            headerName: "View",
            width: 100,
            renderCell: (params: any) => (
                <strong>
                    {/* View button pointing to /user-details/id */}
                    <Button
                        variant="contained"
                        href={`/user-details/${params.row.id}`}
                    >
                        View
                    </Button>
                </strong>
            ),
        },
    ];

    const columns = [
        ...defaultColumns,
        ...(session?.user.details.authorities.includes("ROLE_UPDATE_PRIVILEGE")
            ? [
                  {
                      field: "roleChange",
                      headerName: "Role Change",
                      width: 120,
                      renderCell: (params: any) => (
                          <strong>
                              <Button
                                  variant="contained"
                                  onClick={() => {
                                      roleChange(params.row.id);
                                  }}
                              >
                                  Change
                              </Button>
                          </strong>
                      ),
                  },
              ]
            : []),
    ];

    return (
        <>
            <Modal
                open={modalOpen}
                onClose={() => {
                    setModalOpen(false);
                }}
            >
                <Box
                    sx={{
                        position: "absolute" as "absolute",
                        top: "50%",
                        left: "50%",
                        transform: "translate(-50%, -50%)",
                        minWidth: "10rem",
                        bgcolor: "background.paper",
                        border: "2px solid #000",
                        boxShadow: 24,
                        p: 4,
                    }}
                >
                    <FormControl
                        sx={{ marginBottom: "1rem", display: "block" }}
                    >
                        <InputLabel id="role-label">Role</InputLabel>
                        <Select
                            labelId="role-label"
                            id="role-select"
                            value={selectedRole}
                            onChange={(e) => {
                                setSelectedRole(e.target.value as string);
                            }}
                        >
                            {roles.map((role) => (
                                <MenuItem key={role} value={role}>
                                    {role}
                                </MenuItem>
                            ))}
                        </Select>
                    </FormControl>
                    <Button variant="contained" onClick={handleRoleSubmit}>
                        Submit
                    </Button>
                </Box>
            </Modal>
            <div style={{ width: "100%" }}>
                <DataGrid
                    columns={columns}
                    rows={jsonData}
                    pageSizeOptions={[5, 10, 20, 50, 100]}
                />
            </div>
        </>
    );
}
