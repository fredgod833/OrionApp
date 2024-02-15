package com.openclassrooms.mddapi.core.usecases.user.auth.securityAuth;

import java.util.Collection;

public interface IUserDetails {
    Collection<? extends IGrantedAuthority> getAuthorities();
    String getPassword();
    String getUsername();
    boolean isAccountNonExpired();
    boolean isAccountNonLocked();
    boolean isCredentialsNonExpired();
    boolean isEnabled();
}
