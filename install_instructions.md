### install gcloud
* [TERMINAL] curl https://sdk.cloud.google.com | bash
* [TERMINAL] exec -l $SHELL

### install kubectl (via gcloud)
* [TERMINAL] gcloud components install kubectl

### install virtualbox
* download and install https://download.virtualbox.org/virtualbox/5.2.8/VirtualBox-5.2.8-121009-OSX.dmg

### install minikube
* [TERMINAL] curl -Lo minikube https://storage.googleapis.com/minikube/releases/v0.25.0/minikube-darwin-amd64 && chmod +x minikube && sudo mv minikube /usr/local/bin/

### verify installation
* [TERMINAL] minikube start
* [TERMINAL] kubectl run install-check --image=nginx --port=80
* [TERMINAL] kubectl expose deployment install-check --type=NodePort
* [TERMINAL] minikube service install-check => the browser opens a tab with the default nginx site
