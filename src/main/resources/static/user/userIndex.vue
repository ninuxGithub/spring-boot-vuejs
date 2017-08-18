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
						<div id="pager"></div>
					</div>
					
					<div class="panel-heading text-center">
					    <h2>User List  JQgrid(//TODO : operation)</h2>
					</div>
					<div id="user_table_div" class="panel-body"></div>
				</div>`,
		data(){
			return {
				users:[],
				pageNo:1,
				totalPages:1
			}
		},
		created() {
			this.fetchData(this.pageNo);
		},
		watch:{
			'$route':'fetchData'
		},
	    methods: {
	    	fetchData:function(pageNo){
				this.$http.get('/test.json?pageNo='+pageNo, function(data) {
					//this.users = data.uer;
					console.dir(data)
					this.users = data.userPages.content;
					this.pageNo = data.pageNo;
					this.totalPages = data.userPages.totalPages;
					
					//分页js开始
					var that = this;
		    		$(function(){	    		
						jQuery("#pager").pager({ pagenumber: that.pageNo, pagecount:that.totalPages , buttonClickCallback: pageClick });
						
						function pageClick(pageNo){
							console.dir("current page is:" + pageNo);
							that.fetchData(pageNo);
						}
					});
					
					//####################jqgrid begin####################
					
					$('#user_table_div').empty().html('<table id="user_table" width="100%" class="table table-hover"></table><div id="jQpager" class="panel-body"></div>');
					var $table = $('#user_table');
				    $table.empty();
				    $table.jqGrid({
				        datatype : "local",
				        colModel: [
				            { label: 'ID', name: 'id', width: 200, align:'center', key:true},
				            { label: '用户名称', name: 'username', width: 300, align:'center'},
				            { label: '密码', name: 'password',width:300, align:'center'},
				            { label: '操作', sortable: false,name: '', width: 150 ,align:'center',
				            	formatter: function(cellValue, options, rowObject)
				            	{
					            	return '<a class="success-info" onclick="window.operate('+options.rowId+','+rowObject.id+')" >操作'+rowObject.id+'</a>';
				            	}
				            }
				        ],
				        rowNum: 5,
				        shrinkToFit: false,
				        viewrecords: true,
				        gridview: true,
				        sortable:false,
				        width:1000,
				        height:'auto',
				        loadui: "Disable",
				        recordtext : "记录 {0} ~ {1} | 总记录数 {2}",//显示记录数的格式
				        emptyrecords : "",//空记录时的提示信息
				        pgtext : "{0}/{1}",//页数显示格式
				        rownumbers: true, //可自动在表格前面添加序号
				        rownumWidth:50,
				        
				        //*************需要pager对象
				        pager: "#jQpager"
				    });
				    $table.jqGrid('setLabel', 'rn', '序号', {'text-align':'center','vertical-align': 'center', 'width:':'100px'}, {'title':'序号'});
				    for ( var i = 0; i <= this.users.length; i++){
				   	   $table.jqGrid('addRowData', i + 1, this.users[i]);
				    }
		        }).error(function(data, status, request) {
		            console.log('fail:' + status + "," + request);
		        })
	    	},
	    	operate: function(row, id){
			    alert(id+ ''+ row)
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