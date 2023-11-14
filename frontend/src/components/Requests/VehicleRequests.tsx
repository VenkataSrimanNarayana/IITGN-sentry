import { IconButton, Modal, Box, Button } from "@mui/material";
import DoneIcon from "@mui/icons-material/Done";
import DeleteIcon from "@mui/icons-material/Delete";
import { useState } from "react";
import { useSession } from "next-auth/react";
import { useEffect } from "react";
import { useRouter } from "next/navigation";
import { DataGrid } from "@mui/x-data-grid";
import QRCode from "react-qr-code";
import generateQR from "./QRGenrator";

export default function VehicleRequests({
    allowDelete,
    allowAccept,
    requestType,
}: {
    allowDelete: boolean;
    allowAccept: boolean;
    requestType: string;
}) {
    const [requests, setRequests] = useState([]);
    const { data: session, status } = useSession();
    const [open, setOpen] = useState(false); // For the modal
    const [selectedRequest, setSelectedRequest] = useState("");

    const router = useRouter();

    if (status === "unauthenticated") {
        router.push("/login");
    }

    const postData = async (id: number) => {
        try {
            const response = await fetch(
                process.env.NEXT_PUBLIC_BACKEND_URL +
                    `/api/user-vehicle-log/${id}`,
                {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json",
                        Authorization: `Bearer ${session?.user.accessToken}`,
                        // Add any other headers if needed
                    },
                }
            );

            const responseData = await response.json(); // Assuming the response is JSON
            if (response.ok) {
                // Get request from the id
                const request = requests.find(
                    (request) => request.requestId === id
                );
                if (request.entry === true) {
                    setRequests(
                        requests.map((request) => {
                            if (request.requestId === id) {
                                return {
                                    ...request,
                                    entry: false,
                                };
                            }
                            return request;
                        }) as any
                    );
                } else {
                    setRequests(
                        requests.filter((request) => request.requestId !== id)
                    );
                }
            }
            // Handle the response data or return it as needed
            return responseData;
        } catch (error) {
            console.error("Error posting data:", error);
            // Handle or throw the error as needed
            throw error;
        }
    };

    const fetchData = async (offset: number, limit: number) => {
        let fetch_url = "";
        if (requestType === "all") {
            fetch_url =
                process.env.NEXT_PUBLIC_BACKEND_URL +
                `/api/pending-requests/all?offset=${offset}&limit=${limit}`;
        } else if (requestType === "self") {
            fetch_url =
                process.env.NEXT_PUBLIC_BACKEND_URL +
                `/api/pending-requests/user/all`;
        }
        try {
            if (status == "loading") return; // Exit if the session has not loaded
            const response = await fetch(fetch_url, {
                headers: {
                    Authorization: `Bearer ${session?.user.accessToken}`,
                },
            });
            if (!response.ok) {
                throw new Error("Network response was not ok");
            }
            const responseData = await response.json();
            setRequests(responseData);
        } catch (error) {
            console.error("Error fetching data: ", error);
        }
    };

    const deleteData = async (id: number) => {
        try {
            const response = await fetch(
                process.env.NEXT_PUBLIC_BACKEND_URL +
                    "/api/pending-requests/" +
                    id,
                {
                    method: "DELETE",
                    headers: {
                        "Content-Type": "application/json",
                        Authorization: `Bearer ${session?.user.accessToken}`,
                    },
                }
            );
            if (!response.ok) {
                throw new Error("Network response was not ok");
            }
            const responseData = await response.json();
            fetchData(0, 100);
        } catch (error) {
            console.error("Error deleting data:", error);
            throw error;
        }
    };

    useEffect(() => {
        fetchData(0, 100);
    }, [status]);

    const defaultColumns = [
        {
            field: "requestId",
            headerName: "Request ID",
            width: 150,
        },
        {
            field: "reason",
            headerName: "Reason",
            width: 150,
        },
        {
            field: "vehicleRequestDetails.vehicleNo",
            valueGetter: (params) => {
                return `${params.row.vehicleRequestDetails.vehicleNo}`;
            },
            headerName: "Vehicle No.",
            width: 150,
        },
        {
            field: "vehicleRequestDetails.firstName",
            valueGetter: (params) => {
                return `${params.row.vehicleRequestDetails.firstName}`;
            },
            headerName: "First Name",
            width: 150,
        },
        {
            field: "vehicleRequestDetails.lastName",
            valueGetter: (params) => {
                return `${params.row.vehicleRequestDetails.lastName}`;
            },
            headerName: "Last Name",
            width: 150,
        },
        {
            field: "vehicleRequestDetails.mobileNo",
            valueGetter: (params) => {
                return `${params.row.vehicleRequestDetails.mobileNo}`;
            },
            headerName: "Mobile No.",
            width: 150,
        },
        {
            field: "entry",
            valueGetter: (params) => {
                return params.row.entry === false ? "exit" : "entry";
            },
            headerName: "Entry",
            width: 150,
        },
        {
            field: "generateQR",
            headerName: "Generate QR",
            width: 100,
            renderCell: (params: any) => (
                <strong>
                    <Button
                        variant="contained"
                        onClick={(e: any) => {
                            generateQR(
                                params.row.requestId,
                                params.row.requestType,
                                setOpen,
                                setSelectedRequest
                            );
                        }}
                    >
                        Gen
                    </Button>
                </strong>
            ),
        },
    ];

    const deleteColumn = {
        field: "delete",
        headerName: "Delete",
        width: 100,
        renderCell: (params: any) => (
            <strong>
                <IconButton
                    aria-label="delete"
                    onClick={() => {
                        deleteData(params.row.requestId);
                    }}
                >
                    <DeleteIcon />
                </IconButton>
            </strong>
        ),
    };

    const acceptColumn = {
        field: "accept",
        headerName: "Accept",
        width: 100,
        renderCell: (params: any) => (
            <strong>
                <IconButton
                    aria-label="accept"
                    onClick={() => {
                        postData(params.row.requestId);
                    }}
                >
                    <DoneIcon />
                </IconButton>
            </strong>
        ),
    };

    const columns = [
        ...defaultColumns,
        ...(allowAccept ? [acceptColumn] : []),
        ...(allowDelete ? [deleteColumn] : []),
    ];

    return (
        <>
            <Modal
                open={open}
                onClose={() => {
                    setOpen(false);
                }}
            >
                <Box
                    style={{
                        position: "absolute" as "absolute",
                        top: "50%",
                        left: "50%",
                        transform: "translate(-50%, -50%)",
                    }}
                    id={"modal-qr"}
                >
                    <QRCode value={selectedRequest} />
                </Box>
            </Modal>
            <div style={{ height: 400, width: "100%" }}>
                <DataGrid
                    getRowId={(row) => row.requestId}
                    rows={requests.filter((request: Request) => {
                        if (request.requestType === "vehicle") {
                            return true;
                        }
                    })}
                    columns={columns}
                    pageSize={5}
                    rowsPerPageOptions={[5]}
                    pagination
                    autoHeight
                />
            </div>
        </>
    );
}
