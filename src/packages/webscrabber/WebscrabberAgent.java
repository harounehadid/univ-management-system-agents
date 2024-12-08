package packages.webscrabber;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

public class WebscrabberAgent extends Agent {
    @Override
    protected void setup() {
        System.out.println("Agent " + getLocalName() + " started.");

        // Retrieve the arguments passed to the agent
        Object[] args = getArguments();
        if (args != null && args.length == 5) {
            String filePath = (String) args[0];
            String regex = (String) args[1];
            Boolean isTrailingProductName = (Boolean) args[2];
            String webPageName = (String) args[3];
            int delay = (int) args[4];

            // Add a behavior to perform the web scraping
            addBehaviour(new OneShotBehaviour() {
                @Override
                public void action() {
                    System.out.println("Scraping reviews for " + webPageName);

                    try {
                        Thread.sleep(delay);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    ArrayList<ProductReview> productReviews = scrapeReviews(filePath, regex, isTrailingProductName);
                    displayResults(productReviews, webPageName);
                    ProductReview topReview = getTopReview(productReviews);

                    if (topReview != null) {
                        ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
                        msg.addReceiver(new AID("MainAgent", AID.ISLOCALNAME));
                        msg.setContent(webPageName + " | " + topReview.getName() + " | " + topReview.getRatingScalar());
                        msg.setConversationId("ws");

                        send(msg);
                    }
                }
            });
        } else {
            System.out.println("No arguments provided.");
            doDelete();
        }
    }

    private ArrayList<ProductReview> scrapeReviews(String filePath, String regex, Boolean isTrailingProductName) {
        ArrayList<ProductReview> productReviews = new ArrayList<>();

        // Read the HTML file into a string
        String htmlContent = "";

        try {
            htmlContent = new String(Files.readAllBytes(Paths.get(filePath)));
        } catch (Exception e) {
            System.out.println("Error: There was an error reading the file.\n\t File path: " + filePath);
        } finally {
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(htmlContent);
            
            while (matcher.find()) {
                if (!isTrailingProductName) {
                    productReviews.add(new ProductReview(matcher.group(1), matcher.group(2)));
                } else {
                    productReviews.add(new ProductReview(matcher.group(2), matcher.group(1)));
                }
            }
        }

        return productReviews;
    }

    private void displayResults(ArrayList<ProductReview> reviews, String webPageName) {
        // ANSI escape code for green text to highlight the output
        String greenText = "\u001B[32m";
        String boldText = "\u001B[1m";
        String resetText = "\u001B[0m";
        // ------------------------------

        // Get the product with the highest rating
        int highestRatingIndex = 0;

        for (int i = 1; i < reviews.size(); i++) {
            if (reviews.get(i).getRatingScalar() > reviews.get(highestRatingIndex).getRatingScalar()) {
                highestRatingIndex = i;
            }
        }

        // Print out the product reviews
        System.out.println("\n\n" + boldText + webPageName + " REVIEWS: ##############################################################\n" + resetText);

        for (int i = 0; i < reviews.size(); i++) {
            ProductReview review = reviews.get(i);

            if (i == highestRatingIndex) System.out.print(greenText);

            System.out.println("Product: " + review.getName());
            System.out.println("Rating: " + review.getRating());
            System.out.println("Rating (scalar): " + review.getRatingScalar());
            System.out.println();

            if (i == highestRatingIndex) System.out.print(resetText);
        }
    }

    private ProductReview getTopReview(ArrayList<ProductReview> productReviews) {
        if (productReviews.isEmpty()) {
            return null;
        }

        ProductReview topReview = productReviews.get(0);
        for (ProductReview review : productReviews) {
            if (review.getRatingScalar() > topReview.getRatingScalar()) {
                topReview = review;
            }
        }
        return topReview;
    }
}
