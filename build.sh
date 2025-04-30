#!/bin/bash

SRC_DIR="src"
OUT_DIR="bin"
LIB_DIR="lib"

# Create output directory if it doesn't exist
mkdir -p "$OUT_DIR"

# Build classpath string (include all JARs in lib/)
CP=$(find "$LIB_DIR" -name "*.jar" | paste -sd ":" -)

# Compile Java files
javac -cp "$CP" -d "$OUT_DIR" $(find "$SRC_DIR" -name "*.java")

# Report status
if [ $? -eq 0 ]; then
    echo "Compilation successful."
else
    echo "Compilation failed."
fi
