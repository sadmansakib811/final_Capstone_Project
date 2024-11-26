package energymanagement;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

public class EnergyManagementUI extends JFrame {
    private Battery battery;
    private LogManager logManager;
    private EnergySimulator simulator;

    private JLabel batteryLabel;
    private JTextArea logTextArea;
    private JTextArea realTimeLogTextArea; // For real-time monitoring logs

    public EnergyManagementUI(Battery battery, LogManager logManager, EnergySimulator simulator) {
        this.battery = battery;
        this.logManager = logManager;
        this.simulator = simulator;

        setTitle("Smart House - Energy Management System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTabbedPane tabbedPane = new JTabbedPane();
        JPanel smartObjectsPanel = createSmartObjectsPanel();
        JPanel energySourcesPanel = createEnergySourcesPanel();
        JPanel realTimeMonitorPanel = createRealTimeMonitorPanel();
         //Farhana smart object control tab
        tabbedPane.addTab("Smart Objects", smartObjectsPanel);

        //SADMAN -energy source tab
        tabbedPane.addTab("Energy Sources", energySourcesPanel);

        //Azad tab
        tabbedPane.addTab("Real-time Monitor", realTimeMonitorPanel);

        add(tabbedPane, BorderLayout.CENTER);

        // Log area
        logTextArea = new JTextArea(10, 50);
        logTextArea.setEditable(false);
        JScrollPane logScrollPane = new JScrollPane(logTextArea);
        add(logScrollPane, BorderLayout.SOUTH);

        updateBatteryStatus();
        updateLogDisplay();

        // Add window listener to stop simulation on close
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                simulator.stopSimulation();
                System.exit(0);
            }
        });
    }
    
//farhana
    private JPanel createSmartObjectsPanel() {
        JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10));
        panel.add(createSmartObjectControl("Lighting"));
        panel.add(createSmartObjectControl("Heating"));
        panel.add(createSmartObjectControl("H-Bahn"));
        panel.add(createSmartObjectControl("Car Charging Station"));
        return panel;
    }









    
//farhana and Azad
    private JPanel createSmartObjectControl(String name) {
        JPanel panel = new JPanel();
        JLabel label = new JLabel(name);
        JButton applyButton = new JButton("Apply");
        JTextField consumptionField = new JTextField("100", 5);

        applyButton.addActionListener(e -> {
            try {
                int consumptionLimit = Integer.parseInt(consumptionField.getText());
                setConsumptionLimit(name, consumptionLimit);
                logManager.logEvent(name + " consumption set to " + consumptionLimit);
                updateLogDisplay();
                // Generate new log files based on updated settings
                logManager.generateLogFiles();
            } catch (NumberFormatException ex) {
                logTextArea.append("Invalid consumption value for " + name + ".\n");
            } catch (IOException ioEx) {
                logTextArea.append("Error generating log files.\n");
            }
        });

        panel.add(label);
        panel.add(new JLabel("Max Consumption: "));
        panel.add(consumptionField);
        panel.add(applyButton);
        return panel;
    }





    


    private JPanel createRealTimeMonitorPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        realTimeLogTextArea = new JTextArea(15, 50);
        realTimeLogTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(realTimeLogTextArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        batteryLabel = new JLabel();
        panel.add(batteryLabel, BorderLayout.NORTH);

        // Timer to update the real-time monitoring panel
        Timer timer = new Timer(1000, e -> {
            updateBatteryStatus();
            String logEntry = String.format(
                "Battery Charge: %d\n" +
                "Wind Turbine Generation: %d units\n" +
                "Solar Panel Generation: %d units\n" +
                "Lighting Consumption: %d units\n" +
                "Heating Consumption: %d units\n" +
                "H-Bahn Consumption: %d units\n" +
                "Car Charging Consumption: %d units\n" +
                "--------------------------------------------------\n",
                battery.getCurrentCharge(),
                simulator.getLastWindTurbineGeneration(),
                simulator.getLastSolarPanelGeneration(),
                simulator.getLastLightingConsumption(),
                simulator.getLastHeatingConsumption(),
                simulator.getLastHBahnConsumption(),
                simulator.getLastCarChargingConsumption()
            );
            realTimeLogTextArea.append(logEntry);
            if (realTimeLogTextArea.getLineCount() > 100) {
                realTimeLogTextArea.setText("");
            }
        });
        timer.start();

        return panel;
    }

    private void setConsumptionLimit(String type, int limit) {
        switch (type) {
            case "Lighting":
                simulator.setMaxLightingConsumption(limit);
                break;
            case "Heating":
                simulator.setMaxHeatingConsumption(limit);
                break;
            case "H-Bahn":
                simulator.setMaxHBahnConsumption(limit);
                break;
            case "Car Charging Station":
                simulator.setMaxCarChargingConsumption(limit);
                break;
        }
    }

    private void setGenerationRate(String type, int rate) {
        if ("Solar Panel".equals(type)) {
            simulator.setSolarPanelGenerationRate(rate);
        } else if ("Wind Turbine".equals(type)) {
            simulator.setWindTurbineGenerationRate(rate);
        }
    }

}
