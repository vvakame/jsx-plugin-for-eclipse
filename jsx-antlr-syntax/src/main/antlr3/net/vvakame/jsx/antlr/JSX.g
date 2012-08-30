grammar JSX;

options {
    language=Java;
    backtrack=true;
    memoize=true;
    output=AST;
}

@header {
    package net.vvakame.jsx.antlr;
}

@lexer::header {
    package net.vvakame.jsx.antlr;
}

// this syntax base is https://github.com/jsx/JSX/blob/4053b064a59c387dfcfcc9eb3fbd85750cc0a658/src/parser.js
// commit 4053b064a59c387dfcfcc9eb3fbd85750cc0a658

programFile
	:	 importStatement* classDefinition* EOF
	;

// https://github.com/jsx/JSX/blob/4053b064a59c387dfcfcc9eb3fbd85750cc0a658/src/parser.js#L926
importStatement
	:	'import' (IDENT (',' IDENT)* 'from')? string ('into' IDENT)? ';'
	;

// https://github.com/jsx/JSX/blob/4053b064a59c387dfcfcc9eb3fbd85750cc0a658/src/parser.js#L986
classDefinition
	:	_classModifiers* (_classDef | _interfaceDef | _mixinDef)
	;

// https://github.com/jsx/JSX/blob/4053b064a59c387dfcfcc9eb3fbd85750cc0a658/src/parser.js#L1016
_classModifiers
	:	'abstract' | 'final' | 'native' | '__fake__'
	;

// https://github.com/jsx/JSX/blob/4053b064a59c387dfcfcc9eb3fbd85750cc0a658/src/parser.js#L1038
_classDef
	:	'class' IDENT formalTypeArguments? ('extends' objectTypeDeclaration)? ('implements' objectTypeDeclaration (',' objectTypeDeclaration)*)? '{' memberDefinition* '}'
	;

// https://github.com/jsx/JSX/blob/4053b064a59c387dfcfcc9eb3fbd85750cc0a658/src/parser.js#L1038
_interfaceDef
	:	'interface' IDENT formalTypeArguments? ('implements' objectTypeDeclaration (',' objectTypeDeclaration)*)? '{' memberDefinition* '}'
	;

// https://github.com/jsx/JSX/blob/4053b064a59c387dfcfcc9eb3fbd85750cc0a658/src/parser.js#L1038
_mixinDef	
	:	'mixin' IDENT formalTypeArguments? '{' memberDefinition* '}'
	;

// https://github.com/jsx/JSX/blob/4053b064a59c387dfcfcc9eb3fbd85750cc0a658/src/parser.js#L1090
// https://github.com/jsx/JSX/blob/4053b064a59c387dfcfcc9eb3fbd85750cc0a658/src/parser.js#L1159
memberDefinition
	: 	_memberDefinitionModifiers* 'const' IDENT (':' typeDeclaration)? ('=' assignExpr)? ';'
	|	_memberDefinitionModifiers* 'function' functionDefinition
	|	_memberDefinitionModifiers* 'var' IDENT (':' typeDeclaration)? ('=' assignExpr)? ';'
	;

_memberDefinitionModifiers
	:	'static' | 'abstract' | 'override' | 'final' | 'native' | '__readonly__' | 'inline' | '__pure__'
	;

// https://github.com/jsx/JSX/blob/4053b064a59c387dfcfcc9eb3fbd85750cc0a658/src/parser.js#L1264
functionDefinition
	:	'constructor' formalTypeArguments '(' functionArgumentsExpr '{' initializeBlock
	|	IDENT formalTypeArguments '(' functionArgumentsExpr ':' typeDeclaration (';' | '{' block)
	;

// https://github.com/jsx/JSX/blob/4053b064a59c387dfcfcc9eb3fbd85750cc0a658/src/parser.js#L1355
formalTypeArguments
	:	('.' '<' IDENT (',' IDENT)* '>')?
	;

