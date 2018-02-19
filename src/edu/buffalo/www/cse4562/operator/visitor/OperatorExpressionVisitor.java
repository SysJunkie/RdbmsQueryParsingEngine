package edu.buffalo.www.cse4562.operator.visitor;

import java.sql.SQLException;
import java.util.Map;
import java.util.TreeMap;

import edu.buffalo.www.cse4562.model.SchemaManager;
import edu.buffalo.www.cse4562.model.Tuple;
import edu.buffalo.www.cse4562.model.Tuple.ColumnCell;
import edu.buffalo.www.cse4562.util.Evaluator;
import net.sf.jsqlparser.expression.AllComparisonExpression;
import net.sf.jsqlparser.expression.AnyComparisonExpression;
import net.sf.jsqlparser.expression.BooleanValue;
import net.sf.jsqlparser.expression.CaseExpression;
import net.sf.jsqlparser.expression.DateValue;
import net.sf.jsqlparser.expression.DoubleValue;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.ExpressionVisitor;
import net.sf.jsqlparser.expression.Function;
import net.sf.jsqlparser.expression.InverseExpression;
import net.sf.jsqlparser.expression.JdbcParameter;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.NullValue;
import net.sf.jsqlparser.expression.PrimitiveValue;
import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.expression.TimeValue;
import net.sf.jsqlparser.expression.TimestampValue;
import net.sf.jsqlparser.expression.WhenClause;
import net.sf.jsqlparser.expression.operators.arithmetic.Addition;
import net.sf.jsqlparser.expression.operators.arithmetic.BitwiseAnd;
import net.sf.jsqlparser.expression.operators.arithmetic.BitwiseOr;
import net.sf.jsqlparser.expression.operators.arithmetic.BitwiseXor;
import net.sf.jsqlparser.expression.operators.arithmetic.Concat;
import net.sf.jsqlparser.expression.operators.arithmetic.Division;
import net.sf.jsqlparser.expression.operators.arithmetic.Multiplication;
import net.sf.jsqlparser.expression.operators.arithmetic.Subtraction;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.conditional.OrExpression;
import net.sf.jsqlparser.expression.operators.relational.Between;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.expression.operators.relational.ExistsExpression;
import net.sf.jsqlparser.expression.operators.relational.GreaterThan;
import net.sf.jsqlparser.expression.operators.relational.GreaterThanEquals;
import net.sf.jsqlparser.expression.operators.relational.InExpression;
import net.sf.jsqlparser.expression.operators.relational.IsNullExpression;
import net.sf.jsqlparser.expression.operators.relational.LikeExpression;
import net.sf.jsqlparser.expression.operators.relational.Matches;
import net.sf.jsqlparser.expression.operators.relational.MinorThan;
import net.sf.jsqlparser.expression.operators.relational.MinorThanEquals;
import net.sf.jsqlparser.expression.operators.relational.NotEqualsTo;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.select.SelectExpressionItem;
import net.sf.jsqlparser.statement.select.SubSelect;

/**
 * Each operator will pass a tuple to the visitor with an
 * {@link SelectExpressionItem} that it processes and returns the value for that
 * expression.
 * 
 * Here it will also have an instance of a Map of column name to
 * {@link ColumnCell}.
 * 
 * An operator will invoke the visitor with expression and tuple, visitor will
 * process it be it single column expression or a binary expression, for binary
 * expression process it via the {@link Evaluator} and return result as a
 * {@link ColumnCell}.
 * 
 * @author varunjai
 *
 */
