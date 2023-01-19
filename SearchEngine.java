import java.io.IOException;
import java.net.URI;

class Handler implements URLHandler {
    String[] searchHistory = new String[100];
    String[] results = new String[500];

    /**
     * This method takes in a URI/URL to handle requests and counts how many times you've visited
     * the page.
     */
    public String handleRequest(URI url) {
        /**
         * Checks path
         * If in the default (/) path, display the total count (num)
         */
        if (url.getPath().equals("/")) {
            return "Welcome to the smallest search engine on the internet!\n" 
                + "To add results, use /add?s=<result>.\n"
                + "To search, use /search?s=<search>.\n"
                + "To view your last 100 searches (because I forgot to implement arrayList) for this session, use /searchHistory.\n"
                + "Thank you for using the smallest search engine on the internet.";
        } else if (url.getPath().equals("/searchHistory")) {
            if (searchHistory[0] == null) {
                return "You have no searches! Try searching something!";
            }

            String displayHistory = "";
            for (int i = 0; i < searchHistory.length; i++) {
                if (searchHistory[i] == null) {
                    break;
                }
                displayHistory += searchHistory[i] + "\n";
            }
            return "Here are your most recent searches!\n"
                + displayHistory;
        } else {
            System.out.println("Path: " + url.getPath());
            if (url.getPath().contains("/add")) {
                // splits at = to create an array
                String[] parameters = url.getQuery().split("=");
                // TO DO: throw exception here if nothing after add?
                if (parameters[0].equals("s")) {
                    for (int i = 0; i < results.length; i++) {
                        if (results[i] == null) {
                            results[i] = parameters[1];
                            break;
                        } else if (searchHistory[i] == parameters[1]) {
                            return "This result has already been added!";
                        }
                    }
                    return String.format("You have added results for %s!", parameters[1]);
                }
                
            } else if (url.getPath().contains("/search")) {
                String[] parameters2 = url.getQuery().split("=");
                String displayResults = "";
                if (results[0] == null) {
                    return String.format("There are no results for %s.", parameters2[1]);
                }
                
                if (parameters2[0].equals("s")) {
                    for (int i = 0; i < searchHistory.length; i++) {
                        if (searchHistory[i] == null) {
                            searchHistory[i] = parameters2[1];
                            break;
                        }
                    }

                    for (int i = 0; i < results.length; i++) {
                        if (results[i].contains(parameters2[1])) {
                            displayResults += results[i] + "\n";
                        }
                    }
                }
                return String.format("Here are the results for %s" + displayResults, parameters2[1]);
            }
            
            return "404 Not Found!";
        }
    }
}

public class SearchEngine {
    public static void main(String[] args) throws IOException {
        if(args.length == 0){
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new Handler());
    }
}
