"use client";
import React, { useEffect, useState } from "react";
import { DataGrid } from "@mui/x-data-grid";
import { IconButton, Pagination } from "@mui/material";
import { useSession } from "next-auth/react";
import DeleteIcon from "@mui/icons-material/Delete";

const VehicleLogs = ({
    allowDelete,
    logType,
}: {
    allowDelete: boolean;
    logType: string;
}) => {
    const { data: session, status } = useSession();
    const [jsonData, setData] = useState([]);
    const [limit, setLimit] = useState(5);
    const [offset, setOffset] = useState(0);
    let fetch_url = "";
    if (logType === "self") {
        fetch_url =
            process.env.NEXT_PUBLIC_BACKEND_URL +
            `/api/user-vehicle-log/user/all`;
    } else if (logType === "all") {
        fetch_url =
            process.env.NEXT_PUBLIC_BACKEND_URL + `/api/user-vehicle-log/all`;
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
            setData(jsonData);
        } catch (error) {
            console.error("Error fetching data: ", error);
        }
    };

    const deleteLog = async (id: number) => {
        try {
            const response = await fetch(
                process.env.NEXT_PUBLIC_BACKEND_URL +
                    `/api/user-vehicle-log/${id}`,
                {
                    method: "DELETE",
                    headers: {
                        "Content-Type": "application/json",
                        Authorization: `Bearer ${session?.user.accessToken}`,
                    },
                }
            );
            const jsonData = await response.json();
            console.log(jsonData);
            fetchData();
        } catch (error) {
            console.error("Error fetching data: ", error);
        }
    };

    const defaultColumns = [
        { field: "vehicleNo", headerName: "Vehicle No", width: 200 },
        { field: "firstName", headerName: "First Name", width: 200 },
        { field: "lastName", headerName: "Last Name", width: 200 },
        { field: "mobileNo", headerName: "Mobile No", width: 200 },
        { field: "inDate", headerName: "In Date", width: 200 },
        { field: "inTime", headerName: "In Time", width: 200 },
        { field: "outDate", headerName: "Out Date", width: 200 },
        { field: "outTime", headerName: "Out Time", width: 200 },
        { field: "pickup", headerName: "Pickup", width: 200 },
        {
            field: "vehicleUserLogId",
            headerName: "Vehicle User Log ID",
            width: 200,
        },
    ];

    const columns = allowDelete
        ? [
              ...defaultColumns,
              {
                  field: "action",
                  headerName: "Action",
                  sortable: false,
                  renderCell: (params: any) => {
                      return (
                          <>
                              <IconButton
                                  aria-label="delete"
                                  onClick={() => {
                                      deleteLog(params.row.vehicleUserLogId);
                                  }}
                              >
                                  <DeleteIcon />
                              </IconButton>
                          </>
                      );
                  },
              },
          ]
        : defaultColumns;

    useEffect(() => {
        fetchData();
    }, [offset, limit, status]);

    function getRowId(row: any) {
        return row.vehicleUserLogId;
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
        </div>
    );
};

export default VehicleLogs;
