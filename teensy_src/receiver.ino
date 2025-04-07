#include <Arduino.h>

// Teensy includes USB Keyboard and Mouse automatically with correct USB type

const int SERIAL_BAUD = 115200;

uint16_t getKeyCode(String key) {
  key.toUpperCase();

  // Single character keys
  if (key.length() == 1) {
    char c = key.charAt(0);
    if (c >= 'A' && c <= 'Z') return c + 32;  // convert to lowercase ASCII
    if (c >= '0' && c <= '9') return c;

    // Common symbols
    switch (c) {
      case '-': return '-';
      case '=': return '=';
      case '[': return '[';
      case ']': return ']';
      case '\\': return '\\';
      case ';': return ';';
      case '\'': return '\'';
      case ',': return ',';
      case '.': return '.';
      case '/': return '/';
      case '`': return '`';
      case ' ': return KEY_SPACE;
    }
  }

  // Modifier keys
  if (key == "LSHIFT") return KEY_LEFT_SHIFT;
  if (key == "RSHIFT") return KEY_RIGHT_SHIFT;
  if (key == "LCTRL")  return KEY_LEFT_CTRL;
  if (key == "RCTRL")  return KEY_RIGHT_CTRL;
  if (key == "LALT")   return KEY_LEFT_ALT;
  if (key == "RALT")   return KEY_RIGHT_ALT;

  // Navigation / editing
  if (key == "ENTER")  return KEY_ENTER;
  if (key == "RETURN") return KEY_ENTER;
  if (key == "ESC")    return KEY_ESC;
  if (key == "TAB")    return KEY_TAB;
  if (key == "SPACE")  return KEY_SPACE;
  if (key == "BACKSPACE") return KEY_BACKSPACE;
  if (key == "DELETE")    return KEY_DELETE;
  if (key == "HOME")      return KEY_HOME;
  if (key == "END")       return KEY_END;
  if (key == "PAGEUP")    return KEY_PAGE_UP;
  if (key == "PAGEDOWN")  return KEY_PAGE_DOWN;
  if (key == "INSERT")    return KEY_INSERT;

  // Arrow keys
  if (key == "UP")     return KEY_UP;
  if (key == "DOWN")   return KEY_DOWN;
  if (key == "LEFT")   return KEY_LEFT;
  if (key == "RIGHT")  return KEY_RIGHT;

  // Function keys
  if (key == "F1")  return KEY_F1;
  if (key == "F2")  return KEY_F2;
  if (key == "F3")  return KEY_F3;
  if (key == "F4")  return KEY_F4;
  if (key == "F5")  return KEY_F5;
  if (key == "F6")  return KEY_F6;
  if (key == "F7")  return KEY_F7;
  if (key == "F8")  return KEY_F8;
  if (key == "F9")  return KEY_F9;
  if (key == "F10") return KEY_F10;
  if (key == "F11") return KEY_F11;
  if (key == "F12") return KEY_F12;

  return 0; // Unknown key
}


String nextToken(String& s) {
  int idx = s.indexOf(' ');
  String token = idx >= 0 ? s.substring(0, idx) : s;
  s = idx >= 0 ? s.substring(idx + 1) : "";
  token.trim();
  return token;
}

void setup() {
  Serial.begin(SERIAL_BAUD);
  while (!Serial && millis() < 3000);
  Keyboard.begin();
  Mouse.begin();
  Mouse.screenSize(1920, 1080);
}

void loop() {
  if (Serial.available()) {
    String inputLine = Serial.readStringUntil('\n');
    inputLine.trim();
    String cmd = nextToken(inputLine);
    cmd.toUpperCase();

    if (cmd == "MOUSE") {
      String action = nextToken(inputLine);
      action.toUpperCase();

      if (action == "MOVE") {
        int x = nextToken(inputLine).toInt();
        int y = nextToken(inputLine).toInt();

        Mouse.moveTo(x, y);
      }
      else if (action == "PRESS") {
        String btn = nextToken(inputLine);
        btn.toLowerCase();
        if (btn == "left") Mouse.press(MOUSE_LEFT);
        else if (btn == "right") Mouse.press(MOUSE_RIGHT);
        else if (btn == "middle") Mouse.press(MOUSE_MIDDLE);
      }
      else if (action == "RELEASE") {
        String btn = nextToken(inputLine);
        btn.toLowerCase();
        if (btn == "left") Mouse.release(MOUSE_LEFT);
        else if (btn == "right") Mouse.release(MOUSE_RIGHT);
        else if (btn == "middle") Mouse.release(MOUSE_MIDDLE);
      }
    }

    else if (cmd == "KEY") {
      String action = nextToken(inputLine);
      String key = nextToken(inputLine);

      uint16_t keycode = getKeyCode(key);

      if (keycode == 0) {
        Serial.println("Unknown key: " + key);
        return;
      }

      if (action == "PRESS") {
        Keyboard.press(keycode);
      } else if (action == "RELEASE") {
        Keyboard.release(keycode);
      }
    }
  }
}

void pressKey(uint16_t key) {
  Keyboard.press(key);
  Keyboard.send_now();
}

void releaseKey(uint16_t key) {
  Keyboard.release(key);
  Keyboard.send_now();
}
