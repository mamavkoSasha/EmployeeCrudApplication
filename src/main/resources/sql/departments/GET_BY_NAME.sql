SELECT dpID, dpName
FROM departments
WHERE dpName LIKE :dpName
LIMIT :pageSize OFFSET :currentPage