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
        fetchData(0, 100);
    }, [status]);

    return (
        <>
            <TableContainer component={Paper}>
                <Typography variant="h6" style={{ marginBottom: "16px" }}>
                    All user Requests
                </Typography>
                <Table>
                    <TableHead>
                        <TableRow>
                            <TableCell>requestId</TableCell>
                            <TableCell>reason</TableCell>
                            <TableCell>vehicleNo</TableCell>
                            <TableCell>entry</TableCell>
                            <TableCell>actions</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {requests.map((requests: Request) => (
                            <TableRow>
                                {requests &&
                                    requests.requestType === "self" && (
                                        <>
                                            <TableCell>
                                                {requests.requestId}
                                            </TableCell>
                                            <TableCell>
                                                {requests.reason}
                                            </TableCell>
                                            {requests.vehicleNo === null ? (
                                                <TableCell>NA</TableCell>
                                            ) : (
                                                <TableCell>
                                                    {requests.vehicleNo}
                                                </TableCell>
                                            )}
                                            <TableCell>
                                                {requests.entry
                                                    ? "entry"
                                                    : "exit"}
                                            </TableCell>
                                            <TableCell
                                                sx={{
                                                    display:
                                                        allowAccept ||
                                                        allowDelete
                                                            ? "flex"
                                                            : "none",
                                                }}
                                            >
                                                <IconButton
                                                    sx={{
                                                        display: allowDelete
                                                            ? "flex"
                                                            : "none",
                                                    }}
                                                    aria-label="delete"
                                                    onClick={() =>
                                                        deleteData(
                                                            requests.requestId
                                                        )
                                                    }
                                                >
                                                    <DeleteIcon />
                                                </IconButton>
                                                <IconButton
                                                    sx={{
                                                        display: allowAccept
                                                            ? "flex"
                                                            : "none",
                                                    }}
                                                    aria-label="done"
                                                    onClick={() =>
                                                        postData(
                                                            requests.requestId
                                                        )
                                                    }
                                                >
                                                    <DoneIcon />
                                                </IconButton>
                                            </TableCell>
                                        </>
                                    )}
                            </TableRow>
                        ))}
                    </TableBody>
                </Table>
            </TableContainer>
        </>
    );
}
