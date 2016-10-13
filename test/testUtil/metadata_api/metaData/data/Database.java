package testUtil.metadata_api.metaData.data;

import testUtil.metadata_api.metaData.data.column.Column;
import testUtil.metadata_api.metaData.data.column.ImportedColumn;
import testUtil.metadata_api.metaData.data.column.RelatedColumn;
import testUtil.metadata_api.metaData.data.type.TypeDescription;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Database {

    private Map<String, Table> tables;
    private NavigableSet<Table> orderedTables;

    public Map<String, Table> getTables() {
        return tables;
    }

    protected void setTables(Map<String, Table> tables) {
        this.tables = tables;

    }

    public NavigableSet<Table> getOrderedTables() {
        return orderedTables;
    }

    protected void setOrderedTables(NavigableSet<Table> orderedTables) {
        this.orderedTables = orderedTables;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Database{");
        sb.append("tables=").append(tables.entrySet().stream()
                                          .map(s -> s.getKey() + "=" + s.getValue())
                                          .collect(Collectors.joining("\n", "\n", "\n")));
        sb.append('}');
        return sb.toString();
    }

    public static class DBBuilder{

        private Database database;

        public Database buildDatabase(List<Table> tablesList){
            Map<String, Table> collect = tablesList.stream()
                                                   .collect(Collectors.toMap(Table::getTableName,
                                                           Function.identity()));
            database = new Database();
            database.setTables(collect);
            fullfillAll(database);
            return database;
        }

        private void fullfillAll(Database database){
            interconnectColumns(database);
            orderTables(database);
        }

        private void interconnectColumns(Database database){

            for(Map.Entry<String, Table> entry : database.getTables().entrySet()){
                String key = entry.getKey();
                Table value = entry.getValue();
                Stream<RelatedColumn> columnStream
                        = Stream.concat(value.getImportedColumns().values().stream().map(c -> (RelatedColumn) c),

                        value.getExportedColumns().values().stream().map(c ->(RelatedColumn) c));

                Consumer<RelatedColumn> interrconnect = new Consumer<RelatedColumn>() {
                    @Override
                    public void accept(RelatedColumn p) {
                        String refTable = p.getRefTable();
                        Table related = getTableByName(refTable);
                        String refColumnName = p.getRefColumnName();
                        Column refColumn = getColumnByName(related, refColumnName);

                        p.setRefColumn(refColumn);
                    }
                };
                columnStream.forEach(interrconnect);

                value.getImportedColumns().values().forEach(new Consumer<ImportedColumn>() {
                    @Override
                    public void accept(ImportedColumn importedColumn) {
                        TypeDescription type = importedColumn.getRefColumn().getType();
                        importedColumn.setType(type);
                    }
                });
            }
        }



        private void orderTables(Database database){

            Collection<Table> values = database.getTables().values();
            Deque<Table> tabInitial = new ArrayDeque<>();
            tabInitial.addAll(values);

            NavigableSet<Table> ordered = new TreeSet<>();

            tabInitial.stream().filter(table -> table.getImportedColumns().size() == 0).forEach(table -> {
                ordered.add(table);
                table.setOrderToFill(0);
            });

            tabInitial.removeAll(ordered);
            Table poll;
            int index = 1;
            while ((poll = tabInitial.poll()) != null){

                final Table current = poll;

                Predicate<ImportedColumn> predicate = new Predicate<ImportedColumn>() {
                    @Override
                    public boolean test(ImportedColumn column) {
                        String tableName = column.getRefTable();
                        Table tableByName = getTableByName(tableName);
                        if (tableByName.equals(current)){
                            return true;
                        }
                        return ordered.contains(tableByName);
                    }
                };

                boolean collected = poll.getImportedColumns()
                                        .values()
                                        .stream()
                                        .allMatch(predicate);
                if (collected){
                    ordered.add(poll);
                    poll.setOrderToFill(index);
                    index++;
                } else {
                    tabInitial.offerLast(poll);
                }
            }
            database.setOrderedTables(ordered);
        }


        private Table getTableByName(String name){
            return database.getTables().get(name);
        }
        private Column getColumnByName(Table related, String refColumnName) {
            return related.getColumns().get(refColumnName);
        }
    }

}
