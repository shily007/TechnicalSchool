package com.ts.auth.interceptor;

import java.util.Collection;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 
 * 
 * @author dy 2020年12月17日
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
public class AuthUser {

	private String username;
	private String password;
	private Collection<String> authorities;
	private Boolean expired = false;
	private Boolean locked = false;
	private Boolean enabled = true;
	private Role role;

	/**
	 * Indicates whether the user's account has expired. An expired account cannot
	 * be authenticated.
	 *
	 * @return <code>true</code> if the user's account is valid (ie non-expired),
	 *         <code>false</code> if no longer valid (ie expired)
	 */
	public boolean isAccountNonExpired() {
		return expired;
	}

	/**
	 * Indicates whether the user is locked or unlocked. A locked user cannot be
	 * authenticated.
	 *
	 * @return <code>true</code> if the user is not locked, <code>false</code>
	 *         otherwise
	 */
	public boolean isAccountNonLocked() {
		return locked;
	}

	/**
	 * Indicates whether the user is enabled or disabled. A disabled user cannot be
	 * authenticated.
	 *
	 * @return <code>true</code> if the user is enabled, <code>false</code>
	 *         otherwise
	 */
	public boolean isEnabled() {
		return enabled;
	}
	
	public AuthUser(String username, String password, Role role) {
		this.username = username;
		this.password = password;
		this.role = role;
	}

	public enum Role {
		ADMIN(0, "管理员"), TEACHER(1, "教师"), STUDENT(2, "学生");

		private int code;
		private String roleName;

		Role(int code, String roleName) {
			this.code = code;
			this.roleName = roleName;
		}

		public int getCode() {
			return code;
		}
		public String getRoleName() {
			return roleName;
		}
		
	}

}
