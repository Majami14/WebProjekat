var app = new Vue({
	el: '#managerRegistrationFrom',
	data: {
		newUser: {},
		error: ''
	},
	mounted() {
		this.newUser = { id: '', userName: null, password: null, firstName : null, lastName : null, gender : null, birthDate : null, role : 'MANAGER' }
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
					alert('New user registered.')
                }).catch(() =>{
					alert('username already exists.')
					event.preventDefault();
					return;
				})
			event.preventDefault();
		}
	}
});
