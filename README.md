# apv-project
Project for "***Architectures Paralleles et Virtualization***" course.
Azure VM management (basic stuff).

Made with **Quarkus** and **Azure SDK for Java**. 

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

### 5. fetch the endpoints
````shell
curl --location --request GET 'localhost:8080/azure'
````
## Endpoints
### azure resources
- list all resources
````shell
curl --location --request GET 'localhost:8080/azure'
````
which returns this structure
````json
[
    {
        "name": "resource-name",
        "id": "resource-id",
        "type": "resource-type"
    },
    {
        "name": "nictest-vm92c488414",
        "id": "/subscriptions/xxxx-xxxxx-xxxxx-xxxxx/resourceGroups/java-sdk-group/providers/Microsoft.Network/networkInterfaces/nictest-vmxxxxxx",
        "type": "Microsoft.Network/networkInterfaces"
    }
]
````
- delete a resource
````shell
curl --location --request PUT 'localhost:8080/azure/' \
--header 'Content-Type: text/plain' \
--data-raw '<resource-id>'
````
### virtual machines specific
- list all VMs : ````GET /azure/vm/````
- get VM details : ````GET /azure/vm/details```` with vm-id as raw body, returns something similar to this : 
````json
{
    "id": "07259d13-3773-xxxx-xxxx-xxxxxxxx",
    "name": "test-vm",
    "computerName": "test-vm",
    "os": "Linux",
    "size": "Standard_B1ls",
    "status": "running"
}
````
- deallocate a VM : ````PUT /azure/vm/deallocate```` with vm-id as raw body
- create a VM : ````PUT /azure/vm/{vm-name}````