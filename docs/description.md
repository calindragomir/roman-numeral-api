# Implementation details

### Planning and documentation
The development of the application was planned using [Github issues](https://github.com/calindragomir/roman-numeral-api/issues) and an associated milestone for delivery.
Each commit message follows a [standard convention](https://www.conventionalcommits.org/en/v1.0.0/).

### General setup
The API is implemented using recent Spring Boot (3.2.5) and Java (21) versions.
The approach used is to make models and controllers defined via OpenAPI specification and to use a generator
plugin to create the controllers and models, which are later used by the actual implementation. 
This has the advantage that the contract is ensured and also transparent to everyone.
It can be easily redefined or adapted when needed.

### Implementation
I've chosen to implement a POST endpoint over a GET approach because I thought the application might be further
developed to store the computed information somewhere and also could be more easily 
extended with additional details in the future.<br>
The GET implementation would be pretty similar, with a couple of parameters changed in the yaml, but the same
set of validators and processor behind the scene.<br>

Validation is provided by the OpenAPI specification together with a custom rule defined in the controller
to filter out bad requests.<br>

The converter itself uses a Greedy algorithm to execute the conversion which will run in 
constant time.<br>
The ordering of output is insured by the way we generate the range, so no additional sorting 
needs to be added.<br>
The converter also uses a cache to avoid recomputing previously completed transformations.<br>

Error handling needs to be helpful for the user and bad requests come with associated details about
the input that is faulty.

### Build setup
The application comes with a Github Actions CI/CD workflow defined that builds the application and executes
all tests on every push to the `main` branch.

### Containerization
The application comes with a `Dockerfile` to be able to build images which facilitates usage and portability.<br>
Instructions on how to build an image and run the application are provided in the [documentation](docs/run.md).

### Testing
There is a suite of Integration Tests being executed on build and some unit tests for the convertor logic itself.

### Metrics
Apart from the regular `actuator` metrics is also a custom metric (`convert.integer.method.timed`) that can be used 
to monitor the response time of the convertor and the number of requests executed.<br>
I have provided a helper script (`get_metric.sh`) in `tools` folder to be able to see it easily.

