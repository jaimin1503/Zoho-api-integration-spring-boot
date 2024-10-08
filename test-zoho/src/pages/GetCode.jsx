import axios from "axios";
import { useLocation } from "react-router-dom";
import { useState } from "react";

export default function GetCode() {
  const location = useLocation();
  const queryParams = new URLSearchParams(location.search);
  const code = queryParams.get("code");
  const [responseMessage, setResponseMessage] = useState("");

  const handleGenerateInvoice = async () => {
    try {

      const response = await axios.get(
        `http://localhost:9090/login/oauth2/code/zoho?code=${code}`, // Use '?' for query parameters
        {
          withCredentials: true,
        }
      );
      // Handle the response (if needed)
      setResponseMessage("Invoice generated successfully!");
      console.log(response.data); // Log response data
    } catch (error) {
      console.error("Error generating invoice:", error);
      setResponseMessage("Failed to generate invoice. Please try again.");
    }
  };

  return (
    <div>
      <h1>Get Code</h1>
      <button
        onClick={handleGenerateInvoice}
        style={{
          marginTop: "20px",
          backgroundColor: "#007BFF",
          color: "white",
          padding: "10px",
          borderRadius: "5px",
        }}
      >
        Generate Invoice
      </button>
      {responseMessage && <p>{responseMessage}</p>}{" "}
      {/* Display response message */}
    </div>
  );
}
