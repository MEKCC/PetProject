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
#kubectl apply -f k8s/loadbalancer.yaml
# посмотреть поды и их хешкод
kubectl get pods
kubectl get pods --watch
# посмотреть логи к поду (указать хеш пода)
kubectl logs petproject-59dbc84b48-qxqw4
kubectl delete pods --all
kubectl delete deployments --all
kind delete cluster --name kind
# petproject-59dbc84b48-78xdv заменить на корректный хеш
# два порта, слева тот на который мы обращаемя, справа тот на котором работает наше java app внутри пода
kubectl port-forward petproject-79d7f8d584-kbw7f 8899:8888
kubectl get service
kubectl describe pod petproject-55cb4cbcb7-w7k89
kubectl get ingress
kubectl get services --all-namespaces
kubectl get services -n default
kubectl exec -it petproject-59dbc84b48-qxqw4 -- /bin/bash


# ********************************************* kafka *********************************
Войдите в Docker-контейнер с Kafka с помощью команды:
docker exec -it petproject-kafka-1 bash

Внутри контейнера выполните команду для получения списка всех топиков:
/usr/bin/kafka-topics --list --bootstrap-server petproject-kafka-1:9092
