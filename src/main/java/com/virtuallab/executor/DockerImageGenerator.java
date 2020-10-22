package com.virtuallab.executor;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.virtuallab.executor.ContainerStarter.executeProcess;

@Service
public class DockerImageGenerator {

    public String generateDockerImageForLanguage(String languageName, String fileContent) throws IOException, InterruptedException {
        Language language = Language.toEnum(languageName);
        Path file = createFile(language, fileContent);
        String dockerImageName;
        switch (language) {
            case JAVA:
                dockerImageName = generateJavaDockerImage(file, language);
                break;
            case PYTHON:
                dockerImageName = generatePythonDockerImage();
                break;
            case CPP:
                dockerImageName = generateCppDockerImage();
                break;
            case PASCAL:
                dockerImageName = generatePascalDockerImage();
                break;
            default:
                throw new RuntimeException("Programming language " + language + " not supported");
        }
//        Files.delete(file);
        return dockerImageName;
    }


    private Path createFile(Language language, String fileContent) throws IOException {
        //TODO it's not working for the Java
        String fileName = /*UUIDUtil.generateShortUUID()*/ "Main"+ language.getExtension();
        Path filePath = Paths.get("./images/" + language.name().toLowerCase() + "/" + fileName);
        System.out.println(filePath.toAbsolutePath().toString());
        System.out.println(filePath.getFileName());
        Files.write(filePath, fileContent.getBytes());
        return filePath;
    }

    private String generateJavaDockerImage(Path filePath, Language language) throws IOException, InterruptedException {
        Path dockerfilePath = Paths.get("./images/" + language.name().toLowerCase() + "/Dockerfile");
        System.out.println(dockerfilePath.toAbsolutePath().toString());
        String dockerfile = "FROM openjdk:11.0.1-slim\n" +
                "COPY java_process_execution.sh /usr/local/bin/java_process_execution.sh\n" +
                "RUN chmod +x /usr/local/bin/java_process_execution.sh\n" +
                "COPY " + filePath.getFileName() + " /usr/local/bin/" + filePath.getFileName() + "\n" +
                "CMD /usr/local/bin/java_process_execution.sh";
        Files.write(dockerfilePath, dockerfile.getBytes());
        String dockerImageName = language.name().toLowerCase() + "_" + UUIDUtil.generateShortUUID();
        executeProcess("docker build -t " + dockerImageName + " " + dockerfilePath.getParent().toAbsolutePath().toString());
//        Files.delete(dockerfilePath);
        return dockerImageName;
    }

    private String generatePythonDockerImage() {
        return "";
    }

    private String generateCppDockerImage() {
        return "";
    }

    private String generatePascalDockerImage() {
        return "";
    }
}
