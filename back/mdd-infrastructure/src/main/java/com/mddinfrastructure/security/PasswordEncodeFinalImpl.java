package security;

import com.openclassrooms.mddapi.core.usecases.user.auth.securityAuth.IPasswordEncodeFinal;

public class PasswordEncodeFinalImpl implements IPasswordEncodeFinal {
    private final IPasswordEncode passwordEncode;

    public PasswordEncodeFinalImpl(IPasswordEncode passwordEncode) {
        this.passwordEncode = passwordEncode;
    }

    @Override
    public String passwordEnc(String password) {
        return passwordEncode.encodePass(password);
    }
}
