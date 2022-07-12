var app = new Vue({
	el: '#myProfileID',
	data: {
		newUser1: {},
		error: '',
		newUser: {},
		historyTraining: null
	},
	mounted() {
		axios.get('rest/currentUser')
			.then((response) => {
				this.newUser1 = response.data;
				axios.get('rest/history/getITforUser', { params: { idKorisnika: this.newUser1.id } }).
					then((response) => {
						this.historyTraining = response.data;
					})
			})


	},
	methods: {
		createUser: function(event) {
			axios.put('rest/korisnik/', this.newUser1)
				.then((response) => {
					alert('Podaci su uspesno promenjeni ')
				})
			event.preventDefault();
		},logout: function(event) {
			axios.post('rest/logout').
				then((response) => {
					window.location.href = 'http://localhost:8080/WebShopREST/login.html'
				})
		}
	}
	
	
});



