

var  regFormApp  = Vue.createApp({
	data(){
		return {
			firstName: null,
			lastName: null,
			email: null,
			password:null,
			phone: null,		
		}
	},
	
	methods: {
		registerUser: async function(){
			if(validatePassword()){
				if(validateRegForm()){
					await
					axios.post("/api/registration/storeUser", {
					"firstName" : this.firstName,
					"lastName" : this.lastName,
					"email" : this.email,
					"password" : this.password,
					"phone" : this.phone	
					}).then(function(){
						redirectRegisterCompleted();
					},function(){
						redirectRegisterFail();
					});
				} 																																					
			}
		}
	}
	}).mount('#vueContent');
	

function executeRegistration(){
	regFormApp.registerUser();
}