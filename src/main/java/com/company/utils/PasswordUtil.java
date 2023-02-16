package com.company.utils;

import com.company.domains.Book;
import lombok.NonNull;
import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtil {
    public static String encode(@NonNull String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public static Boolean checkPassword(@NonNull String password, @NonNull String encodedPassword) {
        return BCrypt.checkpw(password, encodedPassword);
    }
}
