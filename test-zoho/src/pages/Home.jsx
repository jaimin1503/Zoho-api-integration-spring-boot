import axios from "axios";
import React from "react";

export default function Home() {
  const handleGetCode = () => {
    // Directly redirect the user to the Zoho OAuth URL
    // window.location.href = "http://localhost:9090/zoho/auth";

    axios.get("http://localhost:9090/zoho/auth").then((response) => {
      console.log(response.data);
      window.location.href = response.data;
    });
  };

  return (
    <div
      style={{
        textAlign: "center",
        fontSize: "30px",
        color: "lightblue",
        fontFamily: "Arial, Helvetica, sans-serif",
        fontWeight: "bold",
        letterSpacing: "1px",
        lineHeight: "1.5",
        textTransform: "uppercase",
        marginBottom: "20px",
        padding: "20px",
        borderRadius: "5px",
        backgroundColor: "white",
        boxShadow: "0 0 10px rgba(0,0,0,0.2)",
        flexDirection: "column",
        display: "flex",
        justifyContent: "center",
        alignItems: "center",
      }}
    >
      Home
      <button
        style={{
          backgroundColor: "lightblue",
          color: "white",
          borderRadius: "5px",
          padding: "10px",
          marginTop: "20px",
          fontSize: "20px",
        }}
        onClick={handleGetCode} // Call the redirect function
      >
        Get Code
      </button>
    </div>
  );
}
