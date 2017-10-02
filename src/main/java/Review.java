public class Review {
    private String text;
    private int sentiment;

    public Review(String text, int sentiment){
        this.text = text;
        this.sentiment = sentiment;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getSentiment() {
        return sentiment;
    }

    public void setSentiment(int sentiment) {
        this.sentiment = sentiment;
    }
}
