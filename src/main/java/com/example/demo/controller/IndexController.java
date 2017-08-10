package com.example.demo.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

	@Autowired
	private UserRepository userRepository;

	@RequestMapping(value = { "/" }, method = RequestMethod.GET)
	public String index() {
		logger.info("this is index page...");
		return "index";
	}


	@ResponseBody
	@RequestMapping("/test.json")
	public List<User> loadUsers() {
		long start = System.currentTimeMillis();
		List<User> users = userRepository.findAll();
		long end = System.currentTimeMillis();
		logger.info("spend time :{}",(end-start));
		return users;
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
