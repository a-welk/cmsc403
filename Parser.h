#include "Givens.h"

_Bool parser(struct lexics *someLexics, int numberOfLexics);

_Bool function(struct lexics *lexics, int numLex, int *index);

_Bool header(struct lexics *lexics, int numLex, int *index);

_Bool arg_decl(struct lexics *lexics, int numLex, int *index);

_Bool body(struct lexics *lexics, int numLex, int *index);

_Bool statementList(struct lexics *lexics, int numLex, int *index);

_Bool statement(struct lexics *lexics, int numLex, int *index);

_Bool whileLoop(struct lexics *lexics, int numLex, int *index);

_Bool returnFunction(struct lexics *lexics, int numLex, int *index);

_Bool assignment(struct lexics *lexics, int numLex, int *index);

_Bool expression(struct lexics *lexics, int numLex, int *index);

_Bool term(struct lexics *lexics, int numLex, int *index);
