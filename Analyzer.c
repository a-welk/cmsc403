#include "Analyzer.h"

int main(){
	//initialize file pointer to NULL 
	FILE *infile = NULL; 
	//Define a character array to store the name of the file to read and write
	char filename[MY_CHAR_MAX];  
	//Prompt the user to input a filename and continue to prompt the user until they enter a correct one
	while(infile == NULL) {  
		printf("Enter filename: ");  
		scanf("%s",filename);
		//When given a filename, use fopen to create a new file pointer. 
			//If fopen can not find the file, it returns null
		infile = fopen(filename, "r+");
		if(infile == NULL){ 
			printf("ERROR: file %s cannot be opened\n", filename);
	  	}
	}


	struct lexics allLexics[1024];
	int numberOfLexics = 0;
	printf("Did tokenize: %d\n",tokenizer(allLexics, &numberOfLexics, infile));
	printf("Number of lexemes: %d\n",numberOfLexics);
	printf("Lexemes: ");
	for(int i = 0; i < numberOfLexics; i++){
		printf("%s  ",allLexics[i].lexeme);
	}
	printf("\nTokens: ");
	for(int i = 0; i < numberOfLexics; i++){
		printf("%s  ",tokenEnumToString(allLexics[i].token));
	}
	printf("\n");
	printf("Did parse: %d\n",parser(allLexics, numberOfLexics));

}

const char* tokenEnumToString(enum token aToken){
	if(aToken == LEFT_PARENTHESIS){
		return "LEFT_PARENTHESIS";
	}
	else if(aToken == RIGHT_PARENTHESIS){
		return "RIGHT_PARENTHESIS";
	}
	else if(aToken == LEFT_BRACKET){
		return "LEFT_BRACKET";
	}
	else if(aToken == RIGHT_BRACKET){
		return "RIGHT_BRACKET";
	}
	else if(aToken == WHILE_KEYWORD){
		return "WHILE_KEYWORD";
	}
	else if(aToken == RETURN_KEYWORD){
		return "RETURN_KEYWORD";
	}
	else if(aToken == EQUAL){
		return "EQUAL";
	}
	else if(aToken == COMMA){
		return "COMMA";
	}
	else if(aToken == EOL){
		return "EOL";
	}
	else if(aToken == VARTYPE){
		return "VARTYPE";
	}
	else if(aToken == IDENTIFIER){
		return "IDENTIFIER";
	}
	else if(aToken == BINOP){
		return "BINOP";
	}
	else if(aToken == NUMBER){
		return "NUMBER";
	}
	else{
		return "ERROR-INVALID_TOKEN";
	}
			
}
