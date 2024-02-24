//package com.mddcore.usecases.auth;
//
//import com.mddcore.domain.models.User;
//import com.mddcore.domain.repository.IUserRepository;
//import com.mddcore.usecases.auth.securityAuth.IJwtExecFinal;
//import com.mddcore.usecases.auth.securityAuth.IPasswordEncodeFinal;
//import com.mddcore.usecases.dto.UserDto;
//import com.mddcore.usecases.mapper.UserMapper;
//import com.mddcore.usecases.request.SignInRequest;
//import com.mddcore.usecases.response.JwtResponse;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//public class AuthServiceImpl implements IAuthService{
//    private final IJwtExecFinal jwtExecFinal;
//    private final IUserRepository userRepository;
//    private final UserMapper userMapper;
//    private final IPasswordEncodeFinal passwordEncodeFinal;
//
//    private final static Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);
//
//    public AuthServiceImpl(IJwtExecFinal jwtExecFinal, IUserRepository userRepository,
//                           UserMapper userMapper, IPasswordEncodeFinal passwordEncodeFinal) {
//        this.jwtExecFinal = jwtExecFinal;
//        this.userRepository = userRepository;
//        this.userMapper = userMapper;
//        this.passwordEncodeFinal = passwordEncodeFinal;
//    }
//
//    @Override
//    public JwtResponse login(SignInRequest request) {
//        logger.info("Attempting login for user: {}", request.email());
//        try {
//            JwtResponse jwtResponse = jwtExecFinal.jwtToken(request);
//            logger.info("Response for authentication : {}", jwtResponse);
//            return jwtExecFinal.jwtToken(request);
//
//        } catch (Exception e) {
//            logger.error("An unexpected error occurred during login for user: {}", request.getEmail(), e);
//            throw new IllegalStateException("An unexpected error occurred during login", e);
//        }
//    }
//
//    @Override
//    public JwtResponse register(UserDto userDto) {
//        logger.info("Attempting registration for user: {}", userDto.getEmail());
//        if(userRepository.existByEmail(userDto.getEmail())) {
//            logger.warn("Registration failed - User email already exists: {}", userDto.getEmail());
//            throw new IllegalArgumentException("User email already exist");
//        };
//
//        if(!validPassword(userDto.getPassword())) {
//            logger.warn("Registration failed - Password security check failed for user: {}", userDto.getEmail());
//            throw new IllegalArgumentException("Password security check failed");
//        };
//        logger.info("Before user to entity");
//        User user = userMapper.toDomain(userDto);
//        logger.info("After user to entity");
//
//        String passwordEnc = passwordEncodeFinal.encodePass(user.getPassword());
//        user.setPassword(passwordEnc);
//        userRepository.save(user);
//        logger.info("User registration successful for user: {}", userDto.getEmail());
//
//        SignInRequest signInRequest = new SignInRequest(user.getEmail(), user.getPassword());
//        return jwtExecFinal.jwtToken(signInRequest);
//    }
//
//    private Boolean validPassword(String password) {
//        return password.length() >= 8
//                && password.matches("^(?=.*[A-Z])(?=.*[a-z])(?=.*[^a-zA-Z0-9]).*$");
//    }
//}
