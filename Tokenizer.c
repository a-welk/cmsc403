//Alex Welk | CMSC403 Assignment 4 | 10/9/2022
#include "Tokenizer.h"
#include "ctype.h"

_Bool tokenizer(struct lexics *aLex, int *numLex, FILE *inf) {
	
	//variable initializations
	char stringArray[256] = "";
	char temp[256] = "";
	char *token;
	char lexemeArray[256][256];
	char tempString[256];
	int i = 0;
	int k = 0;
	char whitespace[] = " \t\r\n\v\f";
	int begin;
	int length = 0;
	enum token realToken;
	char currentLexeme[256];
	char lexemeToken[256];
	
	while(fgets(temp, 256, inf) != NULL) { strcat(stringArray, temp); }

	token = strtok(stringArray, whitespace);

	while(k < strlen(token)) {
		length = 0;	
	//resets tempString for each iteration
	for(int i = 0; i < strlen(tempString); i++) { tempString[i] = '\0'; }
	
	//get all alphanumeric characters are assign respective tokens
	if(isalnum(token[k])) {
		begin = k;
		while(isalnum(token[k]) != 0) {
			length++;
			k++;
		}
		strncat(tempString, &token[begin], k - begin);
		strcpy(lexemeArray[i], tempString);
		i++;
	}
	//character is not alphanumeric so check for == and != case
	else {
		if((strncmp( &token[k], "=", 1) == 0) && (strncmp(&token[k+1], "=", 1) == 0)) {
		strncat(tempString, &token[k], 2);
		k += 2;
		strcpy(lexemeArray[i], tempString);
		i++;	
}
		else if((strncmp(&token[k], "!", 1) == 0) && (strncmp(&token[k+1], "=", 1) == 0)) {
		strncat(tempString, &token[k], 2);
		k += 2;
		strcpy(lexemeArray[i], tempString);
		i++;	
}
		else {
		strncpy(lexemeArray[i], &token[k], 1);
		k++;
		i++;
}
}
}
	
	while(token != NULL) {
		token = strtok(NULL, whitespace);
		k = 0;
	if(token == NULL) { continue; }
	
	while(k < strlen(token)) {
		length = 0;
		
		//reset tempString array
		for(int i = 0; i < strlen(tempString); i++) { tempString[i] = '\0'; }
	if(isalnum(token[k]) == 1) {
		begin = k;
		while(isalnum(token[k]) == 1) {
			k++;
			length++;
}
	strncat(tempString, &token[begin], k - begin);
	strcpy(lexemeArray[i], tempString);
	i++;		
		
}
	//character is not alphanumeric so check for == and != case
	else {
		if((strncmp(&token[k], "=", 1) == 0) && (strncmp(&token[k+1], "=", 1) == 0)) {
			strncat(tempString, &token[k], 2);
			strcpy(lexemeArray[i], tempString);
			i++;
			k += 2;		
}
		else if((strncmp(&token[k], "!", 1) == 0) && (strncmp(&token[k+1], "=", 1) == 0)) {
			strncat(tempString, &token[k], 2);
			strcpy(lexemeArray[i], tempString);
			i++;
			k += 2;
		}
		else {
			strncpy(lexemeArray[i], &token[k], 1);
			k++;
			i++;
		}
		}
	}
}

	//interate through each lexeme element and create the respective token
	for(int i = 0; lexemeArray[i][0] != '\0'; i++) {
	
		//reset currentLexeme array
		for(int k = 0; k < strlen(currentLexeme); k++) { currentLexeme[k] = '\0'; }
		
		for(int p = 0; lexemeArray[i][p] != '\0'; p++) {
			strncat(currentLexeme, &lexemeArray[i][p], 1);
}
		if(isdigit(currentLexeme[0] != 0)) {
			realToken = NUMBER;
			strcpy(lexemeToken, currentLexeme);
		}
		else if (isalpha(currentLexeme[0] != 0)) {
			if(strcmp(currentLexeme, "int") == 0) { 
				realToken = VARTYPE;
				strcpy(lexemeToken, currentLexeme);
			 }
			else if(strcmp(currentLexeme, "void") == 0) {
				realToken = VARTYPE;
				strcpy(lexemeToken, currentLexeme); }
			else if(strcmp(currentLexeme, "return") == 0) {
				realToken = RETURN_KEYWORD;
				strcpy(lexemeToken, currentLexeme); }
			else if(strcmp(currentLexeme, "while") == 0) {
				realToken = WHILE_KEYWORD;
				strcpy(lexemeToken, currentLexeme); }
			else {
				realToken = IDENTIFIER;
				strcpy(lexemeToken, currentLexeme); }
}
		else if(strlen(currentLexeme) == 1) {
			switch(currentLexeme[0]) {
				case '(':
				realToken = LEFT_PARENTHESIS;
				strcpy(lexemeToken, currentLexeme);
				break;
				case ')':
				realToken = RIGHT_PARENTHESIS;
				strcpy(lexemeToken, currentLexeme);
				break;
				case '{':
				realToken = LEFT_BRACKET;
				strcpy(lexemeToken, currentLexeme);
				break;
				case '}':
				realToken = RIGHT_BRACKET;
				strcpy(lexemeToken, currentLexeme);
				break;
				case';':
				realToken = EOL;
				strcpy(lexemeToken, currentLexeme);
				break;
				case '*':
				realToken = BINOP;
				strcpy(lexemeToken, currentLexeme);
				break;
				case '+':
				realToken = BINOP;
				strcpy(lexemeToken, currentLexeme);
				break;
				case '%':
				realToken = BINOP;
				strcpy(lexemeToken, currentLexeme);
				break;
}
}
		else if(strlen(currentLexeme) == 2) {
			switch(currentLexeme[0]) {
				case ('!'):
				realToken = BINOP;
				strcpy(lexemeToken, currentLexeme);
				break;
				case('='):
				realToken = BINOP;
				strcpy(lexemeToken, currentLexeme);
				break;
}
}

//create new lexic and add to lexics array
struct lexics *lexic = (struct lexics*) malloc(sizeof(struct lexics));
lexic -> token = realToken;
strcpy(lexic->lexeme, currentLexeme);
aLex[i] = *lexic;
*numLex = i;					
}

struct lexics *newLexic = (struct lexics*) malloc(sizeof(struct lexics));
newLexic -> token = realToken;
strcpy(newLexic -> lexeme, currentLexeme);
aLex[i+1] = *newLexic;
*numLex = i;
return TRUE;		 
}	
