var cartApp = Vue.createApp({
	data(){
		return{
			cartId: null,
			cartItemList: [],
			productList: [],
			cartPrice: null
		}
	},
	created() {

    },
	methods: {
		getAllCartItems(){
			axios.get("/api/cart/allCartItems")
			.then((response) => {
				setTimeout(() => this.cartItemList = response.data);
			})	
		},
		getAllProducts(){
			axios.get("/api/products/allProducts")
			.then((response) => {
				setTimeout(() => this.productList = response.data);
				
			})	
		},
		deleteItem: async function(itemId){
			if(confirm("Remove this item from cart?")){
				await
				axios.delete("/api/cart/deleteItem/" + itemId)
				.then(function(){
					redirectCart();
				},function(){
					alert("Failed!");
				})
			}
		},
		validateCart: async function(){
			await
			axios.get("/api/cart/cartVerification")
			.then(function(){
				redirectCheckout();
			},function(){
				redirectCartError();
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
		onImageLoadFailure (event) {
        	event.target.src = "https://www.shutterstock.com/image-vector/no-grunge-vintage-square-stamp-260nw-507791674.jpg";    
      	}
	}
}).mount('#vueContent');