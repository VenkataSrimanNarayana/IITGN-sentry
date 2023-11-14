"use client";
import React, { useEffect, useState } from "react";
import { DataGrid } from "@mui/x-data-grid";
import { IconButton } from "@mui/material";
import { useSession } from "next-auth/react";
import DeleteIcon from "@mui/icons-material/Delete";

export default function MaidLogs({
    logType,
    allowDelete,
}: {
    logType: string;
    allowDelete: boolean;
}) {
    const { data: session, status } = useSession({ required: true });
    const [jsonData, setJsonData] = useState([]);

    let fetch_url = "";

    if (logType === "self") {
        fetch_url = process.env.NEXT_PUBLIC_BACKEND_URL + `/api/maid-log/user`;
    } else if (logType === "all") {
        fetch_url = process.env.NEXT_PUBLIC_BACKEND_URL + `/api/maid-log/all`;
    }

    const fetchData = async () => {
        try {
            fetch(fetch_url, {
                method: "GET",
                headers: {
                    "Content-Type": "application/json",
                    Authorization: `Bearer ${session?.user.accessToken}`,
                },
            })
                .then((res) => res.json())
                .then((data) => {
                    setJsonData(data);
                });
        } catch (error) {
            console.error("Error fetching data: ", error);
        }
    };

    useEffect(() => {
        fetchData();
    }, []);

    const defaultColumns = [
        { field: "maidLogId", headerName: "Log ID", width: 150 },
        { field: "eventDate", headerName: "Date", width: 150 },
        { field: "eventTime", headerName: "Time", width: 150 },
        {
            field: "maid.workerId",
            valueGetter: (params: any) => {
                return `${params.row.maid.workerId}`;
            },
            headerName: "Maid ID",
            width: 150,
        },
        {
            field: "maid.firstName",
            valueGetter: (params: any) => {
                return `${params.row.maid.firstName}`;
            },
            headerName: "First Name",
            width: 150,
        },
        {
            field: "maid.lastName",
            headerName: "Last Name",
            valueGetter: (params: any) => {
                return `${params.row.maid.lastName}`;
            },
            width: 150,
        },
        {
            field: "maid.houseNo",
            headerName: "House No.",
            valueGetter: (params: any) => {
                return `${params.row.maid.houseNo}`;
            },
            width: 150,
        },
        {
            field: "maid.mobileNo",
            headerName: "Mobile No.",
            valueGetter: (params: any) => {
                return `${params.row.maid.mobileNo}`;
            },
            width: 150,
        },
        {
            field: "entry",
            valueGetter: (params: any) => {
                return `${params.row.entry}`;
            },
            headerName: "Entry",
            width: 150,
        },
    ];

    function deleteData(maidLogId: string) {
        try {
            fetch(
                process.env.NEXT_PUBLIC_BACKEND_URL +
                    `/api/maid-log/delete/${maidLogId}`,
                {
                    method: "DELETE",
                    headers: {
                        "Content-Type": "application/json",
                        Authorization: `Bearer ${session?.user.accessToken}`,
                    },
                }
            ).then((res) => {
                if (res.ok) {
                    alert("Deleted successfully!");
                    fetchData();
                } else {
                    alert("Error deleting data!");
                }
            });
        } catch (error) {
            alert("Error deleting data:", error);
            throw error;
        }
    }

    const columns =
        allowDelete &&
        session?.user.details.authorities.includes("DELETE_LOG_MAID_PRIVILEGE")
            ? [
                  ...defaultColumns,
                  {
                      field: "delete",
                      headerName: "Delete",
                      width: 100,
                      renderCell: (params: any) => (
                          <strong>
                              <IconButton
                                  onClick={(e: any) => {
                                      deleteData(params.row.maidLogId);
                                  }}
                              >
                                  <DeleteIcon />
                              </IconButton>
                          </strong>
                      ),
                  },
              ]
            : defaultColumns;

    function getRowId(row: any) {
        return row.maidLogId;
    }

    return (
        <div style={{ height: 400, width: "100%" }}>
            <DataGrid
                getRowId={getRowId}
                rows={jsonData}
                columns={columns}
                autoHeight
                initialState={{
                    sorting: {
                        sortModel: [
                            { field: "eventTime", sort: "desc" },
                            { field: "eventDate", sort: "desc" },
                        ],
                    },
                }}
            />
        </div>
    );
}
