package com.charliebaird;

import com.fazecast.jSerialComm.SerialPort;

import java.io.PrintWriter;

public class TeensyController {
    private SerialPort port;
    private PrintWriter writer;

    private static String getComForTeensy()
    {
        SerialPort[] ports = SerialPort.getCommPorts();
        for (SerialPort p : ports) {
            if (p.getDescriptivePortName().startsWith("USB Serial Device")) {
                return p.getSystemPortName();
            }
        }

        return null;
    }

    public TeensyController()
    {
        this(getComForTeensy());
    }

    public TeensyController(String portDescriptor) {
        port = SerialPort.getCommPort(portDescriptor);
        port.setBaudRate(115200);
        port.setNumDataBits(8);
        port.setNumStopBits(SerialPort.ONE_STOP_BIT);
        port.setParity(SerialPort.NO_PARITY);
        port.setComPortTimeouts(SerialPort.TIMEOUT_WRITE_BLOCKING, 0, 0);

        if (port.openPort()) {
            writer = new PrintWriter(port.getOutputStream(), true);
            System.out.println("Connected to Teensy on " + portDescriptor);
        } else {
            throw new RuntimeException("Failed to open serial port: " + portDescriptor);
        }
    }

    public void close() {
        if (writer != null) writer.close();
        if (port != null && port.isOpen()) port.closePort();
    }

    public void sendCommand(String command) {
        if (writer != null) {
            writer.println(command);
            writer.flush();
        }
    }

    // --- Convenience methods ---

    public void mouseMove(int dx, int dy) {
        sendCommand("MOUSE MOVE " + dx + " " + dy);
    }

    public void mouseClick(String button) {
        sendCommand("MOUSE CLICK " + button.toLowerCase());
    }

    public void mousePress(String button) {
        sendCommand("MOUSE PRESS " + button.toLowerCase());
    }

    public void mouseRelease(String button) {
        sendCommand("MOUSE RELEASE " + button.toLowerCase());
    }

    public void keyPress(String key, String... modifiers) {
        sendCommand("KEY PRESS " + String.join(" ", modifiers) + " " + key);
    }

    public void keyRelease(String key) {
        sendCommand("KEY RELEASE " + key);
    }
}