# minikube

```
# start minikube
minikube start

# start minikube with specified resources
minikube start --cpus 4 --memory 4096 --insecure-registry localhost:5000 --extra-config=kubelet.CAdvisorPort=4194

# start minikube with a local docker-registry
minikube start --insecure-registry localhost:5000
eval $(minikube docker-env)
docker run -d -p 5000:5000 --restart=always --name k8s-workshop-docker-registry -v $PWD/docker-registry:/var/lib/registry registry:2

# expose cadvisor on minikube
minikube start --extra-config=kubelet.CAdvisorPort=4194

# show minikube dashboard
minikube dashboard

# ssh into minikube vm
minikube ssh

# stop minikube
minikube stop

# open my-service http endpoint in browser tab
minikube service my-service

# list addons
minikube addons list

# enable addon ingress
minikube addons enable ingress

# mount local folder into minikube vm
minikube mount /folder-on-host:/folder-in-vm

# delete local cluster
minikube delete
```

# gcloud

```
# initialize project and region settings
gcloud init

# see installed gcloud components
gcloud components list

# install kubectl
gcloud components install kubectl

# get server config
gcloud container get-server-config

# update cluster
gcloud container clusters upgrade my-cluster

# list all projects
gcloud projects list

# get info about permissions of a project
gcloud projects get-iam-policy my-project

# get account info
gcloud info | grep Account

# push a docker image to google docker registry
gcloud docker -- push eu.gcr.io/my-project/my-image:my-version
```

# kubectl

```
# expose a Deployment as a Service
kubectl expose deployment my-deployment --target-port=8080 --type=LoadBalancer

# run proxy for k8s dashboard
kubectl proxy

# apply file to k8s
kubectl apply -f something.yml

# apply directoy to k8s
kubectl apply -f my-dir/

# list pods
kubectl get pods

# list pods
kubectl get pods

# see details of a pod
kubectl describe my-pod

# list services
kubectl get list services

# list nodes
kubectl get nodes

# delete a service
kubectl delete service my-service

# delete all deployments
kubectl delete deployment --all

# delete everything
kubectl delete deployments,services,pods,in --all

# stream logging of a container of a pod
kubectl logs -f my-pod

# exec into the container of a pod
kubectl exec -it my-pod bash 

# get config
kubectl config view

# set default namespace
kubectl config set-context $(kubectl config current-context) --namespace=my-namespace
kubectl config view | grep namespace

# apply configmap from file
kubectl apply configmap my-configmap --from-file=my-file.txt

# apply configmap from directory
kubectl apply configmap my-configmap --from-file=my-directory/

# get info about cluster
kubectl cluster-info 

# get kubectl version
kubectl version

# add user to role
kubectl create clusterrolebinding my-binding --clusterrole=my-role --user=my-user

# download k8s resources into yaml file
kubectl get all -o yaml --export > my-file.yml
```

# helm

```
# install tiller into cluster
helm init

# create helm chart
helm create my-chart

# see dependencies of a chart
helm dependency list my-chart

# update dependencies of a chart
helm dependency update my-chart

# start helm repo localy
docker run --rm -it \
  -p 8080:8080 \
  -v $PWD/helm/charts:/charts \
  chartmuseum/chartmuseum:latest \
  --debug \
  --port=8080 \
  --storage="local" \
  --storage-local-rootdir="/charts"

# add helm repo
helm repo add my-repo http://localhost:8080/

# list all repos
helm repo list

# install chart into repo
helm package .
curl --data-binary "@base-chart-0.1.0.tgz" http://localhost:8080/api/charts

# list 
helm list

# releasing
helm install -f ./values.yaml --name my-release my-chart

# upgrading
helm upgrade -f ./values.yaml my-release hust/base-chart

# delete release
helm delete my-release

# list values
helm inspect values

# list releases
helm ls --all my-release
```