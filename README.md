# statefun-ide

This project showcases how to run a Stateful Remote Module from within your IDE. No external dependencies required. 

The project has two sub-modules: 

* **statefun-fn** contains a remote functions, which is served under localhost:1013. 
* **statefun-harness** contains the module.yaml and the harness to run the statefun cluster locally.

## Run

1. Run[`Main`](src/main/java/com/github/knauf/statefun/Main.java) in `statefun-fn`. This serves the `DemoFn` locally. 
2. Run [`Main`](src/main/java/com/github/knaufk/statefunharness/Main.java) in `statefun-harnness`. This starts the Statefun Cluster. 

## About the Module

The [module.yaml]([src/main/resources/module.yaml) define the endpoint of the `DemoFn`, a Kakfa Ingress and a Kafka Egress. 
Every message from the ingress is routed to the `DemoFn`, which in turn routes it to the Egress. 
In the [harness](src/main/java/com/github/knaufk/statefunharness/Main.java) the Kafka Ingress and Egress are "mocked" with a `CyclingInMemoryIngress` and a `PrintingEgress`. 
