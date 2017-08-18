# spring-boot-vuejs
vuejs crud

## 内容
vuejs 增删改查的demo，自动建表 <br>
数据连接池库采用了HikariCP<br>
值得注意的地方时， 在component模块拥有的方法类似于<br>
-----------------------------------------
		var vm =new Vue({
	    	el: '#app',
	    	router:new VueRouter({
		    	routes:[
		    		{ path: '/', redirect: 'userIndex' },
		    		{ path: '/userIndex', name:'userIndex',  component: UserIndex},
		    		{ path: '/userInput/:userId',name:'userInput', component: UserInput },
		    	]
	    	})
		});
		
		
		在组件里面获取router（路由）: this.$router
		在组件里面获取参数：this.$route.params.xxxx   this.$route.query.xxxxx   xxxx 是你要获取的参数
		这两个变量是不同的
		
		
		加入分页的功能
		
## 效果图片
![image](https://github.com/ninuxGithub/spring-boot-vuejs/blob/master/vue.png)



		
