"use client";

import { DataGrid } from "@mui/x-data-grid";
import { useSession } from "next-auth/react";
import { useEffect, useState } from "react";
import { Box, Modal, Button } from "@mui/material";
import QRCode from "react-qr-code";

export default function UserDetails({ detailType }: { detailType: string }) {
    const { data: session, status } = useSession();
    const [jsonData, setJsonData] = useState([]);
    const [open, setOpen] = useState(false); // For the modal
    const [selectedDetail, setSelectedDetail] = useState("");

    function generateQR(id: string) {
        setSelectedDetail(JSON.stringify({ id: id, requestType: "maid" }));
        setOpen(true);
    }

    let fetch_url = "";
    if (detailType === "all") {
        fetch_url = process.env.NEXT_PUBLIC_BACKEND_URL + `/api/maids/all`;
    } else if (detailType === "self") {
        fetch_url = process.env.NEXT_PUBLIC_BACKEND_URL + `/api/maids/user`;
    }

    const fetchData = async () => {
        try {
            const response = await fetch(fetch_url, {
                method: "GET",
                headers: {
                    "Content-Type": "application/json",
                    Authorization: `Bearer ${session?.user.accessToken}`,
                },
            });
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
            field: "workerId",
            headerName: "Worker ID",
            width: 150,
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
            field: "workDoing",
            headerName: "Work Doing",
            width: 150,
        },
        {
            field: "mobileNo",
            headerName: "Mobile No.",
            width: 150,
        },
        {
            field: "userId",
            headerName: "User ID",
            width: 150,
        },
        {
            field: "qrCode",
            headerName: "QR Code",
            width: 100,
            renderCell: (params: any) => {
                return (
                    <Button
                        variant="contained"
                        onClick={() => {
                            generateQR(params.row.workerId);
                        }}
                    >
                        Gen
                    </Button>
                );
            },
        },
    ];

    function getRowId(row: any) {
        return row.workerId;
    }

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
                    <QRCode value={selectedDetail} />
                </Box>
            </Modal>
            <div style={{ width: "100%" }}>
                <DataGrid
                    getRowId={getRowId}
                    columns={defaultColumns}
                    rows={jsonData}
                    pageSizeOptions={[5, 10, 20, 50, 100]}
                />
            </div>
        </>
    );
}
