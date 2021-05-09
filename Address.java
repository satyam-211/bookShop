
public class Address {

	@Override
	public String toString() {
		return "Address [houseNumber=" + houseNumber + ", postCode=" + postCode + ", city=" + city + "]";
	}

	private final String houseNumber;
	private final String postCode;
	private final String city;

	public Address(String houseNumber, String postCode, String city) {
		super();
		this.houseNumber = houseNumber;
		this.postCode = postCode;
		this.city = city;
	}
	
	public String getHouseNumber() {
		return houseNumber;
	}
	
	public String getCity() {
		return city;
	}
	
	public String getPostCode() {
		return postCode;
	}

}
