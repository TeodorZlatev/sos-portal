package bg.tu.sofia.dtos;

import bg.tu.sofia.entities.NightTax;

public class NightTaxDto {
	private String guestName;
	private int roomNumber;
	private int hostId;
	private String date;

	public String getGuestName() {
		return guestName;
	}

	public void setGuestName(String guestName) {
		this.guestName = guestName;
	}

	public int getRoomNumber() {
		return roomNumber;
	}

	public void setRoomNumber(int roomNumber) {
		this.roomNumber = roomNumber;
	}

	public int getHostId() {
		return hostId;
	}

	public void setHostId(int hostId) {
		this.hostId = hostId;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public NightTax toEntity() {
		NightTax nightTax = new NightTax();

		// room is converted in service

		nightTax.setGuestName(this.getGuestName());

		// host is converted in service

		return nightTax;
	}

}
