"use client";
import React, { useEffect, useState } from 'react';
import { DataGrid, GridApi } from '@mui/x-data-grid';
import { Button, IconButton, Pagination } from '@mui/material'
import { useSession } from 'next-auth/react';
import { useRouter } from 'next/navigation';

const columns = [
  {field : 'vehicleNo', headerName: 'Vehicle No', width: 200},
  {field : 'firstName', headerName: 'First Name', width: 200},
  {field : 'lastName', headerName: 'Last Name', width: 200},
  {field : 'mobileNo', headerName: 'Mobile No', width: 200},
  {field : 'inDate', headerName: 'In Date', width: 200},
  {field : 'inTime', headerName: 'In Time', width: 200},
  {field : 'outDate', headerName: 'Out Date', width: 200},
  {field : 'outTime', headerName: 'Out Time', width: 200},
  {field : 'pickup', headerName: 'Pickup', width: 200},
  {field : 'vehicleUserLogId', headerName: 'Vehicle User Log ID', width: 200},
  {
    field: "action",
    headerName: "Action",
    sortable: false,
    renderCell: (params) => {
      <IconButton></IconButton>
    }
  },
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
        const response = await fetch(process.env.NEXT_PUBLIC_BACKEND_URL + `/api/user-vehicle-log/user/all?limit=${limit}&offset=${offset}`, 
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
    return row.vehicleUserLogId;
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
