UPDATE employees
SET empName   = :empName,
    emp_dpID  = :emp_dpID,
    date      = :date,
    age       = :age,
    city      = :city,
    empActive = :empActive
WHERE empID = :empID