package com.virtuallab.executor;

import com.virtuallab.common.Language;
import com.virtuallab.util.UUIDUtil;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class ContainerStarter {
    private final DockerImageGenerator imageGenerator;
    public ContainerStarter(DockerImageGenerator imageGenerator) {
        this.imageGenerator = imageGenerator;
    }

    public void executeJavaCode() throws IOException, InterruptedException {
        String executionFolderName = UUIDUtil.generateShortUUID();
        Path executionFolder = Paths.get("./images/" + Language.JAVA.name().toLowerCase() + "/" + executionFolderName);
        Files.createDirectories(executionFolder);

        System.out.println("---------JAVA---------");
        long start = System.currentTimeMillis();
        String dockerImageName = imageGenerator.generateDockerImageForLanguage(Language.JAVA, executionFolderName,
                "public class Main {\n" +
                "  public static void main(String[] args) {\n" +
                "    System.out.println(\"Test Noner Run\");\n" +
                "  }\n" +
                "}");
        System.out.println("============Start process===========");
        executeProcess("docker run " + dockerImageName);
        long time = System.currentTimeMillis() - start;
        executeProcess("docker rmi -f " + dockerImageName);
        deleteDirectory(executionFolder);
        System.out.println("---------JAVA END----"+time + "ms-----");
    }

    public void executePythonCode() throws IOException, InterruptedException {
        String executionFolderName = UUIDUtil.generateShortUUID();
        Path executionFolder = Paths.get("./images/" + Language.PYTHON.name().toLowerCase() + "/" + executionFolderName);
        Files.createDirectories(executionFolder);

        System.out.println("----------PYTHON----------");
        long start = System.currentTimeMillis();
        String dockerImageName = imageGenerator.generateDockerImageForLanguage(Language.PYTHON, executionFolderName,
                "print(\"This line will be printed.\")");
        System.out.println("============Start process===========");
        executeProcess("docker run " + dockerImageName);
        long time = System.currentTimeMillis() - start;
        executeProcess("docker rmi -f " + dockerImageName);

        deleteDirectory(executionFolder);
        System.out.println("----------PYTHON END---" + time + "ms------");
    }

    public void executeCCode() throws IOException, InterruptedException {
        String executionFolderName = UUIDUtil.generateShortUUID();
        Path executionFolder = Paths.get("./images/" + Language.C.name().toLowerCase() + "/" + executionFolderName);
        Files.createDirectories(executionFolder);

        System.out.println("----------C----------");
        long start = System.currentTimeMillis();
        String dockerImageName = imageGenerator.generateDockerImageForLanguage(Language.C, executionFolderName,
                "#include <stdio.h>\n" +
                "int main()\n" +
                "{\n" +
                "  puts (\"Hello, World!\");\n" +
                "  return 0;\n" +
                "}");
        System.out.println("============Start process===========");
        executeProcess("docker run " + dockerImageName);
        long time = System.currentTimeMillis() - start;
        executeProcess("docker rmi -f " + dockerImageName);

        deleteDirectory(executionFolder);
        System.out.println("----------C END---" + time + "ms------");
    }

    private void deleteDirectory(Path path) throws IOException {
        Files.walk(path).map(Path::toFile).forEach(File::delete);
        Files.delete(path);
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
