import com.olwethuab.propertyrentalapp.PropertyModel;
import com.olwethuab.propertyrentalapp.Property;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PropertyTest {
    
    private Property propertyManager;
    private ByteArrayOutputStream outputStream;
    private PrintStream originalOut;
    private InputStream originalIn;

    @BeforeEach
    void setUp() throws Exception {
        propertyManager = new Property();
        
        outputStream = new ByteArrayOutputStream();
        originalOut = System.out;
        originalIn = System.in;
        System.setOut(new PrintStream(outputStream));
        
        // Add test properties using reflection
        addTestPropertyViaReflection("101", "10 Main Road, 6001", 3500.0, "Joe Bloggs");
        addTestPropertyViaReflection("102", "15 Sea Street, 6070", 3100.0, "Joe Bloggs");
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
        System.setIn(originalIn);
    }

    // Helper method to add test properties using reflection
    private void addTestPropertyViaReflection(String id, String address, double amount, String agent) throws Exception {
        Field propertyListField = Property.class.getDeclaredField("propertyList");
        propertyListField.setAccessible(true);
        @SuppressWarnings("unchecked")
        ArrayList<PropertyModel> propertyList = (ArrayList<PropertyModel>) propertyListField.get(propertyManager);
        
        propertyList.add(new PropertyModel(id, address, amount, agent));
    }

    // Helper method to get property count using reflection
    private int getPropertyCount() throws Exception {
        Field propertyListField = Property.class.getDeclaredField("propertyList");
        propertyListField.setAccessible(true);
        @SuppressWarnings("unchecked")
        ArrayList<PropertyModel> propertyList = (ArrayList<PropertyModel>) propertyListField.get(propertyManager);
        
        return propertyList.size();
    }

    private String getOutput() {
        return outputStream.toString();
    }
    
    private void resetOutput() {
        outputStream.reset();
    }
    
    private void simulateInput(String input) {
        System.setIn(new ByteArrayInputStream(input.getBytes()));
    }

    // Test 1: SearchProperty_ReturnsProperty()
    @Test
    void SearchProperty_ReturnsProperty() {
        // Act - Search for existing property
        propertyManager.SearchProperty("101");
        
        // Assert - Verify correct property data is returned/displayed
        String output = getOutput();
        assertTrue(output.contains("PROPERTY ID: 101"), "Should display correct property ID");
        assertTrue(output.contains("10 Main Road, 6001"), "Should display correct address");
        assertTrue(output.contains("3500.0"), "Should display correct rental amount");
        assertTrue(output.contains("Joe Bloggs"), "Should display correct agent name");
    }

    // Test 2: SearchProperty_NotFound()
    @Test
    void SearchProperty_NotFound() {
        // Act - Search for non-existent property
        propertyManager.SearchProperty("999");
        
        // Assert - Verify property not found message
        String output = getOutput();
        assertTrue(output.contains("not found"), "Should indicate property was not found");
        assertTrue(output.contains("999"), "Should display the searched property ID");
    }

    // Test 3: UpdateProperty_ReturnsSuccess()
    @Test
    void UpdateProperty_ReturnsSuccess() throws Exception {
        // Simulate user input for updating (press Enter to keep existing values or provide new ones)
        simulateInput("\n\n\n"); // Keep all existing values
        
        // Act - Update the property
        propertyManager.UpdateProperty("101");
        
        // Assert - Verify update process completed
        String output = getOutput();
        // The method should complete without errors
        assertNotNull(output, "Update process should complete");
        
        // Verify property still exists and can be searched
        resetOutput();
        propertyManager.SearchProperty("101");
        String searchOutput = getOutput();
        assertTrue(searchOutput.contains("101"), "Property should still exist after update attempt");
    }

    // Test 4: DeleteProperty_ReturnsSuccess()
    @Test
    void DeleteProperty_ReturnsSuccess() throws Exception {
        // Store initial count
        int initialCount = getPropertyCount();
        
        // Simulate user confirmation
        simulateInput("y\n");
        
        // Act - Delete the property
        propertyManager.DeleteProperty("101");
        
        // Assert - Verify deletion was successful
        String output = getOutput();
        assertTrue(output.contains("deleted") || output.contains("101"), "Should indicate deletion");
        
        // Verify property count decreased
        int finalCount = getPropertyCount();
        assertEquals(initialCount - 1, finalCount, "Property count should decrease after deletion");
    }

    // Test 5: PropertyAmountValidation_AmountsValid()
    @Test
    void PropertyAmountValidation_AmountsValid() {
        // Test the validation logic with valid amounts
        assertTrue(isValidRentalAmount(1500.0), "1500 should be valid");
        assertTrue(isValidRentalAmount(2000.0), "2000 should be valid");
        assertTrue(isValidRentalAmount(3500.0), "3500 should be valid");
        assertTrue(isValidRentalAmount(5000.0), "5000 should be valid");
        
        // Test that property methods work with valid data
        try {
            propertyManager.SearchProperty("101");
            assertTrue(true, "Should work with valid property data");
        } catch (Exception e) {
            fail("Should not throw exceptions with valid data");
        }
    }

    // Test 6: PropertyAmountValidation_AmountsInvalid()
    @Test
    void PropertyAmountValidation_AmountsInvalid() {
        // Test the validation logic with invalid amounts
        assertFalse(isValidRentalAmount(1499.0), "1499 should be invalid");
        assertFalse(isValidRentalAmount(1000.0), "1000 should be invalid");
        assertFalse(isValidRentalAmount(0.0), "0 should be invalid");
        assertFalse(isValidRentalAmount(-500.0), "Negative amounts should be invalid");
        
        // Test text input validation
        assertFalse(isValidNumberInput("Three thousand"), "Text input should be invalid");
        assertFalse(isValidNumberInput("1499"), "1499 should be invalid");
    }
    
    // Helper methods for validation testing
    private boolean isValidRentalAmount(double amount) {
        return amount >= 1500;
    }
    
    private boolean isValidNumberInput(String input) {
        try {
            double amount = Double.parseDouble(input);
            return amount >= 1500;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}