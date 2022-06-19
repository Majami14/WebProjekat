var app = new Vue({
	el: '#sportFacility',
	data: {
		sportObjects: null
	},
	mounted() {
		axios.get('rest/facility')
			.then(response => (this.sportFacility = response.data))
	},
	methods: {
		
		
		
	}
});
