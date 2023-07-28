# Introduction
This repository contains a Java application, written by a junior engineer, that searches for DICOM files in a list of directories. This tool was used to audit DICOM data held on company servers; now it is to be expanded to anonymise the data it finds.

# Brief
You have been asked to lead this project and create a proof-of-concept for the anonymisation. You have also been asked to provide feedback to the engineer on their work so far. This technical challenge consists of two tasks.

## Part 1: Review the code
Review the code in this repository, and provide feedback and suggestions for the developer. You may wish to do this in the form of comments on a pull request or feedback in an email.

We will be looking at your assessment and suggestions for the code, as well as how the feedback is communicated to the engineer. 

## Part 2: Create a proof-of-concept
Adapt the code such that the user can "anonymise" the DICOM and produce an audit log of the anonymisation. The application should anonymise any patient details, and the user would also like to normalise times in the data such that it is consistent with an acquisition starting at 12 noon on 1st January 2023 in London. After performing the anonymisation, the application should store the anonymised data as new DICOM files with any further tag changes as required by the DICOM standard.

The application should produce an audit log of the work it performs. The content and format of the log can be of your own design. 

Sample data has been provided in the `sample_data` folder. This is synthetic data so already has synthetic patient details. For the purpose of this exercise, assume that what is there is identifiable and needs anonymising.

We will be looking at your ability to understand and modify the provided code, your knowledge and application of the DICOM standard, and the design decisions you make.

# Getting started
- Please either fork or download a copy of this repository
- Modify the file `/searchtool/src/main/resources/scraperPaths.csv` with the absolute path of the project
- To build the application run `mvn clean package`
- To run the application, invoke the main method in `DicomSearchTool.java` with no arguments
- Put any assumptions, notes or instructions into your README.md.
- When finished, provide us with a link to your repo or a zip of its contents
- If choosing to provide written feedback, please put it in a text document in the repo

Please get in touch if you need any help, we look forward to seeing your solution!