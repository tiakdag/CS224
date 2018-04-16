import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PushbackReader;

import jdk.nashorn.internal.ir.BreakableNode;


public class JackTokenizer {

	
	private PushbackReader reader;
	private boolean hasMoreTokens;
	private char ch;
	private TokenType tokenType;
	private KeyWord keyWord;
	private char symbol;
	private String identifier;


	public JackTokenizer(File src) {
		hasMoreTokens = true;

		try {
			reader = new PushbackReader(new FileReader(src));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.exit(1);
		}	
	}

	public boolean hasMoreTokens() {
		return hasMoreTokens;
	}
	
	public TokenType tokenType(){
		return tokenType;
	}
	
	public KeyWord keyWord(){
		return keyWord;
	}
	
	public char symbol(){
		return symbol;
	}
	
	public String identifier(){
		return identifier;
	}
	
	public int intVal(){
		try{
			return Integer.parseInt(identifier);
		}catch (NumberFormatException e){
			e.printStackTrace();
			System.exit(1);
		}
		return -1;
	}
	
	// Return string without the enclosing quotes.
	public String stringVal(){
		return identifier;
	}
	
	private void getNextChar(){
		try{
			int data = reader.read();
			if (data == -1)
				hasMoreTokens = false;
			else
				ch = (char)data;
		} catch(IOException e){
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	private void unread(){
		try{
			reader.unread(ch);
		} catch(IOException e){
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	public void advance() {
		// Complete to advance over next token, setting values in the fields
		identifier ="";
		this.getNextChar();
		String[] keyList = new String[] {"class","method","function","constructor","int","boolean","char","void","var","static","field","let","do","if","else","while","return","true","false","null","this"};
		char[] symbolList = new char [] {'{','}','(',')','[',']','.',',',';','+','-','*','&','|','<','>','=','-','~'};
		State state = State.START;
		while(hasMoreTokens) {
			switch (state) {
			case START:
				if(Character.isDigit(ch)) {
					identifier += ch;
					state = State.NUMBER;
				}else if (ch == '"'){
					state = State.STRING;
				}else if(Character.isLetter(ch)) {
					identifier += ch;
					state = State.IN_ID;
				}else if(ch =='/') {
					state = State.SLASH;
				}for(int i =0;i<=18;i++) {
					if(ch == symbolList[i]) {
						symbol = ch;
						tokenType=tokenType.SYMBOL;
						return;
					}
				}
				break;
			case NUMBER:
				if(!(Character.isDigit(ch))) {
					this.unread();
					tokenType = tokenType.INT_CONST;	
					return;
				}else {
					identifier += ch;
					state = State.NUMBER;
				}
				break;
			case STRING:
				if(ch=='"') {
					//this.unread();
					tokenType = tokenType.STRING_CONST;
					return;
				}else {
					identifier += ch;
					state = State.STRING;
				}
				break;
			case IN_ID:
				if(Character.isDigit(ch) || Character.isLetter(ch) || ch =='_') {
					identifier += ch;
					state = State.IN_ID;
				}else {
					this.unread();
					state = State.ID;
				}
				break;
			case ID:
						switch(identifier) {
						case "class":
							this.unread();
							tokenType = tokenType.KEYWORD;
							keyWord = keyWord.CLASS;
							return;
						case "method":
							this.unread();
							tokenType = tokenType.KEYWORD;
							keyWord = keyWord.METHOD;
							return;
						case "function":
							this.unread();
							tokenType = tokenType.KEYWORD;
							keyWord=keyWord.FUNCTION;
							return;
						case "constructor":
							this.unread();
							tokenType = tokenType.KEYWORD;
							keyWord=keyWord.CONSTRUCTOR;
							return;
						case "int":
							this.unread();
							tokenType = tokenType.KEYWORD;
							keyWord=keyWord.INT;
							return;
						case "boolean":
							this.unread();
							tokenType = tokenType.KEYWORD;
							keyWord=keyWord.BOOLEAN;
							return;
						case "char":
							this.unread();
							tokenType = tokenType.KEYWORD;
							keyWord=keyWord.CHAR;
							return;
						case "void":
							this.unread();
							tokenType = tokenType.KEYWORD;
							keyWord=keyWord.VOID;
							return;
						case "var":
							this.unread();
							tokenType = tokenType.KEYWORD;
							keyWord=keyWord.VAR;
							return;
						case "static":
							this.unread();
							tokenType = tokenType.KEYWORD;
							keyWord=keyWord.STATIC;
							return;
						case "field":
							this.unread();
							tokenType = tokenType.KEYWORD;
							keyWord=keyWord.FIELD;
							return;
						case "let":
							this.unread();
							tokenType = tokenType.KEYWORD;
							keyWord=keyWord.LET;
							return;
						case "do":
							this.unread();
							tokenType = tokenType.KEYWORD;
							keyWord=keyWord.DO;
							return;
						case "if":
							this.unread();
							tokenType = tokenType.KEYWORD;
							keyWord=keyWord.IF;
							return;
						case "else":
							this.unread();
							tokenType = tokenType.KEYWORD;
							keyWord=keyWord.ELSE;
							return;
						case "while":
							this.unread();
							tokenType = tokenType.KEYWORD;
							keyWord=keyWord.WHILE;
							return;
						case "return":
							this.unread();
							tokenType = tokenType.KEYWORD;
							keyWord=keyWord.RETURN;
							return;
						case "true":
							this.unread();
							tokenType = tokenType.KEYWORD;
							keyWord=keyWord.TRUE;
							return;
						case "false":
							this.unread();
							tokenType = tokenType.KEYWORD;
							keyWord=keyWord.FALSE;
							return;
						case "null":
							this.unread();
							tokenType = tokenType.KEYWORD;
							keyWord=keyWord.NULL;
							return;
						case "this":
							this.unread();
							tokenType = tokenType.KEYWORD;
							keyWord=keyWord.THIS;
							return;
						default:
							this.unread();
							tokenType = tokenType.IDENTIFIER;
							break;
						}
				return;
			case SLASH:
				if(ch == '*' ) {
					state=State.INBLOCK;	
				}else if (ch =='/'){
					state=State.LINECOMMENT;
				}else {
					this.unread();
					symbol = '/';
					tokenType = tokenType.SYMBOL;
					return;
				}
			break;
			case LINECOMMENT:
				if(ch != '\n') {
					state = State.LINECOMMENT;
				}else {
					state=State.START;
				}
			break;
			case INBLOCK:
				
				if (ch != '*') {
					state=State.INBLOCK;
				}else if(ch =='*') {
					state = State.STARSLASH;
				}
				break;
			case STARSLASH:
				if (ch =='/') {
					state=State.START;
				}else {
					state=State.INBLOCK;
				}
				break;
			}
			this.getNextChar();
		}
		
	}


}
