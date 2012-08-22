grammar JSX;

@header {
package test;
}

@lexer::header {
package test;
}

programFile:  importStatement* classDefinition* ;

// https://github.com/jsx/JSX/blob/4053b064a59c387dfcfcc9eb3fbd85750cc0a658/src/parser.js#L926
importStatement
	:	'import' (IDENT (',' IDENT)* 'from')? string ('into' IDENT)? ';'
	;

// https://github.com/jsx/JSX/blob/4053b064a59c387dfcfcc9eb3fbd85750cc0a658/src/parser.js#L986
classDefinition
	:	_classModifiers* (_class | _interface | _mixin)
	;

// https://github.com/jsx/JSX/blob/4053b064a59c387dfcfcc9eb3fbd85750cc0a658/src/parser.js#L1016
_classModifiers
	:	'abstract' | 'final' | 'native' | '__fake__';

// https://github.com/jsx/JSX/blob/4053b064a59c387dfcfcc9eb3fbd85750cc0a658/src/parser.js#L1038
_class
	:	'class' IDENT formalTypeArguments? ('extends' IDENT)? ('implements' IDENT (',' IDENT)*)? '{' memberDefinition* '}'
	;

// https://github.com/jsx/JSX/blob/4053b064a59c387dfcfcc9eb3fbd85750cc0a658/src/parser.js#L1038
// TODO interface is not allowed static member
_interface
	:	'interface' IDENT formalTypeArguments? '{' memberDefinition* '}'
	;
	
// https://github.com/jsx/JSX/blob/4053b064a59c387dfcfcc9eb3fbd85750cc0a658/src/parser.js#L1038
// TODO interface is not allowed static member
_mixin	
	:	'mixin' IDENT formalTypeArguments? '{' memberDefinition* '}'
	;

// https://github.com/jsx/JSX/blob/4053b064a59c387dfcfcc9eb3fbd85750cc0a658/src/parser.js#L1090
// https://github.com/jsx/JSX/blob/4053b064a59c387dfcfcc9eb3fbd85750cc0a658/src/parser.js#L1159
memberDefinition
	: 	_memberDefinitionModifiers+ 'const' IDENT (':' typeDeclaration)? ('=' assignExpr)? ';'
	|	_memberDefinitionModifiers+ 'function' functionDefinition
	|	_memberDefinitionModifiers+ 'var' IDENT (':' typeDeclaration)? ('=' assignExpr)? ';'
	;

_memberDefinitionModifiers
	:	('static' | 'abstract' | 'override' | 'final' | 'native' | '__readonly__' | 'inline' | '__pure__')
	;

// https://github.com/jsx/JSX/blob/4053b064a59c387dfcfcc9eb3fbd85750cc0a658/src/parser.js#L1264
functionDefinition
	:	'function' IDENT formalTypeArguments '(' functionArgumentsExpr? ')' '{'  '}'
	;

// https://github.com/jsx/JSX/blob/4053b064a59c387dfcfcc9eb3fbd85750cc0a658/src/parser.js#L1355
formalTypeArguments
	:	('.' '<' IDENT (',' IDENT)* '>')?
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
	:
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
// FIXME
variableDeclarations
	:
	;
	
// https://github.com/jsx/JSX/blob/4053b064a59c387dfcfcc9eb3fbd85750cc0a658/src/parser.js#L2103
// FIXME
variableDeclaration
	:
	;
	
// https://github.com/jsx/JSX/blob/4053b064a59c387dfcfcc9eb3fbd85750cc0a658/src/parser.js#L2125
// FIXME
expr
	:
	;
	
// https://github.com/jsx/JSX/blob/4053b064a59c387dfcfcc9eb3fbd85750cc0a658/src/parser.js#L2139
// FIXME
assignExpr
	:
	;
	
// https://github.com/jsx/JSX/blob/4053b064a59c387dfcfcc9eb3fbd85750cc0a658/src/parser.js#L2158
// FIXME
condExpr
	:
	;

