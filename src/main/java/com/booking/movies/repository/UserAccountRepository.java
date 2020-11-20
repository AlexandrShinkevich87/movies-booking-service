package com.booking.movies.repository;

import com.booking.movies.entity.User;
import com.booking.movies.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {
    UserAccount getByUser(User user);

    @Modifying
    @Query(value = "update user_account set balance = ?  where user_id = ?", nativeQuery = true)
    void updateBalance(double balance, Long userId);

}
