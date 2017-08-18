package com.example.demo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.bean.CallBack;
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
	public Map<String, Object> loadUserPages(@RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo) {
		long start = System.currentTimeMillis();
		// pageNo-1非常重要, 默认是根据ID 升序排序的
		PageRequest pageRequest = new PageRequest(pageNo - 1, PAGECONT, new Sort(Direction.ASC, "id"));
		Page<User> userPages = userRepository.findAll(pageRequest);

		logger.info(userPages.toString());
		// List<User> users = userRepository.findAll();
		Map<String, Object> map = new HashMap<>();
		map.put("userPages", userPages);
		map.put("pageNo", pageNo);
		long end = System.currentTimeMillis();
		logger.info("spend time :{} ------- pageNo is :{}", (end - start), pageNo);
		return map;
	}

	/**
	 * 为jqgrid 提供数据的请求
	 * @param rows
	 * @param page
	 * @param sidx
	 * @param sord
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/loadUsers", method = RequestMethod.POST)
	public CallBack<User> loadUsers(
			@RequestParam(value = "rows", required = false) Integer rows,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "sidx", required = false, defaultValue="id") String sidx,
			@RequestParam(value = "sord", required = false) String sord) {
		logger.info("rows:"+rows+"  page:"+page+"  sidx:" +sidx+"  sord:"+sord);
		List<User> list = userRepository.findAll();
		CallBack<User> handListPage = handListPage(rows, page, sidx, sord, list);
		return handListPage;
	}

	/**
	 * 分页的核心逻辑：
	 * 			<b>这行变量是通过jqGrid 发送请求自带的参数    (如果是滚动翻页，需要提供page,scroll参数)  采用普通的$.ajax 请求数据是没有的</b>
	 * @param rows
	 * @param page
	 * @param sidx
	 * @param sord
	 * @param pageList
	 * @return
	 */
	private <T, E> CallBack<T> handListPage(Integer rows, Integer page, String sidx, String sord, List<T> pageList) {
		//考虑根据前台的字段排序（省略）
		//TODO: sort jqgrid 
		Integer size = 0;
		Integer totalPage = 0;
		if (null != pageList && pageList.size() > 0) {
			size = pageList.size();
			int by = size / rows;
			totalPage = size % rows == 0 ? by : by + 1;
			if (page > totalPage) {
				page = totalPage;
			}
		}
		Integer startIndex = (page - 1) * rows;
		Integer endIndex = page * rows;
		List<T> subList = new ArrayList<>();

		if (null != pageList && pageList.size() > 0) {
			size = pageList.size();
			if (startIndex > size) {
				startIndex = size - rows;
			}
			if (endIndex > size) {
				endIndex = size;
			}
			subList = pageList.subList(startIndex, endIndex);
		}
		return new CallBack<T>(size, page, totalPage, subList);
	}

	@ResponseBody
	@RequestMapping("/newUser.json")
	public User inputUser(@RequestParam Long id) {
		if (null == id) {
			return new User();
		} else {
			return userRepository.findOne(id);
		}
	}

	@RequestMapping(value = "/addUser", method = RequestMethod.POST)
	public String inputUser(User user) {
		logger.info("user is : " + user.toString());
		user.setRole("ROLE_ADMIN");
		if (user.getId() == null) {
			userRepository.save(user);
		} else {
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
		if (null == id) {
			return new User();
		}
		User user = userRepository.findOne(id);
		return user;
	}
	// @ResponseBody
	// @RequestMapping(value = "/getUser/{id}", method = RequestMethod.GET)
	// public User getUser(@PathVariable(required=false) Long id) {
	// logger.info("getUser id is : {}", id);
	// if(null == id|| id==-1){
	// return new User();
	// }
	// User user = userRepository.findOne(id);
	// return user;
	// }
}
