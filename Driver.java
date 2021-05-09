import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class Driver {

	static List<User> users;
	static List<Book> books;

	public static void main(String[] args) {
		users = readUsersFromFile();
		books = readBooksFromFile();
		Scanner s = new Scanner(System.in);
		User user = chooseUser(users, s);
		if (user.isAdmin()) {
			adminMenu(s);
		} else {
			customerMenu(s,user);
		}
	}
	
	private static void writeToFile(Book book) {
		BufferedWriter bw=null;
			try {
				bw=new BufferedWriter(new FileWriter(new File("Stock.txt"),true));
				bw.write("\n"+book.fileString());
				bw.flush();
			} catch (IOException e) {
				System.out.println("Failed to write");
				e.printStackTrace();
			}
	}
	
	private static void activityLoggin(User user) {
		BufferedWriter bw=null;
		try {
			bw=new BufferedWriter(new FileWriter(new File("ActivityLog.txt"),true));
			bw.write("\n"+user.activityLog());
			bw.flush();
		} catch (IOException e) {
			System.out.println("Failed to write");
			e.printStackTrace();
		}
	}

	private static List<Book> readBooksFromFile() {
		List<Book> books = new ArrayList<>();
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader("Stock.txt"));
			String line = "";
			while ((line = br.readLine()) != null) // returns a Boolean value
			{
				String[] book = line.split(",");
				String isbn = book[0];
				String bookType = book[1];
				String title = book[2];
				Language language = Language.valueOf(book[3].trim());
				Genre genre = Genre.valueOf(book[4].replaceAll("\\s", ""));
				String releaseDate = book[5];
				Double retailPrice = Double.valueOf(book[6].trim());
				int stock = Integer.valueOf(book[7].trim());
				if (bookType.trim().equalsIgnoreCase("paperback")) {
					int numPages = Integer.valueOf(book[8].trim());
					Condition condition = Condition.valueOf(book[9].trim());
					Paperback bookP = new Paperback(isbn, title, language, genre, releaseDate, retailPrice, stock,
							numPages, condition);
					books.add(bookP);
				} else if (bookType.trim().equalsIgnoreCase("audiobook")) {
					double listeningLength = Double.valueOf(book[8].trim());
					AudioFormat audioFormat = AudioFormat.valueOf(book[9].trim());
					Audiobook bookA = new Audiobook(isbn, title, language, genre, releaseDate, retailPrice, stock,
							listeningLength, audioFormat);
					books.add(bookA);
				} else if (bookType.trim().equalsIgnoreCase("ebook")) {
					int numPages = Integer.valueOf(book[8].trim());
					EbookFormat ebookFormat = EbookFormat.valueOf(book[9].trim());
					Ebook bookE = new Ebook(isbn, title, language, genre, releaseDate, retailPrice, stock, numPages,
							ebookFormat);
					books.add(bookE);
				} else {
					System.out.println(bookType);
				}
			}
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		return books;
	}

	private static void customerMenu(Scanner s, User user) {
		String str = "";
		str += "1 View all books \n";
		str += "2 Add item to shopping basket \n";
		str += "3 Checkout \n";
		str +="4 Clear shopping basket \n";
		str +="5 View books by genre \n";
		str +="6 View audiobooks with listening length greater than 5 hours \n";
		str +="Press any other num key to exit. \n";
		System.out.println(str);
		int choice=s.nextInt();
		switch(choice) {
		case 1:viewAllBooks();
		       customerMenu(s,user);
		       break;
		case 2:addItem(s,user);
		       customerMenu(s,user);
		       break;
		case 3:checkout(s,user);
		       customerMenu(s,user);
		       break;
		case 4:clearBasket(user);
		       customerMenu(s,user);
		       break;
		case 5:viewBooksByGenre(s);
		       customerMenu(s,user);
		       break;
		case 6:viewAudioBooks();
		       customerMenu(s,user);
		       break;
		default:
		}
	}

	private static void viewAudioBooks() {
		for(Book b:books) {
			if(b instanceof Audiobook&&((Audiobook) b).getListeningLength()>5) {
				System.out.println(b.toString());
			}
		}
		
	}

	private static void viewBooksByGenre(Scanner s) {
		String str = "Please select one of the following genres \n";
		int i = 1;
		for (Genre g : Genre.values()) {
			str += i + " " + g.name() + "\n";
			i++;
		}
		System.out.println(str);
		int genreChoice = s.nextInt();
		while (genreChoice < 1 && genreChoice > Genre.values().length) {
			System.out.println("Invalid choice. Try Again");
			genreChoice = s.nextInt();
		}
		Genre genre = Genre.values()[genreChoice - 1];
		for(Book b:books) {
			if(b.getGenre().compareTo(genre)==0) {
				System.out.println(b.toString());
			}
		}
	}

	private static void clearBasket(User user) {
		user.cancel();
		activityLoggin(user);
		user.clearBasket();
	}

	private static void checkout(Scanner s, User user) {
		String str="Please enter the mode of payment \n";
		str+="0 Paypal \n";
	    str+="1 Credit card \n";
	    System.out.println(str);
		int choice=s.nextInt();
		switch(choice) {
		case 0:
		     System.out.println("Enter your email address");
		     String email=s.next();
		     user.purchase(PaymentBy.Paypal);
		     System.out.println("Amount paid using PayPal");
		     activityLoggin(user);
		     user.clearBasket();
		     break;
		case 1:System.out.println("Enter your 6 digits card number");
		       int cardNumber=s.nextInt();
		       System.out.println("Enter 3 digit security code");
		       int securityCode=s.nextInt();
		       user.purchase(PaymentBy.CreditCard);
		       System.out.println("Amount paid using Credit Card");
		       activityLoggin(user);
		       user.clearBasket();
			   break;
		}
	}

	private static void addItem(Scanner s, User user) {
		viewAllBooks();
		System.out.println("Enter the isbn number of the book you want to add");
		String isbn=s.next();
		boolean foundBook=false;
	    for(Book b:books) {
	    	if(b.getISBN().equals(isbn)) {
	    		foundBook=true;
	    		if(b.getStock()>0) {
	    			user.addToShoppingBasket(b);
		    		System.out.println(b.getTitle()+" Added successfully to your shopping cart");	
	    		}else {
	    			System.out.println("Item out of stock");
	    		}
	    		
	    		break;
	    	}
	    }
	    if(!foundBook)
	    	System.out.println("Could not find any book with this isbn");
	}

	private static void adminMenu(Scanner s) {
		String str = "";
		str += "1 View all books \n";
		str += "2 Add book \n";
		str += "Any other number key to exit";
		System.out.println(str);
		int choice = s.nextInt();
		switch (choice) {
		case 1:
			viewAllBooks();
			adminMenu(s);
			break;
		case 2:
			addBook(s);
			adminMenu(s);
			break;
		default:
		}
	}

	private static void addBook(Scanner s) {
		System.out.println("Enter ISBN");
		String isbn = s.next();
		for(Book b:books) {
			if(b.getISBN().equals(isbn)) {
				System.out.println("Cannot enter book with already existing isbn number. Book Title:"+b.getTitle());
				addBook(s);
				return;
			}
		}
		System.out.println("Enter title");
		String title = s.next();
		System.out.println("Press 0 for English language any other number key for French");
		Language language;
		int choice = s.nextInt();
		if (choice == 0) {
			language = Language.English;
		} else {
			language = Language.French;
		}
		String str = "Please select one of the following genres \n";
		int i = 1;
		for (Genre g : Genre.values()) {
			str += i + " " + g.name() + "\n";
			i++;
		}
		System.out.println(str);
		int genreChoice = s.nextInt();
		while (genreChoice < 1 && genreChoice > Genre.values().length) {
			System.out.println("Invalid choice. Try Again");
			genreChoice = s.nextInt();
		}
		Genre genre = Genre.values()[genreChoice - 1];
		System.out.println("Enter release date in dd-mm-yyyy form");
		String date = s.next();
		System.out.println("Enter retail price");
		double retailPrice = s.nextDouble();
		System.out.println("Enter stock count");
		int stockCount = s.nextInt();
		str = "Please select one of the book types \n";
		str += "1 Paperback \n";
		str += "2 Audiobook \n";
		str += "3 Ebook";
		System.out.println(str);
		choice = s.nextInt();
		while (choice < 1 && choice > 3) {
			System.out.println("Invalid Choice. Try Again");
			choice = s.nextInt();
		}
		if (choice == 1) {
			System.out.println("Enter num pages");
			int numPages = s.nextInt();
			System.out.println("Enter 0 to set condition of book as New any other number key to set as Used");
			int conditionChoice = s.nextInt();
			Condition condition;
			if (choice == 0) {
				condition = Condition.New;
			} else {
				condition = Condition.Used;
			}
			Paperback book = new Paperback(isbn, title, language, genre, date, retailPrice, stockCount, numPages,
					condition);
			books.add(book);
			writeToFile(book);
		} else if (choice == 2) {
			System.out.println("Enter listening length");
			double listeningLength = s.nextDouble();
			str = "Please select one of the following audio format \n";
			i = 1;
			for (AudioFormat af : AudioFormat.values()) {
				str += i + " " + af.name() + "\n";
				i++;
			}
			System.out.println(str);
			int afChoice = s.nextInt();
			while (afChoice < 1 && afChoice > AudioFormat.values().length) {
				System.out.println("Invalid choice. Try Again");
				afChoice = s.nextInt();
			}
			AudioFormat audioFormat = AudioFormat.values()[afChoice - 1];
			Audiobook book = new Audiobook(isbn, title, language, genre, date, retailPrice, stockCount, listeningLength,
					audioFormat);
			books.add(book);
			writeToFile(book);
		} else if (choice == 3) {
			System.out.println("Enter num pages");
			int numPages = s.nextInt();
			str = "Please select one of the following ebook format \n";
			i = 1;
			for (EbookFormat ef : EbookFormat.values()) {
				str += i + " " + ef.name() + "\n";
				i++;
			}
			System.out.println(str);
			int efChoice = s.nextInt();
			while (efChoice < 1 && efChoice > EbookFormat.values().length) {
				System.out.println("Invalid choice. Try Again");
				efChoice = s.nextInt();
			}
			EbookFormat ebookFormat = EbookFormat.values()[efChoice - 1];
			Ebook book = new Ebook(isbn, title, language, genre, date, retailPrice, stockCount, numPages, ebookFormat);
			books.add(book);
			writeToFile(book);
		}
	}

	private static void viewAllBooks() {
		books.sort(Book.BookPriceComparator);
		books.forEach(book -> System.out.println(book.toString()));
	}

	private static User chooseUser(List<User> users, Scanner s) {
		System.out.println("Please choose your user name");
		for (int i = 0; i < users.size(); i++) {
			System.out.println((i + 1) + users.get(i).getUserName());
		}
		int choice = s.nextInt();
		while (choice < 1 && choice < users.size() + 1) {
			System.out.println("Invalid input. Try Again");
			choice = s.nextInt();
		}
		return users.get(choice - 1);
	}

	private static List<User> readUsersFromFile() {
		List<User> users = new ArrayList<>();
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader("UserAccounts.txt"));
			String line = "";
			while ((line = br.readLine()) != null) // returns a Boolean value
			{
				String[] book = line.split(",");
				String userId = book[0];
				String userName = book[1];
				String surName = book[2];
				String houseNumber = book[3];
				String postCode = book[4];
				String city = book[5];
				boolean isAdmin = Boolean.valueOf(book[6].trim());
				Address address = new Address(houseNumber, postCode, city);
				User user = new User(userId, userName, surName, address, isAdmin);
				users.add(user);
			}
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}

		return users;
	}
}
