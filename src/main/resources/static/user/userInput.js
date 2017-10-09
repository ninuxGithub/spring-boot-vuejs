const UserInput ={
		template:`<div class="panel panel-default">
					<div class="panel-heading text-center">
					    <h2>User Input</h2>
					</div>
					<div class="panel-body">
						<form class="form-horizontal">
							<!-- User Name -->
							<div class="form-group">
								<label for="userName" class="col-sm-2 control-label">UserName</label>
								<div class="col-sm-4">
									<input type="text" class="form-control" v-model="user.username" id="userName" aria-describedby="helpBlock1" placeholder="place input UserName....">
								</div>
								<span id="helpBlock1" class="col-sm-2 has-error help-block ">{{userNameMsg}}</span>
							</div>
							
							<!-- Password -->
							<div class="form-group">
								<label for="password" class="col-sm-2 control-label">Password</label>
								<div class="col-sm-4">
									<input type="password" class="form-control" v-model="user.password" id="password" placeholder="place input Password....">
								</div>
								<div class="col-sm-2 help-block has-error">{{passwordMsg}}</div>
							</div>
				
							<div class="form-group">
								<div class="col-sm-offset-2 col-sm-10">
									<button  class="btn btn-primary" v-on:click="quit">Quit</button>
									<button  class="btn btn-primary" v-on:click="submitForm">Submit</button>
								</div>
							</div>
						</form>
					</div>
				</div>`,
		data(){
			return {
				user:{id: null, username: "", password: "", role: ""},
				userNameMsg:'',
		    	passwordMsg:'',
			}
		},
		
		
		created() {
			this.fetchData()
		},
		watch:{
			'$route':'fetchData',
		},
	    methods: {
	    	fetchData:function(){
	    		var id=this.$route.params.userId;
	    		console.dir(typeof(id))
	    		if(typeof(id)==='string' && id=='null'){
	    			id=null;
	    		}
	    		this.$http.post('/getUser', {'id':id}).then((response) => {
	    			this.user = response.data;
	    			console.dir(response)
	            }, (response) => {
	            	console.dir("2")
	            });
	    	},
	    	submitForm: function(e) {
	    		e.preventDefault();
	           	console.dir(this.user)
	           	var flag = true;
	           	if(this.user.username==null){
	           		this.userNameMsg = '用户名为空';
	           		flag=false;
	           		$("#userName").parents('.form-group').addClass('has-error');
	           	}else{
	           		$("#userName").parents('.form-group').removeClass('has-error');
	           		this.userNameMsg = '';
	           	}
	           	if(this.user.password ==null){
	           		this.passwordMsg = '密码为空';
	           		$("#password").parents('.form-group').addClass('has-error');
	           		flag=false;
	           	}else{
	           		$("#password").parents('.form-group').removeClass('has-error');
	           		this.passwordMsg = '';
	           	}
	           	if(flag){
		            this.$http.post('/addUser', this.user).then((response) => {
		                // success callback
		               console.dir(response)
		               console.dir('success...')
		               this.$router.push({ path: '/'})
		            }, (response) => {
		               console.dir('failed...')
		            });
	           	}
	        },
	        quit: function(){
	        	console.dir('quit submit...')
	        }
        }
}
