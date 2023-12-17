package com.studysprint.api.web;

import com.studysprint.api.model.auth.User;
import com.studysprint.api.service.auth.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @PostMapping("/addUserRole")
    public User addUserRole(@RequestBody String username, @RequestBody String authority)
    {
        return adminService.addUserRole(username, authority);
    }

    @DeleteMapping("/removeUserRole")
    public User removeUserRole(@RequestBody String username, @RequestBody String authority)
    {
        return adminService.removeUserRole(username, authority);
    }
}