// https://github.com/jsx/JSX/blob/4053b064a59c387dfcfcc9eb3fbd85750cc0a658/src/parser.js#L2180
// FIXME
binaryOpExpr
	:
	;
	
// https://github.com/jsx/JSX/blob/4053b064a59c387dfcfcc9eb3fbd85750cc0a658/src/parser.js#L2196
// FIXME
lorExpr
	:
	;
	
// https://github.com/jsx/JSX/blob/4053b064a59c387dfcfcc9eb3fbd85750cc0a658/src/parser.js#L2202
// FIXME
landExpr
	:
	;
	
// https://github.com/jsx/JSX/blob/4053b064a59c387dfcfcc9eb3fbd85750cc0a658/src/parser.js#L2208
// FIXME
borExpr
	:
	;
	
// https://github.com/jsx/JSX/blob/4053b064a59c387dfcfcc9eb3fbd85750cc0a658/src/parser.js#L2214
// FIXME
bxorExpr
	:
	;

// https://github.com/jsx/JSX/blob/4053b064a59c387dfcfcc9eb3fbd85750cc0a658/src/parser.js#L2220
// FIXME
bandExpr
	:
	;
	
// https://github.com/jsx/JSX/blob/4053b064a59c387dfcfcc9eb3fbd85750cc0a658/src/parser.js#L2226
// FIXME
eqExpr
	:
	;

// https://github.com/jsx/JSX/blob/4053b064a59c387dfcfcc9eb3fbd85750cc0a658/src/parser.js#L2232
// FIXME
relExpr
	:
	;
	
// https://github.com/jsx/JSX/blob/4053b064a59c387dfcfcc9eb3fbd85750cc0a658/src/parser.js#L2244
// FIXME
shiftExpr
	:
	;

// https://github.com/jsx/JSX/blob/4053b064a59c387dfcfcc9eb3fbd85750cc0a658/src/parser.js#L2251
// FIXME
addExpr
	:
	;
	
// https://github.com/jsx/JSX/blob/4053b064a59c387dfcfcc9eb3fbd85750cc0a658/src/parser.js#L2260
// FIXME
mulExpr
	:
	;
	
// https://github.com/jsx/JSX/blob/4053b064a59c387dfcfcc9eb3fbd85750cc0a658/src/parser.js#L2266
// FIXME
unaryExpr
	:
	;
	
// https://github.com/jsx/JSX/blob/4053b064a59c387dfcfcc9eb3fbd85750cc0a658/src/parser.js#L2290
// FIXME
asExpr
	:
	;
	
// https://github.com/jsx/JSX/blob/4053b064a59c387dfcfcc9eb3fbd85750cc0a658/src/parser.js#L2305
// FIXME
postfixExpr
	:
	;
	
// https://github.com/jsx/JSX/blob/4053b064a59c387dfcfcc9eb3fbd85750cc0a658/src/parser.js#L2321
// FIXME
lhsExpr
	:
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
	:
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
functionArgumentsExpr
	:	('...'? IDENT)? (':' )?
	;

// https://github.com/jsx/JSX/blob/4053b064a59c387dfcfcc9eb3fbd85750cc0a658/src/parser.js#L2711
// FIXME
argsExpr
	:
	;

string	:	DOUBLE_QUOTED
	|	SINGLE_QUOTED
	;

// FIXME
MULTILINE_COMMENT
	:	'/*' ~('*/')* '*/'
	;

// FIXME
SIGLELINE_COMMENT
	:	'//' ~('\n')* '\n'?
	;
	
// FIXME
CHAR	:	('a'..'z' | 'A'..'Z');

IDENT	:	('a'..'z' | 'A'..'Z' | '_') ('a'..'z' | 'A'..'Z' | '_' | '0'..'9')+;

// FIXME
DOUBLE_QUOTED
	:	'"' CHAR* '"';
// FIXME
SINGLE_QUOTED
	:	'\'' CHAR* '\'';
