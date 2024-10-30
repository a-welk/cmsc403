#ifndef GIVENS_H
#define GIVENS_H

#include <regex.h>
#include <stdio.h>

#define FALSE 0
#define TRUE !FALSE

#define LEXEME_MAX 256
#define MY_CHAR_MAX 256

enum token{ LEFT_PARENTHESIS,
			RIGHT_PARENTHESIS,
			LEFT_BRACKET,
			RIGHT_BRACKET,
			WHILE_KEYWORD,
			RETURN_KEYWORD,
			EQUAL,
			COMMA,
			EOL,
			VARTYPE,
			IDENTIFIER,
			BINOP,
			NUMBER
		   };

struct lexics{
	enum token token;
	char lexeme[LEXEME_MAX];
};

_Bool validNumber(char* aLexeme);
_Bool validIdentifier(char * aLexeme);

//Stuck on file I/O? Research fgets! 
//fgets(input, MY_CHAR_MAX, inf) != NULL

#endif