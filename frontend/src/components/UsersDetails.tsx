"use client";

import { DataGrid } from "@mui/x-data-grid";
import { useSession } from "next-auth/react";
import { useEffect, useState } from "react";

export default function UsersDetails() {
    const { data: session, status } = useSession();
    const [jsonData, setJsonData] = useState([]);

    const fetchData = async () => {
        try {
            const response = await fetch(
                process.env.NEXT_PUBLIC_BACKEND_URL +
                    `/api/users/all?offset=0&limit=10`,
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
            field: "id",
            headerName: "ID",
            width: 100,
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
            field: "userType",
            headerName: "User Type",
            width: 150,
        },
        {
            field: "contactNumbers",
            valueGetter: (params: { row: { contactNumbers: any[] } }) => {
                return params.row.contactNumbers.map((contactNumber: any) => {
                    if (contactNumber.type === "personal") {
                        return contactNumber.phone;
                    }
                });
            },
            headerName: "Contact Numbers",
            width: 150,
        },
        {
            field: "emails",
            headerName: "Emails",
            valueGetter: (params: { row: { emails: any[] } }) => {
                return params.row.emails.map((email: any) => {
                    if (email.type === "college") {
                        return email.email;
                    }
                });
            },
            width: 200,
        },
        {
            field: "view",
            headerName: "View",
            width: 100,
        },
    ];

    return (
        <div style={{ width: "100%" }}>
            <DataGrid
                columns={defaultColumns}
                rows={jsonData}
                pageSizeOptions={[5, 10, 20, 50, 100]}
            />
        </div>
    );
}
