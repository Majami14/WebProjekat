/**
 * 
 */var app = new Vue({
	el: '#viewDues',
	data: {
		memberShip: {},
		error: '',
		trainings: [],
		minDate: Date.now()
	},
	mounted() {
		axios.get('rest/dues/getSelected')
			.then((response) => {
				this.memberShip = response.data;
			})
			this.minDate = new Date().toISOString().split("T")[0];
	},
	methods: {
		bookTraining: function(training) {
			axios.post('rest/dues/', this.memberShip)
			.then((response) => {
				this.memberShip = response.data;
			})
			event.preventDefault();
		}
	}
});