@GetMapping("/{id}")
public ResponseEntity<?> getItemById(@PathVariable Long id) {
    Optional<items> optionalItem = itemRepository.findById(id);
    if (optionalItem.isPresent()) {
        items item = optionalItem.get();
        String base64Image = item.getImage() != null 
            ? Base64.getEncoder().encodeToString(item.getImage()) 
            : null;

        return ResponseEntity.ok(new HashMap<String, Object>() {{
            put("id", item.getId());
            put("title", item.getTitle());
            put("description", item.getDescription());
            put("image", base64Image); // Image as Base64
        }});
    }
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Item not found");
}



------+----+--
import React, { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { Form, Button } from "react-bootstrap";
import { getItems, createItem, updateItem } from "../api";

const Feed = () => {
  const [title, setTitle] = useState("");
  const [description, setDescription] = useState("");
  const [image, setImage] = useState(null); // For new uploaded file
  const [imagePreview, setImagePreview] = useState(null); // For preview
  const { id } = useParams();
  const navigate = useNavigate();

  useEffect(() => {
    if (id) {
      // Fetch the item details for updating
      const fetchItemForUpdate = async () => {
        try {
          const items = await getItems();
          const itemToEdit = items.find((item) => item.id === parseInt(id));
          if (itemToEdit) {
            setTitle(itemToEdit.title);
            setDescription(itemToEdit.description);

            // Use the Base64 string directly for preview
            if (itemToEdit.image) {
              setImagePreview(`data:image/jpeg;base64,${itemToEdit.image}`);
            }
          }
        } catch (error) {
          console.error("Error fetching item:", error);
        }
      };

      fetchItemForUpdate();
    }
  }, [id]);

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
    formData.append("title", title || "");
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
            accept="image/jpeg, image/png"
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
