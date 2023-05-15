package com.radovan.spring.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.radovan.spring.converter.TempConverter;
import com.radovan.spring.dto.ShippingAddressDto;
import com.radovan.spring.entity.ShippingAddressEntity;
import com.radovan.spring.exceptions.InstanceNotExistException;
import com.radovan.spring.repository.ShippingAddressRepository;
import com.radovan.spring.service.ShippingAddressService;

@Service
@Transactional
public class ShippingAddressServiceImpl implements ShippingAddressService {

	@Autowired
	private ShippingAddressRepository addressRepository;

	@Autowired
	private TempConverter tempConverter;

	@Override
	public List<ShippingAddressDto> listAll() {
		// TODO Auto-generated method stub
		List<ShippingAddressDto> returnValue = new ArrayList<ShippingAddressDto>();
		Optional<List<ShippingAddressEntity>> allAddressesOpt = Optional.ofNullable(addressRepository.findAll());
		if (!allAddressesOpt.isEmpty()) {
			allAddressesOpt.get().forEach((address) -> {
				ShippingAddressDto addressDto = tempConverter.shippingAddressEntityToDto(address);
				returnValue.add(addressDto);
			});
		}
		return returnValue;
	}

	@Override
	public ShippingAddressDto getAddressById(Integer addressId) {
		// TODO Auto-generated method stub
		ShippingAddressDto returnValue = null;
		Optional<ShippingAddressEntity> addressOpt = addressRepository.findById(addressId);
		if (addressOpt.isPresent()) {
			returnValue = tempConverter.shippingAddressEntityToDto(addressOpt.get());
		} else {
			Error error = new Error("Address not found!");
			throw new InstanceNotExistException(error);
		}
		return returnValue;
	}

}
