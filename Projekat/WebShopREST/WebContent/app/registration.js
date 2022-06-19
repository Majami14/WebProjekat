var app = new Vue({
	el: '#registrationFrom',
	data: {
		newUser: {},
		error: ''
	},
	mounted() {
		this.newUser = { id: '', userName: null, password: null, firstName : null, lastName : null, gender : null, birthDate : null }
	},
	methods: {
		createUser: function (event) {
			this.error = ""
			if (!this.newUser.firstName || !this.newUser.lastName) {
				this.error = "Unesite ime i prezime";
				event.preventDefault();
				return;
			}
			axios.post('rest/korisnik', this.newUser)
				.then((response) => {
					alert('Novi proizvod uspešno kreiran')
                })
			event.preventDefault();
		}
	}
});
