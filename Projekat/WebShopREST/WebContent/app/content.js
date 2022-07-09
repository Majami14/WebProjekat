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
		this.newContent = { id: '', name: null, type: null, sportFacility: null, duration: null, coach: null, description: null, image: '' }
		axios.get('rest/currentUser')
			.then((response) => {
				this.loggedUser = response.data;

				axios.get('rest/facility/' + this.loggedUser.object.id)
					.then((response) => {
						this.sportObject = response.data;
						this.newContent.object = this.sportObject;
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