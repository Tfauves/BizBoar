package com.bizboar.superCoolBatchProgram.controllers;

import com.bizboar.superCoolBatchProgram.models.auth.ERole;
import com.bizboar.superCoolBatchProgram.models.auth.Role;
import com.bizboar.superCoolBatchProgram.models.auth.User;
import com.bizboar.superCoolBatchProgram.payload.request.LoginRequest;
import com.bizboar.superCoolBatchProgram.payload.request.SignupRequest;
import com.bizboar.superCoolBatchProgram.payload.response.JwtResponse;
import com.bizboar.superCoolBatchProgram.payload.response.MessageResponse;
import com.bizboar.superCoolBatchProgram.repositories.RoleRepository;
import com.bizboar.superCoolBatchProgram.repositories.UserRepository;
import com.bizboar.superCoolBatchProgram.security.jwt.JwtUtils;
import com.bizboar.superCoolBatchProgram.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse( jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                roles));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signupRequest) {
        if (userRepository.existsByUsername(signupRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email already used plz login or reset password"));
        }

        //create new account
        User user = new User(signupRequest.getUsername(), encoder.encode(signupRequest.getPassword()));
        Set<String> strRoles = signupRequest.getRoles();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER).orElseThrow(() -> new RuntimeException("Error: Role is not found"));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "rockers":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ROCKERS).orElseThrow(()-> new RuntimeException(("error")));
                        roles.add(adminRole);
                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER).orElseThrow(()-> new RuntimeException(("error")));
                        roles.add(userRole);
                        break;

                }
            });
        }
        user.setRoles(roles);
        userRepository.save(user);
        return new ResponseEntity(new MessageResponse("User Reg success"), HttpStatus.CREATED);
    }

}
