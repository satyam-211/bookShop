import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

enum PurchaseStatus{
	Purchased,Cancelled;	
}

enum PaymentBy{
	Paypal,CreditCard
}

public class User {

	private final String userId;
	private final String userName;
	private final String surName;
	private final Address address;
	private final boolean isAdmin;
	private List<Book> shoppingBasket;
	private PurchaseStatus purchaseStatus;
	private PaymentBy paymentBy;

	public User(String userId, String userName, String surName, Address address, boolean isAdmin) {
		this.userId = userId;
		this.userName = userName;
		this.surName = surName;
		this.address = address;
		this.isAdmin = isAdmin;
		shoppingBasket=new ArrayList<>();
	}
	
	@Override
	public String toString() {
		return "User [userId=" + userId + ", userName=" + userName + ", surName=" + surName + ", address=" + address
				+ ", isAdmin=" + isAdmin + ", shoppingBasket=" + shoppingBasket + "]";
	}
	
	public String activityLog() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");  
		LocalDateTime now = LocalDateTime.now();
		String str="";
		for(Book book:shoppingBasket) {
			str+=userId+","+address.getPostCode()+","+book.getISBN()+",1,"+paymentBy.name()+","+purchaseStatus.name().toLowerCase()+","+dtf.format(now)+"\n";
		}
		return str;
	}

	public void purchase(PaymentBy paymentBy) {
		purchaseStatus=PurchaseStatus.Purchased;
		this.paymentBy=paymentBy;
	}
	
	public void cancel() {
		purchaseStatus=PurchaseStatus.Cancelled;
	}
	
	public void clearBasket() {
		shoppingBasket.clear();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof User))
			return false;
		User other = (User) obj;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		return true;
	}

	public String getUserId() {
		return userId;
	}

	public String getUserName() {
		return userName;
	}

	public String getSurName() {
		return surName;
	}

	public Address getAddress() {
		return address;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public List<Book> getShoppingBasket() {
		return shoppingBasket;
	}
	
	public void addToShoppingBasket(Book book) {
		shoppingBasket.add(book);
	}

}
