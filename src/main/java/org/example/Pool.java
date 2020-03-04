package org.example;

// make sure it's thread safe
// make sure it respects the max size
// closed connection is not closed but returned to the pool
// it should return an open connection if available/null or exception otherwise
// call to Connection#close should return the connection to the pool

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Pool {

    private final List<PoolConnection> poolConnections = new ArrayList<>();

    public Pool(int maxSize) {
        for (int i = 0; i < maxSize; i++) {
            poolConnections.add(new PoolConnection(new Connection(this, i), true));
        }
    }

    private void closeConnection(final Connection connection) {
        poolConnections.stream().filter(pc -> pc.connection.equals(connection)).findFirst().get().setFree(true);
    }

    public Connection getConnection() {
        Optional<PoolConnection> poolConnection = poolConnections.stream().filter(PoolConnection::isFree).findAny();
        if(poolConnection.isPresent()){
            poolConnection.get().setFree(false);
            return poolConnection.get().connection;
        }  else {
            throw new RuntimeException("no connections left");
        }

    }

    private static class PoolConnection {
        private final Connection connection;
        private Boolean free;

        public PoolConnection(Connection connection, boolean free) {
            this.connection = connection;
            this.free = free;
        }

        public Connection getConnection() {
            return connection;
        }

        public boolean isFree() {
            return free;
        }

        public void setFree(Boolean free) {
            this.free = free;
        }
    }

    static class Connection {

        private final Pool pool;
        private final int id;

        public Connection(Pool pool, int id) {
            this.pool = pool;
            this.id = id;
        }

        void close() {
            pool.closeConnection(this);
        }

        public int getId(){
            return id;
        }

        @Override
        public String toString() {
            return "Connection{" +
                    "id=" + id +
                    '}';
        }
    }

}
