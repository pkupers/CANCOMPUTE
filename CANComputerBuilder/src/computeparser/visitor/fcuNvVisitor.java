package computeparser.visitor;

import computeparser.ASTAction;
import computeparser.ASTActionList;
import computeparser.ASTAdditiveExpression;
import computeparser.ASTAndExpression;
import computeparser.ASTDefine;
import computeparser.ASTDefineList;
import computeparser.ASTEventLiteral;
import computeparser.ASTExpression;
import computeparser.ASTIdentifier;
import computeparser.ASTMessage;
import computeparser.ASTMessageState;
import computeparser.ASTOrExpression;
import computeparser.ASTPrimaryBooleanExpression;
import computeparser.ASTPrimaryBooleanExpression.OpCodes;
import computeparser.ASTPrimaryIntegerExpression;
import computeparser.ASTRelationalExpression;
import computeparser.ASTRule;
import computeparser.ASTRuleList;
import computeparser.ASTSetNN;
import computeparser.ASTSimpleNode;
import computeparser.ASTTime;
import computeparser.ASTUnaryBooleanExpression;
import computeparser.ASTUnits;
import computeparser.ComputeGrammarVisitor;
import computeparser.MessageState;
import computeparser.Node;
import computeparser.SimpleNode;

public class fcuNvVisitor implements ComputeGrammarVisitor {
	private int nvIndex = 1;

	@Override
	public Object visit(SimpleNode node, Object data) {
		node.childrenAccept(this, data);
		return null;
	}
	

	@Override
	public Object visit(ASTSimpleNode node, Object data) {
		node.childrenAccept(this, data);
		return null;
	}

	@Override
	public Object visit(ASTDefineList node, Object data) {
		return null;
	}

	@Override
	public Object visit(ASTRuleList node, Object data) {
		System.out.print("<NodeVars>");
		node.childrenAccept(this, data);
		doNv(nvIndex++, NvOpCode.END);
		System.out.println("</NodeVars>");
		return null;
	}

	@Override
	public Object visit(ASTDefine node, Object data) {
		return null;
	}

	@Override
	public Object visit(ASTRule node, Object data) {
		if (node.jjtGetNumChildren() > 0) {
			doNv(nvIndex++, NvOpCode.RULE);
			ASTExpression expression = (ASTExpression) node.jjtGetChild(0);
			ASTTime within = (ASTTime) node.jjtGetChild(1);
			ASTActionList actions = (ASTActionList) node.jjtGetChild(2);
			within.jjtAccept(this, data);
			expression.jjtAccept(this, data);
			actions.jjtAccept(this, data);
			if (node.jjtGetNumChildren() > 3) {	// have a THEN action list
				doNv(nvIndex++, NvOpCode.THEN);
				actions = (ASTActionList) node.jjtGetChild(3);
				actions.jjtAccept(this, data);
			}
		}
		return null;
	}

	@Override
	public Object visit(ASTTime node, Object data) {
		// Convert to 1/10 of seconds
		Node n = node.jjtGetChild(0);
		ASTUnits u = (ASTUnits)n;
		int val = (node.getTime()*u.getUnits())/100;
		doNv(nvIndex++, val, "Time Value");
		return null;
	}

	@Override
	public Object visit(ASTActionList node, Object data) {
		node.childrenAccept(this, data);
		return null;
	}

	@Override
	public Object visit(ASTAction node, Object data) {
		if (node.getAction() == ASTAction.OpCodes.DELAY) {	// just Time()
			doNv(nvIndex++, NvOpCode.DELAY);
			node.childrenAccept(this, data);
		}
		if (node.getAction() == ASTAction.OpCodes.SEND) {	// Event
			doNv(nvIndex++, NvOpCode.SEND);
			node.childrenAccept(this, data);			
		}
		//node.childrenAccept(this, data);
		return null;
	}

	@Override
	public Object visit(ASTExpression node, Object data) {
		node.childrenAccept(this, data);
		return null;
	}

	@Override
	public Object visit(ASTOrExpression node, Object data) {
		int depth = 0;
		while (true) {
			Node n;
			if (node.jjtGetNumChildren() >  depth+1) {
				doNv(nvIndex++, NvOpCode.OR);
				n = node.jjtGetChild(depth);
				n.jjtAccept(this, data);
				depth++;
			} else {
				n = node.jjtGetChild(depth);
				n.jjtAccept(this, data);
				depth++;
				break;
			}
		}
		return null;
	}

	@Override
	public Object visit(ASTAndExpression node, Object data) {
		int depth = 0;
		while (true) {
			Node n;
			if (node.jjtGetNumChildren() >  depth+1) {
				doNv(nvIndex++, NvOpCode.AND);
				n = node.jjtGetChild(depth);
				n.jjtAccept(this, data);
				depth++;
			} else {
				n = node.jjtGetChild(depth);
				n.jjtAccept(this, data);
				depth++;
				break;
			}
		}
		return null;
	}

	@Override
	public Object visit(ASTIdentifier node, Object data) {
		//System.out.println("identifier");
		node.childrenAccept(this, data);
//		int ev = DefinesVisitor.getIndex(node.getName());
//		if (ev == 0) {
//			System.out.println("Error: undefined variable \""+node.getName()+"\"");
//		} else {
//			doNv(nvIndex++, ev, "Event Number");
//		}
		return null;
	}

	@Override
	public Object visit(ASTEventLiteral node, Object data) {
		node.childrenAccept(this, data);
		return null;
	}

	@Override
	public Object visit(ASTMessageState node, Object data) {
		node.childrenAccept(this, data);
		return null;
	}

