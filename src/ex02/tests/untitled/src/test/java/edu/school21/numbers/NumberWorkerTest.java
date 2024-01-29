package edu.school21.numbers;

import edu.school21.numbers.exception.IllegalNumberException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

public class NumberWorkerTest {


    private final NumberWorker numberWorker = new NumberWorker();


    @ParameterizedTest(name = "{index} - {0} is a prime")
    @ValueSource(ints = {29,3,7})
    void isPrimeForPrimes(int number) {
        assertTrue(numberWorker.isPrime(number));
    }

    @ParameterizedTest(name = "{index} - {0} is a composite")
    @ValueSource(ints = {10,20,1112})
    void isPrimeForNotPrimes(int number) {
        assertFalse(numberWorker.isPrime(number));
    }

    @ParameterizedTest(name = "{index} - {0} not valid")
    @ValueSource(ints = {-1,0,1})
    void isPrimeForIncorrectNumbers(int number) {
        assertThrows(IllegalNumberException.class, () -> numberWorker.isPrime(number));
    }

    @ParameterizedTest(name = "Check sum of {0} is {1}")
    @CsvFileSource(resources = "/data.csv",numLinesToSkip = 0,delimiter = '.')
    void checkSumOfNumber(int number,int sum) {
        assertEquals(sum,numberWorker.digitSum(number));
    }

}
