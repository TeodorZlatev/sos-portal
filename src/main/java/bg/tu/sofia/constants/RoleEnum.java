package bg.tu.sofia.constants;

public enum RoleEnum {
	INHABITED(1),
	HOST(2),
	CASHIER(3),
	ADMINISTRATOR(4);
	
	private int id;
	
	RoleEnum(int id){
		this.id = id;
	}

	public int getId() {
		return id;
	}
}
