package security;

import com.openclassrooms.mddapi.common.request.LoginRequest;
import com.openclassrooms.mddapi.common.response.JwtResponse;
import com.openclassrooms.mddapi.core.usecases.user.auth.securityAuth.IJwtExecFinal;

public class JwtExecFinalImpl implements IJwtExecFinal {

    private final IJwtExec iJwtExec;

    public JwtExecFinalImpl(IJwtExec iJwtExec) {
        this.iJwtExec = iJwtExec;
    }

    @Override
    public JwtResponse jwtToken(LoginRequest loginRequest) {
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();
        return iJwtExec.authenticateAndGenerateToken(email, password);
    }

    @Override
    public String authenticatedEmail() {
        return iJwtExec.getAuthenticateUser();
    }
}
