jq("#grid").jqGrid('navGrid','#pager',
			{edit:false,add:false,del:false,search:true},
			{ },
	        { },
	        { }, 
			{ 
		    	sopt:['eq', 'ne', 'lt', 'gt', 'cn', 'bw', 'ew'],
		        closeOnEscape: true, 
		        multipleSearch: true, 
		        closeAfterSearch: true 
		    }
	);
		
	jq("#grid").navButtonAdd('#pager',
			{ 	caption:"Add", 
				buttonicon:"ui-icon-plus", 
				onClickButton: addRow,
				position: "last", 
				title:"", 
				cursor: "pointer"
			} 
	);
	
	jq("#grid").navButtonAdd('#pager',
			{ 	caption:"Edit", 
				buttonicon:"ui-icon-pencil", 
				onClickButton: editRow,
				position: "last", 
				title:"", 
				cursor: "pointer"
			} 
	);
	
	jq("#grid").navButtonAdd('#pager',
		{ 	caption:"Delete", 
			buttonicon:"ui-icon-trash", 
			onClickButton: deleteRow,
			position: "last", 
			title:"", 
			cursor: "pointer"
		} 
	);
	jq("#btnFilter").click(function(){
		jq("#grid").jqGrid('searchGrid',
				{multipleSearch: false, 
					sopt:['eq']}
		);
	});
	// Toolbar Search
	jq("#grid").jqGrid('filterToolbar',{stringResult: true,searchOnEnter : true, defaultSearch:"cn"});