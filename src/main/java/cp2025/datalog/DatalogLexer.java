// Generated from src/main/java/cp2025/datalog/DatalogLexer.g4 by ANTLR 4.13.2
package cp2025.datalog;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue", "this-escape"})
public class DatalogLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.13.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		Surrogate_id_SYMB_0=1, Surrogate_id_SYMB_1=2, Surrogate_id_SYMB_2=3, Surrogate_id_SYMB_3=4, 
		Surrogate_id_SYMB_4=5, Surrogate_id_SYMB_5=6, Surrogate_id_SYMB_6=7, Surrogate_id_SYMB_7=8, 
		COMMENT_antlr_builtin=9, MULTICOMMENT_antlr_builtin=10, LIdent=11, UIdent=12, 
		LSIdent=13, WS=14, ErrorToken=15;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"LETTER", "CAPITAL", "SMALL", "DIGIT", "Surrogate_id_SYMB_0", "Surrogate_id_SYMB_1", 
			"Surrogate_id_SYMB_2", "Surrogate_id_SYMB_3", "Surrogate_id_SYMB_4", 
			"Surrogate_id_SYMB_5", "Surrogate_id_SYMB_6", "Surrogate_id_SYMB_7", 
			"COMMENT_antlr_builtin", "MULTICOMMENT_antlr_builtin", "LIdent", "UIdent", 
			"LSIdent", "WS", "Escapable", "ErrorToken"
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


	public DatalogLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "DatalogLexer.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\u0004\u0000\u000f\u009b\u0006\uffff\uffff\u0002\u0000\u0007\u0000\u0002"+
		"\u0001\u0007\u0001\u0002\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002"+
		"\u0004\u0007\u0004\u0002\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002"+
		"\u0007\u0007\u0007\u0002\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002"+
		"\u000b\u0007\u000b\u0002\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e"+
		"\u0002\u000f\u0007\u000f\u0002\u0010\u0007\u0010\u0002\u0011\u0007\u0011"+
		"\u0002\u0012\u0007\u0012\u0002\u0013\u0007\u0013\u0001\u0000\u0001\u0000"+
		"\u0003\u0000,\b\u0000\u0001\u0001\u0001\u0001\u0001\u0002\u0001\u0002"+
		"\u0001\u0003\u0001\u0003\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004"+
		"\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004"+
		"\u0001\u0004\u0001\u0005\u0001\u0005\u0001\u0006\u0001\u0006\u0001\u0006"+
		"\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0007\u0001\u0007"+
		"\u0001\b\u0001\b\u0001\b\u0001\t\u0001\t\u0001\n\u0001\n\u0001\u000b\u0001"+
		"\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001"+
		"\u000b\u0001\u000b\u0001\f\u0001\f\u0001\f\u0001\f\u0005\f^\b\f\n\f\f"+
		"\fa\t\f\u0001\f\u0003\fd\b\f\u0001\f\u0001\f\u0003\fh\b\f\u0001\f\u0001"+
		"\f\u0001\r\u0001\r\u0001\r\u0001\r\u0001\u000e\u0001\u000e\u0001\u000e"+
		"\u0001\u000e\u0003\u000et\b\u000e\u0005\u000ev\b\u000e\n\u000e\f\u000e"+
		"y\t\u000e\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0003\u000f"+
		"\u007f\b\u000f\u0005\u000f\u0081\b\u000f\n\u000f\f\u000f\u0084\t\u000f"+
		"\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0003\u0010\u008a\b\u0010"+
		"\u0005\u0010\u008c\b\u0010\n\u0010\f\u0010\u008f\t\u0010\u0001\u0011\u0004"+
		"\u0011\u0092\b\u0011\u000b\u0011\f\u0011\u0093\u0001\u0011\u0001\u0011"+
		"\u0001\u0012\u0001\u0012\u0001\u0013\u0001\u0013\u0000\u0000\u0014\u0001"+
		"\u0000\u0003\u0000\u0005\u0000\u0007\u0000\t\u0001\u000b\u0002\r\u0003"+
		"\u000f\u0004\u0011\u0005\u0013\u0006\u0015\u0007\u0017\b\u0019\t\u001b"+
		"\n\u001d\u000b\u001f\f!\r#\u000e%\u0000\'\u000f\u0001\u0000\u0006\u0003"+
		"\u0000AZ\u00c0\u00d6\u00d8\u00de\u0003\u0000az\u00df\u00f6\u00f8\u00ff"+
		"\u0001\u000009\u0002\u0000\n\n\r\r\u0003\u0000\t\n\f\r  \u0006\u0000\""+
		"\"\\\\ffnnrrtt\u00a3\u0000\t\u0001\u0000\u0000\u0000\u0000\u000b\u0001"+
		"\u0000\u0000\u0000\u0000\r\u0001\u0000\u0000\u0000\u0000\u000f\u0001\u0000"+
		"\u0000\u0000\u0000\u0011\u0001\u0000\u0000\u0000\u0000\u0013\u0001\u0000"+
		"\u0000\u0000\u0000\u0015\u0001\u0000\u0000\u0000\u0000\u0017\u0001\u0000"+
		"\u0000\u0000\u0000\u0019\u0001\u0000\u0000\u0000\u0000\u001b\u0001\u0000"+
		"\u0000\u0000\u0000\u001d\u0001\u0000\u0000\u0000\u0000\u001f\u0001\u0000"+
		"\u0000\u0000\u0000!\u0001\u0000\u0000\u0000\u0000#\u0001\u0000\u0000\u0000"+
		"\u0000\'\u0001\u0000\u0000\u0000\u0001+\u0001\u0000\u0000\u0000\u0003"+
		"-\u0001\u0000\u0000\u0000\u0005/\u0001\u0000\u0000\u0000\u00071\u0001"+
		"\u0000\u0000\u0000\t3\u0001\u0000\u0000\u0000\u000b>\u0001\u0000\u0000"+
		"\u0000\r@\u0001\u0000\u0000\u0000\u000fG\u0001\u0000\u0000\u0000\u0011"+
		"I\u0001\u0000\u0000\u0000\u0013L\u0001\u0000\u0000\u0000\u0015N\u0001"+
		"\u0000\u0000\u0000\u0017P\u0001\u0000\u0000\u0000\u0019Y\u0001\u0000\u0000"+
		"\u0000\u001bk\u0001\u0000\u0000\u0000\u001do\u0001\u0000\u0000\u0000\u001f"+
		"z\u0001\u0000\u0000\u0000!\u0085\u0001\u0000\u0000\u0000#\u0091\u0001"+
		"\u0000\u0000\u0000%\u0097\u0001\u0000\u0000\u0000\'\u0099\u0001\u0000"+
		"\u0000\u0000),\u0003\u0003\u0001\u0000*,\u0003\u0005\u0002\u0000+)\u0001"+
		"\u0000\u0000\u0000+*\u0001\u0000\u0000\u0000,\u0002\u0001\u0000\u0000"+
		"\u0000-.\u0007\u0000\u0000\u0000.\u0004\u0001\u0000\u0000\u0000/0\u0007"+
		"\u0001\u0000\u00000\u0006\u0001\u0000\u0000\u000012\u0007\u0002\u0000"+
		"\u00002\b\u0001\u0000\u0000\u000034\u0005C\u0000\u000045\u0005o\u0000"+
		"\u000056\u0005n\u0000\u000067\u0005s\u0000\u000078\u0005t\u0000\u0000"+
		"89\u0005a\u0000\u00009:\u0005n\u0000\u0000:;\u0005t\u0000\u0000;<\u0005"+
		"s\u0000\u0000<=\u0005:\u0000\u0000=\n\u0001\u0000\u0000\u0000>?\u0005"+
		",\u0000\u0000?\f\u0001\u0000\u0000\u0000@A\u0005R\u0000\u0000AB\u0005"+
		"u\u0000\u0000BC\u0005l\u0000\u0000CD\u0005e\u0000\u0000DE\u0005s\u0000"+
		"\u0000EF\u0005:\u0000\u0000F\u000e\u0001\u0000\u0000\u0000GH\u0005.\u0000"+
		"\u0000H\u0010\u0001\u0000\u0000\u0000IJ\u0005:\u0000\u0000JK\u0005-\u0000"+
		"\u0000K\u0012\u0001\u0000\u0000\u0000LM\u0005(\u0000\u0000M\u0014\u0001"+
		"\u0000\u0000\u0000NO\u0005)\u0000\u0000O\u0016\u0001\u0000\u0000\u0000"+
		"PQ\u0005Q\u0000\u0000QR\u0005u\u0000\u0000RS\u0005e\u0000\u0000ST\u0005"+
		"r\u0000\u0000TU\u0005i\u0000\u0000UV\u0005e\u0000\u0000VW\u0005s\u0000"+
		"\u0000WX\u0005:\u0000\u0000X\u0018\u0001\u0000\u0000\u0000YZ\u0005/\u0000"+
		"\u0000Z[\u0005/\u0000\u0000[_\u0001\u0000\u0000\u0000\\^\b\u0003\u0000"+
		"\u0000]\\\u0001\u0000\u0000\u0000^a\u0001\u0000\u0000\u0000_]\u0001\u0000"+
		"\u0000\u0000_`\u0001\u0000\u0000\u0000`g\u0001\u0000\u0000\u0000a_\u0001"+
		"\u0000\u0000\u0000bd\u0005\r\u0000\u0000cb\u0001\u0000\u0000\u0000cd\u0001"+
		"\u0000\u0000\u0000de\u0001\u0000\u0000\u0000eh\u0005\n\u0000\u0000fh\u0005"+
		"\u0000\u0000\u0001gc\u0001\u0000\u0000\u0000gf\u0001\u0000\u0000\u0000"+
		"hi\u0001\u0000\u0000\u0000ij\u0006\f\u0000\u0000j\u001a\u0001\u0000\u0000"+
		"\u0000kl\u0001\u0000\u0000\u0000lm\u0001\u0000\u0000\u0000mn\u0006\r\u0000"+
		"\u0000n\u001c\u0001\u0000\u0000\u0000ow\u0003\u0005\u0002\u0000pv\u0005"+
		"_\u0000\u0000qt\u0003\u0007\u0003\u0000rt\u0003\u0005\u0002\u0000sq\u0001"+
		"\u0000\u0000\u0000sr\u0001\u0000\u0000\u0000tv\u0001\u0000\u0000\u0000"+
		"up\u0001\u0000\u0000\u0000us\u0001\u0000\u0000\u0000vy\u0001\u0000\u0000"+
		"\u0000wu\u0001\u0000\u0000\u0000wx\u0001\u0000\u0000\u0000x\u001e\u0001"+
		"\u0000\u0000\u0000yw\u0001\u0000\u0000\u0000z\u0082\u0003\u0003\u0001"+
		"\u0000{\u0081\u0005_\u0000\u0000|\u007f\u0003\u0007\u0003\u0000}\u007f"+
		"\u0003\u0003\u0001\u0000~|\u0001\u0000\u0000\u0000~}\u0001\u0000\u0000"+
		"\u0000\u007f\u0081\u0001\u0000\u0000\u0000\u0080{\u0001\u0000\u0000\u0000"+
		"\u0080~\u0001\u0000\u0000\u0000\u0081\u0084\u0001\u0000\u0000\u0000\u0082"+
		"\u0080\u0001\u0000\u0000\u0000\u0082\u0083\u0001\u0000\u0000\u0000\u0083"+
		" \u0001\u0000\u0000\u0000\u0084\u0082\u0001\u0000\u0000\u0000\u0085\u008d"+
		"\u0003\u0005\u0002\u0000\u0086\u008c\u0005_\u0000\u0000\u0087\u008a\u0003"+
		"\u0007\u0003\u0000\u0088\u008a\u0003\u0001\u0000\u0000\u0089\u0087\u0001"+
		"\u0000\u0000\u0000\u0089\u0088\u0001\u0000\u0000\u0000\u008a\u008c\u0001"+
		"\u0000\u0000\u0000\u008b\u0086\u0001\u0000\u0000\u0000\u008b\u0089\u0001"+
		"\u0000\u0000\u0000\u008c\u008f\u0001\u0000\u0000\u0000\u008d\u008b\u0001"+
		"\u0000\u0000\u0000\u008d\u008e\u0001\u0000\u0000\u0000\u008e\"\u0001\u0000"+
		"\u0000\u0000\u008f\u008d\u0001\u0000\u0000\u0000\u0090\u0092\u0007\u0004"+
		"\u0000\u0000\u0091\u0090\u0001\u0000\u0000\u0000\u0092\u0093\u0001\u0000"+
		"\u0000\u0000\u0093\u0091\u0001\u0000\u0000\u0000\u0093\u0094\u0001\u0000"+
		"\u0000\u0000\u0094\u0095\u0001\u0000\u0000\u0000\u0095\u0096\u0006\u0011"+
		"\u0000\u0000\u0096$\u0001\u0000\u0000\u0000\u0097\u0098\u0007\u0005\u0000"+
		"\u0000\u0098&\u0001\u0000\u0000\u0000\u0099\u009a\t\u0000\u0000\u0000"+
		"\u009a(\u0001\u0000\u0000\u0000\u000f\u0000+_cgsuw~\u0080\u0082\u0089"+
		"\u008b\u008d\u0093\u0001\u0006\u0000\u0000";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}