# Group 76 Point of Sale System - NUST PRG620S 

This is a NUST PRG620S  project that involved creating a Point of Sale System to transact electronic money between purchasers and dealers.

The system's scope was limited to just Bob card payment processing, transaction management, report generation and stock tracking.

The project was developed using Java and Swing in IntelliJ IDEA.

## Installation

1. Download the [latest release](https://github.com/siphomateke/Group76Pos/releases/latest)
2. Install the Java 11 Runtime Environment 
3. Run `PointOfSaleSystem.jar` file by double-clicking it

Alternatively, run the following in a terminal:

```bash
java -jar ./PointOfSaleSystem.jar
```

## Usage

There are currently only 3 bank accounts hard-coded into the system that can be used just to demonstrate the functionality:

| Account Number | PIN  | Balance |
| -------------- | ---- | ------- |
| 220040869      | 2001 | 10000   |
| 219002444      | 2000 | 60      |
| 220101361      | 2000 | 800     |

## Development

You can also open this project in a IntelliJ IDEA and run the project by creating a Run configuration that targets the Java 11 SDK and uses `com.group76pos.App` as the main class. 

