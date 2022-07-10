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
		loginForm: function (event) {
			axios.post('rest/login', {userName: this.userName, password: this.password})
				.then((response) => {
					alert('User logged in successfully.')
			   }).catch(() =>{
					alert('not valid username od password')
					event.preventDefault();
					return;
				})
				event.preventDefault();
				return;
		}
	}
});

