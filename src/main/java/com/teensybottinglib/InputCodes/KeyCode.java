package com.TeensyBottingLib.InputCodes;

public enum KeyCode {
    // Letters A-Z
    A("A"), B("B"), C("C"), D("D"), E("E"), F("F"), G("G"),
    H("H"), I("I"), J("J"), K("K"), L("L"), M("M"), N("N"),
    O("O"), P("P"), Q("Q"), R("R"), S("S"), T("T"), U("U"),
    V("V"), W("W"), X("X"), Y("Y"), Z("Z"),

    // Digits 0-9
    DIGIT_0("0"), DIGIT_1("1"), DIGIT_2("2"), DIGIT_3("3"), DIGIT_4("4"),
    DIGIT_5("5"), DIGIT_6("6"), DIGIT_7("7"), DIGIT_8("8"), DIGIT_9("9"),

    // Symbols
    DASH("-"),
    EQUAL("="),
    LEFT_BRACKET("["),
    RIGHT_BRACKET("]"),
    BACKSLASH("\\"),
    SEMICOLON(";"),
    QUOTE("'"),
    COMMA(","),
    PERIOD("."),
    SLASH("/"),
    BACKTICK("`"),
    SPACE("SPACE"),

    // Modifier keys
    LSHIFT("LSHIFT"),
    RSHIFT("RSHIFT"),
    LCTRL("LCTRL"),
    RCTRL("RCTRL"),
    LALT("LALT"),
    RALT("RALT"),

    // Function keys
    F1("F1"), F2("F2"), F3("F3"), F4("F4"), F5("F5"),
    F6("F6"), F7("F7"), F8("F8"), F9("F9"), F10("F10"),
    F11("F11"), F12("F12"),

    // Navigation and editing
    ENTER("ENTER"),
    RETURN("RETURN"),
    ESC("ESC"),
    TAB("TAB"),
    BACKSPACE("BACKSPACE"),
    DELETE("DELETE"),
    INSERT("INSERT"),
    HOME("HOME"),
    END("END"),
    PAGE_UP("PAGEUP"),
    PAGE_DOWN("PAGEDOWN"),

    // Arrow keys
    UP("UP"),
    DOWN("DOWN"),
    LEFT("LEFT"),
    RIGHT("RIGHT");

    private final String serialValue;

    KeyCode(String serialValue) {
        this.serialValue = serialValue;
    }

    public String getSerialValue() {
        return serialValue;
    }
}

