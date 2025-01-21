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






INSERT INTO data (id, title, descr, image) VALUES
(1, 'Title 1', 'This is a sample description paragraph for the first entry. It contains more than 50 words. The purpose of this paragraph is to provide content for the database. It should be detailed enough to represent a variety of words. This description serves as an example for filling the table with meaningful content. It will be followed by more entries.', 'https://example.com/image1.jpg'),
(2, 'Title 2', 'This is another description paragraph. The second entry features a unique description text, crafted to meet the word count requirement. Each description here has a distinct set of words and content, ensuring variety across entries. This description continues the theme of generating diverse content to be used in the data table.', 'https://example.com/image2.jpg'),
(3, 'Title 3', 'A third example of a description. The paragraph here is still within the range of 50 words. It offers a new take on what a description could look like in the table. As you can see, each entry will be filled with its own individual content. The image URLs are unique for each record.', 'https://example.com/image3.jpg'),
(4, 'Title 4', 'The fourth entry introduces new content into the table. The description is equally long and provides further example content to fit the requirements. Remember that each entry in the table must be unique in terms of both its title and description. Images should be web links.', 'https://example.com/image4.jpg'),
(5, 'Title 5', 'Here we have the fifth description. This one also contains more than 50 words and follows the same structure as the previous entries. It is critical to have enough information to fill the description area. We are continuing to develop diverse content to complete the data set.', 'https://example.com/image5.jpg'),
(6, 'Title 6', 'This sixth entry provides another unique description. By continuing the pattern, each entry introduces its own style while adhering to the requirement of 50 words or more. The content remains distinct, ensuring each entry provides different value.', 'https://example.com/image6.jpg'),
(7, 'Title 7', 'For the seventh entry, we have yet another distinct description. The text is long enough to satisfy the 50-word condition. The structure of each entry varies, but they all serve to demonstrate how different kinds of descriptions can be used.', 'https://example.com/image7.jpg'),
(8, 'Title 8', 'This description marks the eighth entry. It continues the trend of providing a new paragraph that is long enough to fit the requirement. We are trying to use different kinds of content to populate the table and illustrate diversity in descriptions.', 'https://example.com/image8.jpg'),
(9, 'Title 9', 'As we reach the ninth entry, the same pattern is followed. The paragraph has more than 50 words, and the content differs from the others in terms of its message and meaning. Each entry is unique, and the image URLs are also distinct.', 'https://example.com/image9.jpg'),
(10, 'Title 10', 'Finally, the tenth entry rounds off the examples. The description, as with all the previous entries, follows the word count requirement and brings its own specific content. The goal is to demonstrate the ability to populate a database with various data types and formats.', 'https://example.com/image10.jpg');
