//Alex Welk | CMSC 403 Assignment 4 | 10/9/2022
#include "Parser.h"
#include "Givens.h"

_Bool parser(struct lexics *someLexics, int numberOfLexics) {
	int index = 0;
	return (function(someLexics, numberOfLexics, &index));

}

_Bool function(struct lexics *lexics, int numLex, int *index) {
	if(*index != numLex) {return FALSE; }
	if(!(header(lexics, numLex, index))) { return FALSE; }
	if(!(body(lexics, numLex, index))) { return FALSE; }
	return TRUE;
}

_Bool header(struct lexics *lexics, int numLex, int *index) {
	if(lexics[*index].token == VARTYPE) {
		*index += 1;
	if(lexics[*index].token == IDENTIFIER) {
		*index += 1;
	if(lexics[*index].token == LEFT_PARENTHESIS) {
		*index += 1;
	if(lexics[*index].token == RIGHT_PARENTHESIS) {
		*index += 1;
		return TRUE;
}
else {
	if(lexics[*index].token == RIGHT_PARENTHESIS && arg_decl(lexics, numLex, index)) {
		*index += 1;
		return TRUE;
}
}
}
}
return FALSE;
}
}

_Bool arg_decl(struct lexics *lexics, int numLex, int *index) {
	if(lexics[*index].token == VARTYPE) {
		*index += 1;
	if(lexics[*index].token == IDENTIFIER) {
		*index += 1;
		int previous = *index;
		int condition = 0;
		while(condition == 0) {
			if(lexics[*index].token == COMMA) {
				*index +=1;
			if(lexics[*index].token == VARTYPE) {
				*index += 1;
			if(lexics[*index].token == IDENTIFIER) {
				*index += 1;
				previous = *index;
}else {condition = 1;}
}else {condition = 1;}
}else {condition = 1;}
}
	*index = previous;
	return TRUE;
} 
}
	return FALSE;
}
//checks EBNF grammar for body
_Bool body(struct lexics *lexics, int numLex, int *index) {
	if(lexics[*index].token == RIGHT_BRACKET) {
		*index += 1;
	if(lexics[*index].token == LEFT_BRACKET) {
		*index += 1;
		return TRUE;
	}
	else {
		if(lexics[*index].token == RIGHT_BRACKET && statementList(lexics, numLex, index)) {
			index += 1;
			return TRUE;
}
}
}
return FALSE;
}
//checks EBNF grammar for statement list
_Bool statementList(struct lexics *lexics, int numLex, int *index) {
	if(!(statement(lexics, numLex, index))) { return FALSE; }
	int previous = *index;
	while(statement(lexics, numLex, index)) { previous = *index; }
	*index = previous;
	return TRUE;
}

_Bool statement(struct lexics *lexics, int numLex, int *index) {
	if(returnFunction(lexics, numLex, index) || assignment(lexics, numLex, index) 
		|| whileLoop(lexics, numLex, index)) {
		return TRUE;
}
return FALSE;
}

//checks EBNF grammer for while loop
_Bool whileLoop(struct lexics *lexics, int numLex, int *index) {
	if(lexics[*index].token == WHILE_KEYWORD) {
		*index += 1;
	if(lexics[*index].token == LEFT_PARENTHESIS) {
		*index += 1;
	if(expression(lexics, numLex, index)) {
	if(lexics[*index].token == RIGHT_PARENTHESIS) {
		*index += 1;
	if(statement(lexics, numLex, index)) {
		return TRUE;
}
}
}
}
}
return FALSE;
}

//Checks EBNF grammer for return
_Bool returnFunction(struct lexics *lexics, int numLex, int *index) {
	if(lexics[*index].token == RETURN_KEYWORD) {
		*index += 1;
	if(expression(lexics, numLex, index)) {
	if(lexics[*index].token == EOL) {
		*index += 1;
		return TRUE;
} 
}
}
return FALSE; }

//checks EBNF grammer for assignment
_Bool assignment(struct lexics *lexics, int numLex, int *index) {
	if(lexics[*index].token == IDENTIFIER) {
		*index += 1;
	if(lexics[*index].token == EQUAL) {
		*index += 1;
	if(expression(lexics, numLex, index)) {
	if(lexics[*index].token == EOL) {
		*index += 1;
		return TRUE;
}
}
}
}
return FALSE;
}

//checks EBNF grammer for expression
_Bool expression(struct lexics *lexics, int numLex, int *index) {
	if(term(lexics, numLex, index)) {
		int previous = *index;
		int condition = 0;
		while(condition = 0) {
			if(lexics[*index].token == BINOP) {
				*index += 1;
			if(term(lexics, numLex, index)) {
				previous = *index;
}
			else {condition = 1;}
}
			else {condition = 1;}
}
	*index = previous;
	return TRUE;
}
	else if(lexics[*index].token == LEFT_PARENTHESIS) {
		*index += 1;
		if(expression(lexics, numLex, index)) {
			if(lexics[*index].token == RIGHT_PARENTHESIS) {
				*index += 1;
				return TRUE;
}
}
}
return FALSE;
}

//Checks EBNF grammer for term
_Bool term(struct lexics *lexics, int numLex, int *index) {
	if(lexics[*index].token == IDENTIFIER || lexics[*index].token == NUMBER) {
		*index += 1;
		return TRUE;
}
return FALSE;
}
