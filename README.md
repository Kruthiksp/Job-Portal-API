# Job-Portal-API
A Spring Boot REST API for a Job Portal application that enables recruiters to post jobs, and job seekers to apply.
Includes JWT authentication, role-based access control, file uploads to Cloudinary, scheduling for job expiry, and detailed API documentation with Swagger.

<h2>Features</h2>
<h4>Authentication & Authorization</h4>
JWT-based login & registration<br>
Role-based access control (RECRUITER, JOB_SEEKER)<br>
Secure endpoints using Spring Security<br>

<h4>Recruiter Features</h4>
Post, edit, and delete jobs<br>
View jobs posted by the recruiter<br>
View applications for their jobs<br>
Auto-expire jobs after last application date (scheduler)<br>

<h4>Job Seeker Features</h4>
Browse available jobs<br>
Apply for jobs<br>
View applied jobs and application details<br>

<h4>General</h4>
Cloudinary integration for resume storage<br>
Validation for all request DTOs<br>
Soft delete for expired jobs<br>
Scheduler to mark jobs as expired daily at midnight<br>
Global exception handling<br>
Swagger API documentation

