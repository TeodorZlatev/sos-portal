package bg.tu.sofia.dtos;

import bg.tu.sofia.constants.NightTaxStatusEnum;
import bg.tu.sofia.entities.NightTax;

public class NightTaxDto {
	private String guestName;
	private int roomId;
	private String roomNumber;
	private int hostId;
	private String hostName;
	private String date;
	private String dateCreated;
	private String creatorName;
	private NightTaxStatusEnum status;
	private String datePaid;

	public String getGuestName() {
		return guestName;
	}

	public void setGuestName(String guestName) {
		this.guestName = guestName;
	}

	public int getRoomId() {
		return roomId;
	}

	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}

	public String getRoomNumber() {
		return roomNumber;
	}

	public void setRoomNumber(String roomNumber) {
		this.roomNumber = roomNumber;
	}

	public int getHostId() {
		return hostId;
	}

	public void setHostId(int hostId) {
		this.hostId = hostId;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(String dateCreated) {
		this.dateCreated = dateCreated;
	}

	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	public NightTaxStatusEnum getStatus() {
		return status;
	}

	public void setStatus(NightTaxStatusEnum status) {
		this.status = status;
	}

	public String getDatePaid() {
		return datePaid;
	}

	public void setDatePaid(String datePaid) {
		this.datePaid = datePaid;
	}

	public NightTax toEntity() {
		NightTax nightTax = new NightTax();

		// room is converted in service

		nightTax.setGuestName(this.getGuestName());

		// host is converted in service
		// date
		// creator
		// status
		// dateCreated
		
		return nightTax;
	}

	public static NightTaxDto fromEntity(NightTax nightTax) {
		NightTaxDto nightTaxDto = new NightTaxDto();

		// roomNumber
		nightTaxDto.setStatus(nightTax.getStatus());
		nightTaxDto.setDate(nightTax.getDate().toString());
		// hostName
		nightTaxDto.setGuestName(nightTax.getGuestName());
		// creatorName
		nightTaxDto.setDateCreated(nightTax.getDateCreated().toString());

		return nightTaxDto;
	}

}
