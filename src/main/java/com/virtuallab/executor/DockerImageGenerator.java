package com.virtuallab.executor;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.virtuallab.executor.ContainerStarter.executeProcess;

@Service
public class DockerImageGenerator {

    public String generateDockerImageForLanguage(Language language, String execFolder, String fileContent) throws IOException, InterruptedException {
        String dockerImageName;
        switch (language) {
            case JAVA:
                createJavaExecScript(language, execFolder);
                dockerImageName = generateJavaDockerImage(createJavaFile(language, execFolder, fileContent), execFolder, language);
                break;
            case PYTHON:
                dockerImageName = generatePythonDockerImage(createFile(language, execFolder, fileContent), execFolder, language);
                break;
            case C:
                dockerImageName = generateCDockerImage(createFile(language, execFolder, fileContent), execFolder, language);
                break;
            case PASCAL:
                dockerImageName = generatePascalDockerImage();
                break;
            default:
                throw new RuntimeException("Programming language " + language + " not supported");
        }
        return dockerImageName;
    }


    private Path createJavaFile(Language language, String execFolder, String fileContent) throws IOException {
        //TODO it's not working for the Java
        String fileName = "Main" + language.getExtension();
        Path filePath = Paths.get("./images/" + language.name().toLowerCase() + "/" + execFolder + "/" + fileName);
        Files.write(filePath, fileContent.getBytes());
        return filePath;
    }

    private Path createJavaExecScript(Language language, String execFolder) throws IOException {
        String fileName = "process_execution.sh";
        String scriptContent = "#!/bin/bash\n" +
                               "cd /usr/local/bin/\n" +
                               "javac Main.java\n" +
                               "java Main\n" +
                               "exit 1";
        Path filePath = Paths.get("./images/" + language.name().toLowerCase() + "/" + execFolder + "/" + fileName);
        Files.write(filePath, scriptContent.getBytes());
        return filePath;
    }

    private Path createFile(Language language, String execFolder, String fileContent) throws IOException {
        String fileName = UUIDUtil.generateShortUUID() + language.getExtension();
        Path filePath = Paths.get("./images/" + language.name().toLowerCase() + "/" + execFolder + "/" + fileName);
        Files.write(filePath, fileContent.getBytes());
        return filePath;
    }

    private String generateJavaDockerImage(Path filePath, String execFolder, Language language) throws IOException, InterruptedException {
        Path dockerfilePath = Paths.get("./images/" + language.name().toLowerCase() + "/" + execFolder + "/Dockerfile");
        String dockerfile = "FROM openjdk:11.0.1-slim\n" +
                "COPY process_execution.sh /usr/local/bin/process_execution.sh\n" +
                "RUN chmod +x /usr/local/bin/process_execution.sh\n" +
                "COPY " + filePath.getFileName() + " /usr/local/bin/" + filePath.getFileName() + "\n" +
                "CMD [\"/usr/local/bin/process_execution.sh\"]";
        Files.write(dockerfilePath, dockerfile.getBytes());
        String dockerImageName = language.name().toLowerCase() + "_" + UUIDUtil.generateShortUUID();
        executeProcess("docker build -t " + dockerImageName + " " + dockerfilePath.getParent().toAbsolutePath().toString());
        return dockerImageName;
    }

    private String generatePythonDockerImage(Path filePath, String execFolder, Language language) throws IOException, InterruptedException {
        Path dockerfilePath = Paths.get("./images/" + language.name().toLowerCase() + "/" + execFolder +  "/Dockerfile");
        String dockerfile = "FROM python:3\n" +
                "WORKDIR /usr/src/app\n" +
                "COPY " + filePath.getFileName() + " ./" + filePath.getFileName() + "\n" +
                "CMD [\"python\", \"./" + filePath.getFileName() + "\"]";
        Files.write(dockerfilePath, dockerfile.getBytes());
        String dockerImageName = language.name().toLowerCase() + "_" + UUIDUtil.generateShortUUID();
        executeProcess("docker build -t " + dockerImageName + " " + dockerfilePath.getParent().toAbsolutePath().toString());
        return dockerImageName;
    }

    private String generateCDockerImage(Path filePath, String execFolder, Language language) throws IOException, InterruptedException {
        Path dockerfilePath = Paths.get("./images/" + language.name().toLowerCase() + "/" + execFolder +  "/Dockerfile");
        String dockerfile = "FROM gcc:4.9\n" +
                "COPY " + filePath.getFileName() + " /usr/src/myapp/" + filePath.getFileName() + "\n" +
                "WORKDIR /usr/src/myapp\n" +
                "RUN gcc -o myapp ./" + filePath.getFileName() + "\n" +
                "CMD [\"./myapp\"]";
        Files.write(dockerfilePath, dockerfile.getBytes());
        String dockerImageName = language.name().toLowerCase() + "_" + UUIDUtil.generateShortUUID();
        executeProcess("docker build -t " + dockerImageName + " " + dockerfilePath.getParent().toAbsolutePath().toString());
        return dockerImageName;
    }

    private String generatePascalDockerImage() {
        return "";
    }
}
