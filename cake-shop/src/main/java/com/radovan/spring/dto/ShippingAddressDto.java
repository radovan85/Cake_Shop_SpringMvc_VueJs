package com.radovan.spring.dto;

import java.io.Serializable;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class ShippingAddressDto implements Serializable {

	/**
		 * 
		 */
	private static final long serialVersionUID = 1L;

	private Integer shippingAddressId;

	@NotEmpty
	@Size(max = 75)
	private String address;

	@NotEmpty
	@Size(max = 40)
	private String city;

	@NotEmpty
	@Size(min = 5, max = 6)
	private String postcode;

	private Integer orderId;

	public Integer getShippingAddressId() {
		return shippingAddressId;
	}

	public void setShippingAddressId(Integer shippingAddressId) {
		this.shippingAddressId = shippingAddressId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	@Override
	public String toString() {
		return "ShippingAddressDto [shippingAddressId=" + shippingAddressId + ", address=" + address + ", city=" + city
				+ ", postcode=" + postcode + "]";
	}

}
