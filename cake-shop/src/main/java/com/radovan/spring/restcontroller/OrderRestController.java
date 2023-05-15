package com.radovan.spring.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.radovan.spring.dto.OrderDto;
import com.radovan.spring.dto.OrderItemDto;
import com.radovan.spring.exceptions.DataNotValidatedException;
import com.radovan.spring.form.OrderForm;
import com.radovan.spring.service.OrderItemService;
import com.radovan.spring.service.OrderService;

@RestController
@RequestMapping(value = "/api/orders")
public class OrderRestController {

	@Autowired
	private OrderService orderService;

	@Autowired
	private OrderItemService orderItemService;

	@Secured(value = "ROLE_USER")
	@RequestMapping(value = "/createOrder", method = RequestMethod.POST)
	public ResponseEntity<String> createOrder(@Validated @RequestBody OrderForm form, Errors errors) {
		if (errors.hasErrors()) {
			Error error = new Error("Data not validated!");
			throw new DataNotValidatedException(error);
		}
		orderService.addOrder(form);
		return ResponseEntity.ok().body("Order completed");
	}

	@RequestMapping(value = "/lastOrder", method = RequestMethod.GET)
	public ResponseEntity<OrderDto> getLastOrderPrice() {
		OrderDto order = orderService.getLastOrder();
		return ResponseEntity.ok().body(order);
	}

	@RequestMapping(value = "/allItemsByOrderId/{orderId}", method = RequestMethod.GET)
	public ResponseEntity<List<OrderItemDto>> getAllItemsByOrderId(@PathVariable("orderId") Integer orderId) {
		List<OrderItemDto> allItems = orderItemService.listAllByOrderId(orderId);
		return ResponseEntity.ok().body(allItems);
	}

}
