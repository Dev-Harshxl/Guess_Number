huconst express = require('express');
const mysql = require('mysql');

const app = express();
const port = 5000;

// MySQL connection setup
const db = mysql.createConnection({
  host: 'localhost',
  user: 'your_username',
  password: 'your_password',
  database: 'your_database',
});

db.connect((err) => {
  if (err) {
    console.error('Database connection failed:', err.stack);
    return;
  }
  console.log('Connected to the database.');
});

// Define an API endpoint
app.get('/api/data', (req, res) => {
  const sql = 'SELECT * FROM your_table';
  db.query(sql, (err, results) => {
    if (err) {
      res.status(500).send(err);
    } else {
      res.json(results);
    }
  });
});

app.listen(port, () => {
  console.log(`Server running at http://localhost:${port}`);
});


ALTER USER 'your_username'@'localhost' IDENTIFIED WITH mysql_native_password BY 'your_password';
FLUSH PRIVILEGES;



import React, { useState, useEffect } from 'react';

const App = () => {
  const [data, setData] = useState([]); // State to hold the API data
  const [error, setError] = useState(null); // State to handle errors

  useEffect(() => {
    fetch('http://localhost:5000/api/data') // Replace with your API endpoint
      .then((response) => {
        if (!response.ok) {
          throw new Error('Network response was not ok');
        }
        return response.json(); // Parse JSON data
      })
      .then((data) => {
        setData(data); // Update state with fetched data
      })
      .catch((error) => {
        setError(error.message); // Handle errors
      });
  }, []); // Empty dependency array ensures it runs only once

  if (error) {
    return <div>Error: {error}</div>; // Display error message if API call fails
  }

  return (
    <div>
      <h1>Fetched Data</h1>
      <ul>
        {data.map((item) => (
          <li key={item.id}>{item.name}</li> // Replace `id` and `name` with your table's columns
        ))}
      </ul>
    </div>
  );
};

export default App;

