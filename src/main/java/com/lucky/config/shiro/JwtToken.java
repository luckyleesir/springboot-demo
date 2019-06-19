package com.lucky.config.shiro;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.apache.shiro.authc.AuthenticationToken;

/**
 * Jwt token
 *
 * @author lucky
 */
@AllArgsConstructor
@NoArgsConstructor
public class JwtToken implements AuthenticationToken {

    /**
     * 密钥
     */
    private String token;

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
