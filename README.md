# turbine-ec2 [![Build Status](https://travis-ci.org/ContaAzul/turbine-ec2.svg?branch=master)](https://travis-ci.org/ContaAzul/turbine-ec2)

Turbine Instance Discovery based on EC2 tags.

## Why

At ContaAzul, we have used [Netflix/Hystrix][hystrix] for circuit-breakers and
[Netflix/Turbine][turbine] to
aggregate Hystrix streams.

Turbine has Eureka InstanceDiscovery, but we don't use it, nor plan to use it
in near future.

It seemed simple enough to just use our already defined tags to wire services
to Turbine.

That's what this project does.

## How it works

You will need to configure the `config.properties` file just as before. The
difference is that you can specify which tag and value should be looked up to find
instances of each cluster.

Example:

```properties
turbine.aggregator.clusterConfig=my-svc,other-svc
turbine.instanceUrlSuffix=:8080/hystrix.stream

# generic tag, used whenever a specific tag is not specified
turbine.ec2.tag=SERVICE

# specific tag and tag value for `my-svc`
turbine.ec2.tag.my-svc=SVC
turbine.ec2.value.my-svc=svc-value

# needed to access AWS api
turbine.ec2.aws.access=AWS_ACCESS_KEY
turbine.ec2.aws.secret=AWS_SECRET_KEY
turbine.ec2.aws.region=REGION
```

If you set a custom tag value via `turbine.ec2.value.${cluster}`, the value
of this property will be used as filter. Otherwise, the cluster name will be
used.

If you set a custom tag name via `turbine.ec2.tag.${cluster}`, the given
value will be used as `tag:${tag_you_provided}`. Otherwise, the value of
`turbine.ec2.tag` will be used instead. If none is set, an exception will
be thrown.

That's it.

## Deploying

You can either [download][download] the `turbine-ec2-core.jar` and wire
it with your own code, or [download][download] the `turbine-ec2-web.war`
and deploy it in a servlet container.

[download]: https://github.com/ContaAzul/turbine-ec2/releases
[hystrix]: https://github.com/Netflix/Hystrix
[turbine]: https://github.com/Netflix/Turbine
