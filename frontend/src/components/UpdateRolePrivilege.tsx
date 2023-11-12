"use client";
import { useState, useEffect } from "react";
import {
  TextField,
  Button,
  FormControl,
  InputLabel,
  Select,
  MenuItem,
} from "@mui/material";
import { useSession } from "next-auth/react";
import React from "react";



const UpdateRolePrivilege: React.FC = () => {


    const fetchData = async () => {
        const res = await fetch("http://localhost:8000/api/roles");
        const data = await res.json();
        setRoles(data);
    }

    useEffect(() => {
        fetchData();
    }, []);

    // const [roles, setRoles] = useState([]);

    // const [role, setRole] = useState("");

    // const [privilege, setPrivilege] = useState("");

    // const [privileges, setPrivileges] = useState([]); 

    // const [rolePrivilege, setRolePrivilege] = useState({
    //     role: "",
    //     privilege: "",
    // });

    
    return (
        <div>
            
        </div>
    );
};

export default UpdateRolePrivilege;