# DICOM Anonymisation Tool
This repository contains a Java application that uses a modified version of the search tool for finding DICOM files in a list of directories. 

## Details
The code use a simple interface that is passed to the search tool to allow DICOM files to be processed via different implementations.  This will allow the same design to be used to do different things with the DICOM files that the search tool finds.  This implementation is to anonymise the data, but other implementations could edit, move or delete the DICOM files.

The anonymisation tool allows for different anonymisation profiles to be defined, to allow different tags to be removed based on requirements.  

The root DICOM UID was 25 characters which is an issue for dcm4che, so it was truncated by 1 for the purposes of this proof of concept.  A different re-UID process may be needed in practice.

General application logging has been performed using log4j but the anonymisation process has been written to write the input and output files being anonymised to the log along with the original study and instance UIDs.  This allows a lookup to compare input and output files.  This logging may need review if there is a requirement to record specific tag mappings.  A better way for this though may be to keep a persistent record of tag mappings in a database to allow de-anonymisation.  UID mappings would be most useful to provide persistence of the mappings of data is anonymised in parts.  This would ensure references between files would be maintained.




