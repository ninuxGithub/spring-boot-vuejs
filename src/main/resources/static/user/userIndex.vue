const UserIndex ={
		template:`<div class="panel panel-default">
					<div class="panel-heading text-center">
					    <h2>User List</h2>
					</div>
					
					<div class="panel-body">
					    <table class="table table-hover">
					        <thead>
					        <tr>
					            <th>ID</th>
					            <th>UserName</th>
					            <th>Password</th>
					            <th>Role</th>
					            <th>Operation &nbsp;<router-link :to="{ name: 'userInput', params: { userId: 'null' }}" class="panel-default" >添加</router-link></th>
					        </tr>
					        </thead>
					        <tbody>
					            <tr v-for="user in users">
					                <td>{{user.id}}</td>
					                <td>{{user.username}}</td>
					                <td>{{user.password}}</td>
					                <td>{{user.role}}</td>
					                <td>
					                	<button v-on:click="removeUser(user.id);" class="btn btn-primary">删除</button>
					                	<button v-on:click="modifyUser(user.id);" class="btn btn-info">修改</button>
					                </td>
					            </tr>
					        </tbody>
					    </table>
					</div>
				</div>`,
		data(){
			return {
				users:[],
			}
		},
		created() {
			this.fetchData()
		},
		watch:{
			'$route':'fetchData'
		},
	    methods: {
	    	fetchData:function(){
				this.$http.get('/test.json', function(data) {
					this.users = data;
		        }).error(function(data, status, request) {
		            console.log('fail:' + status + "," + request);
		        })
	    	},
	    	removeUser:function(id){
	    		this.$http.post('/deleteUser', {'id':id}).then((response) => {
	            	console.dir("1")
	            	console.dir(response)
	            	this.fetchData();
	    			this.$router.push({path:'/'});
	            }, (response) => {
	            	console.dir("2")
	            });
	    	},
	    	modifyUser:function(id){
	    			this.$router.push({name:'userInput', params:{userId:id}});
	    	}
        }
}