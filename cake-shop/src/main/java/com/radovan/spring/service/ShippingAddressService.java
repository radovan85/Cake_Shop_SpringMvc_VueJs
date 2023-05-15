package com.radovan.spring.service;

import java.util.List;

import com.radovan.spring.dto.ShippingAddressDto;

public interface ShippingAddressService {

	List<ShippingAddressDto> listAll();
	
	ShippingAddressDto getAddressById(Integer addressId);
}
