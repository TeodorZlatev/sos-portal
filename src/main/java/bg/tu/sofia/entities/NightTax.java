package bg.tu.sofia.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import bg.tu.sofia.constants.NightTaxStatusEnum;

@NamedQueries({
		@NamedQuery(name = "NightTax.findByUserIdAndStatus", query = "SELECT nt FROM NightTax nt WHERE nt.host.id = :userId AND nt.status = :status ORDER BY nt.dateCreated DESC"),
		@NamedQuery(name = "NightTax.findByUserIdAndGuestNameAndDate", query = "SELECT nt FROM NightTax nt WHERE nt.host.id = :userId AND nt.guestName = :guestName AND nt.date = :date"),
		@NamedQuery(name = "NightTax.payNightTaxes", query = "UPDATE NightTax nt SET nt.status = 'PAID', nt.datePaid = :date WHERE nt.id IN :ids")})

@Entity
@Table(name = "night_tax")
public class NightTax {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User host;
	@Column(name = "guest_name")
	private String guestName;
	@ManyToOne
	@JoinColumn(name = "room_id")
	private Room room;
	@Column(name = "date")
	private Date date;
	@Column(name = "date_created")
	private Date dateCreated;
	@OneToOne
	@JoinColumn(name = "created_by")
	private User creator;
	@Column(name = "date_paid")
	private Date datePaid;
	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	private NightTaxStatusEnum status;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getHost() {
		return host;
	}

	public void setHost(User host) {
		this.host = host;
	}

	public String getGuestName() {
		return guestName;
	}

	public void setGuestName(String guestName) {
		this.guestName = guestName;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public User getCreator() {
		return creator;
	}

	public void setCreator(User creator) {
		this.creator = creator;
	}

	public Date getDatePaid() {
		return datePaid;
	}

	public void setDatePaid(Date datePaid) {
		this.datePaid = datePaid;
	}

	public NightTaxStatusEnum getStatus() {
		return status;
	}

	public void setStatus(NightTaxStatusEnum status) {
		this.status = status;
	}

}
