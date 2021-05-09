
enum EbookFormat {
	EPUB, MOBI, AZW3, PDF
}

public class Ebook extends Book {

	private final int numPages;
	private EbookFormat format;

	public Ebook(String ISBN, String title, Language language, Genre genre, String releaseDate, double retailPrice,
			int stock, int numPages, EbookFormat format) {
		super(ISBN, title, language, genre, releaseDate, retailPrice, stock);
		this.numPages = numPages;
		this.format = format;
	}

	@Override
	public String toString() {
		return super.toString() + " Ebook [numPages=" + numPages + ", format=" + format + "]";
	}

	public String fileString() {
		return super.getISBN() + ",ebook," + super.getTitle() + "," + super.getLanguage() + "," + super.getGenre() + ","
				+ super.getReleaseDate() + "," + super.getRetailPrice() + "," + super.getStock() + "," + numPages + ","
				+ format;
	}

	public int getNumPages() {
		return numPages;
	}

	public EbookFormat getFormat() {
		return format;
	}

	public void setFormat(EbookFormat format) {
		this.format = format;
	}
}
