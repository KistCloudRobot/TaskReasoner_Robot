/* Generated By:JavaCC: Do not edit this line. UtilityFunctionParser.java */
package kr.ac.uos.ai.robot.intelligent.taskReasoner.utility.parser;

import kr.ac.uos.ai.robot.intelligent.taskReasoner.utility.model.*;
public class UtilityFunctionParser implements UtilityFunctionParserConstants {
  public static void main(String args []) throws ParseException
  {
    UtilityFunctionParser parser = new UtilityFunctionParser(System.in);
    while (true)
    {
      try
      {
        FormulaExpression result = parser.parse();
      }
      catch (Exception e)
      {
        System.out.println("NOK.");
        System.out.println(e.getMessage());
      }
      catch (Error e)
      {
        System.out.println("Oops.");
        System.out.println(e.getMessage());
        break;
      }
    }
  }

  final public FormulaExpression parse() throws ParseException {
  FormulaExpression formula;
    formula = function();
    {if (true) return formula;}
    throw new Error("Missing return statement in function");
  }

  final public FormulaExpression function() throws ParseException {
  FormulaExpression left;
  FormulaExpression right;
  Token operatorToken;
  OperatorExpression operatorExpression;
    left = term();
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case PLUS:
    case MINUS:
      operatorToken = expressionOperator();
      right = term();
      operatorExpression = new OperatorExpression();
      operatorExpression.setOperator(operatorToken.image);
      operatorExpression.setRight(right);
      operatorExpression.setLeft(left);
      {if (true) return operatorExpression;}
      break;
    default:
      jj_la1[0] = jj_gen;
      ;
    }
    {if (true) return left;}
    throw new Error("Missing return statement in function");
  }

  final public Token expressionOperator() throws ParseException {
 Token t;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case PLUS:
      t = jj_consume_token(PLUS);
      break;
    case MINUS:
      t = jj_consume_token(MINUS);
      break;
    default:
      jj_la1[1] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    {if (true) return t;}
    throw new Error("Missing return statement in function");
  }

  final public FormulaExpression term() throws ParseException {
  FormulaExpression left;
  FormulaExpression right;
  Token operatorToken;
  OperatorExpression operatorExpression;
    left = unary();
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case MULTIPLY:
    case DIVIDE:
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case DIVIDE:
        operatorToken = jj_consume_token(DIVIDE);
        break;
      case MULTIPLY:
        operatorToken = jj_consume_token(MULTIPLY);
        break;
      default:
        jj_la1[2] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
      //operatorToken = termOperator() 
          right = term();
      operatorExpression = new OperatorExpression();
      operatorExpression.setOperator(operatorToken.image);
      operatorExpression.setRight(right);
      operatorExpression.setLeft(left);
      {if (true) return operatorExpression;}
      break;
    default:
      jj_la1[3] = jj_gen;
      ;
    }
    {if (true) return left;}
    throw new Error("Missing return statement in function");
  }

  final public Token termOperator() throws ParseException {
  Token t;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case MULTIPLY:
      t = jj_consume_token(MULTIPLY);
      break;
    case DIVIDE:
      t = jj_consume_token(DIVIDE);
    {if (true) return t;}
      break;
    default:
      jj_la1[4] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
  }

  final public FormulaExpression unary() throws ParseException {
  FormulaExpression left;
  FormulaExpression right;
  Token operatorToken;
  OperatorExpression operatorExpression = null;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case MINUS:
      jj_consume_token(MINUS);
    operatorExpression = new OperatorExpression();
    operatorExpression.setOperator("-");
    ValueExpression v = new ValueExpression();
    v.setValue(0f);
    operatorExpression.setLeft(v);
      break;
    default:
      jj_la1[5] = jj_gen;
      ;
    }
    right = element();
    if(operatorExpression!=null) {
      operatorExpression.setRight(right);
      {if (true) return operatorExpression;}
        } else {
          {if (true) return right;}
        }
    throw new Error("Missing return statement in function");
  }

  final public FormulaExpression expression() throws ParseException {
  FormulaExpression left;
  FormulaExpression right;
  Token operatorToken;
  OperatorExpression operatorExpression;
    left = term();
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case PLUS:
    case MINUS:
      operatorToken = expressionOperator();
      right = term();
      operatorExpression = new OperatorExpression();
      operatorExpression.setOperator(operatorToken.image);
      operatorExpression.setRight(right);
      operatorExpression.setLeft(left);
      {if (true) return operatorExpression;}
      break;
    default:
      jj_la1[6] = jj_gen;
      ;
    }
    {if (true) return left;}
    throw new Error("Missing return statement in function");
  }

  final public FormulaExpression element() throws ParseException {
  FormulaExpression f;
  ValueExpression value;
  VariableExpression var;
  Token t;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case VALUE:
      t = jj_consume_token(VALUE);
                value = new ValueExpression();
                value.setValue(Float.parseFloat(t.image));
                {if (true) return value;}
      break;
    case CONSTANT:
      t = jj_consume_token(CONSTANT);
                value = new ValueExpression();
                value.setValue(Float.parseFloat(t.image));
                {if (true) return value;}
      break;
    case IDENTIFIER:
      var = variable();
      {if (true) return var;}
      break;
    case 15:
      jj_consume_token(15);
      f = expression();
      jj_consume_token(16);
      {if (true) return f;}
      break;
    default:
      jj_la1[7] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
  }

  final public VariableExpression variable() throws ParseException {
  Token t;
  VariableExpression v = new VariableExpression();
    t = jj_consume_token(IDENTIFIER);
    v.setIdentifier(t.image);
    {if (true) return v;}
    throw new Error("Missing return statement in function");
  }

  /** Generated Token Manager. */
  public UtilityFunctionParserTokenManager token_source;
  SimpleCharStream jj_input_stream;
  /** Current token. */
  public Token token;
  /** Next token. */
  public Token jj_nt;
  private int jj_ntk;
  private int jj_gen;
  final private int[] jj_la1 = new int[8];
  static private int[] jj_la1_0;
  static {
      jj_la1_init_0();
   }
   private static void jj_la1_init_0() {
      jj_la1_0 = new int[] {0x60,0x60,0x180,0x180,0x180,0x40,0x60,0x9a00,};
   }

  /** Constructor with InputStream. */
  public UtilityFunctionParser(java.io.InputStream stream) {
     this(stream, null);
  }
  /** Constructor with InputStream and supplied encoding */
  public UtilityFunctionParser(java.io.InputStream stream, String encoding) {
    try { jj_input_stream = new SimpleCharStream(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source = new UtilityFunctionParserTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 8; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(java.io.InputStream stream) {
     ReInit(stream, null);
  }
  /** Reinitialise. */
  public void ReInit(java.io.InputStream stream, String encoding) {
    try { jj_input_stream.ReInit(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 8; i++) jj_la1[i] = -1;
  }

  /** Constructor. */
  public UtilityFunctionParser(java.io.Reader stream) {
    jj_input_stream = new SimpleCharStream(stream, 1, 1);
    token_source = new UtilityFunctionParserTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 8; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(java.io.Reader stream) {
    jj_input_stream.ReInit(stream, 1, 1);
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 8; i++) jj_la1[i] = -1;
  }

  /** Constructor with generated Token Manager. */
  public UtilityFunctionParser(UtilityFunctionParserTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 8; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(UtilityFunctionParserTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 8; i++) jj_la1[i] = -1;
  }

  private Token jj_consume_token(int kind) throws ParseException {
    Token oldToken;
    if ((oldToken = token).next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    if (token.kind == kind) {
      jj_gen++;
      return token;
    }
    token = oldToken;
    jj_kind = kind;
    throw generateParseException();
  }


/** Get the next Token. */
  final public Token getNextToken() {
    if (token.next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    jj_gen++;
    return token;
  }

/** Get the specific Token. */
  final public Token getToken(int index) {
    Token t = token;
    for (int i = 0; i < index; i++) {
      if (t.next != null) t = t.next;
      else t = t.next = token_source.getNextToken();
    }
    return t;
  }

  private int jj_ntk() {
    if ((jj_nt=token.next) == null)
      return (jj_ntk = (token.next=token_source.getNextToken()).kind);
    else
      return (jj_ntk = jj_nt.kind);
  }

  private java.util.List<int[]> jj_expentries = new java.util.ArrayList<int[]>();
  private int[] jj_expentry;
  private int jj_kind = -1;

  /** Generate ParseException. */
  public ParseException generateParseException() {
    jj_expentries.clear();
    boolean[] la1tokens = new boolean[17];
    if (jj_kind >= 0) {
      la1tokens[jj_kind] = true;
      jj_kind = -1;
    }
    for (int i = 0; i < 8; i++) {
      if (jj_la1[i] == jj_gen) {
        for (int j = 0; j < 32; j++) {
          if ((jj_la1_0[i] & (1<<j)) != 0) {
            la1tokens[j] = true;
          }
        }
      }
    }
    for (int i = 0; i < 17; i++) {
      if (la1tokens[i]) {
        jj_expentry = new int[1];
        jj_expentry[0] = i;
        jj_expentries.add(jj_expentry);
      }
    }
    int[][] exptokseq = new int[jj_expentries.size()][];
    for (int i = 0; i < jj_expentries.size(); i++) {
      exptokseq[i] = jj_expentries.get(i);
    }
    return new ParseException(token, exptokseq, tokenImage);
  }

  /** Enable tracing. */
  final public void enable_tracing() {
  }

  /** Disable tracing. */
  final public void disable_tracing() {
  }

}
