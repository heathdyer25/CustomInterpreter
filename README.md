# .417 Interpreter by Heath Dyer
This project showcases my implementation of a custom programming language. I initially wrote this interpreter 
for CSC 417 at NC State where Dr. Jamie Jennings provided the lexer and parser for the language, however, 
I have since made my own implementation based off of the provided grammar. I have also created a small GUI
for easy interaction with the interpreter. Enjoy!

## Getting started

Directions:
  * First, clone the repository to target directory.
  * To compile the interpreter, run script: ./build.sh
  * Clone and build the [parser](https://gitlab.com/JamieTheRiveter/csc417).
  * To run the interpreter, redirect the target .417 file into the parse executable, and pipe the output into ./run.sh.
  * Program options (listed below) may be added directly to ./run.sh in the command line.

Program options:
  * '-h' for help.
  * '-v' to display interpreter version
  * '-t' to enable tracing for Interpreter expression evaluations.
  * '-d' to enable dynamic scoping (default is lexical scoping)
  * '-np' interpreter will not print the final evaluation of the expression
  * **Note:** If you add an invalid program arg, the program will not run. If you add '-v' or '-h' the program will display the corresponding message (of whichever comes first in the command line) and then exit with status success.

Example Usage:
  * ./run.sh -h
  * ./run.sh -v
  * ./parse <<< "add(1, 2)" | ./run.sh
  * ./parse < ./test-files/cp3ex4.417 | ./run.sh -d
  * ./parse < ./test-files/cp3ex4.417 | ./run.sh -t
  * ./parse < ./test-files/cp3ex4.417 | ./run.sh -t -d

## About the Interpreter
The interpreter reads from standard input and follows the syntax guidelines of the CSC417 language (linked in sources cited).

## Environment Bindings:

Basic Environment Bindings
  *  'i' - Bound to integer 10
  *  'v' - Bound to integer 5
  *  'i' - Bound to integer 1
  *  'true' - Bound to boolean value True
  *  'false' - Bound to boolean value False

Math Procedures
  *  'add' - Bound to procedure add that takes two integer arguments and returns added result
  *  'sub' - Bound to procedure that takes two integer arguments and returns the second subtracted from the first
  *  'mul' - Bound to procedure that takes two integer arguments and returns multiplied result
  *  'div' - Bound to procedure that takes two integer arguments and returns the second divided from the first
  *  'mod' - Bound to procedure that takes two integer arguments and returns the first mod the second

Logic Procedures
  *  'equals?' - Bound to procedure that takes two arguments and tests for equality
  *  'lessThan?' - Bound to procedure lessThan(x, y) that takes two integer arguments and tests x < y
  *  'greaterThan?' - Bound to procedure greaterThan(x, y) that takes two integer arguments and tests x > y
  *  'zero?' - Bound to procedure that takes one argument and tests if it equals integer 0
  *  'and?' - Takes two or more boolean arguments, ands them and returns boolean result
  *  'or?' - Takes two or more boolean arguments, ors them together and returns boolean result
  *  'not?' Takes boolean argument and returns opposite

IO Procedures
  *  'print' - Bound to procedure that takes any number of arguments, concatenates them, prints them to console, and returns resultant string
  *  'fail' - Same as print, but arguments go to standard error and then the program will exit with an error status
  *  'readInput' - reads all input from terminal, returns as string
  *  'readLine' - reads line from terminal, returns as string
  *  'readFile' - reads file from string arg, returns content as string

String Procedures
  *  'concat' - takes any number of strings and concatenates them, returns string
  *  'charAt' - takes string and integer index, returns single char string at that index
  *  'substring' - takes string to check, start index, and end index. returns substring
  *  'length' - takes string, returns integer length
  *  'isDigit?' - takes single char string and checks if digit, returns boolean
  *  'isLetter?' - takes single char string and checks if letter, returns boolean
  *  'parseInt' takes string, converts to int (returns false if cannot)

List Procedures
  *  'cons' - if no arguments, returns empty list. takes one expression and one list, adds expression to head of list, returns list
  *  'head' - takes list, returns first expression in list
  *  'tail' - takes list, returns tail of list (list of everything but head)
  *  'isEmpty?' - takes list and checks if empty, returns boolean
  *  'map' - takes procedure and list, maps list of args to procedure
  *  'append' - takes two lists, adds all values in first list to front of second list

General Procedures
 *  'type' - takes an expression, returns the string name of ENUM constant as string

## Supported Expression Types
  * APPLCIATION - Takes expression (lambda or identifier bound to procedure) and an argument list, applies arglist to expression
  * ARGLIST - List of expressions used in function application
  * PROCEDURE - Built in functions used for function applications described above
  * LAMBDA - Takes a parameters list and a function block that may use said parameters. Applied using APPLICATION
  * PARAMETERS - List of identifiers to be used in function block of lambda
  * COND - Takes list of clauses to evaluate, will return the consequent of the first clause whose test evaluates true or false
  * CLAUSE - Takes a test (boolean expression) and a consequent (any expression)
  * BLOCK - Takes a list of expressions, evaluates all expressions but only returns last expression in block
  * LET - Takes an identifier, expression, and block. Will bind identifier to expression in new enviornment and evalaute the block in that environment.
  * DEF - Takes an identifier, expression, and block. Definition expressions are grouped and bound at the start of the block to allow for mutually recursive functions.
  * IDENTIFIER - A string that can be bound to a value 
  * STRING - basic string type
  * INTEGER - 64 bit signed integer
  * BOOLEAN - true or false
  * LIST - Lists can only be constructed and used with environment bindings, list of expressions

## CP6 Implementation Description (Support for assignments)
  *  Added AssignmentExpression class
  *  Updated deserializer to read AssignmentExpressions
  *  Updated interpreter for performAssignment() to handle assignments
  *  Moved environment (linked list node essentially) to its own class
  *  Updated error handling: removed java like error messages, removed uncessary custom exceptions (just RuntimeExceptions now)
  *  Added new List type that can be handled with new procedures cons(), head(), tail(), isEmpty?()
  *  Added a bunch of IO, Logic, String, and Math procedures to environment listed above at top
  *  fixed bug with let expressions where it wasn't being evaluated before being added to the environment
  *  fixed print statements displaying weird
  *  implemented def statements to allow for mutually recursive functions

## CP5 Implementation Description (Support for Let and Lexical Scoping)
* Added LetExpression class for let expression type to hold IdentifierExpression, Expression, and BlockExpression
* Updated JSON Deserializer to read Let Expressions into LetExpression object
* Added applyLet() function to interpreter to extend environment with new indetifier/expression binding and evaluate the block within that environment
* Added new lexicalScope boolean flag to interpreter for lexical scoping and to interpreter constrcutor
* Added new Evironment field to LambdaExpression to store environments for functions defined with LetExpression
* Refactored evaluate() and apply() methods in interpreter to take an Environment as a parameter
* Restructured environment to be series of linked nodes with map of bindings and parent reference, will allow for assignments in future and is more efficient
* applyLet() will set a Lambda functions environment when lexical scoping is enabled
* applyFunction() will use the set environment if lexical scoping is enabled && the enironmnet has been set for the lambda function (unecessary for anonymous functions)
* Added new command line option for program "d" to revert back to dynamic scoping
* Added div(), lessThan(), greaterThan() built in procedures, changed "=" to "equals"
* Updated tests for CP5

## CP4 Implementation Description 
* Updated ./run.sh script to take arguments directly from command line for easier tracing
* Added print() function for testing
* Updated tests and cleaned up code

## CP3 Implementation Description (Support for Lambda, Conditionals, and Blocks)
* Added support for Boolean values
* Added 'true' and 'false' identifiers to environment as boolean values
* Added 'mul', 'zero?', and '=' procedures to environment
* Added support for function Blocks expression type. Contains a list of expressions
* Added support for Conditional expression type. Contains list of clauses. Clauses contain a test (boolean condition to evaluate) and a consequent (expression to return if test is true)
* Added support for Lambda functions. Lambda functions contain a parameter list (Identifier expressions) and a function block
* Seperated the interpreter Environment into its own class. Rather than a Map<IdentifierExpression, Expression>, it is now a linked list of maps.
* Environment can now extend to add parameter/argumnet bindings for functions
* Added tracing

## CP2 Implementation Description
* Basically deleted everything from CP1
* Created Expression abstract class to implement custom expression types supported by Interpreter
* Created Expressions for Integers, Strings, Identifiers, built-in Procedures, and function Applications
* Created JSONReader and JSONDeserializer. Input from stdin is now given to the JSONReader, and the JSON objects are deserialized into these custom expression types
* Created ApplicationFunctions class to program in supported procedures for the 417 language
* Created Interpreter to process expressions. Innterpreters main functionality is 'evaluate' which given the expression from the JSONReader will evaluate as resultant expression
* Interpreter has enviornment with identifiers bound to build-in procedures and expressions
* Added 'add' and 'sub' procedures to environemnt
* Added 'x', 'v', and 'i' to environment that are equal to their corresponding roman numeral integer


## CP1 Implementation Description
* Created Token class that stores the Type of the token, the text contained in the token, and the start position in the file of the token.
* Enum to store Token types, currently only supports Integer
* Created Lexer, Parser, and Interpreter classes.
* Lexer class is created given input string. Has analyze() method to break down input and return as List of valid tokens. Throws error if token is invalid. Currently only supports integer inputs
* Parser class is created given list of tokens from Parser. Currently only prints tokens
* Main class gets input from stdin and feeds into Lexer. Lexer givens tokens to Parser. Parser outputs tokens to stdout.

## Sources Cited
* Original CSC417 parser from Dr. Jamie Jennings: https://gitlab.com/JamieTheRiveter/csc417
