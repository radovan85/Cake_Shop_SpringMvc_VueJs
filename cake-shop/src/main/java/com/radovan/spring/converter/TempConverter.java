package com.radovan.spring.converter;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.radovan.spring.dto.CartDto;
import com.radovan.spring.dto.CartItemDto;
import com.radovan.spring.dto.CategoryDto;
import com.radovan.spring.dto.CustomerDto;
import com.radovan.spring.dto.OrderDto;
import com.radovan.spring.dto.OrderItemDto;
import com.radovan.spring.dto.ProductDto;
import com.radovan.spring.dto.RoleDto;
import com.radovan.spring.dto.ShippingAddressDto;
import com.radovan.spring.dto.UserDto;
import com.radovan.spring.entity.CartEntity;
import com.radovan.spring.entity.CartItemEntity;
import com.radovan.spring.entity.CategoryEntity;
import com.radovan.spring.entity.CustomerEntity;
import com.radovan.spring.entity.OrderEntity;
import com.radovan.spring.entity.OrderItemEntity;
import com.radovan.spring.entity.ProductEntity;
import com.radovan.spring.entity.RoleEntity;
import com.radovan.spring.entity.ShippingAddressEntity;
import com.radovan.spring.entity.UserEntity;
import com.radovan.spring.repository.CartItemRepository;
import com.radovan.spring.repository.CartRepository;
import com.radovan.spring.repository.CategoryRepository;
import com.radovan.spring.repository.CustomerRepository;
import com.radovan.spring.repository.ProductRepository;
import com.radovan.spring.repository.RoleRepository;
import com.radovan.spring.repository.UserRepository;

@Component
public class TempConverter {

	@Autowired
	private ModelMapper mapper;

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private CartItemRepository cartItemRepository;

	private static final DecimalFormat decfor = new DecimalFormat("0.00");

	public CategoryDto categoryEntityToDto(CategoryEntity categoryEntity) {
		CategoryDto returnValue = mapper.map(categoryEntity, CategoryDto.class);
		return returnValue;
	}

	public CategoryEntity categoryDtoToEntity(CategoryDto categoryDto) {
		CategoryEntity returnValue = mapper.map(categoryDto, CategoryEntity.class);
		return returnValue;
	}

	public ProductDto productEntityToDto(ProductEntity productEntity) {
		ProductDto returnValue = mapper.map(productEntity, ProductDto.class);

		Optional<CategoryEntity> categoryOpt = Optional.ofNullable(productEntity.getCategory());
		if (categoryOpt.isPresent()) {
			returnValue.setCategoryId(categoryOpt.get().getCategoryId());
		}

		return returnValue;
	}

	public ProductEntity productDtoToEntity(ProductDto productDto) {
		Double productPrice = Double.valueOf(decfor.format(productDto.getPrice()));
		productDto.setPrice(productPrice);
		Double weight = Double.valueOf(decfor.format(productDto.getWeight()));
		productDto.setWeight(weight);
		ProductEntity returnValue = mapper.map(productDto, ProductEntity.class);

		Optional<Integer> categoryIdOpt = Optional.ofNullable(productDto.getCategoryId());
		if (categoryIdOpt.isPresent()) {
			Optional<CategoryEntity> categoryOpt = categoryRepository.findById(categoryIdOpt.get());
			if (categoryOpt.isPresent()) {
				returnValue.setCategory(categoryOpt.get());
			}
		}

		return returnValue;
	}

	public CartItemDto cartItemEntityToDto(CartItemEntity cartItem) {
		CartItemDto returnValue = mapper.map(cartItem, CartItemDto.class);
		Optional<ProductEntity> productOpt = Optional.ofNullable(cartItem.getProduct());
		if (productOpt.isPresent()) {
			returnValue.setProductId(productOpt.get().getProductId());
		}
		Optional<CartEntity> cartOpt = Optional.ofNullable(cartItem.getCart());
		if (cartOpt.isPresent()) {
			returnValue.setCartId(cartOpt.get().getCartId());
		}

		return returnValue;
	}

