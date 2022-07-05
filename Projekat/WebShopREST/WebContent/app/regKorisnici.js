var app = new Vue({
	el: '#regKorisnici',
	data: {
		korisnici: null,
		searchValue: "",
		searchType: "",
		loggedKorisnik: {}
	},
	mounted() {
		axios.get('rest/regKorisnici')
			.then(response => (this.korisnici = response.data))
		axios.get('rest/currentUser').then(response=>(this.loggedKorisnik = response.data))

	},
	
	methods: {
//		searchKorisnici: function () {
//			axios.get('rest/regKorisnici/search',{ params: { searchValue: this.searchValue, criterion: this.searchType}} )
//			.then(response => (this.korisnici = response.data))
//		}
		
		
	}
});
