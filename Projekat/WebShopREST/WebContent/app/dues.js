var app = new Vue({
	 el: '#DuesForm',
	 data: {
		 memberships: [],
		 loggedUser: {}
	 },
	 mounted() {	//sta ciljam?
		 axios.get('rest/korisnik/currentUser')
			 .then((response) => {
				 this.loggedUser = response.data;

				 membership1 = { idDues: "0123456789",id: 1 , type: 'DAY', paymentDate: null, firstDay: null, lastDay: null, price: 10, buyer: null, duesStatus: 'INACTIVE', trainingNumbers: 1 };
				 membership2 = { idDues: "hjo2893bd9", id: 2 , type: 'MONTH', paymentDate: null, firstDay: null, lastDay: null, price: 60, buyer: null, duesStatus: 'INACTIVE', trainingNumbers: 30 };
				 membership3 = { idDues: "naoxm37ab2", id: 3, type: 'YEAR', paymentDate: null, firstDay: null, lastDay: null, price: 300, buyer: null, duesStatus: 'INACTIVE', trainingNumbers: 360 };
				 this.memberships.push(membership1);
				 this.memberships.push(membership2);
				 this.memberships.push(membership3);
			 })
						

	 },


	 methods: {
		 Selected: function(membership) {
			 axios.post('rest/dues/setSelected', membership).then(response => {
				 window.location.href = 'http://localhost:8080/WebShopREST/viewDue.html';
			 })

		 }
	 }

 });