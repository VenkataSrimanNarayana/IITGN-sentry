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
        pinCode: number;
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
        authorities: string[];
        accountNonExpired: "bool";
        accountNonLocked: "bool";
        credentialsNonExpired: "bool";
        enabled: "bool";
    };
    interface Request {
        requestId: number;
        userId: number;
        validFromTime: string;
        validFromDate: string;
        validUptoTime: string;
        validUptoDate: string;
        requestType: string;
        reason: string;
        vehicleNo: string;
        userVisitorLog: string;
        userVehicleLog: string;
        requestDetails: {};
        vehicleRequestDetails: {};
        entry: boolean;
    }
}
