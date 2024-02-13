SELECT empID     AS empID,
       empName   AS empName,
       empActive AS empActive,
       date      AS date,
       age       AS age,
       city      AS city,
       dpName    AS departmentName
FROM employees
         JOIN departments on emp_dpID = dpID
WHERE empID = :empID