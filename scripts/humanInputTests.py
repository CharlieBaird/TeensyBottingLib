from pynput import keyboard, mouse
import time

key_state = {}
mouse_state = {}

def format_duration(start, end):
    return f"{(end - start)*1000:.2f} ms"

# Keyboard event handlers
def on_key_press(key):
    now = time.time()
    key_str = str(key)
    if key == keyboard.Key.esc:
        print("Escape pressed. Exiting...")
        return False  # Stops the keyboard listener
    if key_str not in key_state:
        key_state[key_str] = now
        print(f"[KEY DOWN] {key_str} at {now:.4f}")

def on_key_release(key):
    now = time.time()
    key_str = str(key)
    if key_str in key_state:
        duration = format_duration(key_state[key_str], now)
        print(f"[KEY UP  ] {key_str} at {now:.4f} — Held for {duration}")
        del key_state[key_str]

# Mouse event handlers
def on_click(x, y, button, pressed):
    now = time.time()
    btn_str = str(button)
    if pressed:
        mouse_state[btn_str] = now
        print(f"[MOUSE DOWN] {btn_str} at {x},{y} — {now:.4f}")
    else:
        if btn_str in mouse_state:
            duration = format_duration(mouse_state[btn_str], now)
            print(f"[MOUSE UP  ] {btn_str} at {x},{y} — {now:.4f} — Held for {duration}")
            del mouse_state[btn_str]

print("Listening for keyboard and mouse events... (Ctrl+C to stop)")

keyboard_listener = keyboard.Listener(on_press=on_key_press, on_release=on_key_release)
mouse_listener = mouse.Listener(on_click=on_click)

keyboard_listener.start()
mouse_listener.start()

keyboard_listener.join()
mouse_listener.join()
