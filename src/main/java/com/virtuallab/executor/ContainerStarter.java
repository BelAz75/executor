package com.virtuallab.executor;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@Service
public class ContainerStarter {
    private final DockerImageGenerator imageGenerator;
    public ContainerStarter(DockerImageGenerator imageGenerator) {
        this.imageGenerator = imageGenerator;
    }
    
    public void main() throws IOException, InterruptedException {
        long start = System.currentTimeMillis();
        String dockerImageName = imageGenerator.generateDockerImageForLanguage("java", "public class Main {\n" +
                "  public static void main(String[] args) {\n" +
                "    System.out.println(\"Test Noner Run\");\n" +
                "  }\n" +
                "}");
        System.out.println("============Start process===========");
        executeProcess("docker run " + dockerImageName);
        long time = System.currentTimeMillis() - start;
        System.out.println(time + "ms");
    }

    public static void executeProcess(String command) throws IOException, InterruptedException {
        Process process = Runtime.getRuntime().exec(command);
        int processEnd = process.waitFor();
//        if (processEnd == 0) {
            String content = readStreamContent(process.getInputStream());
            System.out.println("Process output: " + content);
//        } else {
            content = readStreamContent(process.getErrorStream());
            System.out.println("Error stream:\n" + content);
//        }
    }

    private static String readStreamContent(InputStream inputStream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder result = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            result.append(line);
            result.append("\n");
        }
        return result.toString();
    }
}