// https://github.com/jsx/JSX/blob/4053b064a59c387dfcfcc9eb3fbd85750cc0a658/src/parser.js#L1375
actualTypeArguments
	:	('.' '<' typeDeclaration (',' typeDeclaration)* '>')?
	;

// https://github.com/jsx/JSX/blob/4053b064a59c387dfcfcc9eb3fbd85750cc0a658/src/parser.js#L1398
typeDeclaration
	:	'void'
	|	typeDeclarationNoArrayNoVoid ('[' ']')?
	;

// https://github.com/jsx/JSX/blob/4053b064a59c387dfcfcc9eb3fbd85750cc0a658/src/parser.js#L1422
typeDeclarationNoArrayNoVoid
	:	'MayBeUndefined'
	|	'Nullable' nullableTypeDeclaration
	|	'variant'
	|	primaryTypeDeclaration
	;

// https://github.com/jsx/JSX/blob/4053b064a59c387dfcfcc9eb3fbd85750cc0a658/src/parser.js#L1440
nullableTypeDeclaration
	:	'.' '<' typeDeclaration '>'
	;

// https://github.com/jsx/JSX/blob/4053b064a59c387dfcfcc9eb3fbd85750cc0a658/src/parser.js#L1466
primaryTypeDeclaration
	:	'(' lightFunctionTypeDeclaration
	|	'function' functionTypeDeclaration
	|	'boolean'
	|	'int'
	|	'number'
	|	'string'
	|	objectTypeDeclaration
	;

// https://github.com/jsx/JSX/blob/4053b064a59c387dfcfcc9eb3fbd85750cc0a658/src/parser.js#L1490
objectTypeDeclaration
	:	(('super' | IDENT) actualTypeArguments)? ('.' IDENT)? actualTypeArguments
	;

// https://github.com/jsx/JSX/blob/4053b064a59c387dfcfcc9eb3fbd85750cc0a658/src/parser.js#L1519
lightFunctionTypeDeclaration
	:	'...'? typeDeclaration (',' '...'? typeDeclaration)* ')' '->' typeDeclaration
	;

// https://github.com/jsx/JSX/blob/4053b064a59c387dfcfcc9eb3fbd85750cc0a658/src/parser.js#L1552
functionTypeDeclaration
	// :	'(' '...'? typeDeclaration (',' '...'? typeDeclaration)* ')' ':' typeDeclaration
	:	IDENT? '(' ')' ':' typeDeclaration
	|	IDENT? '(' '...' IDENT? ':' typeDeclaration ')' ':' typeDeclaration
	|	IDENT? '(' (IDENT? ':' typeDeclaration) (',' IDENT? ':' typeDeclaration)* ',' '...' IDENT? ':' typeDeclaration ')' ':' typeDeclaration
	|	IDENT? '(' (IDENT? ':' typeDeclaration) (',' IDENT? ':' typeDeclaration)* ')' ':' typeDeclaration
	;

// https://github.com/jsx/JSX/blob/4053b064a59c387dfcfcc9eb3fbd85750cc0a658/src/parser.js#L1598
initializeBlock
	:	constructorInvocationStatement* (block? | '}')
	;

// https://github.com/jsx/JSX/blob/4053b064a59c387dfcfcc9eb3fbd85750cc0a658/src/parser.js#L1610
block
	:	statement* '}'
	;

// https://github.com/jsx/JSX/blob/4053b064a59c387dfcfcc9eb3fbd85750cc0a658/src/parser.js#L1621
statement
	:	';'
	|	'var' variableStatement
	|	'if' ifStatement
	|	'continue' continueStatement
	|	'break' breakStatement
	|	'return' returnStatement
	|	'throw' throwStatement
	|	'try' tryStatement
	|	'assert' assertStatement
	|	'log' logStatement
	|	'delete' deleteStatement
	|	'debugger' debuggerStatement
	|	'function' functionStatement
	|	'void'
	|	'{' block
	|	expr ';'
	|	_statementBlock
	;

