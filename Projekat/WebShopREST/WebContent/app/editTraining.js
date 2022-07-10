/**
 * 
 */var app = new Vue({
	 el: '#editTraining',
	 data: {
		 newContent: {},
		 error: '',
		 loggedUser: {},
		 sportObject: {},
		 trainers: [],
		 selectedTrainer: {}
	 },
	 mounted() {
		 axios.get('rest/training/getSelected')
			 .then((response) => {
				 this.newContent = response.data
				 axios.get('rest/korisnici/' + this.newContent.coachID)
					 .then((response) => {
						 this.selectedTrainer = response.data;
					 })
			 })

		 axios.get('rest/korisnici/trainers')
			 .then((response) => {
				 this.trainers = response.data;
			 })
	 },
	 methods: {
		 editTraining: function(event) {
			axios.put('rest/training/', this.newContent)
			 .then((response) => {
				 
			 })
			 event.preventDefault()
		 }
	 }
 });