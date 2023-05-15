package com.radovan.spring.service.impl;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.radovan.spring.converter.TempConverter;
import com.radovan.spring.dto.OrderDto;
import com.radovan.spring.entity.CartEntity;
import com.radovan.spring.entity.CartItemEntity;
import com.radovan.spring.entity.CustomerEntity;
import com.radovan.spring.entity.OrderEntity;
import com.radovan.spring.entity.OrderItemEntity;
import com.radovan.spring.entity.ProductEntity;
import com.radovan.spring.entity.ShippingAddressEntity;
import com.radovan.spring.entity.UserEntity;
import com.radovan.spring.exceptions.InstanceNotExistException;
import com.radovan.spring.form.OrderForm;
import com.radovan.spring.repository.CartItemRepository;
import com.radovan.spring.repository.CustomerRepository;
import com.radovan.spring.repository.OrderItemRepository;
import com.radovan.spring.repository.OrderRepository;
import com.radovan.spring.repository.ShippingAddressRepository;
import com.radovan.spring.service.CartService;
import com.radovan.spring.service.OrderService;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

	@Autowired
	private ModelMapper mapper;

	@Autowired
	private TempConverter tempConverter;

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private ShippingAddressRepository addressRepository;

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private OrderItemRepository orderItemRepository;

	@Autowired
	private CartItemRepository cartItemRepository;

	@Autowired
	private CartService cartService;

	@Override
	public OrderDto addOrder(OrderForm form) {
		// TODO Auto-generated method stub
		UserEntity authUser = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		CustomerEntity customerEntity = customerRepository.findByUserId(authUser.getId());
		ShippingAddressEntity addressEntity = mapper.map(form, ShippingAddressEntity.class);
		CartEntity cartEntity = customerEntity.getCart();
		OrderEntity orderEntity = mapper.map(form, OrderEntity.class);
		List<OrderItemEntity> orderedItems = new ArrayList<OrderItemEntity>();

		Optional<List<CartItemEntity>> cartItems = Optional.ofNullable(cartEntity.getCartItems());

		if (!cartItems.isEmpty()) {
			for (CartItemEntity item : cartItems.get()) {
				ProductEntity product = item.getProduct();
				OrderItemEntity orderedItem = tempConverter.cartItemToOrderItemEntity(item);
				orderedItem.setProductName(product.getName());
				orderedItem.setProductPrice(product.getPrice());
				OrderItemEntity storedOrderedItem = orderItemRepository.save(orderedItem);
				orderedItems.add(storedOrderedItem);
			}
		}

		ShippingAddressEntity storedAddress = addressRepository.save(addressEntity);
		customerEntity.setPhone(form.getPhone());
		customerEntity = customerRepository.saveAndFlush(customerEntity);
		orderEntity.setCustomer(customerEntity);
		orderEntity.setOrderedItems(orderedItems);
		orderEntity.setAddress(storedAddress);
		orderEntity.setOrderPrice(cartEntity.getCartPrice());

		ZonedDateTime currentTime = LocalDateTime.now().atZone(ZoneId.of("Europe/Belgrade"));
		Timestamp orderTime = new Timestamp(currentTime.toInstant().getEpochSecond() * 1000L);
		orderEntity.setOrderTime(orderTime);

		OrderEntity storedOrder = orderRepository.save(orderEntity);

		OrderDto returnValue = tempConverter.orderEntityToDto(storedOrder);

		for (OrderItemEntity item : storedOrder.getOrderedItems()) {
			item.setOrder(storedOrder);
			orderItemRepository.saveAndFlush(item);
		}

		storedAddress.setOrder(storedOrder);
		addressRepository.saveAndFlush(storedAddress);

		cartItemRepository.removeAllByCartId(cartEntity.getCartId());
		cartService.refreshCartState(cartEntity.getCartId());
		return returnValue;
	}

	@Override
	public OrderDto getLastOrder() {
		// TODO Auto-generated method stub
		UserEntity authUser = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		CustomerEntity customer = customerRepository.findByUserId(authUser.getId());
		List<OrderEntity> allOrders = orderRepository.findAllByCustomerId(customer.getCustomerId());
		OrderEntity lastOrder = allOrders.stream().max(Comparator.comparing(OrderEntity::getOrderTime)).get();
		OrderDto returnValue = tempConverter.orderEntityToDto(lastOrder);
		return returnValue;
	}

	@Override
	public void deleteOrder(Integer orderId) {
		// TODO Auto-generated method stub
		Optional<OrderEntity> orderOpt = orderRepository.findById(orderId);
		if (orderOpt.isPresent()) {
			orderRepository.deleteById(orderId);
			orderRepository.flush();
		} else {
			Error error = new Error("Order not exists!");
			throw new InstanceNotExistException(error);
		}

	}

	@Override
	public List<OrderDto> listAll() {
		// TODO Auto-generated method stub
		List<OrderDto> returnValue = new ArrayList<OrderDto>();
		Optional<List<OrderEntity>> allOrdersOpt = Optional.ofNullable(orderRepository.findAll());
		if (!allOrdersOpt.isEmpty()) {
			allOrdersOpt.get().forEach((order) -> {
				OrderDto orderDto = tempConverter.orderEntityToDto(order);
				returnValue.add(orderDto);
			});
		}
		return returnValue;
	}

	@Override
	public List<OrderDto> listAllByCustomerId(Integer customerId) {
		// TODO Auto-generated method stub
		List<OrderDto> returnValue = new ArrayList<OrderDto>();
		Optional<List<OrderEntity>> allOrdersOpt = Optional.ofNullable(orderRepository.findAllByCustomerId(customerId));
		if (!allOrdersOpt.isEmpty()) {
			allOrdersOpt.get().forEach((order) -> {
				OrderDto orderDto = tempConverter.orderEntityToDto(order);
				returnValue.add(orderDto);
			});
		}
		return returnValue;
	}

	@Override
	public OrderDto getOrderById(Integer orderId) {
		// TODO Auto-generated method stub
		OrderDto returnValue = null;
		Optional<OrderEntity> orderOpt = orderRepository.findById(orderId);
		if (orderOpt.isPresent()) {
			returnValue = tempConverter.orderEntityToDto(orderOpt.get());
		} else {
			Error error = new Error("Order not exists!");
			throw new InstanceNotExistException(error);
		}
		return returnValue;
	}

}
