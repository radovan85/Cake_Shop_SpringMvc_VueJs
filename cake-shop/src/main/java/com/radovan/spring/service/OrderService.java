package com.radovan.spring.service;

import java.util.List;

import com.radovan.spring.dto.OrderDto;
import com.radovan.spring.form.OrderForm;

public interface OrderService {

	OrderDto addOrder(OrderForm form);

	OrderDto getLastOrder();

	void deleteOrder(Integer orderId);

	List<OrderDto> listAll();

	List<OrderDto> listAllByCustomerId(Integer customerId);

	OrderDto getOrderById(Integer orderId);
}
