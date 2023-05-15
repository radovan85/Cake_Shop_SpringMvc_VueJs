var productApp = Vue.createApp({
	data(){
		return{
			productId: null,
			name: null,
			price: null,
			weight: null, 
			description: null,
			imageUrl: null,
			categoryId: "",
			dt: null,
			productList: [],
			categoryList: [],
			tempCategory: {
				categoryId: null,
				name : null
			}			
		}
	},
	created() {
		
    },

	
    watch: {             
        productList(val) {
        	this.dt.destroy();
        	this.$nextTick(() => {
        		this.dt = $("#listingTable").DataTable();
        	})
    	}               
	},
	
	
	
	methods: {
		getAllProducts(){
			this.getAllCategories();
			axios.get("/api/products/allProducts")
			.then((response) => {
				this.dt = $("#listingTable").DataTable();
				setTimeout(() => this.productList = response.data);	
			})	
		},
		getAllCategories(){
			axios.get("/api/categories/allCategories")
			.then((response) => {
				setTimeout(() => this.categoryList = response.data);
			})
		},
		
		viewSelectedCategory: function(categoryId){
			redirectShopCategory(categoryId);
		},
		
		listAllProductsByCategoryId: function(categoryId){
			this.getAllCategories();
			axios.get("/api/products/allProducts/" + categoryId)
			.then((response) => {
				this.dt = $("#listingTable").DataTable();
				setTimeout(() => this.productList = response.data);
			})
		},
		
		addProduct: async function(){
			if(validateProduct()){
				if(this.productId == "" || this.productId == null){
					await
					axios.post("/api/admin/storeProduct", {
						"name" : this.name,
						"price" : this.price,
						"weight" : this.weight,
						"description" : this.description,
						"imageUrl" : this.imageUrl,
						"categoryId" : this.categoryId
					}).then(function(){
						redirectAllProducts();
					},function(){
						alert("Failed!");
					})
				}else{
					await
					axios.put("/api/admin/updateProduct/" + this.productId, {
						"name" : this.name,
						"price" : this.price,
						"weight" : this.weight,
						"description" : this.description,
						"imageUrl" : this.imageUrl,
						"categoryId" : this.categoryId
					}).then(function(){
						redirectAllProducts();
					},function(){
						alert("Failed!");
					})
				}
			}
		},
		getSelectedProduct: function(productId){
			axios.get("api/products/productDetails/" + productId)
			.then((response) => {	
				var productData = response.data;
				this.productId = productData.productId;
				this.name = productData.name,
				this.price = productData.price;
				this.weight = productData.weight;
				this.imageUrl = productData.imageUrl;
				this.categoryId = productData.categoryId;
				this.getProductCategory(productData.categoryId);
			},function(){
				alert("Failed!");
			});
		},
		/*
		getProductCategory: function(productId){
			axios.get("api/products/productCategory/" + productId)
			.then((response) => {	
				this.tempCategory = response.data;
			},function(){
				alert("Failed!");
			});
		},
		*/
		getProductCategory: function(categoryId){
			axios.get("api/categories/categoryDetails/" + categoryId)
			.then((response) => {	
				this.tempCategory = response.data;
			},function(){
				alert("Failed!");
			});
		},
		viewSelectedProduct: function(productId){
			redirectProductDetails(productId);
		},
		deleteProduct: async function(productId){
			if(confirm("Remove this product?")){
				await
				axios.delete("/api/admin/deleteProduct/" + productId)
				.then(function(){
					redirectAllProducts();
				},function(){
					alert("Failed!");
				})
			}
		},
		addToCart: async function(productId){
			await
			axios.post("/api/cart/add/" + productId)
			.then(function(){
				alert("Item added to cart!");
			}, function(){
				alert("Failed!");
			})
		},
		updateProduct(productId){
			redirectUpdateProduct(productId);
		},
		onImageLoadFailure (event) {
        	event.target.src = "https://www.shutterstock.com/image-vector/no-grunge-vintage-square-stamp-260nw-507791674.jpg";    
      	}
	}
}).mount('#vueContent');

function executeProductForm(){
	productApp.addProduct();
}