import React, { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { Form, Button } from "react-bootstrap";
import { getItems, createItem, updateItem } from "../api"; // Your API functions

const Feed = () => {
  const [title, setTitle] = useState("");
  const [description, setDescription] = useState("");
  const [image, setImage] = useState(null); // For uploaded file
  const [imagePreview, setImagePreview] = useState(null); // For image preview
  const { id } = useParams(); // Get ID from URL parameter if available
  const navigate = useNavigate(); // Used to navigate programmatically

  useEffect(() => {
    if (id) {
      // Fetch the item details for updating if an ID is present
      const fetchItemForUpdate = async () => {
        try {
          const items = await getItems();
          const itemToEdit = items.find((item) => item.id === parseInt(id));
          if (itemToEdit) {
            setTitle(itemToEdit.title);
            setDescription(itemToEdit.description);

            // Convert binary image data to Base64 for preview
            if (itemToEdit.image) {
              const base64String = toBase64(
                itemToEdit.image.data, // Assuming `image.data` contains binary data
                itemToEdit.image.type || "image/jpeg" // Default MIME type
              );
              setImagePreview(base64String);
            }
          }
        } catch (error) {
          console.error("Error fetching item:", error);
        }
      };

      fetchItemForUpdate();
    }
  }, [id]);

  const toBase64 = (binaryData, mimeType = "image/jpeg") => {
    return `data:${mimeType};base64,${btoa(
      String.fromCharCode(...new Uint8Array(binaryData))
    )}`;
  };

  const handleImageChange = (e) => {
    const file = e.target.files[0];
    setImage(file);

    // Show image preview
    const reader = new FileReader();
    reader.onloadend = () => {
      setImagePreview(reader.result);
    };
    if (file) {
      reader.readAsDataURL(file);
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    const formData = new FormData();
    formData.append("title", title || ""); // Ensure title isn't undefined
    formData.append("description", description || "");
    if (image) {
      formData.append("image", image);
    }
    try {
      if (id) {
        await updateItem(id, formData);
      } else {
        await createItem(formData);
      }
      navigate("/");
    } catch (error) {
      console.error("Error submitting form:", error);
    }
  };

  return (
    <div>
      <h2>{id ? "Update Item" : "Create Item"}</h2>
      <Form onSubmit={handleSubmit}>
        <Form.Group controlId="title">
          <Form.Label>Title</Form.Label>
          <Form.Control
            type="text"
            placeholder="Enter title"
            value={title}
            onChange={(e) => setTitle(e.target.value)}
            required
          />
        </Form.Group>

        <Form.Group controlId="description">
          <Form.Label>Description</Form.Label>
          <Form.Control
            as="textarea"
            rows={3}
            placeholder="Enter description"
            value={description}
            onChange={(e) => setDescription(e.target.value)}
            required
          />
        </Form.Group>

        <Form.Group controlId="image">
          <Form.Label>Upload Image</Form.Label>
          <Form.Control type="file" onChange={handleImageChange} accept="image/*" />
          {/* Image Preview */}
          {imagePreview && (
            <div style={{ marginTop: "10px" }}>
              <img
                src={imagePreview}
                alt="Preview"
                width={100}
                height={100}
                style={{ border: "1px solid #ddd" }}
              />
            </div>
          )}
        </Form.Group>

        <Button variant="primary" type="submit">
          {id ? "Update Item" : "Create Item"}
        </Button>
      </Form>
    </div>
  );
};

export default Feed;