	@Override
	public Object visit(ASTRelationalExpression node, Object data) {
		if (node.jjtGetNumChildren() >  1) {
			String op = node.getOpCode();
			if ("is".equals(op)) {
				doNv(nvIndex++, NvOpCode.EQUALS);
			} else if ("equals".equals(op)) {
				doNv(nvIndex++, NvOpCode.EQUALS);
			} else if ("=".equals(op)) {
				doNv(nvIndex++, NvOpCode.EQUALS);
			} else if ("!=".equals(op)) {
				doNv(nvIndex++, NvOpCode.NOTEQUALS);
			} else if ("<".equals(op)) {
				doNv(nvIndex++, NvOpCode.LESS);
			} else if ("<=".equals(op)) {
				doNv(nvIndex++, NvOpCode.LESSEQUAL);
			} else if (">".equals(op)) {
				doNv(nvIndex++, NvOpCode.MORE);
			} else if (">=".equals(op)) {
				doNv(nvIndex++, NvOpCode.MOREEQUAL);
			} else {
				System.out.println("Error unknown RelationExpression operator"+op);
			}
		}
		node.childrenAccept(this, data);
		return null;
	}


	@Override
	public Object visit(ASTAdditiveExpression node, Object data) {
		if (node.jjtGetNumChildren() >  1) {
			String op = node.getOpCode();
			if ("+".equals(op)) {
				doNv(nvIndex++, NvOpCode.PLUS);
			} else if ("-".equals(op)){
				doNv(nvIndex++, NvOpCode.MINUS);
			} else {
				System.out.println("Error unknown AdditiveExpression operator"+op);
			}
		}
		node.childrenAccept(this, data);
		return null;
	}

	@Override
	public Object visit(ASTUnits node, Object data) {
		node.childrenAccept(this, data);
		return null;
	}

	@Override
	public Object visit(ASTUnaryBooleanExpression node, Object data) {
		if (node.getOp()) {
			doNv(nvIndex++, NvOpCode.NOT);
		}
		node.childrenAccept(this, data);
		return null;
	}


	@Override
	public Object visit(ASTPrimaryBooleanExpression node, Object data) {
		if (node.getOpCode() == OpCodes.STATE) {
			node.childrenAccept(this, data);
			
			ASTIdentifier ei;
			ei = (ASTIdentifier) node.jjtGetChild(0);
			ASTMessageState ms;
			ms = (ASTMessageState) node.jjtGetChild(1);
			if (ms.getState() == MessageState.ON) {
				doNv(nvIndex++, NvOpCode.STATE_ON);
			} else {
				doNv(nvIndex++, NvOpCode.STATE_OFF);
			}
			
			int ev = Variables.getIndex(ei.getName());
			if (ev > 0) {
				doNv(nvIndex++, ev, "Event number");
			} else {
				System.out.println("Error: undefined variable \""+ei.getName()+"\"");
			}
			return null;
		}
		if (node.getOpCode() == OpCodes.SEQUENCE) {
			doNv(nvIndex++, NvOpCode.SEQUENCE);
			doNv(nvIndex++, node.jjtGetNumChildren(), "Number of events");
			node.childrenAccept(this, data);
			return null;
		} 
		if (node.getOpCode() == OpCodes.RECEIVED) {
			doNv(nvIndex++, NvOpCode.RECEIVED);
			node.childrenAccept(this, data);
			return null;
		} 
		if (node.getOpCode() == OpCodes.BEFORE) {
			doNv(nvIndex++, NvOpCode.BEFORE);
			node.childrenAccept(this, data);
			return null;
		}
		if (node.getOpCode() == OpCodes.AFTER) {
			doNv(nvIndex++, NvOpCode.AFTER);
			node.childrenAccept(this, data);
			return null;
		}
		node.childrenAccept(this, data);
		return null;
	}

	@Override
	public Object visit(ASTMessage node, Object data) {
		node.childrenAccept(this, data);
		
		ASTMessageState ms;
		ms = (ASTMessageState) node.jjtGetChild(0);
		ms.jjtAccept(this, data);
		
		ASTIdentifier ei;
		ei = (ASTIdentifier) node.jjtGetChild(1);
		ei.jjtAccept(this, data);
		
		int ev = Variables.getIndex(ei.getName());
		if (ev == 0) {
			System.out.println("Error: undefined variable \""+ei.getName()+"\"");
		} else {
			if (ms.getState() == MessageState.ON) {
				doNv(nvIndex++, ev+0x80, "ON Event number");
			} else {
				doNv(nvIndex++, ev, "OFF Event number");
			}
		}
		return null;
	}


	@Override
	public Object visit(ASTPrimaryIntegerExpression node, Object data) {
		if (node.getOpCode() == ASTPrimaryIntegerExpression.OpCodes.COUNT) {
			doNv(nvIndex++, NvOpCode.COUNT);
		} else if (node.getOpCode() == ASTPrimaryIntegerExpression.OpCodes.INTEGER) {
			doNv(nvIndex++, NvOpCode.INTEGER);
			doNv(nvIndex++, node.getInt(), "Integer value");
		}
		node.childrenAccept(this, data);
		return null;
	}

	private String hex(int i) {
		int i1 = (i>>4)&0xF;
		int i2 = i&0xF;
		return ""+(char)(i1>9?i1-10+'A':i1+'0')+(char)(i2>9?i2-10+'A':i2+'0');
	}
	
	private void doNv(int nvi, NvOpCode op) {
		doNv(nvi, op.code(), ""+op);
	}
	
	private void doNv(int nvi, int val, String desc) {
		System.out.print(hex(val));
	}


	@Override
	public Object visit(ASTSetNN node, Object data) {
		node.childrenAccept(this, data);
		return null;
	}

}
