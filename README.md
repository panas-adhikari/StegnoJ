# Steganographer

## Description
This Java program allows users to hide and retrieve messages within files using steganography techniques. It supports hiding messages in files by appending data to the end of the file and retrieving the hidden messages later.

## Features
- Clone a source file to create a destination file.
- Append a hidden message to the destination file.
- Extract the hidden message from the destination file.
- User-friendly menu-driven interface.

## Usage Instructions
1. Run the program.
2. Choose an option from the menu:
   - **1. Hide Data**: Provide a source file and a message to hide. The program will create a copy of the source file and append the hidden message to it.
   - **2. Get Data**: Provide the path to a file containing hidden data. The program will extract and display the hidden message.
   - **3. Exit**: Exit the program.

## Warning ⚠️
**Use only image or audio files as input (e.g., `.jpg`, `.png`, `.mp3`). Do not use text files or other unsupported formats as input.**

Using unsupported file types may lead to unexpected behavior or data corruption.

## Requirements
- Java Development Kit (JDK) 8 or higher.

## How to Compile and Run
1. Open a terminal and navigate to the directory containing the `Stegnographer.java` file.
2. Compile the program:
   ```
   javac Stegnographer.java
   ```
3. Run the program:
   ```
   java Stegnographer
   ```

## Disclaimer
This program is for educational purposes only. Use it responsibly and ensure you have permission to modify any files used as input.
