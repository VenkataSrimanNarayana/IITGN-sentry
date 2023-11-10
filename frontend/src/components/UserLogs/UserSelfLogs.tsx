"use client";
import React, { useEffect, useState } from 'react';
import { DataGrid } from '@mui/x-data-grid';
import { Pagination } from '@mui/material'
import { useSession } from 'next-auth/react';
import { useRouter } from 'next/navigation';

const columns = [
  { field: 'userLogId', headerName: 'User Log ID', width: 130 },
  { field: 'purpose', headerName: 'Purpose', width: 200 },
  { field: 'eventDate', headerName: 'Event Date', width: 150 },
  { field: 'eventTime', headerName: 'Event Time', width: 150 },
  { field: 'vehicleNo', headerName: 'Vehicle No', width: 150 },
  { field: 'blockName', headerName: 'Block Name', width: 150 },
  { field: 'roomNo', headerName: 'Room No', width: 130 },
  { field: 'userId', headerName: 'User', width: 150 },
  { field: 'entry', headerName: 'Entry', width: 130 },
];

const DataGridWithPagination = () => {
  const { data: session, status } = useSession();
  const [jsonData, setData] = useState([]);
  const [limit, setLimit] = useState(5); 
  const [offset, setOffset] = useState(0);
  const router = useRouter();

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await fetch(process.env.NEXT_PUBLIC_BACKEND_URL + `/api/user-log/user/all`, 
        {
          method: "GET",
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${session?.user.accessToken}`,
          },
        }
        
        );
        const jsonData = await response.json();
        setData(jsonData);
      } catch (error) {
        console.error('Error fetching data: ', error);
      }
    };

    fetchData();
  }, [offset, limit, status]);

  console.log(jsonData);

  function getRowId(row : any) {
    return row.userLogId;
  }

  return (
    <div style={{ height: 400, width: '100%' }}>
      <DataGrid getRowId={getRowId}
        rows={jsonData}
        columns={columns}
        pageSize={limit}
        rowsPerPageOptions={[limit]}
        pagination
        autoHeight
        initialState={{
          sorting: {
            sortModel: [{ field: 'eventTime', sort: 'desc' }, { field: 'eventDate', sort: 'desc' }],
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
};

export default DataGridWithPagination;
