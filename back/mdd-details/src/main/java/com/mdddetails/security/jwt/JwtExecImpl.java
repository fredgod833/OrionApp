package com.mdddetails.security.jwt;


import com.mdddetails.security.userdetails.CustomUserDetails;

@Service
public class JwtExecImpl implements IJwtExec {

    @Autowired
    private final AuthenticationManager authenticationManager;
    @Autowired
    private final JwtTokenProvider jwtTokenProvider;

    public JwtExecImpl(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public JwtResponse authenticateAndGenerateToken(String email, String password) {
        try {
            Authentication authenticate = authenticationManager.authenticate(
                                new UsernamePasswordAuthenticationToken(email, password)
                        );
            CustomUserDetails userDetails = (CustomUserDetails) authenticate.getPrincipal();
            String jwt = jwtTokenProvider.createToken(authenticate);
            return new JwtResponse(jwt, userDetails.getUser().getUser_id(), userDetails.getUsername(), userDetails.getUser().getUsername());
        } catch (AuthenticationException e) {
            throw  new BadRequestException("Authentication failed for email : " + e);
        }
    }

    @Override
    public String getAuthenticateUser() {
       Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ((CustomUserDetails) authentication.getPrincipal()).getUser().getEmail();
    }


}
