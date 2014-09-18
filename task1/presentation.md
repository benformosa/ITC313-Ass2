---
title: ITC313-Assignment 1 Task 1
author: Ben Formosa 11429074
header-includes:
    - \usepackage{fancyhdr}
    - \pagestyle{fancy}
    - \fancyfoot[L]{Ben Formosa 11429074}
    - \fancyfoot[C]{\thepage}
---

# Overview

GradingTool is a class which provides several functions for managing a database of grades.
Grades are stored with one row per student, with one table per subject.

Actions which GradingTool supports:

* List all Subjects
* List all Students in a subject
* List

# Pre-requisites

* MySQL installed

# How to run

Extract

```bash
unzip 11429074_ass2.zip
cd ITC313-Ass2-master/task1
```

Create database

```bash
mysql -u root -p < createDatabase.sql
```

Compile and run using ant

```bash
ant
```

# Expected output
