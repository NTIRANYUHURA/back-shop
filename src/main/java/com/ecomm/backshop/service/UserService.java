package com.ecomm.backshop.service;

import com.ecomm.backshop.exception.InvalidEntityException;
import com.ecomm.backshop.model.Role;
import com.ecomm.backshop.model.User;
import com.ecomm.backshop.repository.RoleRepository;
import com.ecomm.backshop.repository.UserRepository;
import com.ecomm.backshop.validators.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;


    @Autowired
    private PasswordEncoder passwordEncoder;

    public void initRoleAndUser() {

        Role adminRole = new Role();
        adminRole.setRoleName("Admin");
        adminRole.setRoleDescription("Admin role");
        roleRepository.save(adminRole);

        Role userRole = new Role();
        userRole.setRoleName("User");
        userRole.setRoleDescription("Default role for newly created record");
        roleRepository.save(userRole);


        User adminUser = new User();
        adminUser.setUserName("admin123");
        adminUser.setUserPassword(getEncodedPassword("admin@123"));
        adminUser.setUserFirstName("admin");
        adminUser.setUserLastName("admin");
        Set<Role> adminRoles = new HashSet<>();
        adminRoles.add(adminRole);
        adminUser.setRole(adminRoles);
        userRepository.save(adminUser);

        User user = new User();
        user.setUserName("ntir");
        user.setUserPassword(getEncodedPassword("ntir@123"));
        user.setUserFirstName("flo");
        user.setUserLastName("fofo");
        Set<Role> userRoles = new HashSet<>();
        userRoles.add(userRole);
        user.setRole(userRoles);
        userRepository.save(user);



    }

    public User registerNewUser(User user){

        List<String> errors = UserValidator.validate(user);
        if(!errors.isEmpty()){
            throw new InvalidEntityException("verifier les champs username and password");

        }


        Role role = roleRepository.findById("user").get();

        Set<Role> roleSet = new HashSet<>();
        roleSet.add(role);
        user.setRole(roleSet);
        String password = getEncodedPassword(user.getUserPassword());
        user.setUserPassword(password);


        return userRepository.save(user);
    }

    public String getEncodedPassword(String password){
        return passwordEncoder.encode(password);
    }



}
