class MyResource implements AutoCloseable {

    public MyResource() {
        System.out.println("Resource opened");
    }

    public void use() throws Exception {
        System.out.println("Using resource");
        throw new Exception("Original error occurred");
    }

    @Override
    public void close() {
        System.out.println("Resource closed");
    }
}

public class Problem {

    public static void main(String[] args) {

        try (MyResource resource = new MyResource()) {
            resource.use();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        System.out.println("Program completed");
    }
}