_statementBlock
	:	(IDENT ':')? 'do' doWhileStatement
	|	(IDENT ':')? 'while' whileStatement
	|	(IDENT ':')? 'for' forStatement
	|	(IDENT ':')? 'switch' switchStatement
	;

// https://github.com/jsx/JSX/blob/4053b064a59c387dfcfcc9eb3fbd85750cc0a658/src/parser.js#L1696
constructorInvocationStatement
	:	('super' | 'this' | objectTypeDeclaration) '(' argsExpr ';'
	;

// https://github.com/jsx/JSX/blob/4053b064a59c387dfcfcc9eb3fbd85750cc0a658/src/parser.js#L1736
variableStatement
	:	variableDeclarations ';'
	;

// https://github.com/jsx/JSX/blob/4053b064a59c387dfcfcc9eb3fbd85750cc0a658/src/parser.js#L1748
functionStatement
	:	IDENT functionExpr
	;

// https://github.com/jsx/JSX/blob/4053b064a59c387dfcfcc9eb3fbd85750cc0a658/src/parser.js#L1764
ifStatement
	:	'(' expr ')' subStatements ('else' subStatements)?
	;

// https://github.com/jsx/JSX/blob/4053b064a59c387dfcfcc9eb3fbd85750cc0a658/src/parser.js#L1781
doWhileStatement
	:	subStatements 'while' '(' expr ')'
	;

// https://github.com/jsx/JSX/blob/4053b064a59c387dfcfcc9eb3fbd85750cc0a658/src/parser.js#L1796
whileStatement
	:	'(' expr ')' subStatements
	;

// https://github.com/jsx/JSX/blob/4053b064a59c387dfcfcc9eb3fbd85750cc0a658/src/parser.js#L1809
forStatement
	:	forInStatement
	|	'(' ('var' variableDeclarations | expr)? ';' expr? ';' expr? ')' subStatements
	;

// https://github.com/jsx/JSX/blob/4053b064a59c387dfcfcc9eb3fbd85750cc0a658/src/parser.js#L1866
forInStatement
	:	'(' ('var' variableDeclaration | lhsExpr) 'in' expr ')' subStatements
	;

// https://github.com/jsx/JSX/blob/4053b064a59c387dfcfcc9eb3fbd85750cc0a658/src/parser.js#L1889
continueStatement
	:	IDENT? ';'
	;

// https://github.com/jsx/JSX/blob/4053b064a59c387dfcfcc9eb3fbd85750cc0a658/src/parser.js#L1897
breakStatement
	:	IDENT? ';'
	;

// https://github.com/jsx/JSX/blob/4053b064a59c387dfcfcc9eb3fbd85750cc0a658/src/parser.js#L1905
returnStatement
	:	';'
	|	expr ';'
	;

// https://github.com/jsx/JSX/blob/4053b064a59c387dfcfcc9eb3fbd85750cc0a658/src/parser.js#L1919
switchStatement
	:	'(' expr ')' '{' (('case' expr ':' | 'default' ':') statement*)* '}'
	;

// https://github.com/jsx/JSX/blob/4053b064a59c387dfcfcc9eb3fbd85750cc0a658/src/parser.js#L1981
throwStatement
	:	expr
	;

// https://github.com/jsx/JSX/blob/4053b064a59c387dfcfcc9eb3fbd85750cc0a658/src/parser.js#L1989
tryStatement
	:	'{' block ('catch' '(' IDENT ':' typeDeclaration ')' '{' block)* ('finally' '{' block )?
	;

// https://github.com/jsx/JSX/blob/4053b064a59c387dfcfcc9eb3fbd85750cc0a658/src/parser.js#L2037
assertStatement
	:	expr ';'
	;

// https://github.com/jsx/JSX/blob/4053b064a59c387dfcfcc9eb3fbd85750cc0a658/src/parser.js#L2047
logStatement
	:	assignExpr (',' assignExpr)* ';'
	;

// https://github.com/jsx/JSX/blob/4053b064a59c387dfcfcc9eb3fbd85750cc0a658/src/parser.js#L2065
deleteStatement
	:	expr ';'
	;

