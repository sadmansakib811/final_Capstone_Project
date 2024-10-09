# Energy Management Log Simulation

This project simulates energy generation and consumption for different energy sources over multiple days. It generates log files with random values representing energy produced by sources like wind turbines and solar panels, and energy consumed by systems such as lighting, heating, and the H-Bahn. The log files can be searched using both simple string search and regular expression search.
Video Demo
# Team Members:
1. **Sadman Sakib:** (Matriculation Number: 7213446),
2. **Farhana Binta Shaheed:** (Matriculation Number: 7216429),
3. **Md Azad Hossain:** (Matriculation Number: 7213230),
3. **Md Istiak Javed:** (Matriculation Number: 7221689),



# To see a video demonstration of the project, click here to watch the video.
 - Sadman Sakib Video: https://drive.google.com/file/d/1v0NHGPxLZAyZTUaV7oAmSZYPv4dz1gxT/view?usp=sharing
- Farhana Binta Shaheed Video: https://drive.google.com/file/d/1HmPb4ibJ-OcynqIYzDR40xVqd-ZPTEbj/view?usp=sharing

## Contributors
- Sadman Sakib: Implemented the core functionality in Main.java, LogManager.java, and EnergySimulator.java.
- Farhana Binta Shaheed: Implemented regular expression search.
- Istiak Javed: Handled simple string search.
- Ayad Hossain: Managed file reading and displaying log content in LogSearch.java.
## Project Structure

- **`Main.java`**: 
  - Manages the flow of the program.
  - Calls the log generation process and handles user input for searching the log files.
  - Prompts the user to select between simple string search or regular expression search to find specific terms in the log files.

- **`LogManager.java`**:
  - Responsible for generating 5 days of log files, with each log file containing:
    - Energy generation data from wind turbines and solar panels.
    - Energy consumption data for lighting, heating, and the H-Bahn.
    - The balance between total generated and consumed energy.
  - The generated files are saved with the format: `FH_DORTMUND_energy_management_date_YYYY-MM-DD.log`.

- **`EnergySimulator.java`**:
  - Simulates random energy data for various sources and consumption points.
  - It has methods like `simulateWindTurbine()` and `simulateLighting()` that generate random values for energy generation and consumption.

- **`LogSearch.java`**:
  - Handles the search functionality for the generated log files.
  - Offers two types of searches:
    1. **Simple String Search**: Case-insensitive search for specific terms (like "Lighting" or "Heating") in the log files.
    2. **Regular Expression Search**: Allows searching using regular expressions (e.g., find all energy values using `\d+ kWh`).

## How to Use the Program

1. **Run the Program**:
   - When the program starts, it generates 5 days of log files. These files contain random energy generation and consumption data.
   
2. **Select a Log File**:
   - The program lists the available log files. You can choose a log file by entering the corresponding number.

3. **Search the Log File**:
   - After selecting the log file, you can choose to search using:
     - **Simple String Search** (e.g., searching for "Heating").
     - **Regular Expression Search** (e.g., searching for `\d+ kWh` to find energy values).
   - The program will display matching lines from the log file.

## Example Output

- Available log files:

- **FH_DORTMUND_energy_management_date_2024-10-07.log
- **FH_DORTMUND_energy_management_date_2024-10-06.log
- **FH_DORTMUND_energy_management_date_2024-10-05.log
- **FH_DORTMUND_energy_management_date_2024-10-04.log
- **FH_DORTMUND_energy_management_date_2024-10-03.log
- **Enter the number of the log file you want to open: 1
### After opening a log file output:
- Showing content of FH_DORTMUND_energy_management_date_2024-10-07.log: Energy Generation: Wind Turbine:  369 kWh Solar Panel: 95 kWh Total Generated: 464 kWh

- **Energy Consumption: Lighting: 157 kWh Heating: 122 kWh H-Bahn: 9 kWh Total Consumed: 288 kWh

- Energy Balance: 176 kWh

- Select the type of search:

- Simple String Search
- Regular Expression Search
- Enter your search term or regular expression: \d+ kWh

Match found: Wind Turbine: 369 kWh Match found: Solar Panel: 95 kWh Match found: Lighting: 157 kWh Match found: Heating: 122 kWh Match found: H-Bahn: 9 kWh Match found: Total Consumed: 288 kWh Match found: Energy Balance: 176 kWh

bash
Copy code

## How to Run

1. **Clone the repository**
  
2. **run the program or main file**





