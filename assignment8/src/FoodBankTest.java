import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class FoodBankTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @Test
    //@GradedTest(name="FoodBank_ValidObjectTest()", max_score=1.25)
    public void FoodBank_ValidObjectTest(){
        FoodBank testFoodBank = new FoodBank();
        //Test number of instance variables
        @SuppressWarnings("rawtypes")
        Class c = testFoodBank.getClass();
        try {
            assertEquals(
                    "You must only have the instance variables specified. When looking at the number of instance variables we",
                    1, c.getDeclaredFields().length);
        }
        catch (Exception e) {
            fail("Something weird went wrong");
        }

        //Test instance variable properties
        instanceVariablePrivate("food",testFoodBank);
        instanceVariableStatic("food",testFoodBank);
        instanceVariableCorrectType("food", int.class,testFoodBank);

        //Test default constructor
        testVariable("food",testFoodBank,0,"When checking the value of food we after calling the default constructor we");

        //Test for correct functions

        try {
            Method x = c.getDeclaredMethod("giveFood",int.class);
            assertTrue("When checking the return type of giveFood we expected void but it wasn't :( ",x.getReturnType() == void.class);

            x = c.getDeclaredMethod("takeFood",int.class);
            assertTrue("When checking the return type of takeFood we expected void but it wasn't :( ",x.getReturnType() == void.class);

        } catch (NoSuchMethodException e) {
            fail("Error! Missing a method: " + e.getLocalizedMessage());
        }

    }

    @Test
    //@GradedTest(name="FoodProducer_ValidObjectTest()", max_score=1.25)
    public void FoodProducer_ValidObjectTest(){
        FoodProducer testFoodProducer = new FoodProducer(null);
        //Test number of instance variables
        @SuppressWarnings("rawtypes")
        Class c = testFoodProducer.getClass();
        try {
            assertTrue(
                    "You must only have the instance variables specified. When looking at the number of instance variables we expected between a value between 2 and 1 but found a different value",
                    1 == c.getDeclaredFields().length || 2 == c.getDeclaredFields().length);
        }
        catch (Exception e) {
            fail("Something weird went wrong");
        }

        //Test instance variable properties
        instanceVariablePrivate("bank",testFoodProducer);
        instanceVariableStatic("bank",testFoodProducer);
        instanceVariableCorrectType("bank", FoodBank.class,testFoodProducer);

        //Test subclasses Thread
        assertSame("When checking the superclass of FoodProducer we", c.getSuperclass(), Thread.class);

        //Test parameterized constructor
        try {
            c.getDeclaredConstructor(FoodBank.class);
        } catch (NoSuchMethodException e) {
            fail("Error! Missing a constructor: " + e.getLocalizedMessage());
        }

        //Test for correct functions
        try {
            Method x = c.getDeclaredMethod("run");
            assertTrue("When checking the return type of run we expected void but it wasn't :( ",x.getReturnType() == void.class);

        } catch (NoSuchMethodException e) {
            fail("Error! Missing a method: " + e.getLocalizedMessage());
        }
    }

    @Test
    //@GradedTest(name="FoodConsumer_ValidObjectTest()", max_score=1.25)
    public void FoodConsumer_ValidObjectTest(){
        FoodConsumer testFoodConsumer = new FoodConsumer(null);
        //Test number of instance variables
        @SuppressWarnings("rawtypes")
        Class c = testFoodConsumer.getClass();
        try {
            assertTrue(
                    "You must only have the instance variables specified. When looking at the number of instance variables we expected between a value between 2 and 1 but found a different value",
                    1 == c.getDeclaredFields().length || 2 == c.getDeclaredFields().length);
        }
        catch (Exception e) {
            fail("Something weird went wrong");
        }

        //Test instance variable properties
        instanceVariablePrivate("bank",testFoodConsumer);
        instanceVariableStatic("bank",testFoodConsumer);
        instanceVariableCorrectType("bank", FoodBank.class,testFoodConsumer);

        //Test subclasses Thread
        assertSame("When checking the superclass of FoodConsumer we", c.getSuperclass(), Thread.class);

        //Test parameterized constructor
        try {
            c.getDeclaredConstructor(FoodBank.class);
        } catch (NoSuchMethodException e) {
            fail("Error! Missing a constructor: " + e.getLocalizedMessage());
        }

        //Test for correct functions
        try {
            Method x = c.getDeclaredMethod("run");
            assertTrue("When checking the return type of run we expected void but it wasn't :( ",x.getReturnType() == void.class);

        } catch (NoSuchMethodException e) {
            fail("Error! Missing a method: " + e.getLocalizedMessage());
        }
    }

    //Test that give food produces an output test
    @Test
    //@GradedTest(name="Test if FoodBank's giveFood() outputs to console", max_score=2.5)
    public void FoodBank_giveFoodOutputsToConsole(){
        outContent.reset();
        FoodBank testBank = new FoodBank();
        for(int i = 0; i < 100; i++){
            testBank.giveFood(i);
        }
        String[] rawOutput = outContent.toString().split("\n");
        assertTrue("Expected giveFood in FoodBank to output to the console but no output text was detected",rawOutput.length > 2);
        assertEquals("Expected giveFood in FoodBank to output to the console on each invocation but after calling giveFood 100 times and checking the number of output lines we,", 100, rawOutput.length);
    }

    //Test that take food produces an output test
    @Test
    //@GradedTest(name="Test if FoodBank's takeFood() outputs to console", max_score=2.5)
    public void FoodBank_takeFoodOutputsToConsole(){
        outContent.reset();
        FoodBank testBank = createFoodBank(100000);
        for(int i = 0; i < 100; i++){
            testBank.takeFood(i);
        }
        String[] rawOutput = outContent.toString().split("\n");
        assertTrue("Expected takeFood in FoodBank to output to the console but no output text was detected",rawOutput.length > 2);
        assertEquals("Expected takeFood in FoodBank to output to the console on each invocation but after calling takeFood 100 times and checking the number of output lines we,", 100, rawOutput.length);
    }

    //Test that the thread delay is correct by measuring the number of output statements
    @Test
    //@GradedTest(name="Test if FoodBank's takeFood() outputs to console", max_score=2.5)
    public void ProducerConsumer_TestThreadDelay() throws InterruptedException {
        FoodBank testBank = createFoodBank(0);
        FoodProducer producer = new FoodProducer(testBank);

        producer.start();
        //consumer.start();
        Thread.sleep(5000);
        producer.interrupt();
        //consumer.interrupt();

        Class c = testBank.getClass();
        Field field = null;
        try {
            field = c.getDeclaredField("food");
            field.setAccessible(true);
            Object fieldValue = field.get(testBank);
            int value = (int) fieldValue;
            if(value < 1300 || value > 4000){
                fail("After running a producer thread for 5 seconds the value of FoodBank was outside of the expected range. Is your delay 100 milliseconds? Are you adding food to the FoodBank?");
            }
        }catch (NoSuchFieldException e){
            fail("Is FoodBank passing all it's tests? If so something weird went wrong");
        }catch (IllegalAccessException e) {
            fail("Something weird went wrong" + e);
        }

        testBank = createFoodBank(10000);
        FoodConsumer consumer = new FoodConsumer(testBank);
        consumer.start();
        Thread.sleep(5000);
        consumer.interrupt();

        c = testBank.getClass();
        try {
            field = c.getDeclaredField("food");
            field.setAccessible(true);
            Object fieldValue = field.get(testBank);
            int value = (int) fieldValue;
            if(value < 6000 || value > 8700){
                fail("After running a consumer thread for 5 seconds the value of FoodBank was outside of the expected range. Is your delay 100 milliseconds? Are you removing food from the FoodBank?");
            }
        }catch (NoSuchFieldException e){
            fail("Is FoodBank passing all it's tests? If so something weird went wrong");
        }catch (IllegalAccessException e) {
            fail("Something weird went wrong");
        }


    }

    //Test for negative food bank 1 & 1
    @Test
    //@GradedTest(name="Test if FoodBank's bank instance variable ever drops below zero", max_score=2.5)
    public void ProducerConsumer_TestNegativeFoodBank(){
        outContent.reset();
        FoodBank testBank = createFoodBank(0);
        FoodProducer producer = new FoodProducer(testBank);
        FoodConsumer consumer = new FoodConsumer(testBank);

        Class c = testBank.getClass();
        Field field = null;
        try {
            field = c.getDeclaredField("food");
            field.setAccessible(true);

        }catch (NoSuchFieldException e){
            fail("Is FoodBank passing all it's tests? If so something weird went wrong");
        }

        consumer.start();
        producer.start();

        long startTime = System.currentTimeMillis();
        while (true){
            try {
                Object fieldValue = field.get(testBank);
                int value = (int) fieldValue;
                if(value < 0){
                    fail("The FoodBank went negative!");
                }
                if(System.currentTimeMillis() - startTime > 5000){
                    break;
                }
            } catch (IllegalAccessException e) {
                fail("Something weird went wrong");
            }
        }

        producer.interrupt();
        consumer.interrupt();

        String[] rawOutput = outContent.toString().split("\n");
        assertTrue("After starting a FoodProducer and a FoodConsumer thread we expected there to be console output but there was not. Are the foodbank methods outputting to the console?",rawOutput.length > 2);
    }

    //Test for negative food bank stress test
    @Test
    //@GradedTest(name="Test if FoodBank's bank instance variable ever drops below zero with multiple consumer threads", max_score=2.5)
    public void ProducerConsumer_TestNegativeFoodBankStressTest(){
        outContent.reset();
        FoodBank testBank = createFoodBank(0);
        FoodProducer producer = new FoodProducer(testBank);
        FoodConsumer consumer = new FoodConsumer(testBank);
        FoodConsumer consumer2 = new FoodConsumer(testBank);
        FoodConsumer consumer3 = new FoodConsumer(testBank);
        FoodConsumer consumer4 = new FoodConsumer(testBank);

        Class c = testBank.getClass();
        Field field = null;
        try {
            field = c.getDeclaredField("food");
            field.setAccessible(true);

        }catch (NoSuchFieldException e){
            fail("Is FoodBank passing all it's tests? If so something weird went wrong");
        }


        consumer.start();
        consumer2.start();
        consumer3.start();
        consumer4.start();
        producer.start();

        long startTime = System.currentTimeMillis();
        while (true){
            try {
                Object fieldValue = field.get(testBank);
                int value = (int) fieldValue;
                if(value < 0){
                    fail("The FoodBank went negative!");
                }
                if(System.currentTimeMillis() - startTime > 5000){
                    break;
                }
            } catch (IllegalAccessException e) {
                fail("Something weird went wrong");
            }
        }
        producer.interrupt();
        consumer.interrupt();
        consumer2.interrupt();
        consumer3.interrupt();
        consumer4.interrupt();

        String[] rawOutput = outContent.toString().split("\n");
        assertTrue("After starting a FoodProducer and several FoodConsumer threads we expected there to be console output but there was not. Are the foodbank methods outputting to the console?",rawOutput.length > 2);
    }


    @After
    public void cleanUpStreams() {
        System.setOut(null);
        System.setErr(null);
    }

    private void instanceVariablePrivate(String aField, Object testObject) {
        @SuppressWarnings("rawtypes")
        Class c = testObject.getClass();
        try {
            c.getDeclaredField(aField);

            assertTrue("You must make your instance variables private.", Modifier.isPrivate(c.getDeclaredField(aField).getModifiers()));

        } catch (NoSuchFieldException e) {
            fail("Could not find the " + e.getLocalizedMessage() + " instance variable");
        } catch (Exception e) {
            fail("Something weird went wrong");
        }
    }

    private void instanceVariableStatic(String aField, Object testObject) {
        @SuppressWarnings("rawtypes")
        Class c = testObject.getClass();
        try {
            c.getDeclaredField(aField);

            assertEquals("Your instance variables must NOT be static.", false,
                    Modifier.isStatic(c.getDeclaredField(aField).getModifiers()));

        } catch (NoSuchFieldException e) {
            fail("Could not find the " + e.getLocalizedMessage() + " instance variable");
        } catch (Exception e) {
            fail("Something weird went wrong");
        }
    }

    private void instanceVariableCorrectType(String aField, Class<?> aClass,  Object testObject) {
        @SuppressWarnings("rawtypes")
        Class c = testObject.getClass();
        try {
            c.getDeclaredField(aField);

            assertEquals("You must make the speed instance variable of type"+ aClass.toString() +".", aClass, c.getDeclaredField(aField).getType());

        } catch (NoSuchFieldException e) {
            fail("Could not find the " + e.getLocalizedMessage() + " instance variable");
        } catch (Exception e) {
            fail("Something weird went wrong");
        }
    }

    private FoodBank createFoodBank(int someFood){
        FoodBank testFoodBank = new FoodBank();
        @SuppressWarnings("rawtypes")
        Class c = testFoodBank.getClass();
        try {
            Field owner = c.getDeclaredField("food");
            owner.setAccessible(true);
            owner.set(testFoodBank, someFood);

        } catch (Exception e) {
            fail(e.toString());
        }
        return testFoodBank;
    }

    private void testVariable(String aField, Object testObject, Object expected, String message){
        @SuppressWarnings("rawtypes")
        Class c = testObject.getClass();
        try {
            Field field = c.getDeclaredField(aField);
            field.setAccessible(true);
            Object fieldValue = field.get(testObject);

            if(expected == null){
                assertNull(message,fieldValue);
            }
            //If class is a double we have a special Junit assert to run
            else if(expected.getClass().equals(Double.class)){
                double doubleFieldValue = (double) fieldValue;
                double doubleExpected = (double) expected;
                assertEquals(message, doubleExpected, doubleFieldValue, .01);
            }
            //Array of some kind yay
            else if(expected.getClass().isArray()){

            }
            else if(expected.getClass().equals(ArrayList.class)){

            }
            else{
                assertEquals(message, expected, fieldValue);
            }

        }
        catch (Exception e) {
            fail(e.toString());
        }
    }
}
