mvn clean build
docker build . -t mekcc/petproject:0.0.1
docker run -ti --rm mekcc/petproject:0.0.1
docker run -ti --rm -e DATASOURCE_HOST=192.168.0.108 -p 8081:8888 mekcc/petproject:0.0.1
docker rmi mekcc/petproject:0.0.1
docker push mekcc/petproject:0.0.1

kind create cluster --config kind-config.yaml
kubectl apply -f k8s/deployment.yaml
kubectl apply -f k8s/service.yaml
kubectl apply -f k8s/ingress.yaml
# посмотреть поды
kubectl get pods
kubectl get pods --watch
# посмотреть логи к поду (указать хеш пода)
kubectl logs hashcode
kubectl delete pods --all
kubectl delete deployments --all
# petproject-59dbc84b48-78xdv заменить на корректный хеш
# два порта, слева тот на который мы обращаемя, справа тот на котором работает наше java app внутри пода
kubectl port-forward petproject-59dbc84b48-78xdv 8899:8888
kubectl get service
