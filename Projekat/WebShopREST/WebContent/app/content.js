/**
 * 
 */var app = new Vue({
	el: '#creatingContent',
	data: {
		newContent: {},
		error: '',
		loggedUser: {},
		sportObject: {},
		trainers: [],
		selectedTrainer: {}
	},
	mounted() {
		this.newContent = {name: null, type: null, sportFacility: {}, duration: null, coach: null, description: null, image: '', id: '' }
		axios.get('rest/currentUser')
			.then((response) => {
				this.loggedUser = response.data;

				axios.get('rest/facility/' + this.loggedUser.facility.id)
					.then((response) => {
						this.sportObject = response.data;
						this.newContent.sportFacility = this.sportObject;
					})



			})

		axios.get('rest/korisnik/trainers')
			.then((response) => {
				this.trainers = response.data;
			})

	},
	methods: {
		createUser: function(event) {
			this.newContent.coach = this.selectedTrainer;
			axios.post('rest/training/', this.newContent)
				.then((response) => {

				})
			event.preventDefault();
		}
	}
});