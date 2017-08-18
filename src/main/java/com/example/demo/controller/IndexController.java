package com.example.demo.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.bean.User;
import com.example.demo.repository.UserRepository;

@Controller
public class IndexController {

	private static final Logger logger = LoggerFactory.getLogger(IndexController.class);
	
	private static final Integer PAGECONT = 10;

	@Autowired
	private UserRepository userRepository;

	@RequestMapping(value = { "/" }, method = RequestMethod.GET)
	public String index() {
		logger.info("this is index page...");
		return "index";
	}


	@ResponseBody
	@RequestMapping("/test.json")
	public Map<String, Object> loadUserPages (@RequestParam(name="pageNo", defaultValue="1") Integer pageNo) {
		long start = System.currentTimeMillis();
		//pageNo-1非常重要
		PageRequest pageRequest = new PageRequest(pageNo-1, PAGECONT);
		Page<User> userPages = userRepository.findAll(pageRequest);
//		List<User> users = userRepository.findAll();
		Map<String, Object> map = new HashMap<>();
		map.put("userPages", userPages);
		map.put("pageNo", pageNo);
		long end = System.currentTimeMillis();
		logger.info("spend time :{} ------- pageNo is :{}",(end-start), pageNo);
		return map;
	}
	
	@ResponseBody
	@RequestMapping("/newUser.json")
	public User inputUser(@RequestParam Long id) {
		if(null == id){
			return new User();
		}else{
			return userRepository.findOne(id);
		}
	}
	
	
	@RequestMapping(value = "/addUser", method = RequestMethod.POST)
	public String inputUser(User user) {
		logger.info("user is : "+user.toString());
		user.setRole("ROLE_ADMIN");
		if(user.getId()==null){
			userRepository.save(user);
		}else{
			userRepository.saveAndFlush(user);
		}
		return "redirect:/";
	}
	
	
	@RequestMapping(value = "/deleteUser", method = RequestMethod.POST)
	public String deleteUser(@RequestParam Long id) {
		logger.info("delete id is :{}", id);
		userRepository.delete(id);
		return "redirect:/";
	}

	@ResponseBody
	@RequestMapping(value = "/getUser", method = RequestMethod.POST)
	public User getUser(@RequestParam Long id) {
		logger.info("getUser id is : {}", id);
		if(null == id){
			return new  User();
		}
		User user = userRepository.findOne(id);
		return user;
	}
//	@ResponseBody
//	@RequestMapping(value = "/getUser/{id}", method = RequestMethod.GET)
//	public User getUser(@PathVariable(required=false) Long id) {
//		logger.info("getUser id is : {}", id);
//		if(null == id|| id==-1){
//			return new  User();
//		}
//		User user = userRepository.findOne(id);
//		return user;
//	}
}
