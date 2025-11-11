// Generated from src/main/java/cp2025/datalog/DatalogParser.g4 by ANTLR 4.13.2
package cp2025.datalog;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue", "this-escape"})
public class DatalogParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.13.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		Surrogate_id_SYMB_0=1, Surrogate_id_SYMB_1=2, Surrogate_id_SYMB_2=3, Surrogate_id_SYMB_3=4, 
		Surrogate_id_SYMB_4=5, Surrogate_id_SYMB_5=6, Surrogate_id_SYMB_6=7, Surrogate_id_SYMB_7=8, 
		COMMENT_antlr_builtin=9, MULTICOMMENT_antlr_builtin=10, LIdent=11, UIdent=12, 
		LSIdent=13, WS=14, ErrorToken=15;
	public static final int
		RULE_start_ProgramDef = 0, RULE_programDef = 1, RULE_constantsDef = 2, 
		RULE_listLIdent = 3, RULE_rulesDef = 4, RULE_listRuleDef = 5, RULE_ruleDef = 6, 
		RULE_listAtomDef = 7, RULE_atomDef = 8, RULE_listElement = 9, RULE_element = 10, 
		RULE_predicateDef = 11, RULE_queriesDef = 12, RULE_listStatementDef = 13, 
		RULE_statementDef = 14;
	private static String[] makeRuleNames() {
		return new String[] {
			"start_ProgramDef", "programDef", "constantsDef", "listLIdent", "rulesDef", 
			"listRuleDef", "ruleDef", "listAtomDef", "atomDef", "listElement", "element", 
			"predicateDef", "queriesDef", "listStatementDef", "statementDef"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'Constants:'", "','", "'Rules:'", "'.'", "':-'", "'('", "')'", 
			"'Queries:'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "Surrogate_id_SYMB_0", "Surrogate_id_SYMB_1", "Surrogate_id_SYMB_2", 
			"Surrogate_id_SYMB_3", "Surrogate_id_SYMB_4", "Surrogate_id_SYMB_5", 
			"Surrogate_id_SYMB_6", "Surrogate_id_SYMB_7", "COMMENT_antlr_builtin", 
			"MULTICOMMENT_antlr_builtin", "LIdent", "UIdent", "LSIdent", "WS", "ErrorToken"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "DatalogParser.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public DatalogParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Start_ProgramDefContext extends ParserRuleContext {
		public cp2025.datalog.Absyn.ProgramDef result;
		public ProgramDefContext x;
		public TerminalNode EOF() { return getToken(DatalogParser.EOF, 0); }
		public ProgramDefContext programDef() {
			return getRuleContext(ProgramDefContext.class,0);
		}
		public Start_ProgramDefContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_start_ProgramDef; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DatalogParserListener ) ((DatalogParserListener)listener).enterStart_ProgramDef(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DatalogParserListener ) ((DatalogParserListener)listener).exitStart_ProgramDef(this);
		}
	}

	public final Start_ProgramDefContext start_ProgramDef() throws RecognitionException {
		Start_ProgramDefContext _localctx = new Start_ProgramDefContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_start_ProgramDef);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(30);
			((Start_ProgramDefContext)_localctx).x = programDef();
			setState(31);
			match(EOF);
			 ((Start_ProgramDefContext)_localctx).result =  ((Start_ProgramDefContext)_localctx).x.result; 
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ProgramDefContext extends ParserRuleContext {
		public cp2025.datalog.Absyn.ProgramDef result;
		public ConstantsDefContext p_1_1;
		public RulesDefContext p_1_2;
		public QueriesDefContext p_1_3;
		public ConstantsDefContext constantsDef() {
			return getRuleContext(ConstantsDefContext.class,0);
		}
		public RulesDefContext rulesDef() {
			return getRuleContext(RulesDefContext.class,0);
		}
		public QueriesDefContext queriesDef() {
			return getRuleContext(QueriesDefContext.class,0);
		}
		public ProgramDefContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_programDef; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DatalogParserListener ) ((DatalogParserListener)listener).enterProgramDef(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DatalogParserListener ) ((DatalogParserListener)listener).exitProgramDef(this);
		}
	}

	public final ProgramDefContext programDef() throws RecognitionException {
		ProgramDefContext _localctx = new ProgramDefContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_programDef);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(34);
			((ProgramDefContext)_localctx).p_1_1 = constantsDef();
			setState(35);
			((ProgramDefContext)_localctx).p_1_2 = rulesDef();
			setState(36);
			((ProgramDefContext)_localctx).p_1_3 = queriesDef();
			 ((ProgramDefContext)_localctx).result =  new cp2025.datalog.Absyn.Program(((ProgramDefContext)_localctx).p_1_1.result,((ProgramDefContext)_localctx).p_1_2.result,((ProgramDefContext)_localctx).p_1_3.result); 
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ConstantsDefContext extends ParserRuleContext {
		public cp2025.datalog.Absyn.ConstantsDef result;
		public ListLIdentContext p_1_2;
		public TerminalNode Surrogate_id_SYMB_0() { return getToken(DatalogParser.Surrogate_id_SYMB_0, 0); }
		public ListLIdentContext listLIdent() {
			return getRuleContext(ListLIdentContext.class,0);
		}
		public ConstantsDefContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_constantsDef; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DatalogParserListener ) ((DatalogParserListener)listener).enterConstantsDef(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DatalogParserListener ) ((DatalogParserListener)listener).exitConstantsDef(this);
		}
	}

	public final ConstantsDefContext constantsDef() throws RecognitionException {
		ConstantsDefContext _localctx = new ConstantsDefContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_constantsDef);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(39);
			match(Surrogate_id_SYMB_0);
			setState(40);
			((ConstantsDefContext)_localctx).p_1_2 = listLIdent();
			 ((ConstantsDefContext)_localctx).result =  new cp2025.datalog.Absyn.Constants(((ConstantsDefContext)_localctx).p_1_2.result); 
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ListLIdentContext extends ParserRuleContext {
		public cp2025.datalog.Absyn.ListLIdent result;
		public Token p_2_1;
		public Token p_3_1;
		public ListLIdentContext p_3_3;
		public TerminalNode LIdent() { return getToken(DatalogParser.LIdent, 0); }
		public TerminalNode Surrogate_id_SYMB_1() { return getToken(DatalogParser.Surrogate_id_SYMB_1, 0); }
		public ListLIdentContext listLIdent() {
			return getRuleContext(ListLIdentContext.class,0);
		}
		public ListLIdentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_listLIdent; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DatalogParserListener ) ((DatalogParserListener)listener).enterListLIdent(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DatalogParserListener ) ((DatalogParserListener)listener).exitListLIdent(this);
		}
	}

	public final ListLIdentContext listLIdent() throws RecognitionException {
		ListLIdentContext _localctx = new ListLIdentContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_listLIdent);
		try {
			setState(51);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,0,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				 ((ListLIdentContext)_localctx).result =  new cp2025.datalog.Absyn.ListLIdent(); 
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(44);
				((ListLIdentContext)_localctx).p_2_1 = match(LIdent);
				 ((ListLIdentContext)_localctx).result =  new cp2025.datalog.Absyn.ListLIdent(); _localctx.result.addLast(((ListLIdentContext)_localctx).p_2_1.getText()); 
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(46);
				((ListLIdentContext)_localctx).p_3_1 = match(LIdent);
				setState(47);
				match(Surrogate_id_SYMB_1);
				setState(48);
				((ListLIdentContext)_localctx).p_3_3 = listLIdent();
				 ((ListLIdentContext)_localctx).result =  ((ListLIdentContext)_localctx).p_3_3.result; _localctx.result.addFirst(((ListLIdentContext)_localctx).p_3_1.getText()); 
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class RulesDefContext extends ParserRuleContext {
		public cp2025.datalog.Absyn.RulesDef result;
		public ListRuleDefContext p_1_2;
		public TerminalNode Surrogate_id_SYMB_2() { return getToken(DatalogParser.Surrogate_id_SYMB_2, 0); }
		public ListRuleDefContext listRuleDef() {
			return getRuleContext(ListRuleDefContext.class,0);
		}
		public RulesDefContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_rulesDef; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DatalogParserListener ) ((DatalogParserListener)listener).enterRulesDef(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DatalogParserListener ) ((DatalogParserListener)listener).exitRulesDef(this);
		}
	}

	public final RulesDefContext rulesDef() throws RecognitionException {
		RulesDefContext _localctx = new RulesDefContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_rulesDef);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(53);
			match(Surrogate_id_SYMB_2);
			setState(54);
			((RulesDefContext)_localctx).p_1_2 = listRuleDef(0);
			 ((RulesDefContext)_localctx).result =  new cp2025.datalog.Absyn.Rules(((RulesDefContext)_localctx).p_1_2.result); 
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ListRuleDefContext extends ParserRuleContext {
		public cp2025.datalog.Absyn.ListRuleDef result;
		public ListRuleDefContext p_2_1;
		public RuleDefContext p_2_2;
		public TerminalNode Surrogate_id_SYMB_3() { return getToken(DatalogParser.Surrogate_id_SYMB_3, 0); }
		public ListRuleDefContext listRuleDef() {
			return getRuleContext(ListRuleDefContext.class,0);
		}
		public RuleDefContext ruleDef() {
			return getRuleContext(RuleDefContext.class,0);
		}
		public ListRuleDefContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_listRuleDef; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DatalogParserListener ) ((DatalogParserListener)listener).enterListRuleDef(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DatalogParserListener ) ((DatalogParserListener)listener).exitListRuleDef(this);
		}
	}

	public final ListRuleDefContext listRuleDef() throws RecognitionException {
		return listRuleDef(0);
	}

	private ListRuleDefContext listRuleDef(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ListRuleDefContext _localctx = new ListRuleDefContext(_ctx, _parentState);
		ListRuleDefContext _prevctx = _localctx;
		int _startState = 10;
		enterRecursionRule(_localctx, 10, RULE_listRuleDef, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			 ((ListRuleDefContext)_localctx).result =  new cp2025.datalog.Absyn.ListRuleDef(); 
			}
			_ctx.stop = _input.LT(-1);
			setState(67);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,1,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new ListRuleDefContext(_parentctx, _parentState);
					_localctx.p_2_1 = _prevctx;
					pushNewRecursionContext(_localctx, _startState, RULE_listRuleDef);
					setState(60);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(61);
					((ListRuleDefContext)_localctx).p_2_2 = ruleDef();
					setState(62);
					match(Surrogate_id_SYMB_3);
					 ((ListRuleDefContext)_localctx).result =  ((ListRuleDefContext)_localctx).p_2_1.result; _localctx.result.addLast(((ListRuleDefContext)_localctx).p_2_2.result); 
					}
					} 
				}
				setState(69);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,1,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class RuleDefContext extends ParserRuleContext {
		public cp2025.datalog.Absyn.RuleDef result;
		public AtomDefContext p_1_1;
		public ListAtomDefContext p_1_3;
		public TerminalNode Surrogate_id_SYMB_4() { return getToken(DatalogParser.Surrogate_id_SYMB_4, 0); }
		public AtomDefContext atomDef() {
			return getRuleContext(AtomDefContext.class,0);
		}
		public ListAtomDefContext listAtomDef() {
			return getRuleContext(ListAtomDefContext.class,0);
		}
		public RuleDefContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ruleDef; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DatalogParserListener ) ((DatalogParserListener)listener).enterRuleDef(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DatalogParserListener ) ((DatalogParserListener)listener).exitRuleDef(this);
		}
	}

	public final RuleDefContext ruleDef() throws RecognitionException {
		RuleDefContext _localctx = new RuleDefContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_ruleDef);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(70);
			((RuleDefContext)_localctx).p_1_1 = atomDef();
			setState(71);
			match(Surrogate_id_SYMB_4);
			setState(72);
			((RuleDefContext)_localctx).p_1_3 = listAtomDef();
			 ((RuleDefContext)_localctx).result =  new cp2025.datalog.Absyn.Rule(((RuleDefContext)_localctx).p_1_1.result,((RuleDefContext)_localctx).p_1_3.result); 
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ListAtomDefContext extends ParserRuleContext {
		public cp2025.datalog.Absyn.ListAtomDef result;
		public AtomDefContext p_2_1;
		public AtomDefContext p_3_1;
		public ListAtomDefContext p_3_3;
		public AtomDefContext atomDef() {
			return getRuleContext(AtomDefContext.class,0);
		}
		public TerminalNode Surrogate_id_SYMB_1() { return getToken(DatalogParser.Surrogate_id_SYMB_1, 0); }
		public ListAtomDefContext listAtomDef() {
			return getRuleContext(ListAtomDefContext.class,0);
		}
		public ListAtomDefContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_listAtomDef; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DatalogParserListener ) ((DatalogParserListener)listener).enterListAtomDef(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DatalogParserListener ) ((DatalogParserListener)listener).exitListAtomDef(this);
		}
	}

	public final ListAtomDefContext listAtomDef() throws RecognitionException {
		ListAtomDefContext _localctx = new ListAtomDefContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_listAtomDef);
		try {
			setState(84);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,2,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				 ((ListAtomDefContext)_localctx).result =  new cp2025.datalog.Absyn.ListAtomDef(); 
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(76);
				((ListAtomDefContext)_localctx).p_2_1 = atomDef();
				 ((ListAtomDefContext)_localctx).result =  new cp2025.datalog.Absyn.ListAtomDef(); _localctx.result.addLast(((ListAtomDefContext)_localctx).p_2_1.result); 
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(79);
				((ListAtomDefContext)_localctx).p_3_1 = atomDef();
				setState(80);
				match(Surrogate_id_SYMB_1);
				setState(81);
				((ListAtomDefContext)_localctx).p_3_3 = listAtomDef();
				 ((ListAtomDefContext)_localctx).result =  ((ListAtomDefContext)_localctx).p_3_3.result; _localctx.result.addFirst(((ListAtomDefContext)_localctx).p_3_1.result); 
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class AtomDefContext extends ParserRuleContext {
		public cp2025.datalog.Absyn.AtomDef result;
		public PredicateDefContext p_1_1;
		public ListElementContext p_1_3;
		public TerminalNode Surrogate_id_SYMB_5() { return getToken(DatalogParser.Surrogate_id_SYMB_5, 0); }
		public TerminalNode Surrogate_id_SYMB_6() { return getToken(DatalogParser.Surrogate_id_SYMB_6, 0); }
		public PredicateDefContext predicateDef() {
			return getRuleContext(PredicateDefContext.class,0);
		}
		public ListElementContext listElement() {
			return getRuleContext(ListElementContext.class,0);
		}
		public AtomDefContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_atomDef; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DatalogParserListener ) ((DatalogParserListener)listener).enterAtomDef(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DatalogParserListener ) ((DatalogParserListener)listener).exitAtomDef(this);
		}
	}

	public final AtomDefContext atomDef() throws RecognitionException {
		AtomDefContext _localctx = new AtomDefContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_atomDef);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(86);
			((AtomDefContext)_localctx).p_1_1 = predicateDef();
			setState(87);
			match(Surrogate_id_SYMB_5);
			setState(88);
			((AtomDefContext)_localctx).p_1_3 = listElement();
			setState(89);
			match(Surrogate_id_SYMB_6);
			 ((AtomDefContext)_localctx).result =  new cp2025.datalog.Absyn.Atom(((AtomDefContext)_localctx).p_1_1.result,((AtomDefContext)_localctx).p_1_3.result); 
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ListElementContext extends ParserRuleContext {
		public cp2025.datalog.Absyn.ListElement result;
		public ElementContext p_2_1;
		public ElementContext p_3_1;
		public ListElementContext p_3_3;
		public ElementContext element() {
			return getRuleContext(ElementContext.class,0);
		}
		public TerminalNode Surrogate_id_SYMB_1() { return getToken(DatalogParser.Surrogate_id_SYMB_1, 0); }
		public ListElementContext listElement() {
			return getRuleContext(ListElementContext.class,0);
		}
		public ListElementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_listElement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DatalogParserListener ) ((DatalogParserListener)listener).enterListElement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DatalogParserListener ) ((DatalogParserListener)listener).exitListElement(this);
		}
	}

	public final ListElementContext listElement() throws RecognitionException {
		ListElementContext _localctx = new ListElementContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_listElement);
		try {
			setState(101);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,3,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				 ((ListElementContext)_localctx).result =  new cp2025.datalog.Absyn.ListElement(); 
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(93);
				((ListElementContext)_localctx).p_2_1 = element();
				 ((ListElementContext)_localctx).result =  new cp2025.datalog.Absyn.ListElement(); _localctx.result.addLast(((ListElementContext)_localctx).p_2_1.result); 
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(96);
				((ListElementContext)_localctx).p_3_1 = element();
				setState(97);
				match(Surrogate_id_SYMB_1);
				setState(98);
				((ListElementContext)_localctx).p_3_3 = listElement();
				 ((ListElementContext)_localctx).result =  ((ListElementContext)_localctx).p_3_3.result; _localctx.result.addFirst(((ListElementContext)_localctx).p_3_1.result); 
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ElementContext extends ParserRuleContext {
		public cp2025.datalog.Absyn.Element result;
		public Token p_1_1;
		public Token p_2_1;
		public TerminalNode LIdent() { return getToken(DatalogParser.LIdent, 0); }
		public TerminalNode UIdent() { return getToken(DatalogParser.UIdent, 0); }
		public ElementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_element; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DatalogParserListener ) ((DatalogParserListener)listener).enterElement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DatalogParserListener ) ((DatalogParserListener)listener).exitElement(this);
		}
	}

	public final ElementContext element() throws RecognitionException {
		ElementContext _localctx = new ElementContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_element);
		try {
			setState(107);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case LIdent:
				enterOuterAlt(_localctx, 1);
				{
				setState(103);
				((ElementContext)_localctx).p_1_1 = match(LIdent);
				 ((ElementContext)_localctx).result =  new cp2025.datalog.Absyn.ConstElement(((ElementContext)_localctx).p_1_1.getText()); 
				}
				break;
			case UIdent:
				enterOuterAlt(_localctx, 2);
				{
				setState(105);
				((ElementContext)_localctx).p_2_1 = match(UIdent);
				 ((ElementContext)_localctx).result =  new cp2025.datalog.Absyn.VarElement(((ElementContext)_localctx).p_2_1.getText()); 
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class PredicateDefContext extends ParserRuleContext {
		public cp2025.datalog.Absyn.PredicateDef result;
		public Token p_1_1;
		public Token p_2_1;
		public TerminalNode LIdent() { return getToken(DatalogParser.LIdent, 0); }
		public TerminalNode LSIdent() { return getToken(DatalogParser.LSIdent, 0); }
		public PredicateDefContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_predicateDef; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DatalogParserListener ) ((DatalogParserListener)listener).enterPredicateDef(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DatalogParserListener ) ((DatalogParserListener)listener).exitPredicateDef(this);
		}
	}

	public final PredicateDefContext predicateDef() throws RecognitionException {
		PredicateDefContext _localctx = new PredicateDefContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_predicateDef);
		try {
			setState(113);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case LIdent:
				enterOuterAlt(_localctx, 1);
				{
				setState(109);
				((PredicateDefContext)_localctx).p_1_1 = match(LIdent);
				 ((PredicateDefContext)_localctx).result =  new cp2025.datalog.Absyn.LPredicate(((PredicateDefContext)_localctx).p_1_1.getText()); 
				}
				break;
			case LSIdent:
				enterOuterAlt(_localctx, 2);
				{
				setState(111);
				((PredicateDefContext)_localctx).p_2_1 = match(LSIdent);
				 ((PredicateDefContext)_localctx).result =  new cp2025.datalog.Absyn.LSPredicate(((PredicateDefContext)_localctx).p_2_1.getText()); 
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class QueriesDefContext extends ParserRuleContext {
		public cp2025.datalog.Absyn.QueriesDef result;
		public ListStatementDefContext p_1_2;
		public TerminalNode Surrogate_id_SYMB_7() { return getToken(DatalogParser.Surrogate_id_SYMB_7, 0); }
		public ListStatementDefContext listStatementDef() {
			return getRuleContext(ListStatementDefContext.class,0);
		}
		public QueriesDefContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_queriesDef; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DatalogParserListener ) ((DatalogParserListener)listener).enterQueriesDef(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DatalogParserListener ) ((DatalogParserListener)listener).exitQueriesDef(this);
		}
	}

	public final QueriesDefContext queriesDef() throws RecognitionException {
		QueriesDefContext _localctx = new QueriesDefContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_queriesDef);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(115);
			match(Surrogate_id_SYMB_7);
			setState(116);
			((QueriesDefContext)_localctx).p_1_2 = listStatementDef();
			 ((QueriesDefContext)_localctx).result =  new cp2025.datalog.Absyn.Queries(((QueriesDefContext)_localctx).p_1_2.result); 
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ListStatementDefContext extends ParserRuleContext {
		public cp2025.datalog.Absyn.ListStatementDef result;
		public StatementDefContext p_2_1;
		public StatementDefContext p_3_1;
		public ListStatementDefContext p_3_3;
		public StatementDefContext statementDef() {
			return getRuleContext(StatementDefContext.class,0);
		}
		public TerminalNode Surrogate_id_SYMB_1() { return getToken(DatalogParser.Surrogate_id_SYMB_1, 0); }
		public ListStatementDefContext listStatementDef() {
			return getRuleContext(ListStatementDefContext.class,0);
		}
		public ListStatementDefContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_listStatementDef; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DatalogParserListener ) ((DatalogParserListener)listener).enterListStatementDef(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DatalogParserListener ) ((DatalogParserListener)listener).exitListStatementDef(this);
		}
	}

	public final ListStatementDefContext listStatementDef() throws RecognitionException {
		ListStatementDefContext _localctx = new ListStatementDefContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_listStatementDef);
		try {
			setState(128);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,6,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				 ((ListStatementDefContext)_localctx).result =  new cp2025.datalog.Absyn.ListStatementDef(); 
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(120);
				((ListStatementDefContext)_localctx).p_2_1 = statementDef();
				 ((ListStatementDefContext)_localctx).result =  new cp2025.datalog.Absyn.ListStatementDef(); _localctx.result.addLast(((ListStatementDefContext)_localctx).p_2_1.result); 
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(123);
				((ListStatementDefContext)_localctx).p_3_1 = statementDef();
				setState(124);
				match(Surrogate_id_SYMB_1);
				setState(125);
				((ListStatementDefContext)_localctx).p_3_3 = listStatementDef();
				 ((ListStatementDefContext)_localctx).result =  ((ListStatementDefContext)_localctx).p_3_3.result; _localctx.result.addFirst(((ListStatementDefContext)_localctx).p_3_1.result); 
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class StatementDefContext extends ParserRuleContext {
		public cp2025.datalog.Absyn.StatementDef result;
		public PredicateDefContext p_1_1;
		public ListLIdentContext p_1_3;
		public TerminalNode Surrogate_id_SYMB_5() { return getToken(DatalogParser.Surrogate_id_SYMB_5, 0); }
		public TerminalNode Surrogate_id_SYMB_6() { return getToken(DatalogParser.Surrogate_id_SYMB_6, 0); }
		public PredicateDefContext predicateDef() {
			return getRuleContext(PredicateDefContext.class,0);
		}
		public ListLIdentContext listLIdent() {
			return getRuleContext(ListLIdentContext.class,0);
		}
		public StatementDefContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statementDef; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof DatalogParserListener ) ((DatalogParserListener)listener).enterStatementDef(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof DatalogParserListener ) ((DatalogParserListener)listener).exitStatementDef(this);
		}
	}

	public final StatementDefContext statementDef() throws RecognitionException {
		StatementDefContext _localctx = new StatementDefContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_statementDef);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(130);
			((StatementDefContext)_localctx).p_1_1 = predicateDef();
			setState(131);
			match(Surrogate_id_SYMB_5);
			setState(132);
			((StatementDefContext)_localctx).p_1_3 = listLIdent();
			setState(133);
			match(Surrogate_id_SYMB_6);
			 ((StatementDefContext)_localctx).result =  new cp2025.datalog.Absyn.Statement(((StatementDefContext)_localctx).p_1_1.result,((StatementDefContext)_localctx).p_1_3.result); 
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 5:
			return listRuleDef_sempred((ListRuleDefContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean listRuleDef_sempred(ListRuleDefContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 1);
		}
		return true;
	}

	public static final String _serializedATN =
		"\u0004\u0001\u000f\u0089\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001"+
		"\u0002\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004"+
		"\u0002\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007"+
		"\u0002\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b\u0007\u000b"+
		"\u0002\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e\u0001\u0000\u0001"+
		"\u0000\u0001\u0000\u0001\u0000\u0001\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001"+
		"\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001"+
		"\u0003\u0001\u0003\u0003\u00034\b\u0003\u0001\u0004\u0001\u0004\u0001"+
		"\u0004\u0001\u0004\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001"+
		"\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0005\u0005B\b\u0005\n\u0005"+
		"\f\u0005E\t\u0005\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001"+
		"\u0006\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001"+
		"\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0003\u0007U\b\u0007\u0001"+
		"\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\t\u0001\t\u0001\t\u0001"+
		"\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0003\tf\b\t\u0001\n\u0001"+
		"\n\u0001\n\u0001\n\u0003\nl\b\n\u0001\u000b\u0001\u000b\u0001\u000b\u0001"+
		"\u000b\u0003\u000br\b\u000b\u0001\f\u0001\f\u0001\f\u0001\f\u0001\r\u0001"+
		"\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0003\r\u0081"+
		"\b\r\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001"+
		"\u000e\u0001\u000e\u0000\u0001\n\u000f\u0000\u0002\u0004\u0006\b\n\f\u000e"+
		"\u0010\u0012\u0014\u0016\u0018\u001a\u001c\u0000\u0000\u0084\u0000\u001e"+
		"\u0001\u0000\u0000\u0000\u0002\"\u0001\u0000\u0000\u0000\u0004\'\u0001"+
		"\u0000\u0000\u0000\u00063\u0001\u0000\u0000\u0000\b5\u0001\u0000\u0000"+
		"\u0000\n9\u0001\u0000\u0000\u0000\fF\u0001\u0000\u0000\u0000\u000eT\u0001"+
		"\u0000\u0000\u0000\u0010V\u0001\u0000\u0000\u0000\u0012e\u0001\u0000\u0000"+
		"\u0000\u0014k\u0001\u0000\u0000\u0000\u0016q\u0001\u0000\u0000\u0000\u0018"+
		"s\u0001\u0000\u0000\u0000\u001a\u0080\u0001\u0000\u0000\u0000\u001c\u0082"+
		"\u0001\u0000\u0000\u0000\u001e\u001f\u0003\u0002\u0001\u0000\u001f \u0005"+
		"\u0000\u0000\u0001 !\u0006\u0000\uffff\uffff\u0000!\u0001\u0001\u0000"+
		"\u0000\u0000\"#\u0003\u0004\u0002\u0000#$\u0003\b\u0004\u0000$%\u0003"+
		"\u0018\f\u0000%&\u0006\u0001\uffff\uffff\u0000&\u0003\u0001\u0000\u0000"+
		"\u0000\'(\u0005\u0001\u0000\u0000()\u0003\u0006\u0003\u0000)*\u0006\u0002"+
		"\uffff\uffff\u0000*\u0005\u0001\u0000\u0000\u0000+4\u0006\u0003\uffff"+
		"\uffff\u0000,-\u0005\u000b\u0000\u0000-4\u0006\u0003\uffff\uffff\u0000"+
		"./\u0005\u000b\u0000\u0000/0\u0005\u0002\u0000\u000001\u0003\u0006\u0003"+
		"\u000012\u0006\u0003\uffff\uffff\u000024\u0001\u0000\u0000\u00003+\u0001"+
		"\u0000\u0000\u00003,\u0001\u0000\u0000\u00003.\u0001\u0000\u0000\u0000"+
		"4\u0007\u0001\u0000\u0000\u000056\u0005\u0003\u0000\u000067\u0003\n\u0005"+
		"\u000078\u0006\u0004\uffff\uffff\u00008\t\u0001\u0000\u0000\u00009:\u0006"+
		"\u0005\uffff\uffff\u0000:;\u0006\u0005\uffff\uffff\u0000;C\u0001\u0000"+
		"\u0000\u0000<=\n\u0001\u0000\u0000=>\u0003\f\u0006\u0000>?\u0005\u0004"+
		"\u0000\u0000?@\u0006\u0005\uffff\uffff\u0000@B\u0001\u0000\u0000\u0000"+
		"A<\u0001\u0000\u0000\u0000BE\u0001\u0000\u0000\u0000CA\u0001\u0000\u0000"+
		"\u0000CD\u0001\u0000\u0000\u0000D\u000b\u0001\u0000\u0000\u0000EC\u0001"+
		"\u0000\u0000\u0000FG\u0003\u0010\b\u0000GH\u0005\u0005\u0000\u0000HI\u0003"+
		"\u000e\u0007\u0000IJ\u0006\u0006\uffff\uffff\u0000J\r\u0001\u0000\u0000"+
		"\u0000KU\u0006\u0007\uffff\uffff\u0000LM\u0003\u0010\b\u0000MN\u0006\u0007"+
		"\uffff\uffff\u0000NU\u0001\u0000\u0000\u0000OP\u0003\u0010\b\u0000PQ\u0005"+
		"\u0002\u0000\u0000QR\u0003\u000e\u0007\u0000RS\u0006\u0007\uffff\uffff"+
		"\u0000SU\u0001\u0000\u0000\u0000TK\u0001\u0000\u0000\u0000TL\u0001\u0000"+
		"\u0000\u0000TO\u0001\u0000\u0000\u0000U\u000f\u0001\u0000\u0000\u0000"+
		"VW\u0003\u0016\u000b\u0000WX\u0005\u0006\u0000\u0000XY\u0003\u0012\t\u0000"+
		"YZ\u0005\u0007\u0000\u0000Z[\u0006\b\uffff\uffff\u0000[\u0011\u0001\u0000"+
		"\u0000\u0000\\f\u0006\t\uffff\uffff\u0000]^\u0003\u0014\n\u0000^_\u0006"+
		"\t\uffff\uffff\u0000_f\u0001\u0000\u0000\u0000`a\u0003\u0014\n\u0000a"+
		"b\u0005\u0002\u0000\u0000bc\u0003\u0012\t\u0000cd\u0006\t\uffff\uffff"+
		"\u0000df\u0001\u0000\u0000\u0000e\\\u0001\u0000\u0000\u0000e]\u0001\u0000"+
		"\u0000\u0000e`\u0001\u0000\u0000\u0000f\u0013\u0001\u0000\u0000\u0000"+
		"gh\u0005\u000b\u0000\u0000hl\u0006\n\uffff\uffff\u0000ij\u0005\f\u0000"+
		"\u0000jl\u0006\n\uffff\uffff\u0000kg\u0001\u0000\u0000\u0000ki\u0001\u0000"+
		"\u0000\u0000l\u0015\u0001\u0000\u0000\u0000mn\u0005\u000b\u0000\u0000"+
		"nr\u0006\u000b\uffff\uffff\u0000op\u0005\r\u0000\u0000pr\u0006\u000b\uffff"+
		"\uffff\u0000qm\u0001\u0000\u0000\u0000qo\u0001\u0000\u0000\u0000r\u0017"+
		"\u0001\u0000\u0000\u0000st\u0005\b\u0000\u0000tu\u0003\u001a\r\u0000u"+
		"v\u0006\f\uffff\uffff\u0000v\u0019\u0001\u0000\u0000\u0000w\u0081\u0006"+
		"\r\uffff\uffff\u0000xy\u0003\u001c\u000e\u0000yz\u0006\r\uffff\uffff\u0000"+
		"z\u0081\u0001\u0000\u0000\u0000{|\u0003\u001c\u000e\u0000|}\u0005\u0002"+
		"\u0000\u0000}~\u0003\u001a\r\u0000~\u007f\u0006\r\uffff\uffff\u0000\u007f"+
		"\u0081\u0001\u0000\u0000\u0000\u0080w\u0001\u0000\u0000\u0000\u0080x\u0001"+
		"\u0000\u0000\u0000\u0080{\u0001\u0000\u0000\u0000\u0081\u001b\u0001\u0000"+
		"\u0000\u0000\u0082\u0083\u0003\u0016\u000b\u0000\u0083\u0084\u0005\u0006"+
		"\u0000\u0000\u0084\u0085\u0003\u0006\u0003\u0000\u0085\u0086\u0005\u0007"+
		"\u0000\u0000\u0086\u0087\u0006\u000e\uffff\uffff\u0000\u0087\u001d\u0001"+
		"\u0000\u0000\u0000\u00073CTekq\u0080";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}