package com.project.bookthief.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.project.bookthief.entity.User;
import com.project.bookthief.global.GlobalData;
import com.project.bookthief.repository.RoleRepository;
import com.project.bookthief.repository.UserRepository;
import com.project.bookthief.service.UserService;

@Controller
public class LoginController {

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	UserService userService;

	@Autowired
	SessionFactory sessionFactory;

	@GetMapping("/login")
	public String login() {
		GlobalData.cart.clear();
		return "login";
	}

	@GetMapping("/register")
	public String registerGet() {
		return "register";
	}

	@PostMapping("/authenticate")
	public String authenticate(@ModelAttribute("user") User user) {
		Session session = sessionFactory.openSession();
		if (userService.userLogin(user)) {
			if (userService.userRole(userService.getUserById(user.getEmail())) == 1) {
				return "adminHome";
			} else if (userService.userRole(userService.getUserById(user.getEmail())) == 2) {
				return "index2";
			}
		}
		return "login";
	}

	@PostMapping("/register")
	public String registerPost(@ModelAttribute("user") User user, HttpServletRequest request) throws ServletException {
		userService.addUser(user);
		return "login";
	}

	@GetMapping("/logout")
	public String logout() {
		GlobalData.cart.clear();
		return "login";
	}
	
	

}
