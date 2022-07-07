 /**
 * 
 */var app = new Vue({
	el: '#viewSF',
	data: {
		sf: {},
		error: '',
		trainings: []
	},
	mounted() {
		axios.get('rest/facility/getSelected')
		.then((response) => {this.sf = response.data;
		axios.get('rest/training/getTrainingsForObject/' + this.sf.id)
					 .then((response) => {
						this.trainings = response.data
					 })
					 })
		},
	methods: {
		prijavaNaTrening: function(training){
			
		}
	}
})