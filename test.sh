#!/bin/bash

# Declare functions to run tests
function ok {
    echo "Running test: $1"
    output=$(echo "$1" | ./run.sh)
    code=$?
    if [[ $code != 0 ]]; then
        echo "ERROR: Interpreter returned error code for input \"$1\""
        echo "Expected success and output \"$2\""
        exit $code
    fi
    if [[ $2 && "$output" != "$2" ]]; then
        echo "ERROR: Interpreter produced unexpected output: \"$output\""
        echo "Expected this output: \"$2\""
        exit $code
    fi
}

# usage ok_file input.txt "expected output" arg1 arg2 arg3
function ok_file {
    echo "Running file-based test: $1"
    program_args=("${@:3}")

    # Capture output
    output=$(cat "$1" | ./run.sh "${program_args[@]}")
    code=$?

    if [[ $code != 0 ]]; then
        echo "ERROR: Interpreter returned error code for input file \"$1\""
        echo "Expected success and output \"$2\""
        exit $code
    fi

    # Interpret escape sequences like \n in the expected output
    expected_output=$(printf "%b" "$2")

    if [[ "$output" != "$expected_output" ]]; then
        echo "ERROR: Interpreter produced unexpected output: \"$output\""
        echo "Expected this output: \"$expected_output\""
        exit 1
    fi
}

function err {
    echo "Running failure test: $1"
    output=$(echo "$@" | ./run.sh)
    code=$?
    if [[ $code == 0 ]]; then
        echo "ERROR: Interpreter succeeded but should not have!"
        echo "Input was \"$1\""
        exit 1
    fi
    # Suppress output for expected error cases
}

function err_file {
    echo "Running failure test from file: $1"
    output=$(cat "$1" | ./run.sh)
    code=$?
    if [[ $code == 0 ]]; then
        echo "ERROR: Interpreter succeeded but should not have!"
        echo "Input was \"$1\""
        exit 1
    fi
    # Suppress output for expected error cases
}

# Run tests

# CP2 tests
ok '123' 123
ok 'x' 10
ok 'v' 5
ok 'i' 1
ok '000' '0'
ok '-120' '-120'
ok 'add(1, 2)' 3
ok 'add(x, 2)' 12
ok 'add' # no test to compare against because the result as string will vary every time based on memory address
ok 'add(sub(x, 1), 2)' 11
ok '"hi"' '"hi"'
err '(-120.0)'  # Expected error, no output
err '(y)'       # Expected error, no output
err 'add(add, 1)' # Expected error, no output

# CP3 tests
ok_file './test-files/cp3ex1.417' 18
ok_file './test-files/cp3ex2.417' 1
ok_file './test-files/cp3ex3.417' 90
ok_file './test-files/cp3ex4.417' 3628800
ok_file './test-files/cp3-factorial.417' 720

# CP4 tests
ok 'lambda(a, b) {b}' '{"Lambda":[{"Parameters":[{"Identifier": "a"},{"Identifier": "b"}]},{"Block":[{"Identifier": "b"}]}]}'
ok 'lambda(x, y) {mul(x, y)} (3, 4)' 12
ok '{1; 2; add(10,20); sub(add(3,3), 1)}' 5
ok '{add(3,2); 2; cond (false => 1) (true => mul(2, 2))}' 4

# CP5 tests
ok_file './test-files/cp5ex1.417' 6
ok_file './test-files/cp5ex1.417' 6 '-d'
ok_file './test-files/cp5ex2.417' 6
ok_file './test-files/cp5ex2.417' 105 '-d'
err_file './test-files/cp5ex3.417'  # Expected error, no output
ok_file './test-files/cp5ex2.417' 105 '-d'
ok_file './test-files/cp5ex4.417' '"D"'

# CP6 tests
ok_file './test-files/cp6ex1.417' 11
ok_file './test-files/cp6ex2.417' 4
ok_file './test-files/cp6ex3.417' 5040
ok_file './test-files/cp6ex4.417' "x is 333\ny is 333\nz is 333\n" '-np'
# ok_file './test-files/cp6ex4.417'

echo "All tests passed!"

$SHELL
