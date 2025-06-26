package com.p13.ycyw.controller;

import com.p13.ycyw.dto.UserDto;
import com.p13.ycyw.exception.BadRequestException;
import com.p13.ycyw.model.User;
import com.p13.ycyw.controller.payload.request.LoginRequest;
import com.p13.ycyw.controller.payload.request.SignupRequest;
import com.p13.ycyw.controller.payload.response.JwtResponse;
import com.p13.ycyw.controller.payload.response.MessageResponse;
import com.p13.ycyw.security.JwtUtils;
import com.p13.ycyw.service.UserService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        User user = (User) authentication.getPrincipal();

        return ResponseEntity.ok(new JwtResponse(
                jwt,
                user.getId(),
                user.getEmail()
        ));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        try {
            User user = userService.findByEmail(signUpRequest.getEmail());
            if (user != null) {
                return ResponseEntity
                        .badRequest()
                        .body(new MessageResponse("Erreur: Cet email est déjà pris !"));
            }
        } catch (Exception e) {
            // no-op
        }

        // Create new user's account
        userService.create(signUpRequest);

        return ResponseEntity.ok(new MessageResponse("Compte créé avec succès !"));
    }

    @GetMapping("/me")
    public ResponseEntity<UserDto> me(Authentication authentication) {
        try {
            User user = (User) authentication.getPrincipal();
            UserDto userDto = modelMapper.map(user, UserDto.class);
            userDto.setHasConversation(userService.hasConversation(user.getEmail()));

            return ResponseEntity.ok(userDto);
        } catch (Exception e) {
            throw new BadRequestException();
        }
    }

}
