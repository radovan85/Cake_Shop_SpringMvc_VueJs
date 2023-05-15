package com.radovan.spring.restcontroller;

import java.text.DecimalFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.radovan.spring.dto.CartDto;
import com.radovan.spring.dto.CartItemDto;
import com.radovan.spring.dto.CustomerDto;
import com.radovan.spring.dto.ProductDto;
import com.radovan.spring.dto.UserDto;
import com.radovan.spring.entity.UserEntity;
import com.radovan.spring.service.CartItemService;
import com.radovan.spring.service.CartService;
import com.radovan.spring.service.CustomerService;
import com.radovan.spring.service.ProductService;
import com.radovan.spring.service.UserService;

@RestController
@RequestMapping(value = "/api/cart")
public class CartRestController {
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private CartService cartService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private CartItemService cartItemService;
	
	@Autowired
	private UserService userService;
	
	private static final DecimalFormat decfor = new DecimalFormat("0.00");
	
	

	@RequestMapping(value = "/add/{productId}", method = RequestMethod.POST)
	public ResponseEntity<String> addCartItem(@PathVariable(value = "productId") Integer productId) {
		UserEntity user = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		CustomerDto customer = customerService.getCustomerByUserId(user.getId());
		CartDto cart = cartService.getCartByCartId(customer.getCartId());
		List<Integer> cartItemIds = cart.getCartItemsIds();
		ProductDto product = productService.getProductById(productId);
		for (int i = 0; i < cartItemIds.size(); i++) {
			Integer itemId = cartItemIds.get(i);
			CartItemDto cartItem = cartItemService.getCartItem(itemId);
			if (product.getProductId().equals(cartItem.getProductId())) {
				cartItem.setQuantity(cartItem.getQuantity() + 1);
				ProductDto tempProduct = productService.getProductById(cartItem.getProductId());
				cartItem.setPrice(cartItem.getQuantity() * tempProduct.getPrice());
				Double itemPrice = Double.valueOf(decfor.format(cartItem.getPrice()));
				cartItem.setPrice(itemPrice);
				cartItemService.addCartItem(cartItem);
				cartService.refreshCartState(cart.getCartId());
				return ResponseEntity.ok().body("Item added to cart!");
			}
		}
		CartItemDto cartItem = new CartItemDto();
		cartItem.setQuantity(1);
		cartItem.setProductId(productId);
		cartItem.setPrice(product.getPrice() * 1);
		Double itemPrice = Double.valueOf(decfor.format(cartItem.getPrice()));
		cartItem.setPrice(itemPrice);
		cartItem.setCartId(cart.getCartId());
		cartItemService.addCartItem(cartItem);
		cartService.refreshCartState(cart.getCartId());
		return ResponseEntity.ok().body("Item added to cart!");
	}
	
	@RequestMapping(value="/allCartItems",method = RequestMethod.GET)
	public ResponseEntity<List<CartItemDto>> allCartItems(){
		UserDto user = userService.getCurrentUser();
		CustomerDto customer = customerService.getCustomerByUserId(user.getId());
		List<CartItemDto> allCartItems = cartItemService.listAllByCartId(customer.getCartId());
		return ResponseEntity.ok().body(allCartItems);
	}
	
	@RequestMapping(value="/getGrandTotal",method=RequestMethod.GET)
	public ResponseEntity<Double> getGrandTotal(){
		UserDto user = userService.getCurrentUser();
		CustomerDto customer = customerService.getCustomerByUserId(user.getId());
		Double grandTotal = cartService.calculateGrandTotal(customer.getCartId());
		return ResponseEntity.ok().body(grandTotal);
	}
	
	@RequestMapping(value="/deleteItem/{itemId}",method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteItem(@PathVariable ("itemId") Integer itemId){
		cartItemService.removeCartItem(itemId);
		return ResponseEntity.ok().body("Item with id " + itemId + " permanently removed");
	}
	
	@RequestMapping(value="/cartVerification",method = RequestMethod.GET)
	public ResponseEntity<String> cartVerification(){
		UserDto user = userService.getCurrentUser();
		CustomerDto customer = customerService.getCustomerByUserId(user.getId());
		cartService.validateCart(customer.getCartId());
		return ResponseEntity.ok().body("Cart verification passed!");
	}
}
