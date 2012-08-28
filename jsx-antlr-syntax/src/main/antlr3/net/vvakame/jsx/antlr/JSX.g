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

programFile:  importStatement* classDefinition* ;

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
	:	'abstract' | 'final' | 'native' | '__fake__';

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
	:	'.' IDENT actualTypeArguments templateTypeDeclaration
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
	|	block
	;

// https://github.com/jsx/JSX/blob/4053b064a59c387dfcfcc9eb3fbd85750cc0a658/src/parser.js#L1610
block
	:	statement '}'
	;

// https://github.com/jsx/JSX/blob/4053b064a59c387dfcfcc9eb3fbd85750cc0a658/src/parser.js#L1621
statement
	:	'{' block
	|	'var' variableStatement
	|	';'
	|	'if' ifStatement
	|	_statementBlock
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
	|	expr ';'
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
// FIXME
variableStatement
	:	variableDeclarations ';'
	;

// https://github.com/jsx/JSX/blob/4053b064a59c387dfcfcc9eb3fbd85750cc0a658/src/parser.js#L1748
// FIXME
functionStatement
	:
	;
	
// https://github.com/jsx/JSX/blob/4053b064a59c387dfcfcc9eb3fbd85750cc0a658/src/parser.js#L1764
// FIXME
ifStatement
	:
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
// FIXME
forStatement
	:
	;

// https://github.com/jsx/JSX/blob/4053b064a59c387dfcfcc9eb3fbd85750cc0a658/src/parser.js#L1866
// FIXME
forInStatement
	:
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
// FIXME
returnStatement
	:
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
// FIXME
subStatements
	:
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
// FIXME
expr
	:
	;
	
// https://github.com/jsx/JSX/blob/4053b064a59c387dfcfcc9eb3fbd85750cc0a658/src/parser.js#L2139
assignExpr
	:	lhsExpr ('=' | '*=' | '/=' | '%=' | '+=' | '-=' | '<<=' | '>>=' | '>>>=' | '&=' | '^=' | '|=') assignExpr
	|	condExpr
	;
	
// https://github.com/jsx/JSX/blob/4053b064a59c387dfcfcc9eb3fbd85750cc0a658/src/parser.js#L2158
condExpr
	:	lorExpr '?' ':' assignExpr
	|	lorExpr '?' assignExpr ':' assignExpr
	|	lorExpr
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
	:	'++'
	|	'--'
	|	'instanceof' typeDeclaration
	|	lhsExpr
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
	:
	;

// https://github.com/jsx/JSX/blob/4053b064a59c387dfcfcc9eb3fbd85750cc0a658/src/parser.js#L2415
// FIXME
superExpr
	:
	;
	
// https://github.com/jsx/JSX/blob/4053b064a59c387dfcfcc9eb3fbd85750cc0a658/src/parser.js#L2431
// FIXME
lambdaExpr
	:
	;
	
// https://github.com/jsx/JSX/blob/4053b064a59c387dfcfcc9eb3fbd85750cc0a658/src/parser.js#L2449
// FIXME

lambdaBody
	:
	;
	
// https://github.com/jsx/JSX/blob/4053b064a59c387dfcfcc9eb3fbd85750cc0a658/src/parser.js#L2471
// FIXME
functionExpr
	:
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
	:	'this'
	|	'undefined'
	|	'null'
	|	'false'
	|	'true'
	|	'[' arrayLiteral
	|	'{' hashLiteral
	|	'(' expr ')'
	|	IDENT objectTypeDeclaration
	|	IDENT
	;

// TODO literal? not statements?

// https://github.com/jsx/JSX/blob/4053b064a59c387dfcfcc9eb3fbd85750cc0a658/src/parser.js#L2600
// FIXME
nullLiteral
	:
	;
	
// https://github.com/jsx/JSX/blob/4053b064a59c387dfcfcc9eb3fbd85750cc0a658/src/parser.js#L2613
// FIXME
arrayLiteral
	:
	;

// https://github.com/jsx/JSX/blob/4053b064a59c387dfcfcc9eb3fbd85750cc0a658/src/parser.js#L2633
// FIXME
hashLiteral
	:
	;

// https://github.com/jsx/JSX/blob/4053b064a59c387dfcfcc9eb3fbd85750cc0a658/src/parser.js#L2666
// TODO is this right?
functionArgumentsExpr
	:	('...' IDENT | IDENT)? (':' typeDeclaration)? (',' ('...' IDENT | IDENT)? (':' typeDeclaration)?)?
	;

// https://github.com/jsx/JSX/blob/4053b064a59c387dfcfcc9eb3fbd85750cc0a658/src/parser.js#L2711
// FIXME
argsExpr
	:
	;

string
	:	DOUBLE_QUOTED
	|	SINGLE_QUOTED
	;
	

MULTILINE_COMMENT
	:	'/*' ( options {greedy=false;} : . )* '*/' {$channel=HIDDEN;}
	;

SIGLELINE_COMMENT
	:	'//' ~('\n'|'\r')* '\r'? '\n' {$channel=HIDDEN;}
	;

// FIXME this is terrible
CHAR
	:	('a'..'z' | 'A'..'Z' | '/' | '.')
	;

IDENT
	:	('a'..'z' | 'A'..'Z' | '_') ('a'..'z' | 'A'..'Z' | '_' | '0'..'9')+
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
	
DECIMAL_INTEGER_LITERAL
	:	'0'
	|	('1'..'9') ('0'..'9')*
	;
	
fragment
EXPONENT_PART
	:	('e' | 'E') ('+' | '-')? ('0'..'9')+
	;

NUMBER_LITERAL
	:	DECIMAL_INTEGER_LITERAL '.' ('0'..'9')* EXPONENT_PART
	|	'.' ('0'..'9')+ EXPONENT_PART
	|	DECIMAL_INTEGER_LITERAL EXPONENT_PART
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
    