package com.e_garden.api;

import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.lang.management.RuntimeMXBean;
import java.nio.file.FileStore;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@RestController
@Secured({"ADMINISTRATEUR"})
public class ServerInfoController {

    @GetMapping("/server-info")
    public Map<String, Object> getServerInfo() {
        Map<String, Object> info = new HashMap<>();

        // Stockage restant
        try {
            FileStore fileStore = Files.getFileStore(Paths.get("/"));
            info.put("freeStorage", fileStore.getUsableSpace() / (1024 * 1024) + " MB");
            info.put("totalStorage", fileStore.getTotalSpace() / (1024 * 1024) + " MB");
        } catch (Exception e) {
            info.put("freeStorage", "Error retrieving storage info");
        }

        // Heure du dernier redémarrage
        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
        long uptimeMillis = runtimeMXBean.getUptime();
        long lastBootMillis = System.currentTimeMillis() - uptimeMillis;
        info.put("lastBoot", new java.util.Date(lastBootMillis));

        // Version Linux
        info.put("osName", System.getProperty("os.name"));
        info.put("osVersion", System.getProperty("os.version"));

        // Version Java
        info.put("javaVersion", System.getProperty("java.version"));

        // Version Node.js (si installé)
        try {
            Process process = Runtime.getRuntime().exec("node -v");
            java.util.Scanner scanner = new java.util.Scanner(process.getInputStream());
            if (scanner.hasNext()) {
                info.put("nodeVersion", scanner.nextLine());
            } else {
                info.put("nodeVersion", "Node.js not found");
            }
            scanner.close();
        } catch (Exception e) {
            info.put("nodeVersion", "Error retrieving Node.js version");
        }

        // RAM utilisée
        OperatingSystemMXBean osBean = ManagementFactory.getOperatingSystemMXBean();
        Runtime runtime = Runtime.getRuntime();
        info.put("usedMemory", (runtime.totalMemory() - runtime.freeMemory()) / (1024 * 1024) + " MB");
        info.put("totalMemory", runtime.totalMemory() / (1024 * 1024) + " MB");

        // CPU utilisation (%)
        try {
            double load = osBean.getSystemLoadAverage();
            info.put("cpuLoad", load >= 0 ? load * 100 + " %" : "N/A");
        } catch (Exception e) {
            info.put("cpuLoad", "Error retrieving CPU load");
        }

        return info;
    }
}
