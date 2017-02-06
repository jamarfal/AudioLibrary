package jamarfal.jalbertomartinfalcon.audiolibros;

/**
 * Created by jamarfal on 6/2/17.
 */

public interface UserStorage {

    void setUserName(String userName);

    String getUserName();

    void setUserProvider(String userProvider);

    String getUserProvider();

    void setUserEmail(String userEmail);

    String getUserEmail();

    void removeUserName();

    void removeProvider();

    void removeEmail();
}
