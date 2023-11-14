// Function to generate QR and display it on a modal
export default function generateQR(
    id: string,
    requestType: string,
    setOpen: any,
    setSelectedRequest: any
) {
    setSelectedRequest(JSON.stringify({ id: id, requestType: requestType }));
    setOpen(true);
}
