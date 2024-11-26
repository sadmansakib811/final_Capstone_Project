package tests;

// Import the new JUnit 5 annotations
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SelectClasses;

//I have Use the @Suite annotation to indicate this is a test suite
@Suite
// Use @SelectClasses to specify the test classes to include
@SelectClasses({
    EnergySimulatorTest.class,
    FileReaderUtilTest.class,
    LogManagerTest.class,
    SimpleStringSearchTest.class,
    RegexSearchTest.class,
    BatteryTest.class
})
public class AllTests {
    // This class remains empty; it's used only as a holder for the above annotations
}
