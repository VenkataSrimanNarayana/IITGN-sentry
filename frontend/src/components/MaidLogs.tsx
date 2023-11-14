"use client";
import React, { useEffect, useState } from "react";
import { DataGrid } from "@mui/x-data-grid";
import { IconButton, Pagination } from "@mui/material";
import { useSession } from "next-auth/react";
import DeleteIcon from "@mui/icons-material/Delete";

export default function MaidLogs(props) {
  const { logType } = props;
  const { isDelete } = props;

  const { data: session, status } = useSession({ required: true });
  const [jsonData, setData] = useState();

  const [limit, setLimit] = useState(5);
  const [offset, setOffset] = useState(0);

  let fetch_url = "";

  if (logType === "self") {
    fetch_url = process.env.NEXT_PUBLIC_BACKEND_URL + `/api/maid-log/user`;
  } else if (logType === "all") {
    fetch_url = process.env.NEXT_PUBLIC_BACKEND_URL + `/api/maid-log/all`;
  }

  useEffect(() => {
    fetchData();
  }, [fetch_url]);

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
