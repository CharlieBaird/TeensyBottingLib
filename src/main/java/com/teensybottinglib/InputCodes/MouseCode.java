package com.teensybottinglib.InputCodes;

public enum MouseCode
{
    LEFT("left"),
    MIDDLE("middle"),
    RIGHT("right");

    private final String serialValue;

    MouseCode(String serialValue) {
        this.serialValue = serialValue;
    }

    public String getSerialValue() {
        return serialValue;
    }
}
