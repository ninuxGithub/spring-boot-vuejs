package com.example.demo.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.bean.Order;
import com.example.demo.repository.OrderRepository;
import com.fasterxml.jackson.databind.util.JSONPObject;

@Controller
public class AutoCompleteController {
	
	private static final Logger logger = LoggerFactory.getLogger(AutoCompleteController.class);
	
	@Autowired
	private OrderRepository orderRepository;

	//http://www.guriddo.net/demo/bootstrap/
	
	@ResponseBody
	@RequestMapping(value = "/autoScript", method = RequestMethod.GET)
	public String autoScript(@RequestParam("term") String query,
							@RequestParam("element") String element,
							@RequestParam(value ="callback", required=true) String callback,
							@RequestParam(value ="_", required=true) String line){
		logger.info("autoScript run....");
		StringBuffer str = new StringBuffer();
		str.append("<script type='text/javascript'>jQuery(document).ready(function() {if(jQuery.ui) { if(jQuery.ui.autocomplete){jQuery('ShipName').autocomplete({'appendTo':'body','disabled':false,'delay':300,'minLength':1,'source':function (request, response)");
		str.append("{");
		str.append("request.acelem = '"+element+"';");
		str.append("request.query = '"+query+"';");
		str.append("$.ajax({");
		str.append("url: '/autoCompleteStr',");
		str.append("dataType: 'json',");
		str.append("data: request,");
		str.append("type: 'GET',");
		str.append("error: function(res, status) {");
		str.append("alert(res.status+' : '+res.statusText+'. Status: '+status);");
		str.append("},");
		str.append("success: function( data ) {");
		str.append("response( data );");
		str.append("}");
		str.append("});");
		str.append("}});jQuery('"+element+"').autocomplete('widget').css('font-size','11px');jQuery('"+element+"').autocomplete('widget').css('z-index','1000');} } });</script>");
		return str.toString();
	}
	
	@ResponseBody
	@RequestMapping(value = "/autoCompleteStr", method = RequestMethod.GET)
	public List<Entry>  autoCompleteStr(@RequestParam("term") String query, @RequestParam("element") String element){
		List<Order> orders = orderRepository.findAll();
		logger.info("autoCompleteStr run....");
		
		List<Entry> list = new ArrayList<>();
		for (Order order : orders) {
			if(order.getProduceName().contains(query)){
				String str = order.getProduceName();
				list.add(new Entry(str,str));
			}
		}
		return list;
	}
	
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
	
	@ResponseBody
	@RequestMapping(value = "/autoTypeahead", method = RequestMethod.GET)
	public List<Order> autoTypeahead(@RequestParam("term") String query){
		
		List<Order> orders = orderRepository.findAll();
		
		if(StringUtils.isNotBlank(query)){
			List<Order> list = new ArrayList<>();
			for(Order order : orders){
				if(order.getProduceName().contains(query)){
					list.add(order);
				}
			}
			return list;
		}
		return orders;
		
	}
	
	
	@ResponseBody
	@RequestMapping("/amountSelect")
	public String amountSelect(){
		StringBuffer str = new StringBuffer();
		List<Order> orders = orderRepository.findAll();
		Map<Integer, Integer> map = new HashMap<>();
		List<Integer> list = new ArrayList<>();
		for (Order order : orders) {
			Integer stockAmount = order.getStockAmount();
			if(!map.containsKey(stockAmount)){
				list.add(stockAmount);
				map.put(stockAmount, stockAmount);
			}
		}
		Collections.sort(list);
		
		for (Integer i : list) {
			str.append(i+":"+i+";");
		}
		return str.toString();
	}
	
	class Entry{
		String id;
		
		String name;
		public Entry(String id, String name) {
			this.id = id;
			this.name = name;
		}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		@Override
		public String toString() {
			return "Entry [id=" + id + ", name=" + name + "]";
		}
		
	}
	
}

