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






import { useDispatch, useSelector } from "react-redux";
import { fetchItem, updateCartItem } from "../redux/crudAction";
import { useEffect, useState } from "react";
import { toast } from "react-toastify"; // Assuming you're using react-toastify for toast notifications.

const CartTable = ({ phone, i }) => {
  const {
    id,
    about,
    category,
    company,
    description,
    price,
    image,
    name,
    quantity,
  } = phone;

  const dispatch = useDispatch();
  const stock = useSelector((state) => state.items);

  const [cartquantity, setQuantity] = useState(quantity); // Local state for cart quantity

  // Fetch the available stock using the index `i`
  const availableStock = stock[i]?.quantity || 0;

  useEffect(() => {
    dispatch(fetchItem());
  }, [dispatch]);

  const addMore = () => {
    if (cartquantity >= availableStock) {
      toast.warning(`Only ${availableStock} items available in stock.`);
      return;
    }

    const newQuantity = cartquantity + 1;
    dispatch(updateCartItem(id, newQuantity));
    setQuantity(newQuantity);
  };

  const delCart = () => {
    if (cartquantity <= 1) return; // Prevent negative values

    const newQuantity = cartquantity - 1;
    dispatch(updateCartItem(id, newQuantity));
    setQuantity(newQuantity);
  };

  return (
    <div className="cartRow">
      <div className="cartImagebox">
        <img className="cartImage" src={image} alt="" />
      </div>
      <div className="cartData">
        <div className="cartBasic">
          <p
            style={{
              textTransform: "uppercase",
              fontSize: "22px",
              fontWeight: "600",
            }}
          >
            {company} {name}
          </p>
          <p style={{ textTransform: "capitalize", fontSize: "14px" }}>
            {description}
          </p>
          <p style={{ textTransform: "capitalize", fontSize: "14px" }}>
            {category}
          </p>
        </div>
      </div>
      <div className="cartQuantity">
        <div>
          {cartquantity === 0 ? (
            <button className="addCart" onClick={() => setQuantity(1)}></button>
          ) : (
            <div className="quantity">
              <div className="quantityControl">
                <button onClick={delCart}>-</button>
                <span>{cartquantity}</span>
                <button onClick={addMore}>+</button>
              </div>
            </div>
          )}
        </div>
      </div>
      <div className="cartPrice">
        <p>{price} /-</p>
      </div>
    </div>
  );
};

export default CartTable;
