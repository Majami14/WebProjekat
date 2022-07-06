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
		 selectedManager: null,
		 showRegisterForm: false
	 },
	 mounted() {
		 axios.get('rest/korisnik/freeManagers')
			 .then((response) => {
				 console.log(response.data)
				 this.freeManagers = response.data
			 })
		 this.newFacility = { id: '', name: null, type: null, content: null, status: 'CLOSE', location: null, image: null, average: null, startTime: null, endTime: null };
		 this.newLocation = { id: '', length: '', width: '', street: null, city: null, number: '', post: null };


	 },
	 methods: {
		 createFacility: function(event) {
			 this.error = ""
			 if (!this.newFacility.name || !this.newFacility.object_type || !this.newFacility.logo_picture) {
				 this.error = "Fill all input fields.";
				 event.preventDefault();
				 return;
			 }
			 if (!this.newLocation.geo_lenght || !this.newLocation.geo_width || !this.newLocation.street_name || !this.newLocation.street_number || !this.newLocation.city || !this.newLocation.city_number) {
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
					  this.newSO = response.data;
					 this.selectedManager.object = this.newSO;
					 axios.put('rest/users/', this.selectedManager)
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
		 showForm: function() {
			 this.showRegisterForm = true;
		 }, createUser: function(event) {
			 if (!this.selectedManager.name || !this.selectedManager.surname || !this.selectedManager.birthday || !this.selectedManager.gender || !this.selectedManager.user_name || !this.selectedManager.user_password) {
				 this.error = "Fill all input fields.";
				 event.preventDefault();
				 return;
			 }
			 this.selectedManager.role = 'MANAGER';
			 axios.post('rest/users/', this.selectedManager)
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