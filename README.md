# Splitter-Transform EIP with Kafka & Apache Camel

This project demonstrates an enterprise integration pattern (EIP) for stream processing using Apache Kafka and Apache Camel in Java. The core functionality is to receive, split, process, and route batches of flight data messages, offering both stateless transformation and stateful aggregation in a scalable, fault-tolerant way.

## 🛠️ How It Works

1. **Kafka Input**  
   The application consumes batches of flight data messages from a Kafka topic.

2. **Splitting**  
   Each batch is split into individual flight messages using Camel's split pattern.

3. **Parallel Routing**  
   Each flight message is routed to two separate flows:
   - **Stateful Processing:** Maintains a count of flights from each departure location.
   - **Transformation:** Calculates and adds the duration of the flight based on departure and arrival times.

4. **Kafka Output**  
   Transformed messages are published to another Kafka topic. Stateful results are logged and can be further extended for persistence.

5. **Error Handling**  
   Robust centralized exception handling routes failed messages to a dead letter queue (DLQ) in Kafka.

## 🏗️ Architecture

The following diagram illustrates the core architecture:

![image](https://github.com/user-attachments/assets/975c84fb-95c9-4500-acce-be15b7950c1d)

- Input Kafka Topic ⟶ Split (Camel) ⟶ [Stateful Counter, Value Transformer] ⟶ Output Kafka Topic / Logs

## ✈️ Example Use Case

- **Flight Data Processing:**  
  Send a JSON array of flights to the input topic.  
  Each flight:
    - Is counted per departure city (stateful).
    - Has its duration calculated and appended (stateless transform).
    - Is output to downstream Kafka topics or logs.

## 📦 Key Technologies

- **Apache Camel:** For routing, splitting, and pattern implementation.
- **Apache Kafka:** For scalable, distributed messaging.
- **Spring Boot:** For application lifecycle, configuration, and dependency injection.
- **Java:** 100% Java implementation.

## 🚀 Getting Started

1. **Clone the repository:**
    ```bash
    git clone https://github.com/Sahillllxx/Splitter-Transform_EIP_Kafka-Apache-Camel.git
    cd Splitter-Transform_EIP_Kafka-Apache-Camel
    ```

2. **Configure Kafka:**
    - Set your Kafka broker and topic names in `application.properties`.

3. **Build & Run the application:**
    ```bash
    mvn clean install
    mvn spring-boot:run
    ```

4. **Send Messages:**  
   Produce a JSON array of flight objects to the input Kafka topic.  
   Example message:
   ```json
   [
     {
       "flightNumber": "AI101",
       "departure": "DEL",
       "arrival": "NYC",
       "departureTime": "2025-06-08T10:00:00",
       "arrivalTime": "2025-06-08T16:00:00"
     }
   ]
   ```

5. **Observe Output:**  
   Check the output Kafka topic for messages with added duration, and view logs for stateful statistics.

## ⚙️ Configuration

- **Kafka Topics:**  
  - Input, Output, and DLQ topics set in `application.properties`.
- **Custom Processing:**  
  - Modify processors in `src/main/java/com/learning/processor` for new logic.

## 🧩 Extensibility

- Add new processors/routes for additional computation.
- Extend stateful processing to persist results in a database.
- Integrate with monitoring or alerting tools via Camel endpoints.

---

For questions or support, please open an issue in this repository.
