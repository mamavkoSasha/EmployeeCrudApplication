SELECT user_id           AS userId,
       username          AS username,
       email             AS email,
       password          AS password,
       registration_date AS registrationDate
FROM users
WHERE user_id = :userId