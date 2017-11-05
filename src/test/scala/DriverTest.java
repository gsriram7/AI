import org.junit.Test;

public class DriverTest {

    @Test
    public void shouldRunAndTestInput() throws Exception {
        String src = "/Users/selvaram/selva/AI/src/main/resources/input.txt";
        String dest = "/Users/selvaram/selva/AI/src/main/resources/output.txt";
        Driver.driver(new String[]{src, dest});
    }
}