package com.holytransaction;

import org.junit.Test;

public class NoSuchApiFunctionExceptionTest {

    @Test(expected = NoSuchApiFunctionException.class)
    public void NoSuchApiFunctionExceptionTestNoMessage() throws NoSuchApiFunctionException {

        throw new NoSuchApiFunctionException();

    }

    @Test(expected = NoSuchApiFunctionException.class)
    public void NoSuchApiFunctionExceptionTestMessage() throws NoSuchApiFunctionException {

        throw new NoSuchApiFunctionException("test");
    }

}
