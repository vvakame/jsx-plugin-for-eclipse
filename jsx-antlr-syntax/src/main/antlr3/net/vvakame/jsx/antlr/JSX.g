grammar JSX;

options {
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

programFile
	:	 importStatement* classDefinition*
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
	:	'class' IDENT formalTypeArguments? ('extends' IDENT)? ('implements' IDENT (',' IDENT)*)? '{' memberDefinition* '}'
	;

// https://github.com/jsx/JSX/blob/4053b064a59c387dfcfcc9eb3fbd85750cc0a658/src/parser.js#L1038
// TODO interface is not allowed static member
_interfaceDef
	:	'interface' IDENT formalTypeArguments? '{' memberDefinition* '}'
	;
	
// https://github.com/jsx/JSX/blob/4053b064a59c387dfcfcc9eb3fbd85750cc0a658/src/parser.js#L1038
// TODO interface is not allowed static member
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
	:	IDENT formalTypeArguments? '(' functionArgumentsExpr? ')' (':' typeDeclaration)? '{' (initializeBlock | block)?
	;

// https://github.com/jsx/JSX/blob/4053b064a59c387dfcfcc9eb3fbd85750cc0a658/src/parser.js#L1355
formalTypeArguments
	:	'.' '<' IDENT (',' IDENT)* '>'
	;

// https://github.com/jsx/JSX/blob/4053b064a59c387dfcfcc9eb3fbd85750cc0a658/src/parser.js#L1375
actualTypeArguments
	:	'.' '<' typeDeclaration (',' typeDeclaration)+
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
// TODO is this right?
objectTypeDeclaration
	:	(('super' | IDENT) '.' IDENT)? actualTypeArguments templateTypeDeclaration
	;

// https://github.com/jsx/JSX/blob/4053b064a59c387dfcfcc9eb3fbd85750cc0a658/src/parser.js#L1507
// TODO Can this declaration is removal?
templateTypeDeclaration
	:
	;

// https://github.com/jsx/JSX/blob/4053b064a59c387dfcfcc9eb3fbd85750cc0a658/src/parser.js#L1519
lightFunctionTypeDeclaration
	:	'...'? typeDeclaration (',' '...'? typeDeclaration)* ')' '->' typeDeclaration
	;

// https://github.com/jsx/JSX/blob/4053b064a59c387dfcfcc9eb3fbd85750cc0a658/src/parser.js#L1552
functionTypeDeclaration
	:	'(' '...'? typeDeclaration (',' '...'? typeDeclaration)* ')' ':' typeDeclaration
	;
	
// https://github.com/jsx/JSX/blob/4053b064a59c387dfcfcc9eb3fbd85750cc0a658/src/parser.js#L1598
initializeBlock
	:	constructorInvocationStatement
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
// TODO check me. is this implementation valid?
functionStatement
	:	IDENT functionExpr
	;
	
// https://github.com/jsx/JSX/blob/4053b064a59c387dfcfcc9eb3fbd85750cc0a658/src/parser.js#L1764
ifStatement
	:	'(' expr ')' subStatements ('else' subStatements)?
	;
	
// https://github.com/jsx/JSX/blob/4053b064a59c387dfcfcc9eb3fbd85750cc0a658/src/parser.js#L1781
// FIXME
doWhileStatement
	:
	;

// https://github.com/jsx/JSX/blob/4053b064a59c387dfcfcc9eb3fbd85750cc0a658/src/parser.js#L1796
// FIXME	
whileStatement
	:
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
// FIXME
continueStatement
	:
	;

// https://github.com/jsx/JSX/blob/4053b064a59c387dfcfcc9eb3fbd85750cc0a658/src/parser.js#L1897
// FIXME
breakStatement
	:
	;

// https://github.com/jsx/JSX/blob/4053b064a59c387dfcfcc9eb3fbd85750cc0a658/src/parser.js#L1905
returnStatement
	:	';'
	|	expr ';'
	;

// https://github.com/jsx/JSX/blob/4053b064a59c387dfcfcc9eb3fbd85750cc0a658/src/parser.js#L1919
// FIXME
switchStatement
	:
	;

// https://github.com/jsx/JSX/blob/4053b064a59c387dfcfcc9eb3fbd85750cc0a658/src/parser.js#L1981
// FIXME	
throwStatement
	:
	;
	
// https://github.com/jsx/JSX/blob/4053b064a59c387dfcfcc9eb3fbd85750cc0a658/src/parser.js#L1989
// FIXME
tryStatement
	:
	;
	
// https://github.com/jsx/JSX/blob/4053b064a59c387dfcfcc9eb3fbd85750cc0a658/src/parser.js#L2037
// FIXME
assertStatement
	:
	;
	
// https://github.com/jsx/JSX/blob/4053b064a59c387dfcfcc9eb3fbd85750cc0a658/src/parser.js#L2047
// FIXME
logStatement
	:
	;
	
// https://github.com/jsx/JSX/blob/4053b064a59c387dfcfcc9eb3fbd85750cc0a658/src/parser.js#L2065
// FIXME
deleteStatement
	:
	;
	
// https://github.com/jsx/JSX/blob/4053b064a59c387dfcfcc9eb3fbd85750cc0a658/src/parser.js#L2075
// FIXME
debuggerStatement
	:
	;
	
// https://github.com/jsx/JSX/blob/4053b064a59c387dfcfcc9eb3fbd85750cc0a658/src/parser.js#L2080
subStatements
	:	statement
	;
	
// https://github.com/jsx/JSX/blob/4053b064a59c387dfcfcc9eb3fbd85750cc0a658/src/parser.js#L2087
// FIXME parser can't parse "var foo, bar;"
variableDeclarations
	:	variableDeclaration (',' variableDeclaration)*
	;
	
// https://github.com/jsx/JSX/blob/4053b064a59c387dfcfcc9eb3fbd85750cc0a658/src/parser.js#L2103
// FIXME
variableDeclaration
	:	IDENT (':' typeDeclaration)? ('=' assignExpr)?
	;
	
// https://github.com/jsx/JSX/blob/4053b064a59c387dfcfcc9eb3fbd85750cc0a658/src/parser.js#L2125
expr
	:	assignExpr (',' assignExpr)*
	;

// https://github.com/jsx/JSX/blob/4053b064a59c387dfcfcc9eb3fbd85750cc0a658/src/parser.js#L2139
assignExpr
	:	lhsExpr ('=' | '*=' | '/=' | '%=' | '+=' | '-=' | '<<=' | '>>=' | '>>>=' | '&=' | '^=' | '|=') assignExpr
	|	condExpr
	;
	
// https://github.com/jsx/JSX/blob/4053b064a59c387dfcfcc9eb3fbd85750cc0a658/src/parser.js#L2158
condExpr
	:	lorExpr ('?' assignExpr? ':' assignExpr)?
	;

// https://github.com/jsx/JSX/blob/4053b064a59c387dfcfcc9eb3fbd85750cc0a658/src/parser.js#L2180
// FIXME
binaryOpExpr
	:
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
	:	addExpr (('>>>' | '<<' | '>>') addExpr)*
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
	:	postfixExpr ('as' '__noconvert__' typeDeclaration)*
	;
	
// https://github.com/jsx/JSX/blob/4053b064a59c387dfcfcc9eb3fbd85750cc0a658/src/parser.js#L2305
postfixExpr
	:	lhsExpr ('++' | '--' | 'nstanceof' typeDeclaration)?
	;
	
// https://github.com/jsx/JSX/blob/4053b064a59c387dfcfcc9eb3fbd85750cc0a658/src/parser.js#L2321
// FIXME
lhsExpr
	:	superExpr
	|	lambdaExpr _lhsExprSub?
	|	functionExpr _lhsExprSub?
	|	newExpr _lhsExprSub?
	|	primaryExpr _lhsExprSub?
	;
	
_lhsExprSub
	:	'(' argsExpr
	|	'[' expr ']'
	|	':' IDENT actualTypeArguments
	;
	
// https://github.com/jsx/JSX/blob/4053b064a59c387dfcfcc9eb3fbd85750cc0a658/src/parser.js#L2381
// FIXME
newExpr
	:	'temporary'
	;

// https://github.com/jsx/JSX/blob/4053b064a59c387dfcfcc9eb3fbd85750cc0a658/src/parser.js#L2415
// FIXME
superExpr
	:	'temporary'
	;
	
// https://github.com/jsx/JSX/blob/4053b064a59c387dfcfcc9eb3fbd85750cc0a658/src/parser.js#L2431
// FIXME
lambdaExpr
	:	'temporary'
	;
	
// https://github.com/jsx/JSX/blob/4053b064a59c387dfcfcc9eb3fbd85750cc0a658/src/parser.js#L2449
// FIXME

lambdaBody
	:
	;
	
// https://github.com/jsx/JSX/blob/4053b064a59c387dfcfcc9eb3fbd85750cc0a658/src/parser.js#L2471
functionExpr
	:	'(' functionArgumentsExpr ':'? typeDeclaration? '{' block
	;
	
// https://github.com/jsx/JSX/blob/4053b064a59c387dfcfcc9eb3fbd85750cc0a658/src/parser.js#L2518
// FIXME
forEachScope
	:
	;
	
// https://github.com/jsx/JSX/blob/4053b064a59c387dfcfcc9eb3fbd85750cc0a658/src/parser.js#L2532
// FIXME
findLocal
	:
	;
	
// https://github.com/jsx/JSX/blob/4053b064a59c387dfcfcc9eb3fbd85750cc0a658/src/parser.js#L2554
// FIXME
primaryExpr
	:	IDENT objectTypeDeclaration?
	|	'this'
	|	'undefined'
	|	'null'
	|	'false'
	|	'true'
	|	'[' arrayLiteral
	|	'{' hashLiteral
	|	'(' expr ')'
	|	string
	|	numberLiteral
	|	REGEXP_LITERAL
	;

// TODO literal? not statements?

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
// TODO is this right?
functionArgumentsExpr
	:	('...' IDENT | IDENT)? (':' typeDeclaration)? (',' ('...' IDENT | IDENT)? (':' typeDeclaration)?)?
	;

// https://github.com/jsx/JSX/blob/4053b064a59c387dfcfcc9eb3fbd85750cc0a658/src/parser.js#L2711
argsExpr
	:	')'
	|	assignExpr (',' assignExpr)* ')'
	;


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

// FIXME this is terrible
fragment
CHAR
	:	('a'..'z' | 'A'..'Z' | '/' | '.')
	;

IDENT
	:	('a'..'z' | 'A'..'Z' | '_') ('a'..'z' | 'A'..'Z' | '_' | '0'..'9')*
	;

// FIXME this is terrible
DOUBLE_QUOTED
	:	'"' CHAR* '"'
	;

// FIXME this is terrible
SINGLE_QUOTED
	:	'\'' CHAR* '\''
	;

REGEXP_LITERAL
	:	'/' CHAR '/' ('m' | 'g' | 'i')*
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
	
	
// TODO -> (?![\\.0-9eE]) 
INTEGER_LITERAL
	:	'0' ('x' | 'X') ('0' .. '9' | 'a' .. 'f' | 'A' .. 'F')+
	|	DECIMAL_INTEGER_LITERAL
	;

WS
	:	(' '|'\r'|'\t'|'\u000C'|'\n') {$channel=HIDDEN;}
	;
    