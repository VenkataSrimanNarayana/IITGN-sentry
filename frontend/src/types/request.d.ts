export interface Request {
    requestId: number;
    validFromTime: string;
    validFromDate: string;
    validUptoTime: string;
    validUptoDate: string;
    requestType: string;
    reason: string;
    vehicleNo: string;
    userVisitorLog: string;
    userVehicleLog: string;
    requestDetails: string;
    vehicleRequestDetails: string;
    entry: boolean;
  }