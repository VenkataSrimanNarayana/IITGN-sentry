export {};

declare global {
    type LinkName = {
        link: string;
        name: string;
    };
    type Details = {
        id: number;
        firstName: string;
        lastName: string;
        houseNo: string;
        area: string;
        landmark: string;
        pincode: number;
        townCity: string;
        state: string;
        country: string;
        userType: string;
        contactNumbers: {
            id: number;
            type: string;
            phone: string;
        }[];
        emails: {
            type: string;
            email: string;
        }[];
        role: {
            id: number;
            name: string;
            users: [string];
            privileges: [
                {
                    id: number;
                    name: string;
                    roles: [string];
                }
            ];
        };
        room: {
            blockName: string;
            roomNo: number;
        };
        authorities: [
            {
                authority: string;
            }
        ];
        accountNonExpired: "bool";
        accountNonLocked: "bool";
        credentialsNonExpired: "bool";
        enabled: "bool";
    };
}
