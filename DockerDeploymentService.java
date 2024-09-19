
public class DockerDeploymentService {

    public void buildAndDeploy(String appName, String dockerfilePath) {
        try {
            ProcessBuilder pb = new ProcessBuilder("docker", "build", "-t", appName, dockerfilePath);
            Process process = pb.start();
            process.waitFor();
            System.out.println("Docker image built for " + appName);

            pb = new ProcessBuilder("docker", "run", "-d", "--name", appName, appName);
            process = pb.start();
            process.waitFor();
            System.out.println("Container deployed for " + appName);
        } catch (Exception e) {
            System.out.println("Error during deployment: " + e.getMessage());
        }
    }

    public void monitorContainers() {
        try {
            ProcessBuilder pb = new ProcessBuilder("docker", "ps");
            Process process = pb.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (Exception e) {
            System.out.println("Error while monitoring containers: " + e.getMessage());
        }
    }

    public void rollback(String appName) {
        try {
            ProcessBuilder pb = new ProcessBuilder("docker", "stop", appName);
            Process process = pb.start();
            process.waitFor();
            pb = new ProcessBuilder("docker", "rm", appName);
            process = pb.start();
            process.waitFor();
            System.out.println("Rolled back container: " + appName);
        } catch (Exception e) {
            System.out.println("Error during rollback: " + e.getMessage());
        }
    }
}
