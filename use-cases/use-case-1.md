# USE CASE 1: Delete an employee's data from the database

## CHARACTERISTIC INFORMATION

### Goal in Context

As an *HR advisor* I want *to delete an employee's details* so that *the company is compliant with data retention legislation.*

### Scope

Company.

### Level

Primary task.

### Preconditions

We know the employee.  Database contains current employee data.

### Success End Condition

Employee's data is deleted from the database.

### Failed End Condition

Employee's data is still accessible.

### Primary Actor

HR Advisor.

### Trigger

Employee has left the company.

## MAIN SUCCESS SCENARIO

1. Employee leaves company.
2. HR Advisor captures name and ID of departed employee.
3. HR Advisor deletes employee data from database

## EXTENSIONS

3. **Employee has no data**:
    1. HR advisor informs admin employee data was not correctly acquired.

## SUB-VARIATIONS

None.

## SCHEDULE

**DUE DATE**: Release 1.0
