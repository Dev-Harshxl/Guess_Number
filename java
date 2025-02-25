@PutMapping("/{id}")
public ResponseEntity<?> updateCartItem(
        @PathVariable Long id,
        @RequestParam("quantity") int quantity) {

    Optional<PhoneCart> cartItemOptional = cartRepository.findById(id);

    if (cartItemOptional.isPresent()) {
        PhoneCart cartItem = cartItemOptional.get();
        Phone phone = cartItem.getPhone();

        int previousQuantity = cartItem.getQuantity();
        int quantityDifference = quantity - previousQuantity;

        // Ensure stock is available before increasing quantity
        if (quantityDifference > 0 && phone.getQuantity() < quantityDifference) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Not enough stock available.");
        }

        // Update cart item quantity
        cartItem.setQuantity(quantity);
        cartItem.setTotalPrice(quantity * phone.getPrice());
        cartRepository.save(cartItem);

        // Adjust stock correctly
        phone.setQuantity(phone.getQuantity() - quantityDifference);
        phoneRepository.save(phone);

        return ResponseEntity.ok(cartItem);
    }

    return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body("Cart item with ID " + id + " not found.");
}
