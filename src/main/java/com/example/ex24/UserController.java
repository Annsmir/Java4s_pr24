package com.example.ex24;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService usvc;

    @Autowired
    PasswordEncoder pe;

    @Autowired
    SpringEmailService mail;

    @GetMapping()
    public ResponseEntity<User> getUser(HttpSession s) {

        SecurityContext c = SecurityContextHolder.getContext();
        Authentication a = c.getAuthentication();
        if(a != null) {
            User cu = usvc.findByUsername(a.getName());
            return new ResponseEntity<>(cu, HttpStatus.OK);
        } else {
            // should not happen
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/test")
    public ResponseEntity<List<String>> tEntity(HttpSession s) {

        SecurityContext c = SecurityContextHolder.getContext();
        Authentication a = c.getAuthentication();
        List<String> r = new LinkedList<>();
        r.add("UserController.test()");
        r.add(s.getId());
        if(a != null) {
        r.add(a.getName());
        r.add(String.valueOf(a.getCredentials()));
        r.add(String.valueOf(a.getDetails()));
        UserPrincipal p = (UserPrincipal) a.getPrincipal();
        r.add(String.valueOf(p));
        r.add(p.getUsername());
        r.add(p.getPassword());
        } else {
            r.add("auth == null");
        }
        return new ResponseEntity<>(r, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User u, HttpSession s) {

        u.setEnabled((short) 1);
        u.setPassword(pe.encode(u.getPassword())); // should also be encoded on the client
        usvc.create(u);
        mail.sendMail("Welcome", u.toString() , u.getEmail());
        return new ResponseEntity<>(u, HttpStatus.OK);
    }

    @DeleteMapping()
    public void delete(HttpServletRequest request, HttpServletResponse response) throws IOException  { // self-delete
        Authentication a = SecurityContextHolder.getContext().getAuthentication();
        User cu = usvc.findByUsername(a.getName());
        if(cu != null) {
            doLogout(request, response);
            response.setStatus(HttpServletResponse.SC_ACCEPTED);
            
            mail.sendMail("Your account will be deleted", cu.toString() , cu.getEmail());

            usvc.delete(cu);
        } 
    }
    
    @RequestMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doLogout(request, response);
        response.setStatus(HttpServletResponse.SC_ACCEPTED);
        response.sendRedirect("/");
    }

    private void doLogout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){    
            SecurityContextLogoutHandler c = new SecurityContextLogoutHandler();
            c.setClearAuthentication(true);
            c.setInvalidateHttpSession(true);
            c.logout(request, response, auth);
        }
    }

}
