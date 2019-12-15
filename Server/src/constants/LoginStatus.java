package constants;

import java.io.Serializable;

public enum LoginStatus  implements Serializable {
    SUCCESS,
    USERNOTEXIST,
    PASSWORDNOTMATCHED
}
