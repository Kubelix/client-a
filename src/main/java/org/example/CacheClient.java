package org.example;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.InetAddress;
import java.util.Arrays;

@Component
public class CacheClient implements CommandLineRunner {
    private static final String SERVER_B_SERVICE = "server-b.default.svc.cluster.local";
    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public void run(String... args) throws Exception {
        // Get all pod IPs for server-b
        InetAddress[] addresses = InetAddress.getAllByName(SERVER_B_SERVICE);

        // Create sample cache update
        CacheUpdate update = new CacheUpdate();
        update.setKey("sample-key");
        update.setValue("sample-value-" + System.currentTimeMillis());

        // Send update to all pods
        Arrays.stream(addresses).forEach(address -> {
            String targetPort = System.getenv("TARGET_PORT") != null ? System.getenv("TARGET_PORT") : "80";
            String url = String.format("http://%s:%s/api/cache/update", address.getHostAddress(), targetPort);
            try {
                String response = restTemplate.postForObject(url, update, String.class);
                System.out.println("Response from " + address.getHostAddress() + ": " + response);
            } catch (Exception e) {
                System.err.println("Failed to update cache on " + address.getHostAddress() + ": " + e.getMessage());
            }
        });
    }
}
