/*  Created by: Giao Nguyen, Hannah Swiney
    Date created: 11/24/2024
    Last updated: 11/26/2024
*/

/*1. Return the ID and Name of interviewers who participate in interviews where the interviewee's name is "Hellen Cole" arranged for job "11111".*/
SELECT i.interviewer, p.fname, p.lname 
FROM interview i 
JOIN person p ON i.interviewer = p.personalID 
WHERE i.interviewee = ( 
    SELECT personalID  
    FROM person  
    WHERE fname = 'Hellen' AND lname = 'Cole' 
) 
AND i.jid = '11111' 
GROUP BY i.interviewer, p.fname, p.lname;

/*2. Return the ID of all jobs which are posted by department "Marketing" in January 2011. */
SELECT j.jobId 
FROM job j 
JOIN department d ON j.deptid = d.deptID 
WHERE d.dname = 'Marketing' 
AND j.date_posted BETWEEN '2011-01-01' AND '2011-01-31';  

/*3. Return the ID and Name of the employees having no supervisees. */
SELECT e.empId, p.fname, p.lname 
FROM employee e 
JOIN person p ON e.empId = p.personalID 
WHERE e.empId NOT IN ( 
    SELECT supervisorId 
    FROM employee
); 

/*4. Return the Id and Location of the marketing sites with no sale records during March 2011. */
SELECT ms.siteID, ms.slocation 
FROM mkt_site ms 
WHERE ms.siteID NOT IN ( 
    SELECT sh.sid 
    FROM sales_history sh 
    WHERE sh.sale_time BETWEEN '2011-03-01' AND '2011-03-31' 
); 

/*5. Return the job's id and description, which does not hire a suitable person one month after it is posted. */
SELECT j.jobId, j.jdescription
FROM job j 
WHERE j.jobId NOT IN ( 
    SELECT ir.jid 
    FROM interview_result ir 
    WHERE ir.is_selected = TRUE 
) 
AND j.date_posted <= DATE_SUB(CURDATE(), INTERVAL 1 MONTH); 

/*6. Return the ID and Name of the salespeople who have sold all product types whose price is above $200. */
SELECT e.empId, p.fname, p.lname 
FROM employee e 
JOIN person p ON e.empId = p.personalID 
WHERE NOT EXISTS ( 
    SELECT 1 
    FROM product pr 
    WHERE pr.price > 200 
    AND NOT EXISTS ( 
        SELECT 1 
        FROM sales_history sh 
        JOIN product pr1 ON sh.pid = pr1.productId 
        WHERE sh.cid = e.empId 
        AND pr1.productType = pr.productType 
    ) 
); 

/*7. Return the department's id and name, which has no job post during 1/1/2011 and 2/1/2011. */
SELECT d.deptID, d.dname 
FROM department d 
WHERE d.deptID NOT IN ( 
    SELECT j.deptid 
    FROM job j 
    WHERE j.date_posted BETWEEN '2011-01-01' AND '2011-02-01' 
); 

/*8. Return the ID, Name, and Department ID of the existing employees who apply for job "12345". */
SELECT e.empId, p.fname, p.lname, e.deptId 
FROM employee e 
JOIN person p ON e.empId = p.personalID 
WHERE EXISTS ( 
    SELECT 1 
    FROM internal_application ia 
    WHERE ia.eid = e.empId AND ia.jid = '12345' 
); 

/*9. Return the best seller's type in the company (sold the most items). */
SELECT p.productType 
FROM sales_history sh 
JOIN product p ON sh.pid = p.productId 
GROUP BY p.productType 
ORDER BY COUNT(*) DESC 
LIMIT 1; 

/*10. Return the product type whose net profit is highest in the company (money earned minus the part cost). */
SELECT p.productType
FROM sales_history sh
JOIN product p ON sh.pid = p.productId
JOIN ProductPartCost ppc ON p.productId = ppc.productid
GROUP BY p.productType
ORDER BY SUM(p.price) - SUM(ppc.total_part_cost) DESC
LIMIT 1; 

/*11. Return the name and id of the employees who have worked in all departments after being hired by the company. */ 
SELECT p.fname, p.lname, e.empId 
FROM employee e 
JOIN person p ON e.empId = p.personalID 
WHERE NOT EXISTS ( 
    SELECT 1 
    FROM department d 
    WHERE NOT EXISTS ( 
        SELECT 1 
        FROM works_at wa 
        WHERE wa.eid = e.empId AND wa.siteid IN ( 
            SELECT siteID FROM mkt_site WHERE deptid = d.deptID 
        ) 
    ) 
); 

/*12. Return the name and email address of the interviewee who is selected. */
SELECT p.fname, p.lname, p.personalID, p.address 
FROM interview_result ir 
JOIN person p ON ir.interviewee = p.personalID 
JOIN potential_emp pe ON ir.interviewee = pe.potentialEmpId
WHERE ir.is_selected = TRUE;
 

/*13. Retrieve the names, phone numbers, and email addresses of the interviewees selected for all the jobs they apply for. */
SELECT p.fname, p.lname, ph.pnumber, p.address 
FROM interview_result ir 
JOIN interview i ON ir.jid = i.jid AND ir.interviewee = i.interviewee 
JOIN person p ON i.interviewee = p.personalID 
JOIN phone ph ON p.personalID = ph.personID 
JOIN potential_emp pe ON ir.interviewee = pe.potentialEmpId 
WHERE ir.is_selected = TRUE; 

/*14. Return the employee's name and id whose average monthly salary is the highest in the company. */
SELECT p.fname, p.lname, e.empId 
FROM AverageEmployeeSalary aes 
JOIN person p ON aes.empId = p.personalID 
ORDER BY aes.avg_monthly_salary DESC 
LIMIT 1; 

/*15. Return the ID and Name of the vendor who supplies part whose name is "Cup" and weight is smaller than 4 pounds, and the price is lowest among all vendors. */
SELECT v.vname, v.vendorId 
FROM vendor v 
JOIN part pt ON v.vendorId = pt.vid 
WHERE pt.partType = 'Cup' AND pt.weight < 4 
ORDER BY pt.price ASC 
LIMIT 1; 