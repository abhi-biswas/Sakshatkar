package requests;



import java.io.Serializable;
/**
 * @author Abhijeet Biswas
 * username: First parameter for the user who received the friend request
 * friend: Second parameter for the user who sent the friend request

 */
public class AcceptFriendRequest implements Serializable {
    private String username,friend;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFriend() {
        return friend;
    }

    public void setFriend(String friend) {
        this.friend = friend;
    }

    public AcceptFriendRequest(String username, String friend) {
        this.username = username;
        this.friend = friend;
    }
}
