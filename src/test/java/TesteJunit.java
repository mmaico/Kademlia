import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

@RunWith(JUnitPlatform.class)
public class TesteJunit {

    @DisplayName("Test successful")
    @Test
    public void shouldExecute() {
        Assertions.assertTrue(true);
    }
}