// https://github.com/jsx/JSX/blob/4053b064a59c387dfcfcc9eb3fbd85750cc0a658/src/parser.js#L2075
debuggerStatement
	:
	;

// https://github.com/jsx/JSX/blob/4053b064a59c387dfcfcc9eb3fbd85750cc0a658/src/parser.js#L2080
subStatements
	:	statement
	;

// https://github.com/jsx/JSX/blob/4053b064a59c387dfcfcc9eb3fbd85750cc0a658/src/parser.js#L2087
variableDeclarations
	:	variableDeclaration (',' variableDeclaration)*
	;

// https://github.com/jsx/JSX/blob/4053b064a59c387dfcfcc9eb3fbd85750cc0a658/src/parser.js#L2103
variableDeclaration
	:	IDENT (':' typeDeclaration)? ('=' assignExpr)?
	;

// https://github.com/jsx/JSX/blob/4053b064a59c387dfcfcc9eb3fbd85750cc0a658/src/parser.js#L2125
expr
	:	assignExpr (',' assignExpr)*
	;

// https://github.com/jsx/JSX/blob/4053b064a59c387dfcfcc9eb3fbd85750cc0a658/src/parser.js#L2139
assignExpr
	:	lhsExpr _assignExprOpe assignExpr
	|	condExpr
	;

_assignExprOpe
	:	('=' | '*=' | '/=' | '%=' | '+=' | '-=' | '<<=' | '>>=' | '>>>=' | '&=' | '^=' | '|=')
	;

// https://github.com/jsx/JSX/blob/4053b064a59c387dfcfcc9eb3fbd85750cc0a658/src/parser.js#L2158
condExpr
	:	lorExpr ('?' assignExpr? ':' assignExpr)?
	;

// https://github.com/jsx/JSX/blob/4053b064a59c387dfcfcc9eb3fbd85750cc0a658/src/parser.js#L2196
lorExpr
	:	landExpr ('||' landExpr)*
	;

// https://github.com/jsx/JSX/blob/4053b064a59c387dfcfcc9eb3fbd85750cc0a658/src/parser.js#L2202
landExpr
	:	borExpr ('&&' borExpr)*
	;

// https://github.com/jsx/JSX/blob/4053b064a59c387dfcfcc9eb3fbd85750cc0a658/src/parser.js#L2208
// FIXME apply excludePattern
borExpr
	:	bxorExpr ('|' bxorExpr)*
	;

// https://github.com/jsx/JSX/blob/4053b064a59c387dfcfcc9eb3fbd85750cc0a658/src/parser.js#L2214
bxorExpr
	:	bandExpr ('^' bandExpr)*
	;

// https://github.com/jsx/JSX/blob/4053b064a59c387dfcfcc9eb3fbd85750cc0a658/src/parser.js#L2220
// FIXME apply excludePattern
bandExpr
	:	eqExpr ('&' eqExpr)*
	;

// https://github.com/jsx/JSX/blob/4053b064a59c387dfcfcc9eb3fbd85750cc0a658/src/parser.js#L2226
eqExpr
	:	relExpr (('==' | '!=') relExpr)*
	;

// https://github.com/jsx/JSX/blob/4053b064a59c387dfcfcc9eb3fbd85750cc0a658/src/parser.js#L2232
// FIXME in?
relExpr
	:	shiftExpr (('<=' | '>=' | '<' | '>' | 'in') shiftExpr)*
	;

// https://github.com/jsx/JSX/blob/4053b064a59c387dfcfcc9eb3fbd85750cc0a658/src/parser.js#L2244
shiftExpr
	:	addExpr (_shiftExprOpe addExpr)*
	;

_shiftExprOpe
	:	'>>>'
	|	'<<'
	|	('>' '>')=> t1='>' t2='>'
		{
			$t1.getLine() == $t2.getLine() && 
			$t1.getCharPositionInLine() + 1 == $t2.getCharPositionInLine()
		}?
	;

