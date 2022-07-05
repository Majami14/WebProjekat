var app = new Vue({
	el: '#managerFacility',
	data: {
		managerFacility: null,
		searchValue: "",
		searchType: "",
		loggedKorisnik: {}
	},
	mounted() {
		axios.get('rest/manager')
			.then(response => (this.managerFacility = response.data))
		axios.get('rest/currentUser').then(response=>(this.loggedKorisnik = response.data))

	},
	
	methods: {
//		searchKorisnici: function () {
//			axios.get('rest/regKorisnici/search',{ params: { searchValue: this.searchValue, criterion: this.searchType}} )
//			.then(response => (this.korisnici = response.data))
//		}
		
		
	}
});
