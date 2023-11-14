"use client";

import { IconButton } from "@mui/material";
import { DataGrid } from "@mui/x-data-grid";
import { useSession } from "next-auth/react";
import { useEffect, useState } from "react";
import DeleteIcon from "@mui/icons-material/Delete";

export default function UserDetails() {
  const { data: session, status } = useSession();
  const [jsonData, setJsonData] = useState([]);

  const deleteData = async (id: string) => {
    try {
      const response = await fetch(
        process.env.NEXT_PUBLIC_BACKEND_URL + `/api/maids/${id}`,
        {
          method: "DELETE",
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
  }

  const fetchData = async () => {
    try {
      const response = await fetch(
        process.env.NEXT_PUBLIC_BACKEND_URL + `/api/maids/all`,
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
      field: "actions",
      headerName: "Actions",
      width: 150,
      renderCell: (params: any) => (
        <>
          <IconButton>
            <DeleteIcon />
          </IconButton>
        </>
      ),
    },
  ];

  function getRowId(row: any) {
    return row.workerId;
  }

  return (
    <div style={{ width: "100%" }}>
      <DataGrid
        getRowId={getRowId}
        columns={defaultColumns}
        rows={jsonData}
        pageSizeOptions={[5, 10, 20, 50, 100]}
      />
    </div>
  );
}