	public CartItemEntity cartItemDtoToEntity(CartItemDto cartItem) {
		CartItemEntity returnValue = mapper.map(cartItem, CartItemEntity.class);
		Optional<Integer> productId = Optional.ofNullable(cartItem.getProductId());
		if (productId.isPresent()) {
			Optional<ProductEntity> productOpt = productRepository.findById(productId.get());
			if (productOpt.isPresent()) {
				returnValue.setProduct(productOpt.get());
			}
		}

		Optional<Integer> cartId = Optional.ofNullable(cartItem.getCartId());
		if (cartId.isPresent()) {
			Optional<CartEntity> cartOpt = cartRepository.findById(cartId.get());
			if (cartOpt.isPresent()) {
				returnValue.setCart(cartOpt.get());
			}
		}

		return returnValue;
	}

	public CartDto cartEntityToDto(CartEntity cartEntity) {
		CartDto returnValue = mapper.map(cartEntity, CartDto.class);
		Optional<CustomerEntity> customerOpt = Optional.ofNullable(cartEntity.getCustomer());
		if (customerOpt.isPresent()) {
			returnValue.setCustomerId(customerOpt.get().getCustomerId());
		}

		List<Integer> cartItemsIds = new ArrayList<Integer>();
		Optional<List<CartItemEntity>> cartItemsOpt = Optional.ofNullable(cartEntity.getCartItems());
		if (!cartItemsOpt.isEmpty()) {
			cartItemsOpt.get().forEach((itemEntity) -> {
				cartItemsIds.add(itemEntity.getCartItemId());
			});
		}

		returnValue.setCartItemsIds(cartItemsIds);
		return returnValue;
	}

	public CartEntity cartDtoToEntity(CartDto cartDto) {
		CartEntity returnValue = mapper.map(cartDto, CartEntity.class);
		Optional<Integer> customerId = Optional.ofNullable(cartDto.getCustomerId());
		if (customerId.isPresent()) {
			Optional<CustomerEntity> customerOpt = customerRepository.findById(customerId.get());
			if (customerOpt.isPresent()) {
				returnValue.setCustomer(customerOpt.get());
			}
		}

		List<CartItemEntity> cartItems = new ArrayList<CartItemEntity>();
		Optional<List<Integer>> cartItemsIdsOpt = Optional.ofNullable(cartDto.getCartItemsIds());
		if (!cartItemsIdsOpt.isEmpty()) {
			cartItemsIdsOpt.get().forEach((itemId) -> {
				Optional<CartItemEntity> cartItemOpt = cartItemRepository.findById(itemId);
				if (cartItemOpt.isPresent()) {
					cartItems.add(cartItemOpt.get());
				}
			});
		}

		returnValue.setCartItems(cartItems);
		return returnValue;
	}

	public CustomerDto customerEntityToDto(CustomerEntity customer) {
		CustomerDto returnValue = mapper.map(customer, CustomerDto.class);
		Optional<UserEntity> userOpt = Optional.ofNullable(customer.getUser());
		if (userOpt.isPresent()) {
			returnValue.setUserId(userOpt.get().getId());
		}

		Optional<CartEntity> cartOpt = Optional.ofNullable(customer.getCart());
		if (cartOpt.isPresent()) {
			returnValue.setCartId(cartOpt.get().getCartId());
		}

		return returnValue;
	}

	public CustomerEntity customerDtoToEntity(CustomerDto customer) {
		CustomerEntity returnValue = mapper.map(customer, CustomerEntity.class);
		Optional<Integer> userId = Optional.ofNullable(customer.getUserId());
		if (userId.isPresent()) {
			Optional<UserEntity> userOpt = userRepository.findById(userId.get());
			if (userOpt.isPresent()) {
				returnValue.setUser(userOpt.get());
			}
		}

		Optional<Integer> cartId = Optional.ofNullable(customer.getCartId());
		if (cartId.isPresent()) {
			Optional<CartEntity> cartOpt = cartRepository.findById(cartId.get());
			if (cartOpt.isPresent()) {
				returnValue.setCart(cartOpt.get());
			}
		}

		return returnValue;
	}

	public UserDto userEntityToDto(UserEntity userEntity) {
		UserDto returnValue = mapper.map(userEntity, UserDto.class);
		returnValue.setEnabled(userEntity.getEnabled());
		Optional<List<RoleEntity>> rolesOpt = Optional.ofNullable(userEntity.getRoles());
		List<Integer> rolesIds = new ArrayList<Integer>();

		if (!rolesOpt.isEmpty()) {
			rolesOpt.get().forEach((roleEntity) -> {
				rolesIds.add(roleEntity.getId());
			});
		}

		returnValue.setRolesIds(rolesIds);

		return returnValue;
	}

