/*  Created by: Giao Nguyen, Hannah Swiney
    Date created: 11/24/2024
    Last updated: 11/26/2024
*/

-- View1: Average Monthly Salary
create view AverageEmployeeSalary as
select e.empId, p.fname, p.lname, avg(ms.amount) as avg_monthly_salary
from employee e
join person p on e.empId = p.personalID
join monthly_salary ms on e.empId = ms.empId
group by e.empId, p.fname, p.lname;

-- View2: Interview Rounds Passed
create view InterviewRoundsPassed as
select ig.interviewee, ig.jid, j.jdescription, count(*) as rounds_passed
from interview_grade ig
join job j on ig.jid = j.jobId
where ig.round_passed = 1
group by ig.interviewee, ig.jid, j.jdescription;

-- View3: Product Type Sales Count
create view ProductTypeSales as
select p.productType, count(*) as items_sold
from sales_history sh
join product p on sh.pid = p.productId
group by p.productType;

-- View4: Part Purchase Cost per Product
create view ProductPartCost as
select co.productid, p.productType, sum(co.qtyUsed * pt.price) as total_part_cost
from consists_of co
join part pt on co.partType = pt.partType
join product p on co.productid = p.productId
group by co.productid;