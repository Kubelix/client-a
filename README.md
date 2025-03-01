<h1>Introduction</h1>

Sample Spring boot client to test requests sent to a headless service that is configured in the same K8s cluster as this client.

<h2>Steps to deploy</h2>

Follow the same deployment steps as in [server-b](https://github.com/Kubelix/server-b/blob/master/README.md) 

To verify connectivity to the `server-b` pod, we can `exec` into the `client-a` pod shell and send a `curl` request. 

First install the `curl` package into the pod:

![curl_install](https://github.com/Kubelix/client-a/blob/master/utils/images/curl_install.png)

Send the curl request to the `server-b` pod:

![curl_req_server_b](https://github.com/Kubelix/client-a/blob/master/utils/images/curl_req_server_b.png)

We can also get the IPs for the server-b cluster DNS. For this, first install the `dnsutils` package in the pod using `apt-get update && apt-get install -y dnsutils`. 

Then, run `nslookup` on the server-b endpoint:

`nslookup server-b.default.svc.cluster.local`

If the cluster is correctly setup, it will send requests to the server-b upon startup. The client logs will be as below.

![client_logs](https://github.com/Kubelix/client-a/blob/master/utils/images/client_logs.png)

The logs clearly show that sample cache data was updated on `server-b` pods.




