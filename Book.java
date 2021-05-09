import java.util.Comparator;

enum Language {
	English,French
}

enum Genre {
	Politics,Medicine,Business,ComputerScience,Biography
}

public class Book implements Comparable<Book>{

	private final String ISBN;
	private final String title;
	private final Language language;
	private final Genre genre;
	private final String releaseDate;
	private double retailPrice;
	private int stock;

	public Book(String ISBN, String title, Language language, Genre genre, String releaseDate, double retailPrice,
			int stock) {
		this.ISBN = ISBN;
		this.title = title;
		this.language = language;
		this.genre = genre;
		this.releaseDate = releaseDate;
		this.retailPrice = retailPrice;
		this.stock = stock;
	}

	public double getRetailPrice() {
		return retailPrice;
	}

	@Override
	public String toString() {
		return "Book [ISBN=" + ISBN + ", title=" + title + ", language=" + language + ", genre=" + genre
				+ ", releaseDate=" + releaseDate + ", retailPrice=" + retailPrice + ", stock=" + stock + "]";
	}
	
	public String fileString() {
		return "";
	}

	public int getStock() {
		return stock;
	}

	public String getISBN() {
		return ISBN;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ISBN == null) ? 0 : ISBN.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Book))
			return false;
		Book other = (Book) obj;
		if (ISBN == null) {
			if (other.ISBN != null)
				return false;
		} else if (!ISBN.equals(other.ISBN))
			return false;
		return true;
	}

	public String getTitle() {
		return title;
	}

	public Language getLanguage() {
		return language;
	}

	public Genre getGenre() {
		return genre;
	}

	public String getReleaseDate() {
		return releaseDate;
	}

	public void setRetailPrice(double retailPrice) {
		this.retailPrice = retailPrice;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	@Override
	public int compareTo(Book arg0) {
		return (int) (this.retailPrice-arg0.retailPrice);
	}
	
	public static Comparator<Book> BookPriceComparator = new Comparator<Book>() {

		public int compare(Book book1, Book book2) {

			double bookPrice1 = book1.getRetailPrice();
			double bookPrice2 = book2.getRetailPrice();

			return (int) (bookPrice1 - bookPrice2);

		}

	};

}
