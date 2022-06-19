var app = new Vue({
	el: '#sportFacility',
	data: {
		sportObjects: null,
		searchValue: "",
		searchType: "",
		nesto: "",
		loggedUser: {}
	},
	mounted() {
		axios.get('rest/facility')
			.then(response => (this.sportObjects = response.data))
		axios.get('rest/currentUser').then(response=>(this.loggedUser = response.data))

	},
	
	methods: {
		searchFacilitys: function () {
			axios.get('rest/facility/searchFacility',{ params: { searchValue: this.searchValue, criterion: this.searchType}} )
			.then(response => (this.sportObjects = response.data))
		}
		
		
	}
});