// https://github.com/jsx/JSX/blob/4053b064a59c387dfcfcc9eb3fbd85750cc0a658/src/parser.js#L2251
// FIXME apply excludePattern
addExpr
	:	mulExpr (('+' | '-') mulExpr)*
	;

// https://github.com/jsx/JSX/blob/4053b064a59c387dfcfcc9eb3fbd85750cc0a658/src/parser.js#L2260
mulExpr
	:	unaryExpr (('*' | '/' | '%') unaryExpr)*
	;

// https://github.com/jsx/JSX/blob/4053b064a59c387dfcfcc9eb3fbd85750cc0a658/src/parser.js#L2266
unaryExpr
	:	('++' | '--' | '+' | '-' | '!' | 'typeof') unaryExpr
	|	asExpr
	;

// https://github.com/jsx/JSX/blob/4053b064a59c387dfcfcc9eb3fbd85750cc0a658/src/parser.js#L2290
asExpr
	:	postfixExpr ('as' '__noconvert__'? typeDeclaration)*
	;

// https://github.com/jsx/JSX/blob/4053b064a59c387dfcfcc9eb3fbd85750cc0a658/src/parser.js#L2305
postfixExpr
	:	lhsExpr ('++' | '--' | 'instanceof' typeDeclaration)?
	;

// https://github.com/jsx/JSX/blob/4053b064a59c387dfcfcc9eb3fbd85750cc0a658/src/parser.js#L2321
lhsExpr
	:	'super' superExpr
	|	'(' lambdaExpr _lhsExprSub*
	|	'function' functionExpr _lhsExprSub*
	|	'new' newExpr _lhsExprSub*
	|	primaryExpr _lhsExprSub*
	;

_lhsExprSub
	:	'(' argsExpr
	|	'[' expr ']'
	|	'.' IDENT actualTypeArguments
	;

// https://github.com/jsx/JSX/blob/4053b064a59c387dfcfcc9eb3fbd85750cc0a658/src/parser.js#L2381
newExpr
	:	typeDeclarationNoArrayNoVoid ('[' assignExpr? ']')* ('(' argsExpr)?
	;

// https://github.com/jsx/JSX/blob/4053b064a59c387dfcfcc9eb3fbd85750cc0a658/src/parser.js#L2415
superExpr
	:	'.' IDENT '(' argsExpr
	;

// https://github.com/jsx/JSX/blob/4053b064a59c387dfcfcc9eb3fbd85750cc0a658/src/parser.js#L2431
lambdaExpr
	:	functionArgumentsExpr (':' typeDeclaration)? '->' lambdaBody
	;

// https://github.com/jsx/JSX/blob/4053b064a59c387dfcfcc9eb3fbd85750cc0a658/src/parser.js#L2449
lambdaBody
	:	'{' block
	|	expr
	;

// https://github.com/jsx/JSX/blob/4053b064a59c387dfcfcc9eb3fbd85750cc0a658/src/parser.js#L2471
functionExpr
	:	'(' functionArgumentsExpr ':'? typeDeclaration? '{' block
	;

// https://github.com/jsx/JSX/blob/4053b064a59c387dfcfcc9eb3fbd85750cc0a658/src/parser.js#L2554
primaryExpr
	:	IDENT objectTypeDeclaration?
	|	'this'
	|	'undefined'
	|	'null' nullLiteral
	|	'false'
	|	'true'
	|	'[' arrayLiteral
	|	'{' hashLiteral
	|	'(' expr ')'
	|	string
	|	numberLiteral
	|	REGEXP_LITERAL
	;

// https://github.com/jsx/JSX/blob/4053b064a59c387dfcfcc9eb3fbd85750cc0a658/src/parser.js#L2600
nullLiteral
	:	(':' typeDeclaration)?
	;

// https://github.com/jsx/JSX/blob/4053b064a59c387dfcfcc9eb3fbd85750cc0a658/src/parser.js#L2613
arrayLiteral
	:	(assignExpr (',' assignExpr)*)? ']' (':' typeDeclaration)?
	;

