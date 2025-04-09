package com.TeensyBottingLib;

import com.fazecast.jSerialComm.SerialPort;

import java.io.PrintWriter;
import java.util.ArrayList;

public class TeensyIO
{
    private SerialPort port;
    private PrintWriter writer;

    private static ArrayList<String> getComForTeensy()
    {
        SerialPort[] ports = SerialPort.getCommPorts();
        ArrayList<String> portsList = new ArrayList<>();
        for (SerialPort p : ports) {
            if (p.getDescriptivePortName().startsWith("USB Serial Device")) {
                portsList.add(p.getSystemPortName());
            }
        }

        return portsList;
    }

    public TeensyIO()
    {
        this(getComForTeensy());
    }

    public TeensyIO(ArrayList<String> ports)
    {
        for (String portDescriptor : ports)
        {
            port = SerialPort.getCommPort(portDescriptor);
            port.setBaudRate(115200);
            port.setNumDataBits(8);
            port.setNumStopBits(SerialPort.ONE_STOP_BIT);
            port.setParity(SerialPort.NO_PARITY);
            port.setComPortTimeouts(SerialPort.TIMEOUT_WRITE_BLOCKING, 0, 0);

            if (port.openPort()) {
                writer = new PrintWriter(port.getOutputStream(), true);
                System.out.println("Connected to Teensy on " + portDescriptor);
                return;
            } else {
                System.out.println("Failed to connect to Teensy on " + portDescriptor);
            }
        }
    }

    public void close()
    {
        if (writer != null) writer.close();
        if (port != null && port.isOpen()) port.closePort();
    }

    public void sendCommand(String command)
    {
        if (writer != null) {
            writer.println(command);
            writer.flush();
        }
    }

    // --- Convenience methods ---

    public void mouseMove(int x, int y)
    {
        sendCommand("MOUSE MOVE " + x + " " + y);
    }

    public void mouseMoveRelative(int x, int y)
    {
        sendCommand("MOUSE MOVEREL " + x + " " + y);
    }

    public void mousePress(String button)
    {
        sendCommand("MOUSE PRESS " + button.toLowerCase());
    }

    public void mouseRelease(String button)
    {
        sendCommand("MOUSE RELEASE " + button.toLowerCase());
    }

    public void keyPress(String key)
    {
        sendCommand("KEY PRESS " + key);
    }

    public void keyRelease(String key)
    {
        sendCommand("KEY RELEASE " + key);
    }
}