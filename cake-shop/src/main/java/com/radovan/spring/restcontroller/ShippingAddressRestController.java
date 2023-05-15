package com.radovan.spring.restcontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.radovan.spring.dto.ShippingAddressDto;
import com.radovan.spring.service.ShippingAddressService;

@RestController
@RequestMapping(value="/api/addresses")
public class ShippingAddressRestController {

	@Autowired
	private ShippingAddressService addressService;
	
	@RequestMapping(value = "/allAddresses", method = RequestMethod.GET)
	public ResponseEntity<List<ShippingAddressDto>> getAllAddresses() {
		List<ShippingAddressDto> allAddresses = addressService.listAll();
		return ResponseEntity.ok().body(allAddresses);
	}
	
	@RequestMapping(value = "/addressDetails/{addressId}",method = RequestMethod.GET)
	public ResponseEntity<ShippingAddressDto> getAddressDetails(@PathVariable ("addressId") Integer addressId){
		ShippingAddressDto address = addressService.getAddressById(addressId);
		return ResponseEntity.ok().body(address);
	}
}
