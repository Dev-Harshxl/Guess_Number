@GetMapping
public List<Map<String, Object>> getAllItems() {
    List<items> items = itemRepository.findAll();
    List<Map<String, Object>> response = new ArrayList<>();

    for (items item : items) {
        Map<String, Object> itemMap = new HashMap<>();
        itemMap.put("id", item.getId());
        itemMap.put("title", item.getTitle());
        itemMap.put("description", item.getDescription());

        if (item.getImage() != null) {
            // Default to JPEG if the MIME type is not explicitly known
            String base64Image = "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(item.getImage());
            itemMap.put("image", base64Image);
        } else {
            itemMap.put("image", null);
        }

        response.add(itemMap);
    }

    return response;
}
