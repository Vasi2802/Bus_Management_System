package org.antwalk.dao;

import org.antwalk.entity.User;

public interface UserDao {

    public User findByUserName(String userName);
    
    public void save(User user);

	public User findByEmail(String email);
    
}
