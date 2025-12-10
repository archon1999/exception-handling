package uz.xhamdam.demoexceptionapp;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Service;
import uz.xhamdam.AppException;

@Service
public class UserService {

  private final Map<Long,String> db = new ConcurrentHashMap<>();

  public UserService() {
    db.put(1L, "alice@example.com");
  }

  public String getUserEmail(long id) {
    String email = db.get(id);
    if (email == null) {
      // passing id as placeholder arg for i18n: "User with id {0} not found"
      throw new AppException(UserErrors.USER_NOT_FOUND, id);
    }
    return email;
  }

  public void createUser(long id, String email) {
    if (db.containsKey(id)) {
      throw new AppException(UserErrors.USER_ALREADY_EXISTS, email);
    }
    if (!email.contains("@")) {
      throw new AppException(UserErrors.INVALID_EMAIL, email);
    }
    db.put(id, email);
  }

}
