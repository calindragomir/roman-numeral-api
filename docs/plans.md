# Future steps to production

### Metrics
The application provides a basic set of metrics at the moment.<br>
The default `actuator` metrics and a custom one that times the execution and counts the number of calls.<br>
But others might be useful in the future also:
- datetime of each request = to figure out the access patterns
- ranges requested = to see what kind of ranges are more interesting for the users
- error events = to figure out the flaws of the application

### Logging
The logging provided by the application at the moment is basic. Logs need to be collected and a centralized
logging solution is essential when the application runs on multiple instances.

### Dashboards
With metrics implemented, the application would need a couple of dashboards to display the gathered
information.<br>
This will be useful in analysing patterns and also to predict possible incidents.

### Load testing
It would be useful to test out the performance of the API and add a test suite that checks the
response times under stress conditions.<br>
This will be useful to evaluate the impact of changes brought in time to the application and 
also figure out some limitations.

### More complex CI/CD pipeline
The CI/CD pipeline included in this project is quite basic, but can easily be extended to 
perform more tasks.<br>
Things to consider are:
- sending alert emails on build failures
- :warn: The `publish` step is intentionally left out, this needs to be completed to push packages to
allowed repositories.

### Add Spring Boot Admin
The fact that the project uses Spring Boot makes it possible to enable the Spring Boot Admin application.<br>
This provides a very useful set of features and also comes with a packaged UI interface which makes debugging
and overseeing the application very accessible.

### Add security layer
It would be useful to provide an HTTPS endpoint to serve the application.<br>
Also the actuator endpoint, even if it is on a separate port than 8080, it needs to require authentication.
Spring Security offers a feature set to accomplish this.<br>
`Vulnerability scans` in the build pipeline might be useful to detect any known issues that might be
included with newer releases.

### Audit
An extra security measure would be to audit the access to the application and more importantly to the
restricted parts of it, like the administration interface.

### Different configuration layers (NPE & PROD)
The application has only the basic local development configuration included at the moment. There are no configuration 
files for other non-production environments: DEV, STAGE, PRE-PROD or for PROD application. These need to be 
included in a production-ready application.

### Infrastructure
There is no deployment related configuration included in the present state of the application. Things to 
consider would be to add some automated tooling to help developers deploy the application to different 
environments. 
Technologies such as `Ansible`, `Terraform` or `Kubernetes` might help with having a standardized 
deployment process.<br>
For `Kubernetes`, a helm chart and some helm configuration values need to be written up.

### Autoscaling
If the application is to be deployed with cloud providers, some autoscaling rules (up/down) would be helpful for
accommodating traffic spikes and keeping costs reasonable.

### Git protection
Contributions to the applications need to be documented by an associated issue and also code reviews
must be enforced.

### SLAs and Alerting
A production-ready application needs to offer a set of SLAs.<br>
Once agreed, there should be alerting set in place with different levels: `WARNING` and `CRITICAL` to avoid breaches.

### Deployment strategy and disaster recovery plan
A deployment strategy needs be decided, either being a `canary release` approach or `blue-green deployment`, in
order to minimize downtime of the application.
A plan needs to be put in place before the first release with how to recover from a critical failure.
Moreover, a number of developers need to take ownership of running this application within the agreed parameters.

### Connection to external resources
If the application needs to connect to external services in the future, some other features
need to be implemented like retry mechanisms, circuit breakers and a docker compose setup with local stubs
to ease up local development.

### Define interaction with application maintainers
A protocol to communicate with the application maintainers might be useful by establishing some 
channels, either being GitHub issues, JIRA issues or distribution lists. If there are multiple
channels , some of the information might be overlooked.
