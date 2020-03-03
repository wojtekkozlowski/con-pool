package org.example;

public class Pool {


    public Pool(int maxSize) {

    }

    // make sure it's thread safe
    // make sure it respects the max size
    // closed connection is not closed but returned to the pool
    // it should return an open connection if available/null or exception otherwise
    // call to Connection#close should return the connection to the pool
    public Conection getConnection() {
        return new Conection() {

            @Override
            public void close() {
                // return to the pool here
            }
        };
    }


}
