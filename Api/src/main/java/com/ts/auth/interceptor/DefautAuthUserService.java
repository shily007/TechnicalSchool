package com.ts.auth.interceptor;

import org.springframework.stereotype.Component;

import com.ts.auth.interceptor.AuthUser.Role;

@Component
public class DefautAuthUserService implements AuthUserService {

	@Override
	public AuthUser loadByUsernamer(String username) {
		return new AuthUser(username, "52885bd6e18a6b66Nd5QrmYSVjO3rQA1rMMW7rGwY3cyaco8pBueR99xCYsOgAzV", Role.STUDENT);
	}

	@Override
	public AuthUser loadByOpenid(String openid, String loginType) {
		// TODO Auto-generated method stub
		return null;
	}

}
