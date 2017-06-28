package bg.tu.sofia.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@NamedQueries({
		@NamedQuery(name = "User.findAllByRoomIdAndBlockId", query = "SELECT u FROM UserRoom ur JOIN ur.room r JOIN ur.user u WHERE r.id = :roomId AND r.block.id = :blockId"),
		@NamedQuery(name = "User.getPageOfPeopleWithTaxes", query = "SELECT DISTINCT u FROM NightTax nt JOIN nt.host u JOIN nt.room r ORDER BY u.username"),
		@NamedQuery(name = "User.getPageOfPeopleWithTaxesByBlockId", query = "SELECT DISTINCT u FROM NightTax nt JOIN nt.host u JOIN nt.room r WHERE r.block.id = :blockId ORDER BY u.username"),
		@NamedQuery(name = "User.getPageOfPeopleWithTaxesByBlockIdAndMarker", query = "SELECT DISTINCT u FROM NightTax nt JOIN nt.host u JOIN nt.room r WHERE r.block.id = :blockId AND (u.username LIKE :marker OR u.personalNumber LIKE :marker) ORDER BY u.username"),
		@NamedQuery(name = "User.getPageOfPeopleWithTaxesByMarker", query = "SELECT DISTINCT u FROM NightTax nt JOIN nt.host u JOIN nt.room r WHERE u.username LIKE :marker OR u.personalNumber LIKE :marker ORDER BY u.username"),
		@NamedQuery(name = "User.getPersonWithNightTaxesByUserId", query = "SELECT DISTINCT u FROM NightTax nt JOIN nt.host u WHERE u.id = :userId")})

@Entity
@Table(name = "user")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name = "username")
	private String username;
	@Column(name = "personal_number")
	private String personalNumber;
	@Column(name = "email")
	private String email;
	@OneToOne
	@JoinColumn(name = "role_id")
	private Role role;
	@Column(name = "activated")
	private boolean isActivated;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPersonalNumber() {
		return personalNumber;
	}

	public void setPersonalNumber(String personalNumber) {
		this.personalNumber = personalNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public boolean isActivated() {
		return isActivated;
	}

	public void setActivated(boolean isActivated) {
		this.isActivated = isActivated;
	}

}
