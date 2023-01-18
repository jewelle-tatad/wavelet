import java.io.IOException;
import java.net.URI;

class Handler implements URLHandler {
    // The one bit of state on the server: a number that will be manipulated by
    // various requests.
    int num = 0;

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
            return String.format("Jewelle's Number: %d", num);
        // If in /increment path, increment num by 1
        } else if (url.getPath().equals("/increment")) {
            num += 1;
            return String.format("Number incremented!");
        // adds a specified amount to num
        // /add?count=<num>
        } else {
            System.out.println("Path: " + url.getPath());
            if (url.getPath().contains("/add")) {
                // splits at = to create an array
                String[] parameters = url.getQuery().split("=");
                // looks for object "count" at the first idx of the parameters[]
                if (parameters[0].equals("count")) {
                    // adds the "count" to num
                    num += Integer.parseInt(parameters[1]);
                    // displays total
                    return String.format("Number increased by %s! It's now %d", parameters[1], num);
                }
            }
            return "404 Not Found!";
        }
    }
}

class NumberServer {
    public static void main(String[] args) throws IOException {
        if(args.length == 0){
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new Handler());
    }
}
