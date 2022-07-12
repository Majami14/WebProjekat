/**
 * 
 */var app = new Vue({
	 el: '#createFacilityForm',
	 data: {
		 newFacility: {},
		 newLocation: {},
		 error: '',
		 freeManagers: null,
		 haveManagers: 'true',
		 selectedManager: {},
		 showRegisterForm: false
	 },
	 mounted() {
		 axios.get('rest/korisnik/freeManagers')
			 .then((response) => {
				 console.log(response.data)
				 this.freeManagers = response.data
			 })
		 this.newFacility = { id: '', name: null, type: null,  status: 'CLOSE', location: null, image: null, average: null, startTime: null, endTime: null };
		 this.newLocation = { id: '', length: '', width: '', street: null,number: '', city: null,  post: null };


	 },
	 methods: {
		 createFacility: function(event) {
			 this.error = ""
			 if (!this.newFacility.name || !this.newFacility.type  || !this.newFacility.startTime || !this.newFacility.endTime) {
				 this.error = "Fill all input fields.";
				 event.preventDefault();
				 return;
			 }
			 if (!this.newLocation.length || !this.newLocation.width || !this.newLocation.street || !this.newLocation.number || !this.newLocation.city || !this.newLocation.post) {
				 this.error = "Fill all input fields.";
				 event.preventDefault();
				 return;
			 }
			 if (this.selectedManager === null) {
				 this.selectedManager = this.freeManagers[0]
			 }

			 this.newFacility.location = this.newLocation
			 axios.post('rest/facility/', this.newFacility)
				 .then((response) => {
					 alert('New sport object created.')
					  this.newFacility = response.data;
					 this.selectedManager.facility = this.newFacility;
					 axios.put('rest/korisnik/', this.selectedManager)
						 .then((response) => {
							 alert('Sport object added to manager')
						 }).catch(() => {
							 alert('This sport object already exists.USER');
							 this.error = "This sport object already exists.";
							 event.preventDefault();
							 return;
						 })
				 })
				 .catch(() => {
					 alert('This sport object already exists.');
					 this.error = "This sport object already exists.";
					 event.preventDefault();
					 return;
				 })
				
			 event.preventDefault();
		 },
		 selectManager: function(manager) {
			 this.selectedManager = manager;
		 },
		 uploadImage: function() {
			 var fileData = event.target.files[0];
			 this.newFacility.image = fileData.name;
			 
		 },
		 showForm: function() {
			 this.showRegisterForm = true;
		 }, createUser: function(event) {
			 if (!this.selectedManager.userName || !this.selectedManager.password || !this.selectedManager.firstName || !this.selectedManager.lastName || !this.selectedManager.gender || !this.selectedManager.birthDate   ) {
				 this.error = "Fill all input fields.";
				 event.preventDefault();
				 return;
			 }
			 this.selectedManager.role = 'MANAGER';
			 axios.post('rest/korisnik/', this.selectedManager)
				 .then((response) => {
					 alert('New user registered.')
					 this.selectedManager = response.data
				 })
				 .catch(() => {
					 alert('username already exists.')
					 this.error = "username already exists.";
					 event.preventDefault();
					 return;
				 })
			 event.preventDefault();
		 }
	 }
 });