	public UserEntity userDtoToEntity(UserDto userDto) {
		UserEntity returnValue = mapper.map(userDto, UserEntity.class);
		List<RoleEntity> roles = new ArrayList<>();
		Optional<List<Integer>> rolesIdsOpt = Optional.ofNullable(userDto.getRolesIds());

		if (!rolesIdsOpt.isEmpty()) {
			rolesIdsOpt.get().forEach((roleId) -> {
				RoleEntity role = roleRepository.findById(roleId).get();
				roles.add(role);
			});
		}

		returnValue.setRoles(roles);

		return returnValue;
	}

	public RoleDto roleEntityToDto(RoleEntity roleEntity) {
		RoleDto returnValue = mapper.map(roleEntity, RoleDto.class);
		Optional<List<UserEntity>> usersOpt = Optional.ofNullable(roleEntity.getUsers());
		List<Integer> userIds = new ArrayList<>();

		if (!usersOpt.isEmpty()) {
			usersOpt.get().forEach((user) -> {
				userIds.add(user.getId());
			});
		}

		returnValue.setUserIds(userIds);
		return returnValue;
	}

	public RoleEntity roleDtoToEntity(RoleDto roleDto) {
		RoleEntity returnValue = mapper.map(roleDto, RoleEntity.class);
		Optional<List<Integer>> usersIdsOpt = Optional.ofNullable(roleDto.getUserIds());
		List<UserEntity> users = new ArrayList<>();

		if (!usersIdsOpt.isEmpty()) {
			usersIdsOpt.get().forEach((userId) -> {
				UserEntity userEntity = userRepository.findById(userId).get();
				users.add(userEntity);
			});
		}
		returnValue.setUsers(users);
		return returnValue;
	}

	public OrderItemEntity cartItemToOrderItemEntity(CartItemEntity item) {
		// TODO Auto-generated method stub
		OrderItemEntity returnValue = mapper.map(item, OrderItemEntity.class);
		return returnValue;
	}

	public OrderDto orderEntityToDto(OrderEntity orderEntity) {
		// TODO Auto-generated method stub
		OrderDto returnValue = mapper.map(orderEntity, OrderDto.class);
		Optional<CustomerEntity> customerOpt = Optional.ofNullable(orderEntity.getCustomer());
		if (customerOpt.isPresent()) {
			returnValue.setCustomerId(customerOpt.get().getCustomerId());
		}

		List<Integer> orderItemsIds = new ArrayList<Integer>();
		Optional<List<OrderItemEntity>> orderedItemsOpt = Optional.ofNullable(orderEntity.getOrderedItems());
		if (!orderedItemsOpt.isEmpty()) {
			orderedItemsOpt.get().forEach((orderItem) -> {
				orderItemsIds.add(orderItem.getOrderItemId());
			});
		}

		returnValue.setOrderedItemsIds(orderItemsIds);

		Optional<ShippingAddressEntity> addressOpt = Optional.ofNullable(orderEntity.getAddress());
		if (addressOpt.isPresent()) {
			returnValue.setAddressId(addressOpt.get().getShippingAddressId());
		}

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

		Optional<Timestamp> orderTimeOpt = Optional.ofNullable(orderEntity.getOrderTime());
		if (orderTimeOpt.isPresent()) {
			ZonedDateTime orderTimeZoned = orderTimeOpt.get().toLocalDateTime().atZone(ZoneId.of("Europe/Belgrade"));
			String orderTimeStr = orderTimeZoned.format(formatter);
			returnValue.setOrderTimeStr(orderTimeStr);
		}
		return returnValue;
	}

	public OrderItemDto orderItemEntityToDto(OrderItemEntity itemEntity) {
		OrderItemDto returnValue = mapper.map(itemEntity, OrderItemDto.class);

		Optional<OrderEntity> orderOpt = Optional.ofNullable(itemEntity.getOrder());
		if (orderOpt.isPresent()) {
			returnValue.setOrderId(orderOpt.get().getOrderId());
		}

		return returnValue;
	}

	public ShippingAddressDto shippingAddressEntityToDto(ShippingAddressEntity address) {
		ShippingAddressDto returnValue = mapper.map(address, ShippingAddressDto.class);
		return returnValue;
	}

	public ShippingAddressEntity shippingAddressDtoToEntity(ShippingAddressDto address) {
		ShippingAddressEntity returnValue = mapper.map(address, ShippingAddressEntity.class);
		return returnValue;
	}
}
