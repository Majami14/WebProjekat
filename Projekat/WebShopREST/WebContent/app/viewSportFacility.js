 /**
 * 
 */var app = new Vue({
	 el: '#viewSF',
	 data: {
		 sf: {},
		 error: '',
		 trainings: [],
		 loggedUser: {}
	 },
	 mounted() {
		 axios.get('rest/facility/getSelected')
			 .then((response) => {
				 this.sf = response.data;
				 axios.get('rest/training/getTrainingsForObject/' + this.sf.id)
					 .then((response) => {
						 this.trainings = response.data
					 })
			 })
		 axios.get('rest/currentUser')
			 .then((response) => {
				 this.loggedUser = response.data;
			 })
	 },
	 methods: {
		 prijavaNaTrening: function(training) {
			 axios.post('rest/training/addTrainingToUser', training)
				 .then((response) => {
					 alert('booked training')
				 }).catch((response) => {
					 alert('membership expired')
				 })
		 },
		 updateTraining: function(training) {
			 axios.post('rest/training/setSelected', training)
				 .then((response) => {
					 window.location.href = 'http://localhost:8080/WebShopREST/editTraining.html';
				 })
		 }
	 }
 })