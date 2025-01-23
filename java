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




// ModalComponent.js
import React from 'react';
import Button from 'react-bootstrap/Button';
import Modal from 'react-bootstrap/Modal';

const ModalComponent = ({ show, handleClose }) => {
  return (
    <Modal
      show={show}
      onHide={handleClose}
      backdrop="static"
      keyboard={false}
    >
      <Modal.Header closeButton>
        <Modal.Title>Modal title</Modal.Title>
      </Modal.Header>
      <Modal.Body>
        I will not close if you click outside me. Do not even try to press
        escape key.
      </Modal.Body>
      <Modal.Footer>
        <Button variant="secondary" onClick={handleClose}>
          Close
        </Button>
        <Button variant="primary">Understood</Button>
      </Modal.Footer>
    </Modal>
  );
};

export default ModalComponent;




// ParentComponent.js
import React, { useState } from 'react';
import ModalComponent from './ModalComponent'; // Import the modal component
import ButtonComponent from './ButtonComponent'; // Import the button component

const ParentComponent = () => {
  const [show, setShow] = useState(false);

  const handleShow = () => setShow(true);
  const handleClose = () => setShow(false);

  return (
    <div>
      <h1>Parent Component</h1>
      {/* Pass handleShow to ButtonComponent */}
      <ButtonComponent handleShow={handleShow} />
      {/* Pass show and handleClose to ModalComponent */}
      <ModalComponent show={show} handleClose={handleClose} />
    </div>
  );
};

export default ParentComponent;


spring.datasource.url=jdbc:mysql://localhost:3306/your_database_name
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# Hibernate Configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect



package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

package com.example.demo.repository;

import com.example.demo.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}





package com.example.demo.controller;

import com.example.demo.entity.Item;
import com.example.demo.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/items")
public class ItemController {

    @Autowired
    private ItemRepository itemRepository;

    // Fetch all items
    @GetMapping
    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    // Create a new item
    @PostMapping
    public Item createItem(@RequestBody Item item) {
        return itemRepository.save(item);
    }

    // Delete an item by ID
    @DeleteMapping("/{id}")
    public void deleteItem(@PathVariable Long id) {
        itemRepository.deleteById(id);
    }
}


_--------------


package com.example.demo.repository;

import com.example.demo.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    // JPQL query to find users by their name
    @Query("SELECT u FROM User u WHERE u.name = :name")
    List<User> findUsersByName(@Param("name") String name);
}





// src/App.js
import React, { useEffect, useState } from 'react';
import { getItems, createItem, deleteItem } from './services/api';

const App = () => {
  const [items, setItems] = useState([]);
  const [newItem, setNewItem] = useState({ title: '', description: '' });

  useEffect(() => {
    fetchItems();
  }, []);

  const fetchItems = async () => {
    const response = await getItems();
    setItems(response.data);
  };

  const handleCreate = async () => {
    await createItem(newItem);
    setNewItem({ title: '', description: '' });
    fetchItems();
  };

  const handleDelete = async (id) => {
    await deleteItem(id);
    fetchItems();
  };

  return (
    <div>
      <h1>Items</h1>
      <ul>
        {items.map((item) => (
          <li key={item.id}>
            {item.title} - {item.description}{' '}
            <button onClick={() => handleDelete(item.id)}>Delete</button>
          </li>
        ))}
      </ul>
      <div>
        <input
          type="text"
          placeholder="Title"
          value={newItem.title}
          onChange={(e) => setNewItem({ ...newItem, title: e.target.value })}
        />
        <input
          type="text"
          placeholder="Description"
          value={newItem.description}
          onChange={(e) => setNewItem({ ...newItem, description: e.target.value })}
        />
        <button onClick={handleCreate}>Add Item</button>
      </div>
    </div>
  );
};

export default App;






package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")  // Enable CORS for all endpoints
                .allowedOrigins("http://localhost:3000")  // Allow requests from React on port 3000
                .allowedMethods("GET", "POST", "PUT", "DELETE")  // Specify allowed HTTP methods
                .allowedHeaders("*");  // Allow all headers
    }
}




// src/App.js
import React, { useEffect, useState } from 'react';
import { getItems, createItem, deleteItem } from './services/api';

