/* Generated By:JJTree: Do not edit this line. ASTPrimaryBooleanExpression.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package computeparser;

import computeparser.ASTPrimaryExpression.OpCodes;

public
class ASTPrimaryBooleanExpression extends SimpleNode {
	public enum OpCodes {
		EXPRESSION,
		BEFORE,
		AFTER
	};
	OpCodes opCode;
  public ASTPrimaryBooleanExpression(int id) {
    super(id);
  }

  public ASTPrimaryBooleanExpression(ComputeGrammar p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(ComputeGrammarVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }

public void setOpCode(OpCodes o) {
	opCode = o;
}
public OpCodes getOpCode() {
	return opCode;
}

}
/* JavaCC - OriginalChecksum=32606088f93e36d99eea01590808492f (do not edit this line) */
