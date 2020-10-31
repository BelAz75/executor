package com.virtuallab.executor;

import com.virtuallab.common.Language;
import com.virtuallab.submission.entrypoint.SubmissionResult;
import com.virtuallab.submission.usecase.UpdateSubmission;
import com.virtuallab.util.UUIDUtil;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class ContainerStarter {
    private final DockerImageGenerator imageGenerator;
    private final UpdateSubmission updateSubmission;

    public ContainerStarter(DockerImageGenerator imageGenerator, UpdateSubmission updateSubmission) {
        this.updateSubmission = updateSubmission;
        this.imageGenerator = imageGenerator;
    }

    public void executeJavaCode(String id, String taskId, String code) throws IOException, InterruptedException {
        String executionFolderName = UUIDUtil.generateShortUUID();
        SubmissionResult submissionResult = new SubmissionResult();
        submissionResult.setStatus("Started");
        updateSubmission.setResult(id, submissionResult);
        Path executionFolder = Paths.get("./images/" + Language.JAVA.name().toLowerCase() + "/" + executionFolderName);
        Files.createDirectories(executionFolder);

        System.out.println("---------JAVA---------");
        long start = System.currentTimeMillis();
        String dockerImageName = imageGenerator.generateDockerImageForLanguage(Language.JAVA, executionFolderName, taskId, code);
        System.out.println("============Start process===========");
        String executionOutput = executeProcess("docker run " + dockerImageName);
        submissionResult.setStatus("Finished");
        submissionResult.setError(executionOutput);
        countPassedTests(executionOutput, submissionResult);
        updateSubmission.setResult(id, submissionResult);
        long time = System.currentTimeMillis() - start;
        executeProcess("docker rmi -f " + dockerImageName);
        deleteDirectory(executionFolder);
        System.out.println("---------JAVA END----"+time + "ms-----");
    }

    public void executePythonCode(String id, String taskId, String code) throws IOException, InterruptedException {
        String executionFolderName = UUIDUtil.generateShortUUID();
        SubmissionResult submissionResult = new SubmissionResult();
        submissionResult.setStatus("Started");
        updateSubmission.setResult(id, submissionResult);
        Path executionFolder = Paths.get("./images/" + Language.PYTHON.name().toLowerCase() + "/" + executionFolderName);
        Files.createDirectories(executionFolder);

        System.out.println("----------PYTHON----------");
        long start = System.currentTimeMillis();
        String dockerImageName = imageGenerator.generateDockerImageForLanguage(Language.PYTHON, executionFolderName, taskId, code);
        System.out.println("============Start process===========");
        String executionOutput = executeProcess("docker run " + dockerImageName);
        submissionResult.setStatus("Finished");
        submissionResult.setError(executionOutput);
        countPassedTests(executionOutput, submissionResult);
        updateSubmission.setResult(id, submissionResult);
        long time = System.currentTimeMillis() - start;
        executeProcess("docker rmi -f " + dockerImageName);

        deleteDirectory(executionFolder);
        System.out.println("----------PYTHON END---" + time + "ms------");
    }

    public void executeCCode(String id, String taskId, String code) throws IOException, InterruptedException {
        String executionFolderName = UUIDUtil.generateShortUUID();
        SubmissionResult submissionResult = new SubmissionResult();
        submissionResult.setStatus("Started");
        updateSubmission.setResult(id, submissionResult);
        Path executionFolder = Paths.get("./images/" + Language.C.name().toLowerCase() + "/" + executionFolderName);
        Files.createDirectories(executionFolder);

        System.out.println("----------C----------");
        long start = System.currentTimeMillis();
        String dockerImageName = imageGenerator.generateDockerImageForLanguage(Language.C, executionFolderName, taskId, code);
        System.out.println("============Start process===========");
        String executionOutput = executeProcess("docker run " + dockerImageName);
        submissionResult.setStatus("Finished");
        submissionResult.setError(executionOutput);
        updateSubmission.setResult(id, submissionResult);
        long time = System.currentTimeMillis() - start;
        executeProcess("docker rmi -f " + dockerImageName);

        deleteDirectory(executionFolder);
        System.out.println("----------C END---" + time + "ms------");
    }

    private void deleteDirectory(Path path) throws IOException {
        Files.walk(path).map(Path::toFile).forEach(File::delete);
        Files.delete(path);
    }

    public static String executeProcess(String command) throws IOException, InterruptedException {
        Process process = Runtime.getRuntime().exec(command);
        int processEnd = process.waitFor();
//        if (processEnd == 0) {
        String content = readStreamContent(process.getErrorStream());
        System.out.println("Error stream:\n" + content);
        content = readStreamContent(process.getInputStream());
        System.out.println("Process output: " + content);
        return content;
    }

    private static void countPassedTests(String outputResult, SubmissionResult submissionResult) {
        String[] split = outputResult.split("\n");
        int passedTests = 0;
        for (int i = 0; i < split.length; i++) {
            if (split[i].contains("Passed")) passedTests++;
        }
        submissionResult.setTestsPassed(passedTests);
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
