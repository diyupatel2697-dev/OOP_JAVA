abstract class Employee
{
    String name;
    String id;

    public Employee(String name,String id) 
    {
        this.name=name;
        this.id=id;
    }
    abstract double monthlySalary();
}

class FullTime extends Employee
{   
    double salary;
    public FullTime(String name, String id ,double salary)
    {
        super(name,id);
        this.salary=salary;
    }
    @Override
    double monthlySalary()
    {
        return this.salary;
    }
}

class PartTime extends Employee
{
    double hours;
    double rate;
    PartTime(String name , String id,double hours,double rate)
    {
        super(name ,id);
        this.hours=hours;
        this.rate=rate;
    }
    
    @Override
    double monthlySalary()
    {
        return this.hours*this.rate;
    }
}

class Intern extends Employee
{
    double stipend;
    public Intern(String name,String id, double stipend)
    {
        super(name,id);
        this.stipend=stipend;
    }

    @Override
    double monthlySalary()
    {
        return this.stipend;
    }
}
public class Payroll 
{
    public static void main(String[] args)
    {
        Employee[] staff=
        {
            new FullTime("Diya","AB1",150000),
            new PartTime("Heer","EF90",120,300),
            new Intern("Janvi","QA24",4000)
        };

        for(Employee e : staff)
        {
            double salary = e.monthlySalary();
            System.out.print(e.name+"("+e.id+")salary="+salary+"\n");
          

            if(e instanceof Intern)
            {
                System.out.print("Intern Stipend\n");
            }
        }
        //  System.out.println();
        //   total += salary;
           
        }

        //System.out.println("Total Payroll = " + total);
    }


