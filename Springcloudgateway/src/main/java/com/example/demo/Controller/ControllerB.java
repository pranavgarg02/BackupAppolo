//package com.example.demo.Controller;
//import java.security.Principal;
//
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController; 
//
//@RestController
//@RequestMapping("/serviceB") 
//public class ControllerB {
//	 
//	    @GetMapping("/songusers") 
//	    public String customers(Principal principal, Model model) {
//	        addCustomers();
//	        model.addAttribute("customers", customerDAO.findAll());
//	        model.addAttribute("username", principal.getName());
//	        return "customers";
//	    }
//	} 
