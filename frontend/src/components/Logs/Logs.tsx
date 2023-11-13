"use client";
import React, { useEffect, useState } from "react";
import { DataGrid } from "@mui/x-data-grid";
import { IconButton, Pagination } from "@mui/material";
import { useSession } from "next-auth/react";
import DeleteIcon from "@mui/icons-material/Delete";

export default function Log(props: { isDelete: boolean; logType: string }) {
    const isDelete = props.isDelete;
    const logType = props.logType;

    let fetch_url = "";
    if (logType === "self") {
        fetch_url =
            process.env.NEXT_PUBLIC_BACKEND_URL + `/api/user-log/user/all`;
    } else if (logType === "all") {
        fetch_url = process.env.NEXT_PUBLIC_BACKEND_URL + `/api/user-log/all`;
    }

    const { data: session, status } = useSession();
    const [jsonData, setData] = useState([]);
    const [limit, setLimit] = useState(5);
    const [offset, setOffset] = useState(0);

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
            setData(jsonData);
        } catch (error) {
            console.error("Error fetching data: ", error);
        }
    };

    const deleteLog = async (id: number) => {
        try {
            const response = await fetch(
                process.env.NEXT_PUBLIC_BACKEND_URL + `/api/user-log/${id}`,
                {
                    method: "DELETE",
                    headers: {
                        "Content-Type": "application/json",
                        Authorization: `Bearer ${session?.user.accessToken}`,
                    },
                }
            );
            const jsonData = await response.json();
            fetchData();
        } catch (error) {
            console.error("Error fetching data: ", error);
        }
    };

    const defaultColumns = [
        {
            field: "userLogId",
            headerName: "User Log ID",
            width: 150,
        },
        { field: "purpose", headerName: "Purpose", width: 200 },
        { field: "eventDate", headerName: "Event Date", width: 150 },
        { field: "eventTime", headerName: "Event Time", width: 150 },
        { field: "vehicleNo", headerName: "Vehicle No", width: 150 },
        { field: "blockName", headerName: "Block Name", width: 150 },
        { field: "roomNo", headerName: "Room No", width: 130 },
        {
            field: "userId",
            headerName: "User",
            width: 150,
        },
        { field: "entry", headerName: "Entry", width: 130 },
    ];

    const columns = isDelete
        ? [
              ...defaultColumns,
              {
                  field: "action",
                  headerName: "Action",
                  width: 150,
                  renderCell: (params: any) => (
                      <strong>
                          <IconButton
                              aria-label="delete"
                              onClick={() => {
                                  deleteLog(params.row.userLogId);
                              }}
                          >
                              <DeleteIcon />
                          </IconButton>
                      </strong>
                  ),
              },
          ]
        : defaultColumns;

    useEffect(() => {
        fetchData();
    }, [offset, limit, status]);

    function getRowId(row: any) {
        return row.userLogId;
    }

    return (
        <div style={{ height: 400, width: "100%" }}>
            <DataGrid
                getRowId={getRowId}
                rows={jsonData}
                columns={columns}
                pageSize={limit}
                rowsPerPageOptions={[limit]}
                pagination
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
            <Pagination
                count={10}
                page={offset / limit + 1}
                onChange={(event, value) => setOffset((value - 1) * limit)}
            />
        </div>
    );
}
