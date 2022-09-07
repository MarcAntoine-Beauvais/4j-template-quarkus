package com.me.my_application;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;


@QuarkusTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MyApplicationTest {

    @BeforeAll
    public void setup() {
    }

    @Test
    public void getTest() {
    }

}
