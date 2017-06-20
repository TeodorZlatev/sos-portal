package bg.tu.sofia.security.model;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Holds the info for a authenticated user (Principal)
 */
public class AuthenticatedUser implements UserDetails {

	private static final long serialVersionUID = -7069780796706809502L;

	private final int id;
	private final String username;
	private final String token;
	private final String blockId;
	private final String roomId;
	private final Collection<? extends GrantedAuthority> authorities;

	public AuthenticatedUser(int id, String username, String blockId, String roomId, String token,
			Collection<? extends GrantedAuthority> authorities) {
		this.id = id;
		this.username = username;
		this.token = token;
		this.blockId = blockId;
		this.roomId = roomId;
		this.authorities = authorities;
	}

	@JsonIgnore
	public int getId() {
		return id;
	}

	@Override
	public String getUsername() {
		return username;
	}

	public String getBlockId() {
		return blockId;
	}

	public String getRoomId() {
		return roomId;
	}

	@Override
	@JsonIgnore
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	@JsonIgnore
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	@JsonIgnore
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	@JsonIgnore
	public boolean isEnabled() {
		return true;
	}

	public String getToken() {
		return token;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return null;
	}

}
