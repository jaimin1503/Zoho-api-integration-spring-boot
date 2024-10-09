import axios from "axios";
import { useLocation } from "react-router-dom";
import { useState, useEffect } from "react";
import { customerData } from "../data/Customer.js";

export default function GetCode() {
  const location = useLocation();
  const queryParams = new URLSearchParams(location.search);
  const code = queryParams.get("code");
  const [responseMessage, setResponseMessage] = useState("");
  const [customers, setCustomers] = useState([]);

  useEffect(() => {
    if (!code) {
      setResponseMessage("Authorization code is missing.");
    }
  }, [code]);

  useEffect(() => {
    axios.get("http://localhost:9090/api/customers/all").then((response) => {
      setCustomers(response.data.contacts);
      console.log(response.data);
    });
  }, []);

  const handleGetAccessToken = async () => {
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
        setResponseMessage("Access token obtained successfully!");
      } else {
        setResponseMessage("Failed to obtain access token.");
      }
    } catch (error) {
      handleError(error, "Error obtaining access token.");
    }
  };

  const handleCreateCustomer = async () => {
    try {
      const response = await axios.post(
        "http://localhost:9090/api/customers/create",
        customerData,
        {
          withCredentials: true,
        }
      );

      // Handle response
      if (response.status === 200) {
        const { customer_id } = response.data;
        console.log(response.data);
        // Save customer ID in localStorage
        localStorage.setItem("customer_id", customer_id);
        setResponseMessage("Customer created successfully!");
      } else {
        setResponseMessage("Failed to create customer.");
      }
    } catch (error) {
      handleError(error, "Error creating customer.");
    }
  };

  const handleGenerateInvoice = async () => {
    try {
      const response = await axios.post(
        "http://localhost:9090/invoice/generate",
        {},
        {
          withCredentials: true,
        }
      );

      // Handle response
      if (response.status === 200) {
        const { invoice_id } = response.data;
        console.log(response.data);
        // Save invoice ID in localStorage
        localStorage.setItem("invoice_id", invoice_id);
        setResponseMessage("Invoice generated successfully!");
      } else {
        setResponseMessage("Failed to generate invoice.");
      }
    } catch (error) {
      handleError(error, "Error generating invoice.");
    }
  };

  const handleError = (error, defaultMessage) => {
    console.error(defaultMessage, error);
    if (error.response) {
      setResponseMessage(
        `Error: ${error.response.data.message || defaultMessage}`
      );
    } else {
      setResponseMessage("Network error. Please check your connection.");
    }
  };

  return (
    <div style={containerStyle}>
      <h1 style={headingStyle}>Zoho Invoice</h1>
      <button onClick={handleGetAccessToken} style={buttonStyle}>
        Get Access Token
      </button>
      {responseMessage && <p style={messageStyle}>{responseMessage}</p>}

      <button onClick={handleCreateCustomer} style={buttonStyle}>
        Create Customer
      </button>

      <button onClick={handleGenerateInvoice} style={buttonStyle}>
        Generate Invoice
      </button>
      {customers.length > 0 && (
        <div>
          <h2>Customers</h2>
          {customers.map((customer) => (
            <div
              key={customer.contact_name}
              style={{
                margin: "10px 0",
                padding: "10px",
              }}
            >
              <table
                style={{
                  width: "100%",
                  border: "1px solid black",
                  borderCollapse: "collapse",
                }}
              >
                <thead>
                  <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Email</th>
                  </tr>
                </thead>
                <tbody>
                  <tr>
                    <td>{customer.id}</td>
                    <td>{customer.contact_name}</td>
                    <td>{customer.email}</td>
                  </tr>
                </tbody>
              </table>
            </div>
          ))}
        </div>
      )}
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
