package computeparser.visitor;

import java.util.HashMap;
import java.util.Map;

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
import computeparser.ASTPrimaryIntegerExpression;
import computeparser.ASTRelationalExpression;
import computeparser.ASTRule;
import computeparser.ASTRuleList;
import computeparser.ASTSimpleNode;
import computeparser.ASTTime;
import computeparser.ASTUnaryBooleanExpression;
import computeparser.ASTUnits;
import computeparser.ComputeGrammarVisitor;
import computeparser.SimpleNode;

public class DefinesVisitor implements ComputeGrammarVisitor {
	static private Map<String, Integer> defines = new HashMap<String, Integer>();
	static private int eventIndex = 1;
	
	static public int getIndex(String name) {
		if (defines.get(name) == null) {
			return 0;
		};
		return defines.get(name);
	}

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
		node.childrenAccept(this, data);
		return null;
	}

	@Override
	public Object visit(ASTRuleList node, Object data) {
		node.childrenAccept(this, data);
		return null;
	}
 
	@Override
	public Object visit(ASTDefine node, Object data) {		
		ASTIdentifier id = (ASTIdentifier)node.jjtGetChild(0);
		ASTEventLiteral ev = (ASTEventLiteral)node.jjtGetChild(1);
		System.out.println("Got a define for identifier("+id.getName()+")="+ev.getEvent());
		System.out.println("Add Event "+ev.getEvent()+" EV#1="+eventIndex);
		defines.put(id.getName(), eventIndex++);
		node.childrenAccept(this, data);
		return null;
	}

	@Override
	public Object visit(ASTRule node, Object data) {
		return null;
	}

	@Override
	public Object visit(ASTTime node, Object data) {
		return null;
	}

	@Override
	public Object visit(ASTActionList node, Object data) {
		return null;
	}

	@Override
	public Object visit(ASTAction node, Object data) {
		return null;
	}

	@Override
	public Object visit(ASTExpression node, Object data) {
		return null;
	}

	@Override
	public Object visit(ASTOrExpression node, Object data) {
		return null;
	}

	@Override
	public Object visit(ASTAndExpression node, Object data) {
		return null;
	}

	@Override
	public Object visit(ASTIdentifier node, Object data) {
		return null;
	}

	@Override
	public Object visit(ASTEventLiteral node, Object data) {
		return null;
	}

	@Override
	public Object visit(ASTMessageState node, Object data) {
		return null;
	}

	@Override
	public Object visit(ASTRelationalExpression node, Object data) {
		return null;
	}

	@Override
	public Object visit(ASTAdditiveExpression node, Object data) {
		return null;
	}

	@Override
	public Object visit(ASTUnits node, Object data) {
		return null;
	}

	@Override
	public Object visit(ASTUnaryBooleanExpression node, Object data) {
		return null;
	}

	@Override
	public Object visit(ASTPrimaryBooleanExpression node, Object data) {
		return null;
	}

	@Override
	public Object visit(ASTMessage node, Object data) {
		return null;
	}

	@Override
	public Object visit(ASTPrimaryIntegerExpression node, Object data) {
		return null;
	}
}
