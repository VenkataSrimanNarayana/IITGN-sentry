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
import DeleteIcon from "@mui/icons-material/Delete";
import { useState, useEffect } from "react";
import { useSession } from "next-auth/react";

export default function SelfRequests() {
  const [requests, setRequests] = useState([]);
  const { data: session, status } = useSession();

  const fetchData = async (offset: number, limit: number) => {
    try {
      if (status == "loading") return; // Exit if the session has not loaded
      const response = await fetch(
        process.env.NEXT_PUBLIC_BACKEND_URL + `/api/pending-requests/user/all`,
        {
          headers: {
            Authorization: `Bearer ${session?.user.accessToken}`,
          },
        }
      );
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
          "/api/pending-requests/user/" +
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
    <TableContainer component={Paper}>
      <Typography variant="h6" style={{ marginBottom: "16px" }}>
        All User Requests
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
              {requests && requests.requestType === "self" && (
                <>
                  <TableCell>{requests.requestId}</TableCell>
                  <TableCell>{requests.reason}</TableCell>
                  {requests.vehicleNo === null ? (
                    <TableCell>NA</TableCell>
                  ) : (
                    <TableCell>{requests.vehicleNo}</TableCell>
                  )}
                  <TableCell>{requests.entry ? "entry" : "exit"}</TableCell>
                  <TableCell>
                    <IconButton
                      aria-label="delete"
                      onClick={() => deleteData(requests.requestId)}
                    >
                      <DeleteIcon />
                    </IconButton>
                  </TableCell>
                </>
              )}
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </TableContainer>
  );
}
