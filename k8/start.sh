#!/bin/bash
kubectl apply -f ./postgres/secrets.yml
kubectl apply -f ./postgres/user/config.yml
kubectl apply -f ./postgres/user/pv.yml
kubectl apply -f ./postgres/user/db.yml

kubectl apply -f ./postgres/secrets.yml
kubectl apply -f ./postgres/address/config.yml
kubectl apply -f ./postgres/address/pv.yml
kubectl apply -f ./postgres/address/db.yml

kubectl apply -f ./postgres/secrets.yml
kubectl apply -f ./postgres/patient/config.yml
kubectl apply -f ./postgres/patient/pv.yml
kubectl apply -f ./postgres/patient/db.yml

kubectl apply -f ./ms/eureka/config.yml
# kubectl apply -f ./ms/eureka/ms.yml

kubectl apply -f ./ms/gateway/config.yml
kubectl apply -f ./ms/gateway/ms.yml

kubectl apply -f ./ms/user/config.yml
kubectl apply -f ./ms/user/ms.yml

kubectl apply -f ./ms/oauth2/config.yml
kubectl apply -f ./ms/oauth2/ms.yml

kubectl apply -f ./ms/address/config.yml
kubectl apply -f ./ms/address/ms.yml

kubectl apply -f ./ms/patient/config.yml
kubectl apply -f ./ms/patient/ms.yml

kubectl apply -f ./ms/grafana/ms.yml

kubectl apply -f ./ms/prometheus/pv.yml
kubectl apply -f ./ms/prometheus/ms.yml
