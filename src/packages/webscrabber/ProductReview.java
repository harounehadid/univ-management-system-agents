package packages.webscrabber;
import java.util.regex.*;

public class ProductReview {
    private String name;
    private String rating;
    private Float ratingScalar;

    public ProductReview(String name, String rating) {
        this.name = name;
        this.rating = rating;

        String regex = "(\\d+\\.?\\d*)[^\\d]+(\\d+\\.?\\d*)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(rating);

        while (matcher.find()) {
            // Access the first capturing group
            Float numerator = Float.parseFloat(matcher.group(1));
            Float denominator = Float.parseFloat(matcher.group(2));

            this.ratingScalar = numerator / denominator;
        }
    }

    public String getName() {
        return this.name;
    }

    public String getRating() {
        return this.rating;
    }

    public Float getRatingScalar() {
        return this.ratingScalar;
    }
}
