# BPMS (Blood Pressure Monitoring System)

This project was developed within the course Advanced Programming Techniques in ECE
at University of Patras.

BPMS is a system used for remote measurements of blood pressure of patients from doctors. 
Cloud and IoT technologies are used in order to implement the system to be as realistic
as possible. In this implementation Microsoft Azure was used along with Leshan LWM2M. 

## System Architecture
* Server is deployed in Azure in a Docker Container along with an SQL database
* App is installed in doctor's computer
* Device is installed in the device used to measure blood pressure
