@PutMapping("/{id}")
public ResponseEntity<?> updateItem(
        @PathVariable Long id,
        @RequestParam("title") String title,
        @RequestParam("description") String description,
        @RequestParam(value = "image", required = false) MultipartFile image) {
    try {
        if (!itemRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Item with ID " + id + " not found.");
        }

        // Fetch existing item
        items existingItem = itemRepository.findById(id).orElse(null);

        if (existingItem != null) {
            // Update fields
            existingItem.setTitle(title);
            existingItem.setDescription(description);

            // Update image only if a new image is provided
            if (image != null && !image.isEmpty()) {
                byte[] imageBytes = processImage(image);
                existingItem.setImage(imageBytes);
            }

            // Save updated item
            itemRepository.save(existingItem);
            return ResponseEntity.ok(existingItem);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Item with ID " + id + " not found.");
    } catch (IOException e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error processing image: " + e.getMessage());
    } catch (Exception e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Error: " + e.getMessage());
    }
}
