package com.radovan.spring.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.radovan.spring.dto.CartDto;
import com.radovan.spring.dto.CategoryDto;
import com.radovan.spring.dto.CustomerDto;
import com.radovan.spring.dto.OrderDto;
import com.radovan.spring.dto.ProductDto;
import com.radovan.spring.dto.UserDto;
import com.radovan.spring.exceptions.DataNotValidatedException;
import com.radovan.spring.service.CartItemService;
import com.radovan.spring.service.CartService;
import com.radovan.spring.service.CategoryService;
import com.radovan.spring.service.CustomerService;
import com.radovan.spring.service.OrderItemService;
import com.radovan.spring.service.OrderService;
import com.radovan.spring.service.ProductService;
import com.radovan.spring.service.UserService;

@RestController
@RequestMapping(value = "/api/admin")
public class AdminRestController {

	@Autowired
	private ProductService productService;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private UserService userService;

	@Autowired
	private CartService cartService;

	@Autowired
	private OrderItemService orderItemService;

	@Autowired
	private OrderService orderService;

	@Autowired
	private CartItemService cartItemService;

	@RequestMapping(value = "/storeCategory", method = RequestMethod.POST)
	public ResponseEntity<String> addCategory(@Validated @RequestBody CategoryDto category, Errors errors) {

		if (errors.hasErrors()) {
			System.out.println("Errors detected");
			Error error = new Error("Not validated");
			throw new DataNotValidatedException(error);
		}
		CategoryDto storedCategory = categoryService.addCategory(category);
		return ResponseEntity.ok().body("Category stored with id " + storedCategory.getCategoryId());

	}

	@RequestMapping(value = "/updateCategory/{categoryId}", method = RequestMethod.PUT)
	public ResponseEntity<String> updateCategory(@Validated @RequestBody CategoryDto category,
			@PathVariable("categoryId") Integer categoryId, Errors errors) {

		if (errors.hasErrors()) {
			System.out.println("Errors detected");
			Error error = new Error("Not validated");
			throw new DataNotValidatedException(error);
		}

		CategoryDto updatedCategory = categoryService.updateCategory(categoryId, category);
		return ResponseEntity.ok().body("Category updated with id " + updatedCategory.getCategoryId());
	}

	@RequestMapping(value = "/deleteCategory/{categoryId}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteCategory(@PathVariable("categoryId") Integer categoryId) {

		List<ProductDto> allProducts = productService.listAllByCategoryId(categoryId);
		allProducts.forEach(product -> {
			cartItemService.eraseAllByProductId(product.getProductId());
			productService.deleteProduct(product.getProductId());
		});
		categoryService.deleteCategory(categoryId);
		return ResponseEntity.ok().body("Category with id " + categoryId + " is permanently deleted");
	}

	@RequestMapping(value = "/storeProduct", method = RequestMethod.POST)
	public ResponseEntity<String> addProduct(@Validated @RequestBody ProductDto product, Errors errors) {

		if (errors.hasErrors()) {
			Error error = new Error("Data not validated!");
			throw new DataNotValidatedException(error);
		}

		ProductDto storedProduct = productService.addProduct(product);
		return ResponseEntity.ok().body("Product stored with id " + storedProduct.getProductId());
	}

	@RequestMapping(value = "/updateProduct/{productId}", method = RequestMethod.PUT)
	public ResponseEntity<String> updateProduct(@Validated @RequestBody ProductDto product,
			@PathVariable("productId") Integer productId, Errors errors) {

		if (errors.hasErrors()) {
			Error error = new Error("Data not validated!");
			throw new DataNotValidatedException(error);
		}

		ProductDto updatedProduct = productService.updateProduct(productId, product);
		return ResponseEntity.ok().body("Product with id " + updatedProduct.getProductId() + " is updated!");
	}

	@RequestMapping(value = "/deleteProduct/{productId}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteProduct(@PathVariable("productId") Integer productId) {

		cartItemService.eraseAllByProductId(productId);
		productService.deleteProduct(productId);
		return ResponseEntity.ok("Product with id " + productId + " is permanently deleted!");
	}

	@RequestMapping(value = "/allCustomers", method = RequestMethod.GET)
	public ResponseEntity<List<CustomerDto>> getAllCustomers() {
		List<CustomerDto> allCustomers = customerService.getAllCustomers();
		return ResponseEntity.ok().body(allCustomers);
	}

	@RequestMapping(value = "/allUsers", method = RequestMethod.GET)
	public ResponseEntity<List<UserDto>> getAllUsers() {
		List<UserDto> allUsers = userService.listAllUsers();
		return ResponseEntity.ok().body(allUsers);
	}

	@RequestMapping(value = "/suspendUser/{userId}", method = RequestMethod.GET)
	public ResponseEntity<String> suspendUser(@PathVariable("userId") Integer userId) {
		userService.suspendUser(userId);
		return ResponseEntity.ok().body("User with id " + userId + " is suspended");
	}

	@RequestMapping(value = "/reactivateUser/{userId}", method = RequestMethod.GET)
	public ResponseEntity<String> reactivateUser(@PathVariable("userId") Integer userId) {
		userService.reactivateUser(userId);
		return ResponseEntity.ok().body("User with id " + userId + " is reactivated");
	}

	@RequestMapping(value = "/deleteCustomer/{customerId}", method = RequestMethod.DELETE)
	public ResponseEntity<String> removeCustomer(@PathVariable("customerId") Integer customerId) {
		CustomerDto customer = customerService.getCustomer(customerId);
		CartDto cart = cartService.getCartByCartId(customer.getCartId());
		UserDto user = userService.getUserById(customer.getUserId());

		List<OrderDto> allOrders = orderService.listAllByCustomerId(customerId);
		allOrders.forEach((order) -> {
			orderItemService.deleteAllByOrderId(order.getOrderId());
			orderService.deleteOrder(order.getOrderId());
		});

		cartItemService.eraseAllCartItems(cart.getCartId());
		customerService.resetCustomer(customerId);
		cartService.deleteCart(cart.getCartId());
		customerService.deleteCustomer(customerId);
		userService.deleteUser(user.getId());
		return ResponseEntity.ok().body("Customer with id " + customerId + " is permanently deleted");
	}

	@RequestMapping(value = "/allOrders", method = RequestMethod.GET)
	public ResponseEntity<List<OrderDto>> getAllOrders() {
		List<OrderDto> allOrders = orderService.listAll();
		return ResponseEntity.ok().body(allOrders);
	}

	@RequestMapping(value = "/orderDetails/{orderId}", method = RequestMethod.GET)
	public ResponseEntity<OrderDto> getOrderDetails(@PathVariable("orderId") Integer orderId) {
		OrderDto order = orderService.getOrderById(orderId);
		return ResponseEntity.ok().body(order);
	}
	
	@RequestMapping(value = "/deleteOrder/{orderId}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteOrder(@PathVariable ("orderId") Integer orderId){
		orderItemService.deleteAllByOrderId(orderId);
		orderService.deleteOrder(orderId);
		return ResponseEntity.ok().body("Order with id " + orderId + " is permanently deleted");
	}

}
