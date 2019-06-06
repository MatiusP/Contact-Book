package savedata;

import java.util.List;

import by.finalwork.contactbook.User;

public interface UserRepository {

	User userSave(User user);

	User userUpdate(User user);

	List<User> getAll();

	public String getUserNameById(int userId);

	boolean delete(int id);
}