const App = () => {
  const [items, setItems] = useState([]);
  const [newItem, setNewItem] = useState({ title: '', description: '' });
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [deleteId, setDeleteId] = useState(null);

  useEffect(() => {
    fetchItems();
  }, []);

  const fetchItems = async () => {
    const response = await getItems();
    setItems(response.data);
  };

  const handleCreate = async () => {
    await createItem(newItem);
    setNewItem({ title: '', description: '' });
    fetchItems();
  };

  const openDeleteModal = (id) => {
    setDeleteId(id);
    setIsModalOpen(true);
  };

  const closeDeleteModal = () => {
    setIsModalOpen(false);
    setDeleteId(null);
  };

  const confirmDelete = async () => {
    if (deleteId) {
      await deleteItem(deleteId);
      fetchItems();
      closeDeleteModal();
    }
  };

  return (
    <div>
      <h1>Items</h1>
      <ul>
        {items.map((item) => (
          <li key={item.id}>
            {item.title} - {item.description}{' '}
            <button onClick={() => openDeleteModal(item.id)}>Delete</button>
          </li>
        ))}
      </ul>
      <div>
        <input
          type="text"
          placeholder="Title"
          value={newItem.title}
          onChange={(e) => setNewItem({ ...newItem, title: e.target.value })}
        />
        <input
          type="text"
          placeholder="Description"
          value={newItem.description}
          onChange={(e) => setNewItem({ ...newItem, description: e.target.value })}
        />
        <button onClick={handleCreate}>Add Item</button>
      </div>

      {/* Modal */}
      {isModalOpen && (
        <div style={modalStyle}>
          <p>Are you sure you want to delete this item?</p>
          <button onClick={confirmDelete}>Yes</button>
          <button onClick={closeDeleteModal}>Cancel</button>
        </div>
      )}
    </div>
  );
};

// Modal Styling (You can customize this)
const modalStyle = {
  position: 'fixed',
  top: '50%',
  left: '50%',
  transform: 'translate(-50%, -50%)',
  backgroundColor: 'white',
  padding: '20px',
  border: '1px solid #ccc',
  borderRadius: '5px',
  zIndex: 1000,
};

export default App;
_---------3++++-----+++--+
// src/App.js
import React, { useEffect, useState } from 'react';
import { getItems, createItem, deleteItem } from './services/api';
import Modal from './components/Modal';

const App = () => {
  const [items, setItems] = useState([]);
  const [newItem, setNewItem] = useState({ title: '', description: '' });
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [deleteId, setDeleteId] = useState(null);

  useEffect(() => {
    fetchItems();
  }, []);

  const fetchItems = async () => {
    const response = await getItems();
    setItems(response.data);
  };

  const handleCreate = async () => {
    await createItem(newItem);
    setNewItem({ title: '', description: '' });
    fetchItems();
  };

  const openDeleteModal = (id) => {
    setDeleteId(id);
    setIsModalOpen(true);
  };

  const closeDeleteModal = () => {
    setIsModalOpen(false);
    setDeleteId(null);
  };

  const confirmDelete = async () => {
    if (deleteId) {
      await deleteItem(deleteId);
      fetchItems();
      closeDeleteModal();
    }
  };

  return (
    <div>
      <h1>Items</h1>
      <ul>
        {items.map((item) => (
          <li key={item.id}>
            {item.title} - {item.description}{' '}
            <button onClick={() => openDeleteModal(item.id)}>Delete</button>
          </li>
        ))}
      </ul>
      <div>
        <input
          type="text"
          placeholder="Title"
          value={newItem.title}
          onChange={(e) => setNewItem({ ...newItem, title: e.target.value })}
        />
        <input
          type="text"
          placeholder="Description"
          value={newItem.description}
          onChange={(e) => setNewItem({ ...newItem, description: e.target.value })}
        />
        <button onClick={handleCreate}>Add Item</button>
      </div>

      {/* Modal Component */}
      <Modal
        isOpen={isModalOpen}
        onConfirm={confirmDelete}
        onCancel={closeDeleteModal}
      />
    </div>
  );
};

export default App;




hhhhhhhhhhhhh
// src/components/Modal.js
import React from 'react';

const Modal = ({ isOpen, onConfirm, onCancel }) => {
  if (!isOpen) return null;

  return (
    <div style={modalStyle}>
      <p>Are you sure you want to delete this item?</p>
      <button onClick={onConfirm}>Yes</button>
      <button onClick={onCancel}>Cancel</button>
    </div>
  );
};

// Modal Styling
const modalStyle = {
  position: 'fixed',
  top: '50%',
  left: '50%',
  transform: 'translate(-50%, -50%)',
  backgroundColor: 'white',
  padding: '20px',
  border: '1px solid #ccc',
  borderRadius: '5px',
  zIndex: 1000,
};

export default Modal;






import React, { useState, useEffect } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import { createItem, updateItem } from './services/api';

const FormPage = () => {
  const location = useLocation();
  const navigate = useNavigate();
  const [formData, setFormData] = useState({ title: '', description: '' });

  useEffect(() => {
    if (location.state) {
      // If state exists, pre-fill form with item data (edit mode)
      setFormData(location.state);
    }
  }, [location.state]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (location.state) {
      // Edit mode: update existing item
      await updateItem(location.state.id, formData);
    } else {
      // Create mode: add new item
      await createItem(formData);
    }
    navigate('/'); // Redirect to main page
  };

  return (
    <div>
      <h1>{location.state ? 'Edit Item' : 'Create Item'}</h1>
      <form onSubmit={handleSubmit}>
        <div>
          <label>Title:</label>
          <input
            type="text"
            name="title"
            value={formData.title}
            onChange={handleChange}
          />
        </div>
        <div>
          <label>Description:</label>
          <input
            type="text"
            name="description"
            value={formData.description}
            onChange={handleChange}
          />
        </div>
        <button type="submit">{location.state ? 'Update' : 'Create'}</button>
      </form>
    </div>
  );
};

export default FormPage;


