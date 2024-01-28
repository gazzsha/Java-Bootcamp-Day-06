package edu.school21.numbers;

import edu.school21.numbers.exception.IllegalNumberException;

import java.util.stream.IntStream;

public class NumberWorker {

    public boolean isPrime(int number) {
        if (number < 2 ) throw new IllegalNumberException();
        for (int i = 2; i < Math.sqrt(number); i++) {
            if (number % i == 0) return false;
        }
        return true;
    }

    public int digitSum(int number) {
        return String.valueOf(number).chars().map( x -> x - '0').sum();
    }
}
