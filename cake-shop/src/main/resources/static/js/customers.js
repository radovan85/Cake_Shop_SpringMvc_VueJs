var customerApp = Vue.createApp({
	data(){
		return {
			dt: null,
			customerList: [],
			userList: []
		}
	},
	created() {
		
    },
    
    
    
    watch: {             
        customerList(val) {
        	this.dt.destroy();
        	this.$nextTick(() => {
        		this.dt = $("#listingTable").DataTable();
        	})
    	}               
	},
	
	   
    mounted() {
      this.getAllCustomers();
    },
	methods: {
		
		getAllCustomers(){
			this.getAllUsers();
			axios.get("/api/admin/allCustomers")
			.then((response) => {
				this.dt = $("#listingTable").DataTable();
				setTimeout(() => this.customerList = response.data);
				
			})	
		},
		
		getAllUsers(){
			axios.get("/api/admin/allUsers")
			.then((response) => {
				setTimeout(() => this.userList = response.data);
			})
		},
		suspendUser: async function(userId){
			if(confirm("Suspend this user?")){
				await
				axios.get("/api/admin/suspendUser/" + userId)
				.then(function(){
				redirectAllCustomers();
				},function(){
					alert("Failed!");
				})
			}
		},
		reactivateUser: async function(userId){
			if(confirm("Reactivate this user?")){
				await
				axios.get("/api/admin/reactivateUser/" + userId)
				.then(function(){
					redirectAllCustomers();
				},function(){
					alert("Failed!");
				})
			}
		},
		deleteCustomer: async function(customerId){
			if(confirm("Remove this customer?\nIt will affect all related orders!")){
				await
				axios.delete("/api/admin/deleteCustomer/" + customerId)
				.then(function(){
					redirectAllCustomers();
				},function(){
					alert("Failed");
				})
			}
		}
		
	}
}).mount("#vueContent");