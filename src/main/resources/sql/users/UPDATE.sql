UPDATE users
SET username = :username,
    password = :password,
    email    = :email
WHERE user_id = :userId