--Create tables.
CREATE TABLE Closet (
  No VARCHAR2(10),
  ClosetSize VARCHAR2(10) NOT NULL,
  PRIMARY KEY (No)
);
/
CREATE TABLE Customer (
  Phone VARCHAR2(10),
  Fname VARCHAR2(20) NOT NULL,
  Minit CHAR,
  Lname VARCHAR2(20) NOT NULL,
  Gender CHAR NOT NULL,
  Age NUMBER NOT NULL,
  Height FLOAT,
  Weight FLOAT,
  ClosetNo VARCHAR2(10),
  PRIMARY KEY (Phone),
  FOREIGN KEY (ClosetNo) REFERENCES CLOSET(No) ON DELETE CASCADE
);
/
CREATE TABLE MemberCard (
  CardNo VARCHAR2(10),
  Phone VARCHAR2(10),
  ValidationDate DATE NOT NULL,
  ExpirationDate DATE NOT NULL,
  PRIMARY KEY (CardNo, Phone),
  FOREIGN KEY (Phone) REFERENCES Customer(Phone) ON DELETE CASCADE
);
/
CREATE TABLE FitnessProgram (
  ProgramID NUMBER,
  Name VARCHAR2(30) NOT NULL,
  Description VARCHAR2(100) NOT NULL,
  Price FLOAT NOT NULL,
  TotalEnrollment NUMBER DEFAULT 0,
  PRIMARY KEY (ProgramID)
);
/
CREATE TABLE AerobicInformation (
  AerobicType VARCHAR2(20),
  AerobicInformation VARCHAR2(300) NOT NULL,
  PRIMARY KEY (AerobicType)
);
/
CREATE TABLE AerobicProgram (
  ProgramID   NUMBER,
  Intensity   VARCHAR2(15) NOT NULL,
  MinimalTime NUMBER NOT NULL,
  AerobicType VARCHAR2(20),
  PRIMARY KEY (ProgramID),
  FOREIGN KEY (ProgramID) REFERENCES FitnessProgram (ProgramID) ON DELETE CASCADE ,
  FOREIGN KEY (AerobicType) REFERENCES AerobicInformation(AerobicType) ON DELETE CASCADE ,
  CONSTRAINT maximumMinimalTime CHECK (MinimalTime < 90)
);
/
CREATE TABLE StrengthProgram (
  ProgramID NUMBER,
  StrengthLevel VARCHAR2(15) NOT NULL,
  PRIMARY KEY (ProgramID),
  FOREIGN KEY (ProgramID) REFERENCES FitnessProgram (ProgramID) ON DELETE CASCADE
);
/
CREATE TABLE HIITProgram (
  ProgramID NUMBER,
  Intensity VARCHAR2(15) NOT NULL,
  Duration NUMBER NOT NULL,
  PRIMARY KEY (ProgramID),
  FOREIGN KEY (ProgramID) REFERENCES FitnessProgram (ProgramID) ON DELETE CASCADE ,
  CONSTRAINT maximumDuration CHECK (Duration < 90)
);
/
CREATE TABLE Staff (
  SSN CHAR(9),
  Fname VARCHAR2(20) NOT NULL,
  Minit CHAR,
  Lname VARCHAR2(20) NOT NULL,
  Phone VARCHAR2(10) NOT NULL,
  Salary NUMBER NOT NULL,
  Address VARCHAR2(60) NOT NULL,
  PRIMARY KEY (SSN),
  CONSTRAINT minSalary CHECK (Salary > 5000)
);
/
CREATE TABLE Maintainer (
  SSN CHAR(9),
  PRIMARY KEY (SSN),
  FOREIGN KEY (SSN) REFERENCES Staff(SSN) ON DELETE CASCADE
);
/
CREATE TABLE Trainer (
  SSN CHAR(9),
  LicenseID CHAR(10),
  TrainerID NUMBER UNIQUE NOT NULL,
  No_of_years NUMBER NOT NULL,
  No_of_clients NUMBER DEFAULT 0,
  PRIMARY KEY (SSN),
  FOREIGN KEY (SSN) REFERENCES Staff(SSN) ON DELETE CASCADE
);
/
CREATE TABLE Vendor (
  Name VARCHAR2(20) NOT NULL,
  VendorName VARCHAR2(20),
  Model VARCHAR2(20),
  Price FLOAT NOT NULL,
  PRIMARY KEY (VendorName, Model)
);
CREATE TABLE Equipment (
  EquipID VARCHAR(10),
  Vendor VARCHAR2(20),
  Model VARCHAR2(20),
  Status VARCHAR2(20) NOT NULL,
  ResponsibleMaintainer CHAR(9),
  EquipAge NUMBER DEFAULT 0,
  PRIMARY KEY (EquipID),
  FOREIGN KEY (ResponsibleMaintainer) REFERENCES Maintainer(SSN) ON DELETE CASCADE,
  FOREIGN KEY (Vendor, Model) REFERENCES Vendor(VendorName, Model) ON DELETE CASCADE
);
/
CREATE TABLE Contract (
  ContractNo CHAR(10),
  ProgramNo NUMBER,
  CustomerPhone VARCHAR2(10),
  TrainerID NUMBER NOT NULL,
  SignUpDate DATE NOT NULL,
  Price FLOAT NOT NULL,
  Discount Float,
  PRIMARY KEY (ContractNo, ProgramNo, CustomerPhone),
  FOREIGN KEY (ProgramNo) REFERENCES FitnessProgram(ProgramID) ON DELETE CASCADE,
  FOREIGN KEY (CustomerPhone) REFERENCES Customer(Phone) ON DELETE CASCADE,
  FOREIGN KEY (TrainerID) REFERENCES Trainer(TrainerID) ON DELETE CASCADE
);
/
CREATE TABLE TrainerEligibility (
  ProgramID NUMBER,
  TrainerID NUMBER,
  PRIMARY KEY (ProgramID, TrainerID),
  FOREIGN KEY (ProgramID) REFERENCES FitnessProgram(ProgramID) ON DELETE CASCADE ,
  FOREIGN KEY (TrainerID) REFERENCES Trainer(TrainerID) ON DELETE CASCADE
);
/
CREATE TABLE ExerciseSession (
  SessionID CHAR(15),
  ProgramID NUMBER,
  CustomerPhone VARCHAR2(10),
  Duration NUMBER NOT NULL,
  EstCalories FLOAT NOT NULL,
  AvgHR NUMBER NOT NULL,
  MaxHR NUMBER NOT NULL,
  SDATE DATE NOT NULL,
  PRIMARY KEY (SessionID, ProgramID, CustomerPhone),
  FOREIGN KEY (ProgramID) REFERENCES FitnessProgram(ProgramID) ON DELETE CASCADE ,
  FOREIGN KEY (CustomerPhone) REFERENCES Customer(Phone) ON DELETE CASCADE
);
