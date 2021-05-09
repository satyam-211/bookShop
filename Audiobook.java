

enum AudioFormat{
	MP3,WMA,AAC
}

public class Audiobook extends Book {

	private final double listeningLength;
	private AudioFormat format;

	public Audiobook(String ISBN, String title, Language language, Genre genre, String releaseDate, double retailPrice,
			int stock, double listeningLength, AudioFormat format) {
		super(ISBN, title, language, genre, releaseDate, retailPrice, stock);
		this.listeningLength = listeningLength;
		this.format = format;
	}

	@Override
	public String toString() {
		return super.toString() + " Audiobook [listeningLength=" + listeningLength + ", format=" + format + "]";
	}

	public String fileString() {
		return super.getISBN() + ",audiobook," + super.getTitle() + "," + super.getLanguage() + "," + super.getGenre()
				+ "," + super.getReleaseDate() + "," + super.getRetailPrice() + "," + super.getStock() + ","
				+ listeningLength + "," + format;
	}

	public double getListeningLength() {
		return listeningLength;
	}

	public AudioFormat getFormat() {
		return format;
	}

	public void setFormat(AudioFormat format) {
		this.format = format;
	}
}
