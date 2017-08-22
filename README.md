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
		
		jqgrid 数据展示
		
		jqgrid 增删改查
		
		gons json转对象
		
		Excel导出 demo 
		
		
		今天从Excel4J源代码发现可以通过注解的方式在JavaBean中来标记想要导出Excel的字段，具体代码如下：
		
```java
public static List<ExcelFieldBean> analyseExcelField(Class<?> clazz) {
	List<ExcelFieldBean> excleFieldBeanList = new ArrayList<>();
	for(Class<?> c = clazz; c != Object.class; c = c.getSuperclass()){
		Field[] declaredFields = c.getDeclaredFields();
		for (Field field : declaredFields) {
			if(field.isAnnotationPresent(ExcelField.class)){
				ExcelField excelField = field.getAnnotation(ExcelField.class);
				ExcelFieldBean excelFieldBean = new ExcelFieldBean(excelField.title(), excelField.order(), field.getName(), field.getType());
				excleFieldBeanList.add(excelFieldBean);
			}
			
		}
	}
	
	//排序
	Collections.sort(excleFieldBeanList);
	return excleFieldBeanList;
}```java
		
		
		
## 效果图片
![image](https://github.com/ninuxGithub/spring-boot-vuejs/blob/master/vue.png)


## JQgrid
![image](https://github.com/ninuxGithub/spring-boot-vuejs/blob/master/jQgridTable.png)

## JQgrid 增删改查的demo
	项目启动后的链接地址：http://localhost/user/orders.html<br/>
	效果：
![image](https://github.com/ninuxGithub/spring-boot-vuejs/blob/master/jqgrid-curd.png)




		
