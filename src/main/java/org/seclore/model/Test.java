
package org.seclore.model;

public class Test {

    private String ids;

    public Test(String id){
        this.ids = id;
    }

    public void TestMethodWithNamingFailure() {
        // does nothing
    }

    public void testMethodWithLoggerIssue() {
        System.out.println("test");
    }

    public static void exampleMethodForDeadCode() {
        System.out.println("Start of method");

        int x = 10;

        if (x > 5) {
            System.out.println("x is greater than 5");
            return; // This return statement makes the following code unreachable
        }

        // Dead code - This part will never execute
        System.out.println("This line is unreachable");

        int y = 100;  // Dead variable - never used
        y++;
    }

}