var app = new Vue({
	el: '#loginForm',
	data: {
		userName: '',
        password: '',
		errorUsername: '',
		error: ''
	},
	mounted() {},
	methods:{
		loginUser: function () {
			alert("Nesto");
			console.log("Nesto2");
			axios.post('rest/login', this.userName, this.password)
				.then((response) => {
					alert('User created successfully')
                })
		}
	}
});
