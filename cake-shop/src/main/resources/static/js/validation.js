function validateRegForm(){
	var firstName = document.getElementById("firstName").value;
	var lastName = document.getElementById("lastName").value;
	var email = document.getElementById("email").value;
	var password = document.getElementById("password").value;
	var phone = document.getElementById("phone").value;
	
	var firstNameError = document.getElementById("firstNameError");
	var lastNameError = document.getElementById("lastNameError");
	var emailError = document.getElementById("emailError");
	var passwordError = document.getElementById("passwordError");
	var phoneError = document.getElementById("phoneError");
	
	var regEmail = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/g;
	var returnValue = true;
	
	if(firstName === "" || firstName.length > 40){
		firstNameError.style.visibility = "visible";
		returnValue = false;
	}else{
		firstNameError.style.visibility = "hidden";
	}
	
	if(lastName === "" || lastName.length > 40){
		lastNameError.style.visibility = "visible";
		returnValue = false;
	}else{
		lastNameError.style.visibility = "hidden";
	}
	
	if(email === "" || !regEmail.test(email)){
		emailError.style.visibility = "visible";
		returnValue = false;
	}else{
		emailError.style.visibility = "hidden";
	}
	
	if(password === "" || password.length < 6){
		passwordError.style.visibility = "visible";
		returnValue = false;
	}else{
		passwordError.style.visibility = "hidden";
	}
	
	if(phone.length < 8 || phone.length > 10){
		phoneError.style.visibility = "visible";
		returnValue = false;
	}else{
		phoneError.style.visibility = "hidden";
	}
	
	return returnValue;
	
};

function validateCategory(){
	var name = document.getElementById("name").value;
	var nameError = document.getElementById("nameError");
	var returnValue = true;
	
	if(name === "" || name.length > 30){
		nameError.style.visibility = "visible";
		returnValue = false;
	}else{
		nameError.style.visibility = "hidden";
	}
	
	return returnValue;
};

function validateProduct(){
	var name = document.getElementById("name").value;
	var categoryId = document.getElementById("categoryId").value;
	var price = document.getElementById("price").value;
	var weight = document.getElementById("weight").value;
	var description = document.getElementById("description").value;
	var imageUrl = document.getElementById("imageUrl").value;
	
	description = description.trim();
	
	var nameError = document.getElementById("nameError");
	var categoryIdError = document.getElementById("categoryIdError");
	var priceError = document.getElementById("priceError");
	var weightError = document.getElementById("weightError");
	var descriptionError = document.getElementById("descriptionError");
	var imageUrlError = document.getElementById("imageUrlError");
	
	var priceNum = Number(price);
	var weightNum = Number(weight);
	
	var returnValue = true;
	
	if(name === "" || name.length > 30){
		nameError.style.visibility = "visible";
		returnValue = false;
	}else{
		nameError.style.visibility = "hidden";
	};
	
	if(price === "" || priceNum < 0.01){
		priceError.style.visibility = "visible";
		returnValue = false;
	}else{
		priceError.style.visibility = "hidden";
	};
	
	if(weight === "" || weightNum < 0.01){
		weightError.style.visibility = "visible";
		returnValue = false;
	}else{
		weightError.style.visibility = "hidden";
	};
	
	if(categoryId === ""){
		categoryIdError.style.visibility = "visible";
		returnValue = false;
	}else{
		categoryIdError.style.visibility = "hidden";
	};
	
	if(description === "" || description.length > 100){
		descriptionError.style.visibility = "visible";
		returnValue = false;
	}else{
		descriptionError.style.visibility = "hidden";
	};
	
	if(imageUrl === "" || imageUrl.length > 255){
		imageUrlError.style.visibility = "visible";
		returnValue = false;
	}else{
		imageUrlError.style.visibility = "hidden";
	};
	
	return returnValue;
		
};

function validateOrder(){
	var address = document.getElementById("address").value;
	var city = document.getElementById("city").value;
	var postcode = document.getElementById("postcode").value;
	var phone = document.getElementById("phone").value;
	var addInfo = document.getElementById("addInfo").value;
	
	var addressError = document.getElementById("addressError");
	var cityError = document.getElementById("cityError");
	var postcodeError = document.getElementById("postcodeError");
	var phoneError = document.getElementById("phoneError");
	var addInfoError = document.getElementById("addInfoError");
	
	var returnValue = true;
	
	if(address === "" || address.length > 75){
		addressError.style.visibility = "visible";
		returnValue = false;
	}else{
		addressError.style.visibility = "hidden";
	}
	
	if(city === "" || city.length > 40){
		cityError.style.visibility = "visible";
		returnValue = false;
	}else{
		cityError.style.visibility = "hidden";
	}
	
	if(postcode.length < 5 || postcode.length > 6){
		postcodeError.style.visibility = "visible";
		returnValue = false;
	}else{
		postcodeError.style.visibility = "hidden";
	}
	
	if(phone.length < 8 || postcode.length > 10){
		phoneError.style.visibility = "visible";
		returnValue = false;
	}else{
		phoneError.style.visibility = "hidden";
	}
	
	if(addInfo.length > 100){
		addInfoError.style.visibility = "visible";
		returnValue = false;
	}else{
		addInfoError.style.visibility = "hidden";
	}
	
	return returnValue;
	
}


function validateNumber(e) {
	var pattern = /^\d{0,4}(\.\d{0,4})?$/g;

	return pattern.test(e.key)
};

function validatePassword(){
	var password = document.getElementById("password").value;
	var confirmpass = document.getElementById("confirmpass").value;
	var returnValue = false;
	
	if(password === confirmpass){
		returnValue = true;
	}else{
		alert("Password does not match!");
	}
	
	return returnValue;
}

