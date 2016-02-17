# turbine-ec2

Turbine Instance Discovery based on EC2 tags.

## Why

At ContaAzul, we are using Hystrix for circuit-breakers and Turbine to
aggregate Hystrix streams.

Turbine has Eureka InstanceDiscovery, but we don't use it, nor want to use it.

It seamed simple enough to just use our already defined tags to wire services
to Turbine.

That's what this project do.

## How it works

You will need to configure the `config.properties` file just as before. The
difference is that you can setup what tag should be looked up to find instances
of each service.

Example:

```properties
turbine.aggregator.clusterConfig=my-svc,other-svc
turbine.instanceUrlSuffix=:8080/hystrix.stream

# specific tag for `my-svc`
turbine.ec2.my-svc.tag=SVC

# generic tag, used whenever a specific tag is not specified
turbine.ec2.tag=SERVICE

# needed to access AWS api
turbine.ec2.aws.access=AWS_ACCESS_KEY
turbine.ec2.aws.secret=AWS_SECRET_KEY
```

That's it.

## Deploying

You can either download the `turbine-ec2-core.jar` and wire it with
your own code, or download the `turbine-ec2-web.war` and deploy it in a
servlet container.
