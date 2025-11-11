// Generated from src/main/java/cp2025/datalog/DatalogParser.g4 by ANTLR 4.13.2
package cp2025.datalog;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link DatalogParser}.
 */
public interface DatalogParserListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link DatalogParser#start_ProgramDef}.
	 * @param ctx the parse tree
	 */
	void enterStart_ProgramDef(DatalogParser.Start_ProgramDefContext ctx);
	/**
	 * Exit a parse tree produced by {@link DatalogParser#start_ProgramDef}.
	 * @param ctx the parse tree
	 */
	void exitStart_ProgramDef(DatalogParser.Start_ProgramDefContext ctx);
	/**
	 * Enter a parse tree produced by {@link DatalogParser#programDef}.
	 * @param ctx the parse tree
	 */
	void enterProgramDef(DatalogParser.ProgramDefContext ctx);
	/**
	 * Exit a parse tree produced by {@link DatalogParser#programDef}.
	 * @param ctx the parse tree
	 */
	void exitProgramDef(DatalogParser.ProgramDefContext ctx);
	/**
	 * Enter a parse tree produced by {@link DatalogParser#constantsDef}.
	 * @param ctx the parse tree
	 */
	void enterConstantsDef(DatalogParser.ConstantsDefContext ctx);
	/**
	 * Exit a parse tree produced by {@link DatalogParser#constantsDef}.
	 * @param ctx the parse tree
	 */
	void exitConstantsDef(DatalogParser.ConstantsDefContext ctx);
	/**
	 * Enter a parse tree produced by {@link DatalogParser#listLIdent}.
	 * @param ctx the parse tree
	 */
	void enterListLIdent(DatalogParser.ListLIdentContext ctx);
	/**
	 * Exit a parse tree produced by {@link DatalogParser#listLIdent}.
	 * @param ctx the parse tree
	 */
	void exitListLIdent(DatalogParser.ListLIdentContext ctx);
	/**
	 * Enter a parse tree produced by {@link DatalogParser#rulesDef}.
	 * @param ctx the parse tree
	 */
	void enterRulesDef(DatalogParser.RulesDefContext ctx);
	/**
	 * Exit a parse tree produced by {@link DatalogParser#rulesDef}.
	 * @param ctx the parse tree
	 */
	void exitRulesDef(DatalogParser.RulesDefContext ctx);
	/**
	 * Enter a parse tree produced by {@link DatalogParser#listRuleDef}.
	 * @param ctx the parse tree
	 */
	void enterListRuleDef(DatalogParser.ListRuleDefContext ctx);
	/**
	 * Exit a parse tree produced by {@link DatalogParser#listRuleDef}.
	 * @param ctx the parse tree
	 */
	void exitListRuleDef(DatalogParser.ListRuleDefContext ctx);
	/**
	 * Enter a parse tree produced by {@link DatalogParser#ruleDef}.
	 * @param ctx the parse tree
	 */
	void enterRuleDef(DatalogParser.RuleDefContext ctx);
	/**
	 * Exit a parse tree produced by {@link DatalogParser#ruleDef}.
	 * @param ctx the parse tree
	 */
	void exitRuleDef(DatalogParser.RuleDefContext ctx);
	/**
	 * Enter a parse tree produced by {@link DatalogParser#listAtomDef}.
	 * @param ctx the parse tree
	 */
	void enterListAtomDef(DatalogParser.ListAtomDefContext ctx);
	/**
	 * Exit a parse tree produced by {@link DatalogParser#listAtomDef}.
	 * @param ctx the parse tree
	 */
	void exitListAtomDef(DatalogParser.ListAtomDefContext ctx);
	/**
	 * Enter a parse tree produced by {@link DatalogParser#atomDef}.
	 * @param ctx the parse tree
	 */
	void enterAtomDef(DatalogParser.AtomDefContext ctx);
	/**
	 * Exit a parse tree produced by {@link DatalogParser#atomDef}.
	 * @param ctx the parse tree
	 */
	void exitAtomDef(DatalogParser.AtomDefContext ctx);
	/**
	 * Enter a parse tree produced by {@link DatalogParser#listElement}.
	 * @param ctx the parse tree
	 */
	void enterListElement(DatalogParser.ListElementContext ctx);
	/**
	 * Exit a parse tree produced by {@link DatalogParser#listElement}.
	 * @param ctx the parse tree
	 */
	void exitListElement(DatalogParser.ListElementContext ctx);
	/**
	 * Enter a parse tree produced by {@link DatalogParser#element}.
	 * @param ctx the parse tree
	 */
	void enterElement(DatalogParser.ElementContext ctx);
	/**
	 * Exit a parse tree produced by {@link DatalogParser#element}.
	 * @param ctx the parse tree
	 */
	void exitElement(DatalogParser.ElementContext ctx);
	/**
	 * Enter a parse tree produced by {@link DatalogParser#predicateDef}.
	 * @param ctx the parse tree
	 */
	void enterPredicateDef(DatalogParser.PredicateDefContext ctx);
	/**
	 * Exit a parse tree produced by {@link DatalogParser#predicateDef}.
	 * @param ctx the parse tree
	 */
	void exitPredicateDef(DatalogParser.PredicateDefContext ctx);
	/**
	 * Enter a parse tree produced by {@link DatalogParser#queriesDef}.
	 * @param ctx the parse tree
	 */
	void enterQueriesDef(DatalogParser.QueriesDefContext ctx);
	/**
	 * Exit a parse tree produced by {@link DatalogParser#queriesDef}.
	 * @param ctx the parse tree
	 */
	void exitQueriesDef(DatalogParser.QueriesDefContext ctx);
	/**
	 * Enter a parse tree produced by {@link DatalogParser#listStatementDef}.
	 * @param ctx the parse tree
	 */
	void enterListStatementDef(DatalogParser.ListStatementDefContext ctx);
	/**
	 * Exit a parse tree produced by {@link DatalogParser#listStatementDef}.
	 * @param ctx the parse tree
	 */
	void exitListStatementDef(DatalogParser.ListStatementDefContext ctx);
	/**
	 * Enter a parse tree produced by {@link DatalogParser#statementDef}.
	 * @param ctx the parse tree
	 */
	void enterStatementDef(DatalogParser.StatementDefContext ctx);
	/**
	 * Exit a parse tree produced by {@link DatalogParser#statementDef}.
	 * @param ctx the parse tree
	 */
	void exitStatementDef(DatalogParser.StatementDefContext ctx);
}