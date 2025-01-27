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
