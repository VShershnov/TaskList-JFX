select `Employee ID`, Username, Department, a.Location, `Job title`
from (
select `Employee ID`, Username, Department, Location, `Job title` from	`Internal Users`
union all 
select `Employee ID`, Username, null, Location, null from`External Users`) a

inner join Locations g on a.Location=g.Location
where g.`Facility Manager`='Alpha'


select 
  from `External Users` t, `Internal Users`  t2, Locations t3
  where t.Locations  = t2.Locations 
  and t2.Locations = t3.Locations
  and t3.`Facility Manager` = 'Alpha'