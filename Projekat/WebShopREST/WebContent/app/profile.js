var app = new Vue({
	el: '#myProfileID',
	data: {
		newUser1: {},
		error: '',
		newUser: {}
	},
	mounted() {
		a = { asd: "asd", b: "asd" };
		axios.get('rest/currentUser').then((response) => { this.newUser1 = response.data })
	},
	methods: {
		createUser: function(event) {
			axios.put('rest/korisnik/' + this.newUser.id, this.newUser)
				.then((response) => {
					alert('Podaci su uspesno promenjeni ')
				})
			event.preventDefault();
		}
	}
});

