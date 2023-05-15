var orderApp = Vue.createApp({
	data(){
		return{
			tempUser: {
				userId: null,
				firstName: null,
				lastName: null,
				email: null
			},
			tempCustomer: {
				customerId: null,
				phone: null
			},
			tempAddress: {
				shippingAddressId: null,
				address: null,
				city: null,
				postcode: null,
			},
			address: null,
			city: null,
			postcode: null,
			orderItemList: [],
			productList: [],
			orderList: [],
			userList: [],
			customerList: [],
			dt:null,
			orderId: null,
			addInfo: null,
			orderTimeStr: null,
			customerId: null,
			addressId: null,
			orderPrice: null,
			cartPrice: null,
			phone: null
			
			
		}	
	},
	created() {

    },
    watch: {             
        orderList(val) {
        	this.dt.destroy();
        	this.$nextTick(() => {
        		this.dt = $("#listingTable").DataTable();
        	})
    	}               
	},
    methods: {
		getAuthenticatedUser: function(){
			axios.get("/api/customers/getCurrentUser")
			.then((response) => {
				var userData = response.data;
				this.tempUser = userData;			
			})
		},
		getAuthCustomer: async function(){
			await
			axios.get("/api/customers/getCurentCustomer")
			.then((response) => {
				var customerData = response.data;
				this.tempCustomer = customerData;
				this.phone = customerData.phone;
			})
		},
		addOrder: async function(){
			if(validateOrder()){
				await
				axios.post("/api/orders/createOrder", {
					"address" : this.address,
					"city" : this.city,
					"postcode" : this.postcode,
					"phone" : this.phone,
					"addInfo" : this.addInfo
				})
				.then(function(){
					redirectOrderCompleted();
				},function(){
					alert("Failed!");
				})
			}
			
		},
		getLastOrderedItems: function(){
			this.getAllProducts();
			axios.get("/api/orders/lastOrderItems")
			.then((response) => {
				setTimeout(() => this.orderItemList = response.data);	
			})	
		},
		getAllProducts: function(){
			axios.get("/api/products/allProducts")
			.then((response) => {
				setTimeout(() => this.productList = response.data);	
			})	
		},
		retrieveLastOrder: async function(){
			await
			axios.get("/api/orders/lastOrder")
			.then((response) => {
				var orderData = response.data;
				this.orderId = orderData.orderId;
				this.addInfo = orderData.additionalInfo;
				this.orderTimeStr = orderData.orderTimeStr;
				this.addressId = orderData.addressId;
				this.orderPrice = orderData.orderPrice;
				this.getOrderItems(orderData.orderId);
				this.getAddress(orderData.addressId);
				this.getOrderItems(orderData.orderId);
			})
		},
		getAllCustomers(){
			this.getAllUsers();
			axios.get("/api/admin/allCustomers")
			.then((response) => {
				setTimeout(() => this.customerList = response.data);
				
			})	
		},
		getAllUsers(){
			axios.get("/api/admin/allUsers")
			.then((response) => {
				setTimeout(() => this.userList = response.data);
			})
		},
		getAllAddresses(){
			axios.get("/api/admin/allAddresses")
			.then((response) => {
				setTimeout(() => this.addressList = response.data);
			})
		},
		getAllOrders(){
			this.getAllCustomers();
			axios.get("/api/admin/allOrders")
			.then((response) => {
				this.dt = $("#listingTable").DataTable();
				setTimeout(() => this.orderList = response.data);
			})
		},
		getOrderItems: async function(orderId){
			this.getAllProducts();
			await
			axios.get("/api/orders/allItemsByOrderId/" + orderId)
			.then((response) => {
				setTimeout(() => this.orderItemList = response.data);
			})	
			
		},
		viewSelectedOrder: function(orderId){
			redirectOrderDetails(orderId);
		},
		getOrder: async function(orderId){
			await
			axios.get("/api/admin/orderDetails/" + orderId)
			.then((response) => {
				var orderData = response.data;
				this.orderId = orderData.orderId;
				this.orderTimeStr = orderData.orderTimeStr;
				this.addInfo = orderData.addInfo;
				this.orderPrice = orderData.orderPrice;
				this.addressId = orderData.addressId;
				this.customerId = orderData.customerId;
				this.getAddress(orderData.addressId);
				this.getOrderItems(orderData.orderId);
				this.getUserData(orderData.customerId);
				
			},function(){
				alert("Failed!");
			})
		},
		getGrandTotal: async function(){
			await
			axios.get("/api/cart/getGrandTotal")
			.then((response) => {
				var cartPriceData = JSON.parse(response.data);
				this.cartPrice = cartPriceData;
			})
		},
		getAddress: async function(addressId){
			await
			axios.get("api/addresses/addressDetails/" + addressId)
			.then((response) => {
				var addressData = response.data;
				this.address = addressData.address;
				this.postcode = addressData.postcode;
				this.city = addressData.city;
			})
		},
		getUserData: async function(customerId){
			await 
			axios.get("/api/customers/getUserData/" + customerId)
			.then((response) => {
				this.tempUser = response.data;
			},function(){
				alert("Failed!");
			})
		},
		deleteOrder: async function(orderId){
			if(confirm("Remove this order?")){
				await
				axios.delete("/api/admin/deleteOrder/" + orderId)
				.then(function(){
					redirectAllOrders();
				},function(){
					alert("Failed!");
				})
			}
		}
	}
}).mount("#vueContent");

function executeCheckoutForm(){
	orderApp.addOrder();
}