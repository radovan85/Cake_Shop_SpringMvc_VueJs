package com.radovan.spring.form;

import java.io.Serializable;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class OrderForm implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@NotEmpty
	@Size(min = 8, max = 10)
	private String phone;

	@Size(max = 100)
	private String additionalInfo;

	@NotEmpty
	@Size(max = 75)
	private String address;

	@NotEmpty
	@Size(max = 40)
	private String city;

	@NotEmpty
	@Size(min = 5, max = 8)
	private String postcode;

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAdditionalInfo() {
		return additionalInfo;
	}

	public void setAdditionalInfo(String additionalInfo) {
		this.additionalInfo = additionalInfo;
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

	@Override
	public String toString() {
		return "OrderForm [phone=" + phone + ", additionalInfo=" + additionalInfo + ", address=" + address + ", city="
				+ city + ", postcode=" + postcode + "]";
	}

}
