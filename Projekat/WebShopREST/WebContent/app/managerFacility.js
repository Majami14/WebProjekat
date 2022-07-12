var app = new Vue({
	el: '#managerFacility',
 data: {

		 loggedUser: { userName: null, password: null, firstName: null, lastName: null, gender: null, birthDate: null, role: null, dues: null, object: { id: "", name: null, type: null, status: null, location: { id: "", length: "", width: "", street: null, number: null, city: null, post: null }, image: null, average: "", startTime: null, endTime: null } },
		 id: "",
		 trainings: null,
		 peopleThatVisited: [],
		 trainers: []


	 },
	 mounted() {
		 axios.get('rest/currentUser')
			 .then((response) => {
				 this.loggedUser = response.data;

				 this.id = this.loggedUser.facility.id;

				 axios.get('rest/training/getTrainingsForObject/' + this.loggedUser.facility.id)
					 .then((response) => {
						 this.trainings = response.data
					 })
				 axios.get('rest/korisnik/getPeopleThatVisitedObject/' + this.loggedUser.facility.id)
					 .then((response) => {
						 this.peopleThatVisited = response.data
					 })

				 axios.get('rest/korisnik/getTrainersForObject/' + this.loggedUser.facility.id)
					 .then((response) => {
						 this.trainers = response.data
					 })



			 })
	 },


	 methods: {
		 updateTraining: function(training) {
			 axios.post('rest/training/setSelected', training)
				 .then((response) => {
					 window.location.href = 'http://localhost:8080/WebShopREST/editTraining.html';
				 })
		 }
	 }
});
