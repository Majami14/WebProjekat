var app = new Vue({
	el: '#registrationFrom',
	data: {
		newUser: {},
		error: ''
	},
	mounted() {
		this.newUser = { id: '', userName: null, password: null, firstName : null, lastName : null, gender : null, birthDate : null, role : 'CUSTOMER' }
	},
	methods: {
		createUser: function (event) {
			this.error = ""
			if (!this.newUser.userName || !this.newUser.password || !this.newUser.firstName || !this.newUser.lastName || !this.newUser.gender || !this.newUser.birthDate) {
				this.error = "Fill all input fields.e";
				event.preventDefault();
				return;
			}
			axios.post('rest/korisnik/', this.newUser)
				.then((response) => {
					if(responde.data ===""){
						alert('User name already exist.')
					}else{
						alert('Succesfuly registration')
					}
                })
			event.preventDefault();
		}
	}
});


