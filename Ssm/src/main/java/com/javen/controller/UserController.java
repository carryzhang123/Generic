package com.javen.controller;

import com.javen.model.User;
import com.javen.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
  
  
@Controller  
@RequestMapping("/user")
public class UserController {  
	private static Logger log=LoggerFactory.getLogger(UserController.class);
	 @Autowired
	 private IUserService userService;
    
    @RequestMapping(value="/showUser",method=RequestMethod.GET)
    @ResponseBody
    public User toIndex(@RequestBody User user){

    User resultUser = userService.getUser(user);
        return resultUser;
    }  
    
    @RequestMapping(value="/delete/{userId}",method=RequestMethod.DELETE)
    public void toIndex2(@PathVariable("userId") Integer userId){
        userService.deleteUser(userId);
    }

    @RequestMapping(value="/update",method=RequestMethod.PUT)
    @ResponseBody
    public void updateUser(@RequestBody User user){
       userService.updateUser(user);
    }
    
    @RequestMapping(value="/saveUser",method=RequestMethod.POST)
    public void getUserInJson(@RequestBody User user){
        userService.saveUser(user);
    }
}