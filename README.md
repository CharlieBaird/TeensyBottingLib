# TeensyBottingLib

Java framework used to generate serial messages to a Teensy 4.0 microcontroller.

This is made as a library to be used for creating undetectable computer-generated inputs, as a Teensy microcontroller acts as a real USB device.

Includes various other features to prevent bot detection, whether in a videogame or a social media usecase. See `Notes` section below.

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

## Teensy as a Spoof Keyboard/Mouse

Use ArduinoIDE with Teensyduino extension.

To spoof a real keyboard/mouse, find a known `VENDOR_ID` and `PRODUCT_ID` and replace the teensy ones in `...arduino/packages/teensy/hardware/avr/[version]/cores/teensy4/usb_desc.h` using your configuration. If you're using `SERIAL+KEYBOARD+MOUSE+JOYSTICK`, change the values under `USB_SERIAL_HID`.

Further, change the `MANUFACTURER_NAME` and `PRODUCT_NAME` to something realistic (Razer, Logitech, etc., and one of their products)

Finally, change the serial number in `usb_desc.c` to a random 8–16 character alphanumeric string by changing the following:
```c
struct usb_string_descriptor_struct usb_string_serial_number = {
      sizeof(struct {
          uint8_t bLength;
          uint8_t bDescriptorType;
          uint16_t wString[8];
      }),
      3,
      {'5','1','A','6','2','4','B','4'}
  };
```


Restart your ArduinoIDE and relaunch your code onto your Teensy. Look in regedit (`HKEY_LOCAL_MACHINE/SYSTEM/CurrentControlSet/Enum/USB/` for a device of your `VENDOR_ID` and `PRODUCT_ID`)

Alternatively, use `USBDeview` to more easily look at your USB devices to see if the spoof worked properly.

## Notes

Uses (NaturalMouseMotion)[https://github.com/JoonasVali/NaturalMouseMotion] to generate human-like mouse movement.

Delay method `delayMS` adds a random element to the delay to prevent detection.

Prevents releasing keys/clicks that are not being held down.
