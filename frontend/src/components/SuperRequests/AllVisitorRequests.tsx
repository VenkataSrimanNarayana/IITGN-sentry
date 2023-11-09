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
import { useRouter } from "next/navigation";
// import { fetchData } from "@/api/pending-requests-api/fetchData";

export default function AllVisitorRequests() {
  const [requests, setRequests] = useState([]);
  const { data: session, status } = useSession();

  const router = useRouter();

  if (status === "unauthenticated") {
    router.push("/login");
  }

  const postData = async (id: number) => {
    try {
      const response = await fetch(
        process.env.NEXT_PUBLIC_BACKEND_URL + `/api/user-visitor-log/${id}`,
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${session?.user.accessToken}`,
            // Add any other headers if needed
          },
        }
      );

      const responseData = await response.json().then(() => {}); // Assuming the response is JSON
      console.log("Response data:", responseData);
      if (response.ok) {
        setRequests(requests.filter((request) => request.requestId !== id));
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
    try {
      if (status == "loading") return; // Exit if the session has not loaded
      const response = await fetch(
        process.env.NEXT_PUBLIC_BACKEND_URL +
          `/api/pending-requests/all?offset=${offset}&limit=${limit}`,
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
        process.env.NEXT_PUBLIC_BACKEND_URL + "/api/pending-requests/" + id,
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
    } catch (error) {
      console.error("Error deleting data:", error);
      throw error;
    }
  };

  useEffect(() => {
    fetchData(0, 100);
  }, [status]);

//   console.log(requests);

  return (
    <>
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
              <TableCell>firstName</TableCell>
              <TableCell>lastName</TableCell>
              <TableCell>mobileNo</TableCell>
              <TableCell>entry</TableCell>
              <TableCell>actions</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {requests.map((requests: Request) => (
              <TableRow>
                {requests && requests.requestType === "other" && (
                  <>
                    <TableCell>{requests.requestId}</TableCell>
                    <TableCell>{requests.reason}</TableCell>
                    {requests.requestDetails.vehicleNo === null ? (
                      <TableCell>NA</TableCell>
                    ) : (
                      <TableCell>
                        {requests.requestDetails.vehicleNo}
                      </TableCell>
                    )}
                    <TableCell>
                      {requests.requestDetails.firstName}
                    </TableCell>
                    <TableCell>
                      {requests.requestDetails.lastName}
                    </TableCell>
                    <TableCell>
                      {requests.requestDetails.mobileNo}
                    </TableCell>
                    <TableCell>{requests.entry ? "entry" : "exit"}</TableCell>
                    <TableCell>
                      <IconButton
                        aria-label="delete"
                        onClick={() => deleteData(requests.requestId)}
                      >
                        <DeleteIcon />
                      </IconButton>
                      <IconButton
                        aria-label="done"
                        onClick={() => postData(requests.requestId)}
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
