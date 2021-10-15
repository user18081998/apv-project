# apv-project

## What You Will Need
### 1. create a service principal with azure cli
```shell script
az ad sp create-for-rbac -n "apv-project" --skip-assignment
```
### 2. give the created sp roles 
Contributor role works

>portal> subscriptions> your-subscription >acess control
### 3. set environment variables (or make a .env file) with required azure creds
````shell
AZURE_CLIENT_ID=xxxx-xxxxx-xxxxxx-xxxxx-xxxxxx
AZURE_CLIENT_SECRET=xxxxxxxxxxxxxxxxxxxxxxxxxx
AZURE_TENANT_ID=xxxx-xxxxx-xxxxxx-xxxxx-xxxxxx
AZURE_SUBSCRIPTION_ID=xxxx-xxxxx-xxxxxx-xxxxx-xxxxxx
````
### 4. run application in dev mode
```shell script
./mvnw compile quarkus:dev
```
