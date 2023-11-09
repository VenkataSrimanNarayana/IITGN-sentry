"use client";
import React, { useEffect, useState } from 'react';
import { DataGrid } from '@mui/x-data-grid';
import { IconButton, Pagination } from '@mui/material'
import { useSession } from 'next-auth/react';
import { useRouter } from 'next/navigation';

const columns = [
  {field : "userVisitorLogId", headerName: "User Visitor Log ID", width: 150},
  {field : "vehicleNo", headerName: "Vehicle No", width: 150},
  {field : "firstName", headerName: "First Name", width: 150},
  {field : "lastName", headerName: "Last Name", width: 150},
  {field : "mobileNo", headerName: "Mobile No", width: 150},
  {field : "purpose", headerName: "Purpose", width: 150},
  {field : "houseNo", headerName: "House No", width: 150},
  {field : "area", headerName: "Area", width: 150},
  {field : "landmark", headerName: "Landmark", width: 150},
  {field : "pinCode", headerName: "Pin Code", width: 150},
  {field : "townCity", headerName: "Town City", width: 150},
  {field : "state", headerName: "State", width: 150},
  {field : "country", headerName: "Country", width: 150},
  {field : "inDate", headerName: "In Date", width: 150},
  {field : "inTime", headerName: "In Time", width: 150},
  {field : "outDate", headerName: "Out Date", width: 150},
  {field : "outTime", headerName: "Out Time", width: 150},
];

// /api/user-visitor-log/all

const DataGridWithPagination = () => {
  const { data: session, status } = useSession();
  const [jsonData, setData] = useState([]);
  const [limit, setLimit] = useState(5); 
  const [offset, setOffset] = useState(0);
  const router = useRouter();

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await fetch(process.env.NEXT_PUBLIC_BACKEND_URL + `/api/user-visitor-log/all`, 
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
    return row.userVisitorLogId;
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





