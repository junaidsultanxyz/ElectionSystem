
# CLIENT PROGRAM FLOW

## Login
- Voter Opens the application
- **Client** connects to **Server** at X port
- Voter enters `(cnic, password)` and presses **Login Button**
- Login credentials sent as a `Message(String type, Object message)` object through `ObjectOutputStream` as following:

    ``` 
    String[] credentials = new String {cnic, password};
    Message msg = new Message("LOGIN", credentials);
    ```
- Message is then sent to **Server** by `out.writeObject(msg);`

##
## Login Response
- **Server** receives the message, validates the credentials and sends a response
- Client reads the response through `ObjectInputStream` and checks the type of message through following code:
    ``` 
    Message msg = (Message) in.readObject();
    ```
- If the message type is `LOGIN_SUCCESS`, then there is `Voter` object in message, which is saved and then displays the **Voter Dashboard**
- If the message type is `LOGIN_FAILURE`, the client displays an error message
    ```
    if (msg.getType().equalsIgnoreCase("LOGIN_SUCCESS")) {
        currentUser = (Voter) msg.getMessage(); // check for instance error or casting error
    }
    else if (msg.getType().equalsIgnoreCase("LOGIN_FAILURE")) {
    }
    ```
##
## Dashboard
There are 3 main sections in Dashboard: 
1. **Vote Details**
2. **Results**
3. **Announcements**

#### `Vote Details` (only useful after the user has voted)
- Shows MPA and MNA vote of that user (if voted)
- Send request to server as following
    ```
    Message requestVoteDetail = new Message("VOTE_DETAILS", currentUser.getCNIC());
    ```
- Once Server gets the data from Database, it sends responds with a `Message` object containing the vote details and Client receives as following:
    ```
    Message response = (Message) in.readObject();
    if (response.getType().equalsIgnoreCase("VOTE_DETAILS")) {

        Vote[] voteDetails = (Vote[]) response.getMessage();

        // vote already contains the type (MNA or MPA vote)
    }
    ```
- If there already exists

#### `Results` (only accessible after user has voted)
- Shows the current results of the election (country, and of users division)
- Results are fetched from the server and displayed in a table (refreshed every 5 seconds)
- Send request to server as following
    ```
    Message requestVoteDetail = new Message("RESULT_COUNTRY", currentUser.getCNIC());

    // For Country Results can use cnic for logging purpose

    Message requestVoteDetail = new Message("RESULT_DIVISION", currentUser.getCNIC());
    ```
- To read the response, it is done as following:
    ```
    Message response = (Message) in.readObject();

    if (response.getType().equalsIgnoreCase("RESULT_COUNTRY")) {
        List<Result> countryResults = (List<Result>) response.getMessage();
    }

    if (response.getType().equalsIgnoreCase("RESULT_DIVISION")) {
        List<Result> divisionResults = (List<Result>) response.getMessage();
    }
    ```

#### `Announcements` (broadcast message from server)
- Shows the announcements from the server (refreshed every 5 seconds)
- No request from client side
    ```
    Message response = (Message) in.readObject();

    if (response.getType().equalsIgnoreCase("ANNOUNCEMENT")) {
        String announcement = (String) response.getMessage();
    }
    ```
##
## Voting (Load Party Information)

In voting, we have to get the party information only once, since it won't change during election. It reduces the requests to server and reduces load on server and database.

- Send request to Server as following to get `Party` information:
    ```
    Message requestPartyInfo = new Message("PARTY", null);

    // no parameter needed
    ```
- Then it is set to **Server** using `out.writeObject(requestPartyInfo);`
- Server receives the request and send respond with all the party data
- Client receives information as following:
    ```
    Message response = (Message) in.readObject();

    if (response.getType().equalsIgnoreCase("PARTY")) {
        Party[] parties = (Party[]) response.getMessage();
    }
    ```
- Then display these Party details in voting section for both MNA and MPA vote
- If user has already voted, then disable the voting section and display the vote details. These details will be used for 

##
## Voting (Cast Vote)
When the voter submits the vote, it will be sent to **Server** to be saved in **Database** upon validation.

- Voter selects party to vote for (MNA and MPA) and presses **Submit Vote** button
- Vote is sent to **Server** as following:
    ```
    Vote voteMNA = new Vote(currentUser.getCNIC(), party_id); // MNA
    Vote voteMPA = new Vote(currentUser.getCNIC(), party_id); // MPA

    Message requestVote = new Message("VOTE", new Vote[] {voteMNA,voteMPA});
    ```
- Server receives the vote and validates it
- If vote is valid, then it is saved in **Database** and response is sent to **Client**. This response contains the vote that was submitted
- Client receives the response as following:
    ```
    Message response = (Message) in.readObject();

    if (response.getType().equalsIgnoreCase("VOTE")) {
        Vote[] vote = (Vote[]) response.getMessage();
    }
    ```
- Then the vote is displayed in **Vote Details** section
- If vote is invalid, then error message is displayed and user gets to retry the vote
- Once the user has voted, they cannot vote again

##
## Settings
- Voter can view their details like
    - Name
    - CNIC
    - Division
- Voter can logout
- Voter can exit the application