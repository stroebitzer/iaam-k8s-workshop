# k8s workshop

## getting stuff running

explain docker reg issue
```
# everything has to be done in the same console
# check proper directory
# delete k8s-workshop-docker-registry
# new terminal
# ensure no reg running with docker ps

minikube stop
minikube delete
minikube start --cpus 4 --memory 4096 --insecure-registry localhost:5000 --extra-config=kubelet.CAdvisorPort=4194
eval $(minikube docker-env)
docker run -d -p 5000:5000 --restart=always --name k8s-workshop-docker-registry -v $PWD/k8s-workshop-docker-registry:/var/lib/registry registry:2
minikube dashboard

#http://[MINIKUBE-IP]:5000/v2/_catalog
#http://[MINIKUBE-IP]:5000/v2/hello-k8s/tags/list
```

## pre

### intro
* time constraints for workshop
* CNCF - https://raw.githubusercontent.com/cncf/landscape/master/landscape/CloudNativeLandscape_latest.png
* k8s vs kubernetes
* k8s situation: clouds & competitors
* what problem does k8s solve (borg-sized)
* gcloud versus kubectl versus minikube versus helm
* hust no knowledge
* targets no k8s knowledge audience
* discussion if a dev should use kubectl or not

### basics
* explain pod, service,...
* show cheatsheet 
* declarative vs imperative
* mention that we are not working with pods
* prometheus integration http://192.168.99.100:4194/metrics
* 12 factor apps
* brendan burns paper

### infra
* show nodes
* ssh into node
* show os

### logging
* stuff gets into files into /var/log/containers
* explain daemonset 
* explain our fluentd scenario

## topics
* show cheatsheet
* explain how the labs work
* mention that the spaces of the TODOS in the yaml files are correct
* intellij TODOS support


### 01 hello k8s

#### todos
* show app
* locally
    * ../../gradlew clean build
    * run app
* in docker
    * docker build -t localhost:5000/hello-k8s .
    * docker run -it -p 8080:8080 localhost:5000/hello-k8s 
* k8s
    * docker push localhost:5000/hello-k8s
    * kubectl apply -f hello-k8s-pod.yml 
        * minikube ssh
        * curl localhost:8080

#### tosays
* describe labels
* describe hostnetwork
* describe terminationGracePeriodSeconds
* describe imagepullpolicy
* mention what is shared in a pod: file system, network, secrets

#### toshows
* kubectl get pods
* kubectl logs -f hello-k8s
* kubectl exec -it hello-k8s bash


### 02 getting visible

#### todos
* show app
* show k8s
* ./build.sh 
* kubectl apply -f k8s
* minikube service getting-visible

#### tosays
* explain pod replicationset deployment
* Service selects Deployment via label selector
* Service Types:
     * ClusterIP: use a cluster-internal IP only - this is the default and is discussed above. Choosing this value means that you want this service to be reachable only from inside of the cluster.
     * NodePort: on top of having a cluster-internal IP, expose the service on a port on each node of the cluster (the same port on each node). You’ll be able to contact the service on any :NodePort address.
     * LoadBalancer: on top of having a cluster-internal IP and exposing service on a NodePort also, ask the cloud provider for a load balancer which forwards to the Service exposed as a :NodePort for each Node.

#### toshows
* kubectl get pods => now we have cryptic pod names
* show how to scale


### 03 configure stuff

#### toexplain
* explain spring config mechanism and hierarchy, paint scenario

#### todos
* show app
* show k8s
* ./build.sh 
* kubectl apply -f k8s
* minikube service configure-stuff
* do change of configmap in ui and restart pod
* show changed file in container

#### tosays
* overriding existing files can get tricky => https://stackoverflow.com/questions/47882403/kubernetes-configmap-directory-permissions


### 04 share stuff

#### toexplain 
* sidecar container
* shared volumes
* shared network
* shared secrets

#### todos
* show app
* show k8s
* ./build.sh 
* kubectl apply -f k8s
* minikube service share-stuff
* do in app
     * app1: /call
     * app1: /write?content=ööö
     * app2: /read


### 05 init container

#### todos
* show app
* show k8s
* ./build.sh 
* kubectl apply -f k8s
* minikube service init-container
* app: /read


### 06 lifecycle

#### toexplain
* liveness
* readyness
* terminationGracePeriodSeconds
* application shut down: k8s sends SigTerm event which Spring Boot understands
* HTTP status code 503

#### todos
* show app
* show k8s
* ./build.sh 
* kubectl apply -f k8s
* minikube service lifecycle (waiting is OK)
* app: /set_live/false

#### tomention
* explain why readyness does not work with service type NodePort


###07 resources

#### toexplain
* show Dockerfile settings
* explain our issue
* mention that topic does not work

#### todos
* show app
* show k8s
* ./build.sh 
* kubectl apply -f k8s
* minikube service resources
* show dashboard - Nodes


### 08 dns

#### todos
* show app
* show k8s
* ./build.sh
* kubectl apply -f k8s
* minikube service dns-app1
* minikube service dns-app2
* app2: /
* app1: /call

#### toshowoff
* show in application properties that they are running on the same port


### 09 namespaces

#### toexplain
* namespace scenario

#### todos
* show app
* show k8s
* ./build.sh
* kubectl apply -f k8s
* minikube service dns-app1
* minikube service dns-app2 -n hades
* app1: /
* app2: /
* app1: /call
* app2: /call


## to be done
* volumes & persistentvolumeclaims
* stateful set
* dns & secrets & ingresses
* helm

## stuff
* https://github.com/googlefonts/fontbakery-dashboard/issues/3
* http://alesnosek.com/blog/2017/02/14/accessing-kubernetes-pods-from-outside-of-the-cluster/
* https://coreos.com/kubernetes/docs/latest/pods.html

## TODOs 
* bash completion
* readynessprobes are not working due to service type nodeport in minikube => can i fix this?
* java resources mess
* Dockerfiles of LABs do net grab the jar
* LABs need on jars and own docker containers => otherwise registry messes
 

