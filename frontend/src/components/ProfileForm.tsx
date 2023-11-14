import { Divider, TextField, Button, Typography } from "@mui/material";
import React, { useState, useEffect } from "react";
import { useSession } from "next-auth/react";

// Define custom styles for CustomTextField
const CustomTextField = (props: any) => {
    return (
        <TextField
            label="Always Visible Label"
            InputLabelProps={{
                shrink: true,
            }}
            sx={{ margin: "1rem" }}
            {...props}
        />
    );
};

// Define a custom divider
const CustomDivider = (props: any) => {
    return <Divider sx={{ margin: "1rem 0 1rem 0" }} {...props} />;
};

// Define a custom button
const CustomButton = (props: any) => {
    return <Button sx={{ margin: "1rem 0 1rem 1rem" }} {...props} />;
};

const ProfileForm = ({ userID }: { userID: string }) => {
    const [formData, setFormData] = useState({} as Details);
    const { data: session } = useSession({ required: true });
    const fetchUrl =
        userID === session?.user.userID
            ? process.env.NEXT_PUBLIC_BACKEND_URL + "/api/users"
            : process.env.NEXT_PUBLIC_BACKEND_URL +
              "/api/users/" +
              userID +
              "/details";
    const updateUrl =
        userID === session?.user.userID
            ? process.env.NEXT_PUBLIC_BACKEND_URL + "/api/users"
            : process.env.NEXT_PUBLIC_BACKEND_URL + "/api/users/" + userID;
    // Fetch the user data from the backend
    useEffect(() => {
        fetch(fetchUrl, {
            method: "GET",
            headers: {
                "Content-Type": "application/json",
                Authorization: "Bearer " + session?.user.accessToken,
            },
        })
            .then((res) => res.json())
            .then((data) => setFormData(data));
    }, []);

    // Function to set the email: email value in the form data
    const setEmailEmail = (index: number, email: string) => {
        setFormData({
            ...formData,
            emails: formData.emails.map((e, i) =>
                i == index ? { ...e, email: email } : e
            ),
        });
    };

    // Function to set the email: email value in the form data
    const setEmailType = (index: number, type: string) => {
        setFormData({
            ...formData,
            emails: formData.emails.map((e, i) =>
                i == index ? { ...e, type: type } : e
            ),
        });
    };

    // Function to set the contact: phone value in the form data
    const setContactPhone = (index: number, phone: string) => {
        setFormData({
            ...formData,
            contactNumbers: formData.contactNumbers.map((e, i) =>
                i == index ? { ...e, phone: phone } : e
            ),
        });
    };

    // Function to set the contact: type value in the form data
    const setContactType = (index: number, type: string) => {
        setFormData({
            ...formData,
            contactNumbers: formData.contactNumbers.map((e, i) =>
                i == index ? { ...e, type: type } : e
            ),
        });
    };

    // Function to add a new and empty email to the form data
    const addEmail = () => {
        setFormData({
            ...formData,
            emails: [...formData.emails, { type: "", email: "" }],
        });
    };

    // Function to add a new and empty contact to the form data
    const addContact = () => {
        setFormData({
            ...formData,
            contactNumbers: [
                ...formData.contactNumbers,
                { id: formData.contactNumbers.length, type: "", phone: "" },
            ],
        });
    };

    const handleChange = (e: any) => {
        const { name, value } = e.target;
        setFormData({ ...formData, [name]: value });
    };



    const handleSubmit = async (e: any) => {
        e.preventDefault();
        // Generate form data for the allowed fields
        const allowedFields = [
            "houseNo",
            "area",
            "landmark",
            "pinCode",
            "townCity",
            "state",
            "country",
            "contactNumbers",
            "emails",
        ];
        let formDataAllowed: any = {};
        allowedFields.forEach((field) => {
            formDataAllowed[field] = formData[field] as any;
        });

        try {
            const res = await fetch(
                updateUrl,
                {
                    method: "PUT",
                    headers: {
                        "Content-Type": "application/json",
                        Authorization: `Bearer ${session?.user.accessToken}`,
                    },
                    body: JSON.stringify(
                        formDataAllowed,
                    ),
                }
            );
            const response = await res.json();
            if (res.ok) {
                alert(response.message);
            }
        } catch (error) {
            console.error("Error creating role:", error);
        }

        // Make a request to the backend to update the user details
    };

    return (
        <form onSubmit={handleSubmit}>
            <CustomTextField
                type="text"
                name="id"
                label="id"
                value={formData.id}
                onChange={handleChange}
                disabled
            />
            <CustomTextField
                type="text"
                name="firstName"
                label="First Name"
                value={formData.firstName}
                disabled
                onChange={handleChange}
            />
            <CustomTextField
                type="text"
                name="lastName"
                label="Last Name"
                value={formData.lastName}
                disabled
                onChange={handleChange}
            />
            <CustomTextField
                type="text"
                name="userType"
                label="User Type"
                value={formData.userType}
                onChange={handleChange}
                disabled
            />
            <CustomDivider />
            <Typography margin="1rem" variant="h6">
                Address
            </Typography>
            <CustomTextField
                type="text"
                name="houseNo"
                label="House No."
                value={formData.houseNo}
                onChange={handleChange}
            />
            <CustomTextField
                type="text"
                name="area"
                label="Area"
                value={formData.area}
                onChange={handleChange}
            />
            <CustomTextField
                type="text"
                name="landmark"
                label="LandMark"
                value={formData.landmark}
                onChange={handleChange}
            />
            <CustomTextField
                type="text"
                name="pinCode"
                label="Pincode"
                value={formData.pinCode}
                onChange={handleChange}
            />
            <CustomTextField
                type="text"
                name="townCity"
                label="Town/City"
                value={formData.townCity}
                onChange={handleChange}
            />
            <CustomTextField
                type="text"
                name="state"
                label="State"
                value={formData.state}
                onChange={handleChange}
            />
            <CustomTextField
                type="text"
                name="country"
                label="Country"
                value={formData.country}
                onChange={handleChange}
            />
            <CustomDivider />
            <Typography margin="1rem" variant="h6">
                {" "}
                Contact Numbers
            </Typography>
            {formData.contactNumbers?.map((contact, index) => {
                return (
                    <div key={index}>
                        <CustomTextField
                            type="text"
                            name="mobileType"
                            label="Type"
                            value={contact.type}
                            onChange={(e: any) => {
                                setContactType(index, e.target.value);
                            }}
                        />
                        <CustomTextField
                            type="text"
                            name="mobileNo"
                            label="Phone"
                            value={contact.phone}
                            onChange={(e: any) => {
                                setContactPhone(index, e.target.value);
                            }}
                        />
                    </div>
                );
            })}
            <CustomButton
                variant="outlined"
                onClick={(e: any) => {
                    addContact();
                }}
            >
                Add Contact
            </CustomButton>
            <CustomDivider />
            <Typography margin="1rem" variant="h6">
                {" "}
                Emails
            </Typography>
            {formData.emails?.map((contact, index) => {
                return (
                    <div key={index}>
                        <CustomTextField
                            type="text"
                            name="emailType"
                            label="Type"
                            value={contact.type}
                            onChange={(e: any) => {
                                setEmailType(index, e.target.value);
                            }}
                        />
                        <CustomTextField
                            type="email"
                            name="email"
                            label="Email"
                            value={contact.email}
                            onChange={(e: any) => {
                                setEmailEmail(index, e.target.value);
                            }}
                        />
                    </div>
                );
            })}
            <CustomButton
                variant="outlined"
                onClick={(e: any) => {
                    addEmail();
                }}
            >
                Add Email
            </CustomButton>
            <CustomDivider />
            <CustomButton variant="contained" type="submit">
                Save
            </CustomButton>
            <CustomButton variant="outlined" href="/profile">
                Back
            </CustomButton>
        </form>
    );
};

export default ProfileForm;
