package security;

import com.openclassrooms.mddapi.common.response.JwtResponse;

public interface IJwtExec {
    JwtResponse authenticateAndGenerateToken(String email, String password);
    String getAuthenticateUser();
}