// https://github.com/jsx/JSX/blob/4053b064a59c387dfcfcc9eb3fbd85750cc0a658/src/parser.js#L2633
hashLiteral
	:	((IDENT | numberLiteral | string) ':' assignExpr (',' (IDENT | numberLiteral | string) ':' assignExpr)*)? '}' (':' typeDeclaration)?
	;

// https://github.com/jsx/JSX/blob/4053b064a59c387dfcfcc9eb3fbd85750cc0a658/src/parser.js#L2666
functionArgumentsExpr
	:	')'
	|	('...' IDENT | IDENT)? (':' typeDeclaration)? (',' ('...' IDENT | IDENT)? (':' typeDeclaration)?)* ')'
	;

// https://github.com/jsx/JSX/blob/4053b064a59c387dfcfcc9eb3fbd85750cc0a658/src/parser.js#L2711
argsExpr
	:	')'
	|	assignExpr (',' assignExpr)* ')'
	;

// literal

string
	:	DOUBLE_QUOTED
	|	SINGLE_QUOTED
	;

numberLiteral
	:	NUMBER_LITERAL
	|	INTEGER_LITERAL
	;


MULTILINE_COMMENT
	:	'/*' ( options {greedy=false;} : . )* '*/' {$channel=HIDDEN;}
	;

SIGLELINE_COMMENT
	:	'//' ~('\n'|'\r')* '\r'? '\n' {$channel=HIDDEN;}
	;

IDENT
	:	('a'..'z' | 'A'..'Z' | '_') ('a'..'z' | 'A'..'Z' | '_' | '0'..'9')*
	;

fragment
CHAR_EXCLUDE_DOUBLE_QUOTED_SPECIAL
	:	~('"' | '\\')
	;

fragment
DOUBLE_QUOTED_CHARS
	:	CHAR_EXCLUDE_DOUBLE_QUOTED_SPECIAL* ('\\' . CHAR_EXCLUDE_DOUBLE_QUOTED_SPECIAL*)*
	;

DOUBLE_QUOTED
	:	'"' DOUBLE_QUOTED_CHARS  '"'
	;

fragment
CHAR_EXCLUDE_SINGLE_QUOTED_SPECIAL
	:	~('\'' | '\\')
	;

fragment
SINGLE_QUOTED_CHARS
	:	CHAR_EXCLUDE_SINGLE_QUOTED_SPECIAL* ('\\' . CHAR_EXCLUDE_SINGLE_QUOTED_SPECIAL*)*
	;

SINGLE_QUOTED
	:	'\'' SINGLE_QUOTED_CHARS '\''
	;

fragment
CHAR_EXCLUDE_REGEXP_SPECIAL
	:	~('/' | '\\')
	;

fragment
REGEXP_CHARS
	:	CHAR_EXCLUDE_REGEXP_SPECIAL* ('\\' . CHAR_EXCLUDE_REGEXP_SPECIAL*)*
	;

REGEXP_LITERAL
	:	'/' REGEXP_CHARS '/' ('m' | 'g' | 'i')*
	;


fragment
DECIMAL_INTEGER_LITERAL
	:	'0'
	|	('1'..'9') ('0'..'9')*
	;

fragment
EXPONENT_PART
	:	('e' | 'E') ('+' | '-')? ('0'..'9')+
	;

NUMBER_LITERAL
	:	DECIMAL_INTEGER_LITERAL( '.' ('0'..'9')*)? EXPONENT_PART?
	|	'.' ('0'..'9')+ EXPONENT_PART?
	|	'NaN'
	|	'Infinity'
	;

INTEGER_LITERAL
	:	'0' ('x' | 'X') ('0' .. '9' | 'a' .. 'f' | 'A' .. 'F')+
	|	DECIMAL_INTEGER_LITERAL
	;

WS
	:	(' ' | '\r' | '\t' | '\u000C' | '\n') {$channel=HIDDEN;}
	;
