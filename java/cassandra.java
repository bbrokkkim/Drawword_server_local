public class cassandra {
    public static void main(String[] args) throws Exception {
        String keyspaceName = "Keyspace1";
        String columnFamilyName = "Standard1";
        String serverAddress = "localhost:9160";

        cluster cluster = HFactory.getOrCreateCluster("Cluster-Name", serverAddress);
        Keyspace keyspace = HFactory.createKeyspace(keyspaceName, cluster);
        try {
            // Mutation
            Mutator mutator = HFactory.createMutator(keyspace, StringSerializer.get());
            mutator.insert("id-1", columnFamilyName, HFactory.createStringColumn("Animesh", "killo"));

            // Look up the same column
            ColumnQuery columnQuery = HFactory.createStringColumnQuery(keyspace);
            columnQuery.setColumnFamily(columnFamilyName).setKey("id-1").setName("Animesh");
            QueryResult result = columnQuery.execute();
            System.out.println("Readumn from cassandra: " + result.get());
        } catch (HectorException e) {
            e.printStackTrace();
        }

    }
}