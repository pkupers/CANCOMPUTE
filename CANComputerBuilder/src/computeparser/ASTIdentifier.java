/* Generated By:JJTree: Do not edit this line. ASTIdentifier.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package computeparser;

public
class ASTIdentifier extends SimpleNode {
	private String name;
  public ASTIdentifier(int id) {
    super(id);
  }

  public ASTIdentifier(ComputeGrammar p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(ComputeGrammarVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }

  public void setName(String name) {
	this.name = name;
  }
  public String getName() {
	  return name;
  }
}
/* JavaCC - OriginalChecksum=590c9310a34dd85b5255cd38afa4bab4 (do not edit this line) */