public class OperatorExpressionVisitor
    implements
      ExpressionVisitor,
      OperatorVisitor {

  public Evaluator evaluator;
  public Tuple currentTuple;
  private Map<String, ColumnCell> column2ColumnCell = new TreeMap<>();
  private ColumnCell outputColumnCell;

  /**
   * 
   */
  public OperatorExpressionVisitor() {
    this.evaluator = new Evaluator();
  }

  @Override
  public void visit(NullValue arg0) {
    // TODO Auto-generated method stub

  }

  @Override
  public void visit(Function arg0) {
    // TODO Auto-generated method stub

  }

  @Override
  public void visit(InverseExpression arg0) {
    // TODO Auto-generated method stub

  }

  @Override
  public void visit(JdbcParameter arg0) {
    // TODO Auto-generated method stub

  }

  @Override
  public void visit(DoubleValue arg0) {
    // TODO Auto-generated method stub

  }

  @Override
  public void visit(LongValue arg0) {
    // TODO Auto-generated method stub

  }

  @Override
  public void visit(DateValue arg0) {
    // TODO Auto-generated method stub

  }

  @Override
  public void visit(TimeValue arg0) {
    // TODO Auto-generated method stub

  }

  @Override
  public void visit(TimestampValue arg0) {
    // TODO Auto-generated method stub

  }

  @Override
  public void visit(BooleanValue arg0) {
    // TODO Auto-generated method stub

  }

  @Override
  public void visit(StringValue arg0) {
    // TODO Auto-generated method stub

  }

  @Override
  public void visit(Addition addition) {

    // null check
    if (null == addition) {
      return;
    }
    // evaluate LHS and RHS
    addition.getLeftExpression().accept(this);
    addition.getRightExpression().accept(this);

    PrimitiveValue cellValue = null;
    // pass column values map to evaluator for processing.
    evaluator.setColumn2ColumnCell(this.column2ColumnCell);
    try {
      cellValue = evaluator.eval(addition);
    } catch (final SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    // if null, no-op
    if (null == cellValue) {
      return;
    }

    // TODO: how to set aliases???
    this.column2ColumnCell.put(addition.getStringExpression(),
        new ColumnCell(cellValue));
    // creating new instance, as we will be destroying map and wasn't sure if
    // it will destroy the object as well
    this.outputColumnCell = new ColumnCell(cellValue);
  }

  @Override
  public void visit(Division arg0) {
    // TODO Auto-generated method stub

  }

  @Override
  public void visit(Multiplication arg0) {
    // TODO Auto-generated method stub

  }

  @Override
  public void visit(Subtraction arg0) {
    // TODO Auto-generated method stub

  }

  @Override
  public void visit(AndExpression arg0) {
    // TODO Auto-generated method stub

  }

  @Override
  public void visit(OrExpression arg0) {
    // TODO Auto-generated method stub

  }

  @Override
  public void visit(Between arg0) {
    // TODO Auto-generated method stub

  }

  @Override
  public void visit(EqualsTo arg0) {
    // TODO Auto-generated method stub

  }

  @Override
  public void visit(GreaterThan arg0) {
    // TODO Auto-generated method stub

  }

  @Override
  public void visit(GreaterThanEquals arg0) {
    // TODO Auto-generated method stub

  }

  @Override
  public void visit(InExpression arg0) {
    // TODO Auto-generated method stub

  }

  @Override
  public void visit(IsNullExpression arg0) {
    // TODO Auto-generated method stub

  }

  @Override
  public void visit(LikeExpression arg0) {
    // TODO Auto-generated method stub

  }

  @Override
  public void visit(MinorThan arg0) {
    // TODO Auto-generated method stub

  }

  @Override
  public void visit(MinorThanEquals arg0) {
    // TODO Auto-generated method stub

  }

  @Override
  public void visit(NotEqualsTo arg0) {
    // TODO Auto-generated method stub

  }

  @Override
  public void visit(Column column) {
    // return null
    if (this.currentTuple.isEmpty()) {
      return;
    }

    for (final ColumnCell columnCell : this.currentTuple.getColumnCells()) {

      Integer tableId = SchemaManager.getTableId(column.getColumnName());
      tableId = tableId == null ? columnCell.getTableId() : tableId;

      if (SchemaManager.getColumnIdByTableId(tableId,
          column.getColumnName()) == columnCell.getColumnId()) {
        this.column2ColumnCell.put(column.getWholeColumnName(), columnCell);
        this.outputColumnCell = columnCell;
      } // if
    } // for
  }

  @Override
  public void visit(SubSelect arg0) {
    // TODO Auto-generated method stub

  }

  @Override
  public void visit(CaseExpression arg0) {
    // TODO Auto-generated method stub

  }

  @Override
  public void visit(WhenClause arg0) {
    // TODO Auto-generated method stub

  }

  @Override
  public void visit(ExistsExpression arg0) {
    // TODO Auto-generated method stub

  }

  @Override
  public void visit(AllComparisonExpression arg0) {
    // TODO Auto-generated method stub

  }

  @Override
  public void visit(AnyComparisonExpression arg0) {
    // TODO Auto-generated method stub

  }

  @Override
  public void visit(Concat arg0) {
    // TODO Auto-generated method stub

  }

  @Override
  public void visit(Matches arg0) {
    // TODO Auto-generated method stub

  }

  @Override
  public void visit(BitwiseAnd arg0) {
    // TODO Auto-generated method stub

  }

  @Override
  public void visit(BitwiseOr arg0) {
    // TODO Auto-generated method stub

  }

  @Override
  public void visit(BitwiseXor arg0) {
    // TODO Auto-generated method stub

  }

  @Override
  public ColumnCell getValue(Tuple tuple, Expression expression) {
    this.currentTuple = tuple;

    expression.accept(this);
    return this.outputColumnCell;
  }
}
