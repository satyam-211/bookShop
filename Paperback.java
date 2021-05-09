
enum Condition {
	New,Used
}

public class Paperback extends Book {

	private int numPages;
	private Condition condition;

	public Paperback(String ISBN, String title, Language language, Genre genre, String releaseDate, double retailPrice,
			int stock, int numPages, Condition condition) {
		super(ISBN, title, language, genre, releaseDate, retailPrice, stock);
		this.numPages = numPages;
		this.condition = condition;
	}

	@Override
	public String toString() {
		return super.toString() + " Paperback [numPages=" + numPages + ", condition=" + condition + "]";
	}

	public String fileString() {
		return super.getISBN() + ",paperback," + super.getTitle() + "," + super.getLanguage() + "," + super.getGenre()
				+ "," + super.getReleaseDate() + "," + super.getRetailPrice() + "," + super.getStock() + "," + numPages
				+ "," + condition;
	}

	public int getNumPages() {
		return numPages;
	}

	public Condition getCondition() {
		return condition;
	}

	public void setNumPages(int numPages) {
		this.numPages = numPages;
	}

	public void setCondition(Condition condition) {
		this.condition = condition;
	}

}
