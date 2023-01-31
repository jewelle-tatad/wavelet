import java.io.IOException;
import java.net.URI;

class Handler implements URLHandler {
    // The one bit of state on the server: a number that will be manipulated by
    // various requests.
    String strHistory = "";

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
            return strHistory;
        // if add-message, concatenate message to the end of string History
        } else {
            System.out.println("Path: " + url.getPath());
            if (url.getPath().contains("/add-message")) {
                // splits at = to create an array
                String[] parameters = url.getQuery().split("=");
                // looks for object "s" at the first idx of the parameters[]
                if (parameters[0].equals("s")) {
                    // adds str to strHistory
                    strHistory += parameters[1] + "\n";
                    return strHistory;
                }
            }
            return "404 Not Found!";
        }
    }
}

public class StringServer {
    public static void main(String[] args) throws IOException {
        if(args.length == 0){
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new Handler());
    }
    
}
