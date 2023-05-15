var categoryApp = Vue.createApp({
	data(){
		return {
			categoryId: null,
			name: null,
			dt: null,
			categoryList: []
		}
	},
	created() {

    },

    watch: {             
        categoryList(val) {
        	this.dt.destroy();
        	this.$nextTick(() => {
        		this.dt = $("#listingTable").DataTable();
        	})
    	}               
	},
	methods: {
		getAllCategories(){
			axios.get("/api/categories/allCategories")
			.then((response) => {
				this.dt = $("#listingTable").DataTable();
				setTimeout(() => this.categoryList = response.data);
			})
		},
		addCategory: async function(){
			if(validateCategory()){
				if (this.categoryId == '' || this.categoryId == null){
					await
					axios.post("/api/admin/storeCategory", {
						"name" : this.name
					}).then(function(){
						redirectAllCategories();
					},function(){
						alert("Failed!");
					})
				}else {
					await
					axios.put("/api/admin/updateCategory/" + this.categoryId, {
						"name" : this.name
					}).then(function(){
						redirectAllCategories();
					},function(){
						alert("Failed!");
					})
				}
			}
		},
		getSelectedCategory: function(categoryId){
			axios.get("api/categories/categoryDetails/" + categoryId)
			.then((response) => {
				var categoryData = response.data;
				this.categoryId = categoryData.categoryId;
				this.name = categoryData.name;
			},function(){
				alert("Failed!");
			})
		},
		updateCategory(categoryId){
			redirectUpdateCategory(categoryId);
		},
		deleteCategory: async function(categoryId){
			if (confirm("Remove this category?\nIt will affect all related products!")){
				await 
				axios.delete("/api/admin/deleteCategory/" + categoryId)
				.then(function(){
					redirectAllCategories();
				},function(){
					alert("Failed!");
				});
				
			}
		}
	}
}).mount('#vueContent');

function executeCategoryForm(){
	categoryApp.addCategory();
}