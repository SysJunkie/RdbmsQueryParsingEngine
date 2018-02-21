package edu.buffalo.www.cse4562.operator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import edu.buffalo.www.cse4562.model.SchemaManager;
import edu.buffalo.www.cse4562.model.TableSchema;
import edu.buffalo.www.cse4562.model.Tuple;
import edu.buffalo.www.cse4562.model.Tuple.ColumnCell;
import edu.buffalo.www.cse4562.util.CollectionUtils;
import net.sf.jsqlparser.statement.create.table.ColumnDefinition;
/**
 * This operator is responsible for adding Alias information whenever its passed
 * with a subselect.
 *
 * @author varunjai
 *
 */
public class SubSelectOperator implements Operator {

  private final String alias;

  public SubSelectOperator(String alias) {
    this.alias = alias;
  }

  @Override
  public Collection<Tuple> process(Collection<Tuple> tuples) throws Throwable {

    // empty check
    if (CollectionUtils.areTuplesEmpty(tuples)) {
      return tuples;
    }

    // add table entry in Schema Manager
    final Iterator<Tuple> tupleItr = tuples.iterator();
    final List<ColumnDefinition> columnDefinitions = new ArrayList<>();
    final Tuple tuple = tupleItr.next();

    for (final ColumnCell columnCell : tuple.getColumnCells()) {
      final ColumnDefinition columnDefinition = new ColumnDefinition();
      columnDefinition.setColumnName(SchemaManager.getColumnNameById(
          columnCell.getTableId(), columnCell.getColumnId()));
      columnDefinitions.add(columnDefinition);
    } // for

    SchemaManager.addTableSchema(this.alias,
        new TableSchema(this.alias, columnDefinitions));

    // update table id to alias name in each column of the returned tuples
    for (final Tuple tupleVal : tuples) {
      for (final ColumnCell columnCell : tupleVal.getColumnCells()) {
        
        // update column's id and table id
        columnCell.setColumnId(SchemaManager.getColumnIdByTableId(
            SchemaManager.getTableId(this.alias),
            SchemaManager.getColumnNameById(columnCell.getTableId(),
                columnCell.getColumnId())));

        columnCell.setTableId(SchemaManager.getTableId(this.alias));
      }
    }

    return tuples;
  }

  public String getAlias() {
    return alias;
  }

}
