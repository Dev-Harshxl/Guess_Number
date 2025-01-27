import React, { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { Form, Button } from "react-bootstrap";
import { getItems, createItem, updateItem } from "../api"; // Your API functions

const Feed = () => {
  const [title, setTitle] = useState("");
  const [description, setDescription] = useState("");
  const [image, setImage] = useState(null); // For new uploaded file
  const [imagePreview, setImagePreview] = useState(null); // For preview (Base64 URL)
  const { id } = useParams();
  const navigate = useNavigate();

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

            // Generate Base64 preview if the image exists
            if (itemToEdit.image && itemToEdit.image.data) {
              const base64String = toBase64(
                itemToEdit.image.data, // Assuming backend sends binary data in `image.data`
                "image/jpeg" // Default MIME type; adjust based on your backend
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
    if (binaryData) {
      return `data:${mimeType};base64,${btoa(
        String.fromCharCode(...new Uint8Array(binaryData))
      )}`;
    }
    return null;
  };

  const handleImageChange = (e) => {
    const file = e.target.files[0];
    setImage(file);

    // Update preview dynamically for the new file
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
          <Form.Control
            type="file"
            onChange={handleImageChange}
            accept="image/jpeg, image/png, image/jpg"
          />
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
