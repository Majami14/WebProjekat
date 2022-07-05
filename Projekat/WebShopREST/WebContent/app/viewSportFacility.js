 /**
 * 
 */var app = new Vue({
	el: '#viewSF',
	data: {
		sf: {},
		error: ''
	},
	mounted() {
		axios.get('rest/facility/getSelected')
		.then((response) => {this.sf = response.data})
		},
	methods: {
		
	}
})