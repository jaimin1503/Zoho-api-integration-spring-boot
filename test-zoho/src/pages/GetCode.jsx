import axios from "axios";
import { useLocation } from "react-router-dom";
import { useState, useEffect } from "react";

export default function GetCode() {
  const location = useLocation();
  const queryParams = new URLSearchParams(location.search);
  const code = queryParams.get("code");
  const [responseMessage, setResponseMessage] = useState("");

  useEffect(() => {
    if (!code) {
      setResponseMessage("Authorization code is missing.");
    }
  }, [code]);

  const handleGenerateInvoice = async () => {
    if (!code) return;

    try {
      const response = await axios.get(
        `http://localhost:9090/login/oauth2/code/zoho?code=${code}`,
        {
          withCredentials: true,
        }
      );

      // Handle response
      if (response.status === 200) {
        const { access_token, expiry_date } = response.data;
        console.log(response.data);
        // Save token and expiry date in localStorage
        localStorage.setItem("access_token", access_token);
        localStorage.setItem("access_token_expiry", expiry_date);

        setResponseMessage("Invoice generated and token saved successfully!");
      } else {
        setResponseMessage("Failed to generate invoice.");
      }
    } catch (error) {
      console.error("Error generating invoice:", error);
      if (error.response) {
        setResponseMessage(
          `Error: ${
            error.response.data.message ||
            "Failed to generate invoice. Please try again."
          }`
        );
      } else {
        setResponseMessage(
          "Network error. Please check your connection and try again."
        );
      }
    }
  };

  return (
    <div style={containerStyle}>
      <h1 style={headingStyle}>Zoho Invoice</h1>
      <button onClick={handleGenerateInvoice} style={buttonStyle}>
        Generate Invoice
      </button>
      {responseMessage && <p style={messageStyle}>{responseMessage}</p>}
    </div>
  );
}

// Inline styles
const containerStyle = {
  padding: "20px",
  maxWidth: "400px",
  margin: "0 auto",
};

const headingStyle = {
  textAlign: "center",
};

const buttonStyle = {
  marginTop: "20px",
  backgroundColor: "#007BFF",
  color: "white",
  padding: "10px",
  borderRadius: "5px",
  border: "none",
  cursor: "pointer",
  width: "100%",
  fontSize: "16px",
};

const messageStyle = {
  textAlign: "center",
  marginTop: "20px",
};
