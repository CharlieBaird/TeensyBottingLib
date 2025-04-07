# TeensyBottingLib

Java framework used to generate serial messages to a Teensy 4.0 microcontroller.

This is made as a library to be used for creating undetectable computer-generated inputs, as a Teensy microcontroller acts as a real USB device.

## Usage

Either use the wrapper methods with an instance of `TeensyBot` like below:

```java
TeensyBot teensyBot = new TeensyBot();

teensyBot.mouseMoveGeneralLocation(new Point(500, 500));

teensyBot.leftClick();
teensyBot.keyClick("a");

teensyBot.keyPress(KeyCode.LSHIFT);
teensyBot.keyPress(KeyCode.A);
teensyBot.delayMS(1000);
teensyBot.keyRelease(KeyCode.A);
teensyBot.keyRelease(KeyCode.LSHIFT);
```

Or, send direct commands such as `MOUSE MOVE 50 100` to move the mouse to (50, 100) on the screen.
Others:
- `MOUSE PRESS LEFT`
- `MOUSE RELEASE LEFT`
- `KEY PRESS X`
- `KEY PRESS LSHIFT`
- etc.
