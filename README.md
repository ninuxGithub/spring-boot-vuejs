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
		
		jqgrid 增删改查:
		{
			jqgrid 标准的修改流程(更加优雅)：http://localhost/user/jqgridDemo.html
			jqgrid 自定义按钮修改(自定义)：http://localhost/user/orders.html
		}
		
		
		jqgrid [合并单元格：](http://www.cnblogs.com/puke/archive/2012/10/17/2728435.html)
		请查看效果：http://localhost/user/merge.html
		
		gons json转对象
		
		Excel导出 demo 
		
		
		今天从Excel4J源代码发现可以通过注解的方式在JavaBean中来标记想要导出Excel的字段，具体代码如下：
		在注解类加入了表头标题， 日期格式化，数字格式化， 以及文字居中的设置，开启用户自定义的选择
		
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
}
```		

		还有一个核心的方法，那就是构造多行不规则的表头的问题，比较复杂， 需要实现接口ExcelHeaderData ， 需要实现方法buildHeaderDataList()
```java
	public static void buildTableHeader(HSSFWorkbook wb, HSSFSheet sheet, ExcelHeaderData excelHeaderData, int rownum) {
		List<ExcelRow> headerRows = excelHeaderData.buildHeaderDataList();
		int totalCols = getTotalCols(headerRows);
		logger.trace("totalColspan :" + totalCols);

		HSSFCellStyle titleStyle = ExcelUtil.titleStyle(wb);
		for (int r = 0; r < headerRows.size(); r++) {
			Row row = null;
			Cell cell = null;
			row = sheet.createRow(rownum + r);
			increaseRow();//增加行
			ExcelRow excelRow = headerRows.get(r);
			LinkedList<ExcelCell> rows = excelRow.getRows();
			for (int c = 0; c < totalCols; c++) {
				ExcelCell excelCell = rows.get(c);
				int colspan = excelCell.getColspan();
				int rowspan = excelCell.getRowspan();
				String title = excelCell.getTitle();
				int colIndex = excelCell.getColIndex();

				cell = row.createCell(c);
				cell.setCellStyle(titleStyle);
				if (colIndex == c) {
					if(null != title){
						cell.setCellValue(title);
					}
					if (rowspan > 1 || colspan > 1) {
						CellRangeAddress region = new CellRangeAddress(rownum + r, rownum + r + rowspan - 1, c, c + colspan - 1);
						sheet.addMergedRegion(region);
					}
				}
			}
		}
	}
```

Jqgrid 自动提示
```javascript
dataInit : function (element)
{
    $(element).attr("autocomplete", "off").typeahead(
    {
        appendTo : "body",
        source : function (query, proxy)
        {
            $.ajax(
            {
                url : '/autoJson?callback=?',
                dataType : "jsonp",
                data :
                {
                    term : query
                },
                success : proxy
            }
            );
        }
    }
    );
}
```
对应的后台接口为：
```java
	@ResponseBody
	@RequestMapping(value = "/autoJson", method = RequestMethod.GET)
	public JSONPObject autoScriptUpdate(@RequestParam("term") String query,
							@RequestParam(value ="callback", required=true) String callback,
							@RequestParam(value ="_", required=true) String line){
		
		List<Order> orders = orderRepository.findAll();
		logger.info("autoJson run....");
		logger.info(callback);
		
		List<Entry> list = new ArrayList<>();
		for (Order order : orders) {
			if(order.getProduceName().contains(query)){
				String str = order.getProduceName();
				list.add(new Entry(str,str));
			}
		}
		System.out.println(list);
		
		return new JSONPObject(callback, list);   
	}
```		




## Excel 一些常用的API
from:http://blog.csdn.net/spp_1987/article/details/13769043  
HSSFCellStyle cellStyle = wb.createCellStyle();    
### 一、设置背景色:  
  
  
cellStyle.setFillForegroundColor((short) 13);// 设置背景色    
cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);    
### 二、设置边框:  
  
  
cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框    
cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框    
cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框    
cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框    
### 三、设置居中:  
  
  
cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中    
### 四、设置字体:  
HSSFFont font = wb.createFont();    
font.setFontName("黑体");    
font.setFontHeightInPoints((short) 16);//设置字体大小    
    
HSSFFont font2 = wb.createFont();    
font2.setFontName("仿宋_GB2312");    
font2.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//粗体显示    
font2.setFontHeightInPoints((short) 12);    
    
cellStyle.setFont(font);//选择需要用到的字体格式    
### 五、设置列宽:  
  
sheet.setColumnWidth(0, 3766);   
//第一个参数代表列id(从0开始),第2个参数代表宽度值  参考 ："2012-08-10"的宽度为2500    
### 六、设置自动换行:  
  
cellStyle.setWrapText(true);//设置自动换行    
### 七、合并单元格:  
  
Region region1 = new Region(0, (short) 0, 0, (short) 6);//参数1：行号 参数2：起始列号 参数3：行号 参数4：终止列号    
//此方法在POI3.8中已经被废弃，建议使用下面一个    
或者用  
CellRangeAddress region1 = new CellRangeAddress(rowNumber, rowNumber, (short) 0, (short) 11);     
  
  
//参数1：起始行 参数2：终止行 参数3：起始列 参数4：终止列      
但应注意两个构造方法的参数不是一样的，具体使用哪个取决于POI的不同版本。   
  
  
sheet.addMergedRegion(region1);    


### 反射：
Class c = ArrayList.class;

c.isPrimitive(); //判断c是否为基本数据类型

c.isAssignableFrom(List.class);  //判断c是否是List类的子类或父类

c.getGenericType(); //得到泛型类型

		
		
## 效果图片
![image](https://github.com/ninuxGithub/spring-boot-vuejs/blob/master/vue.png)


## JQgrid
![image](https://github.com/ninuxGithub/spring-boot-vuejs/blob/master/jQgridTable.png)

## JQgrid 增删改查的demo
	项目启动后的链接地址：http://localhost/user/orders.html<br/>
	效果：
![image](https://github.com/ninuxGithub/spring-boot-vuejs/blob/master/jqgrid-curd.png)

## JQgrid 单元格合并
![image](https://github.com/ninuxGithub/spring-boot-vuejs/blob/master/jqgridMerge.png)




		
