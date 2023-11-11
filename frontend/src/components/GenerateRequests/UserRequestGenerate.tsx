"use client";
import React, { useState } from "react";
import FormControl from "@mui/material/FormControl";
import TextField from "@mui/material/TextField";
import Button from "@mui/material/Button";
import Container from "@mui/material/Container";
import { useSession } from "next-auth/react";
import { useRouter } from 'next/navigation';


const CustomTextField = (props: any) => {
  return (
    <TextField
      label="Always Visible Label"
      required
      InputLabelProps={{
        shrink: true,
      }}
      sx={{ margin: "1rem", display: "block" }}
      {...props}
    />
  );
};

const Form = () => {
  const [validFromTime, setValidFromTime] = useState();
  const [validFromDate, setValidFromDate] = useState();
  const [validUptoTime, setValidUptoTime] = useState();
  const [validUptoDate, setValidUptoDate] = useState();
  const [vehicleNo, setVehicleNo] = useState();
  const [reason, setReason] = useState();
  const [entry, setEntry] = useState();
  const { data: session } = useSession();
  const router = useRouter();


  const handleSubmit = async (event : Event) => {
    event.preventDefault();

    // check for required
    if (
      !validFromTime ||
      !validFromDate ||
      !validUptoTime ||
      !validUptoDate ||
      !vehicleNo ||
      !reason
    ) {
      // show error to fill all fields
      alert("Please fill all the fields");
    }

    const formData = {
      validFromTime,
      validFromDate,
      validUptoTime,
      validUptoDate,
      vehicleNo,
      reason,
      entry,
    };

    // Send the form data to the API here
    const response = await fetch(
      process.env.NEXT_PUBLIC_BACKEND_URL + "/api/pending-requests/raise-self",
      {
        method: "POST",
        headers: {
          Authorization: "Bearer " + session?.user.accessToken,
          "Content-Type": "application/json",
        },
        body: JSON.stringify(formData),
      }
    );

    const data = await response.json();

    // Check if the response is ok
    if(response.ok){
        alert("Request generated successfully");
        router.push("/");
    }else{
        alert(data.message);
    }

  }

  return (
    <Container maxWidth="xs">
      <form onSubmit={handleSubmit}>
        <FormControl className="form-control">
          <CustomTextField
            id="validFromTime"
            label="Valid from time"
            type="time"
            value={validFromTime}
            onChange={(e) => {
              setValidFromTime(e.target.value);
            }}
          />
        </FormControl>

        <FormControl className="form-control">
          <CustomTextField
            id="validFromDate"
            label="Valid from date"
            type="date"
            value={validFromDate}
            onChange={(e) => {
                setValidFromDate(e.target.value);
                }
            }
          />
        </FormControl>

        <FormControl className="form-control">
          <CustomTextField
            id="validUptoTime"
            label="Valid upto time"
            type="time"
            value={validUptoTime}
            onChange={(e) => {
                setValidUptoTime(e.target.value);
                }
            }
          />
        </FormControl>

        <FormControl className="form-control">
          <CustomTextField
            id="validUptoDate"
            label="Valid upto date"
            type="date"
            value={validUptoDate}
            onChange={(e) => {
                setValidUptoDate(e.target.value);
                }
            }
          />
        </FormControl>

        <FormControl className="form-control">
          <CustomTextField
            id="vehicleNo"
            label="Vehicle number"
            value={vehicleNo}
            type="text"
            onChange={(e) => {
                setVehicleNo(e.target.value);
                // console.log(vehicleNo); 
                }
            }
          />
        </FormControl>

        <FormControl className="form-control">
          <CustomTextField id="reason" label="Reason" type="text" 
          onChange = {
                (e) => {
                    setReason(e.target.value);
                }
          }
          
          />
        </FormControl>

        <div style={{ display: "flex", flexDirection: "column" }}>
          <FormControl
            className="form-control"
            style={{ marginBottom: "10px" }}
          >
            <div style={{ display: "inline-flex" }}>
              <input
                type="checkbox"
                id="entry"
                style={{ marginLeft: "12.5px", marginRight: "5px" }}
                onChange={(e) => {
                    if(e.target.value){
                        setEntry(1);
                    }else{
                        setEntry(0);
                    }
                }}
              />
              <label htmlFor="entry">Entry</label>
            </div>
          </FormControl>
          <Button type="submit" onClick={handleSubmit}>
            Submit
          </Button>
        </div>
      </form>
    </Container>
  );
};

export default Form;
