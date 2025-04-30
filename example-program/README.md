# Example Program: Interpreter Written in .417 by Heath Dyer (hadyer)

The program interpreter.417 is an interpreter for a custom programming language (written in a custom programming language). The program contains a tokenizer, parser, and interpreter within the one file. To execute, follow the same directions as listed in root folder of the repository. At the bottom of the interpreter.417, there is a line:
```
let file = "./example-program/demo.heath";
```
This string represents the file that will be read by the interpreter. To change the target file to parse, change this string to a new target file. I am using the .heath extension but feel free to choose any extension you would like!

## Grammar

The syntax is designed for an expression-oriented language (similar to .417) where every program is a list of expressions, and the last expression is the result of the program. The premise of the syntax is taking human-readable syntax to the extreme. The grammar for the language is as follows:

```
<PROGRAM> := <EXPRESSION0> <PROGRAM> 
           | epsilon

<EXPRESSION0> := <EXPRESSION1> .

<EXPRESSION1> := <INTEGER> 
              |  <STRING>
              |  <BOOLEAN>
              |  <PROCEDURE>
              |  <IDENTIFIER>
              |  <LET> 
              |  <ASSIGNMENT> 
              |  <APPLICATION> 
              |  <FUNCTION> 
              |  <COND>

<INTEGER> := integer 64 bit unsigned

<STRING> := string

<PROCEDURE> := built in procedure reference 

<IDENTIFIER> := identifier containing only a-z, A-Z

<LET> := let <IDENTIFIER> be <EXPRESSION1> 

<ASSIGNMENT> := change <IDENTIFIER> to <EXPRESSION1> 

<APPLICATION> := do <IDENTIFIER> 
              |  do <IDENTIFIER> on <ARGLIST> 

<ARGLIST> := <EXPRESSION1> 
          |  <EXPRESSION1> and <ARGLIST>

<FUNCTION> := function that <BLOCK> 
           |  function on <PARAMLIST> that <BLOCK>

<PARAMLIST> := <IDENTIFIER> 
            |  <IDENTIFIER> and <PARAMLIST>

<BLOCK> := <EXPRESSION1> , <BLOCK>
        |  <EXPRESSION1>

<COND> := <CLAUSE>
       |  <CLAUSE> ; else <CLAUSE>

<CLAUSE> := if <EXPRESSION1> then <BLOCK>
```
Notes:
  *  Although <PROGRAM> evaluates very similarly to <BLOCK>, notice the difference in syntax (periods vs commas). This is to distinguish between expressions in the main program, vs. the block component of other expressions. <BLOCK> cannot be used freely inside of <PROGRAM>, only with <CLAUSE> and <FUNCTION>.
  *  For <CLAUSE>, the test condition <EXPRESSION1> is not restricted to booleans, but if it does not evaluate to a boolean an error will be thrown.
  *  <IDENTIFIER> can only use a-z, A-Z

## Built in procedures
* add - takes two integers, adds together
* subtract - subtracts 2nd int arg from 1 int arg
* multiply - multiplies two int args
* divide - divides 1st int arg by 2nd int arg
* equals - checks two expressions for equality

## Example function application program

```
do add on 3 and 4.
```

## Example let and assingment

```
let x be 4.
do print on x.
change x to "Hello world!".
do print on x.
```

## Example function declaration

Program that defines a function of two params, adds them together, prints the result, and returns the result.
```
let f be function on x and y that let z be do add on x and y, do print on "result: " and z, z.
do f on 3 and 4.
```

## Example function declaration with conditional

Factorial program
```diff
let factorial be function on n that if do equals on n and 0? 1; if true? do multiply on n and do fact on do subtract on n and 1.
do factorial on 7.

- NOTE: Work in progress, recursive functions are not yet evaluated properly by the interpreter
```



