package org.example;

import org.example.Pool.Connection;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PoolTest {

    @Test
    public void test() {
        Pool pool = new Pool(3);
        Connection connection1 = pool.getConnection();
        assertEquals(0, connection1.getId());
        Connection connection2 = pool.getConnection();
        assertEquals(1, connection2.getId());
        Connection connection3 = pool.getConnection();
        assertEquals(2, connection3.getId());
        try {
            pool.getConnection();
        } catch (RuntimeException e) {
            assertEquals("no connections left", e.getMessage());
        }
        connection2.close();
        assertEquals(1, pool.getConnection().getId());
    }

}
