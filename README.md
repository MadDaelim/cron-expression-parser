# Cron Expression Parser

This program is a simple cron expression parser written in Java. It helps you understand when a scheduled task will run
based on the provided cron expression.

What it does:

Takes a cron expression as input (e.g., "*/15 0 1,15 * 1-5 /usr/bin/find").
Parses the expression and analyzes each field (minute, hour, day of month, etc.).
Prints a human-readable description of when the command associated with the cron expression will be executed.
For example,

Input:

```
*/15 0 1,15 * 1-5 /usr/bin/find
```

Output:

```
minute        0 15 30 45
hour          0
day of month  1 15
month         1 2 3 4 5 6 7 8 9 10 11 12
day of week   1 2 3 4 5
command       /usr/bin/find
```

# Building from sources

## Requirements

The graalVM JDK is required to build the application.

### GraalVM

##### Installation via SDKMAN!

SDKMAN! is a popular tool that helps you install and switch between JDKs, including GraalVM.

```
sdk install java 21.0.3-graal
```

Set GraalVM as default java.

```
sdk use java 21.0.3-graal
```

##### other methods

https://www.graalvm.org/latest/docs/getting-started/linux/

# Testing

To run the tests, execute the following command:

```
./gradlew test
```

# Executable

To build the executable file, execute the following gradle command:

```
./gradlew nativeCompile
```

After the command is successfully completed, the executable file will be in folder `build\native\nativeCompile`.
