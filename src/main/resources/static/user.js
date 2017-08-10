
Vue.use(VueRouter);

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
			</div>`
}
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
					            <th>Operation</th>
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
					     <div>
					    	<a href="/userInput.html"  class="panel-default">添加</a>
					    </div>
					</div>
				</div>`
}



// 创建路由实例
const router = new VueRouter({
	routes:[
		{ path: '/', redirect: 'userIndex' },
		{ path: '/userIndex',name:'userIndex', component: UserIndex },
		{ path: '/userInput',name:'userInput', component: UserInput },
	]
});

var vm = new Vue({
    el: '#app',
    ready: function() {
    	console.dir("ready..")
    	router.push({path:'/userIndex'})
        this.$http.get('test.json', function(data) {
        	users = data;
            this.$set('users', data);
        }).error(function(data, status, request) {
            console.log('fail' + status + "," + request);
        })
    },
    data: {
    	
    },
    methods:{
    	removeUser:function(id){
    		this.$http.post('/deleteUser/'+id, function(data){
    			window.location.href="http://localhost/index.html";    			
    		}).error(function(data, status, request) {
                window.location.href="http://localhost/index.html";
            })
    	},
    	modifyUser:function(id){
    		this.$http.post('/modifyUser/'+id, function(data){
    			window.location.href="http://localhost/userInput.html?id="+id;    			
    		}).error(function(data, status, request) {
                window.location.href="http://localhost/index.html";
            })
    	}
    	
    },
    router:router
});