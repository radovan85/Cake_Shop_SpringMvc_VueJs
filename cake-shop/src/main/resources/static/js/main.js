window.onload = redirectHome;

function redirectHome(){
	$("#ajaxLoadedContent").load("/home");
}

function redirectLogin(){
	$("#ajaxLoadedContent").load("/login");
}

function redirectRegister(){
	$("#ajaxLoadedContent").load("/registration")
}

function redirectRegisterCompleted() {
	$("#ajaxLoadedContent").load("/registerComplete");
}

function redirectRegisterFail(){
	$("#ajaxLoadedContent").load("/registerFail");
}

function redirectAdmin(){
	$("#ajaxLoadedContent").load("/mvc/admin/");
}

function redirectAllCategories(){
	$("#ajaxLoadedContent").load("/mvc/admin/allCategories");
}

function redirectAddCategory(){
	$("#ajaxLoadedContent").load("/mvc/admin/addCategory");
}

function redirectUpdateCategory(categoryId){
	$("#ajaxLoadedContent").load("/mvc/admin/updateCategory/" + categoryId);
}

function redirectAllProducts(){
	$("#ajaxLoadedContent").load("/mvc/products/allProducts");
}

function redirectAddProduct(){
	$("#ajaxLoadedContent").load("/mvc/admin/addProduct");
}


function redirectUpdateProduct(productId){
	$("#ajaxLoadedContent").load("/mvc/admin/updateProduct/" + productId);
}

function redirectShop(){
	$("#ajaxLoadedContent").load("/mvc/products/shop");
}

function redirectShopCategory(categoryId){
	$("#ajaxLoadedContent").load("/mvc/products/shop/" + categoryId);
}

function redirectProductDetails(productId){
	$("#ajaxLoadedContent").load("/mvc/products/productDetails/" + productId);
}

function redirectCart(){
	$("#ajaxLoadedContent").load("/mvc/cart/cart");
}

function redirectCheckout(){
	$("#ajaxLoadedContent").load("/mvc/cart/checkout");
}

function redirectOrderCompleted(){
	$("#ajaxLoadedContent").load("/mvc/cart/orderCompleted");
}

function redirectCartError(){
	$("#ajaxLoadedContent").load("/mvc/cart/cartError");
}

function redirectAllCustomers(){
	$("#ajaxLoadedContent").load("/mvc/admin/allCustomers");
}

function redirectAllOrders(){
	$("#ajaxLoadedContent").load("/mvc/admin/allOrders");
}

function redirectOrderDetails(orderId){
	$("#ajaxLoadedContent").load("/mvc/admin/orderDetails/" + orderId);
}



function loginInterceptor(formName) {
	var $form = $("#" + formName);

	$form.on('submit', function(e) {
		e.preventDefault();

		$.ajax({
			url : "http://localhost:8080/login",
			type : "POST",
			data : $form.serialize()
		})
		.done(function(){
			confirmLoginPass();
		})
		.fail(function(){
			alert("Failed!");
		})

	});
};

function confirmLoginPass() {
	$.ajax({
		url : "http://localhost:8080/loginPassConfirm",
		type : "POST"
	})
	.done(function(){
		checkForSuspension();
	})
	.fail(function(){
		$("#ajaxLoadedContent").load("/loginErrorPage");
	})
}

function checkForSuspension() {
	$.ajax({
		url : "http://localhost:8080/suspensionChecker",
		type : "POST"
	})
	.done(function(){
		window.location.href = "/";
	})
	.fail(function(){
		alert("Account suspended!");
			redirectLogout();
	})
}

function redirectLogout() {
	$.ajax({
		url : "http://localhost:8080/loggedout",
		type : "GET"
	})
	.done(function(){
		window.location.href = "/";
	})
	.fail(function(){
		alert("Failed!");
	})
}





