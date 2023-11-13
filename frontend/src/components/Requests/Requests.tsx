import {
    Table,
    TableBody,
    TableCell,
    TableContainer,
    TableHead,
    TableRow,
    Paper,
    Typography,
    IconButton,
} from "@mui/material";
import DoneIcon from "@mui/icons-material/Done";
import DeleteIcon from "@mui/icons-material/Delete";
import { useState } from "react";
import { useSession } from "next-auth/react";
import { useEffect } from "react";
import { DataGrid } from "@mui/x-data-grid";

export default function Requests({
    allowDelete,
    allowAccept,
    requestType,
}: {
    allowDelete: boolean;
    allowAccept: boolean;
    requestType: string;
}) {
    const [requests, setRequests] = useState([] as Request[]);
    const { data: session, status } = useSession();

    const postData = async (id: number) => {
        try {
            const response = await fetch(
                process.env.NEXT_PUBLIC_BACKEND_URL + `/api/user-log/${id}`,
                {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json",
                        Authorization: `Bearer ${session?.user.accessToken}`,
                    },
                }
            );

            const responseData = await response.json(); // Assuming the response is JSON
            console.log("Response data:", responseData);
            if (response.ok) {
                setRequests(
                    requests.filter(
                        (request: Request) => request.requestId !== id
                    )
                );
            }
            return responseData;
        } catch (error) {
            console.error("Error posting data:", error);
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
            console.log(responseData);
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
            setRequests(
                requests.filter((request: Request) => request.requestId !== id)
            );
        } catch (error) {
            console.error("Error deleting data:", error);
            throw error;
        }
    };

    useEffect(() => {
        fetchData(0, 1000);
    }, [status]);

    const defaultColumns = [
        {
            field: "requestId",
            headerName: "Request ID",
            width: 150,
        },
        { field: "reason", headerName: "Reason", width: 200 },
        { field: "vehicleNo", headerName: "Vehicle No", width: 150 },
        { field: "entry", headerName: "Entry", width: 130 },
        { field: "requestType", headerName: "Request Type", width: 150 },
        {
            field: "validFromDate",
            headerName: "Valid From Date",
            width: 150,
        },
        { field: "validFromTime", headerName: "Valid From Time", width: 150 },
        {
            field: "validUptoDate",
            headerName: "Valid Upto Date",
            width: 150,
        },
        { field: "validUptoTime", headerName: "Valid Upto Time", width: 150 },
    ];

    return (
        <div style={{ width: "100%" }}>
            <DataGrid
                getRowId={(row: any) => row.requestId}
                rows={requests}
                columns={defaultColumns}
                pageSize={5}
                rowsPerPageOptions={[5]}
                disableSelectionOnClick
                pageSizeOptions={[5, 10, 20, 50, 100]}
            />
        </div>
    );
}
